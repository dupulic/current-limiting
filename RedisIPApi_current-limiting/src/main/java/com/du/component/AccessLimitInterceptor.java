package com.du.component;

import com.du.entity.RdRequestCount;
import com.du.service.AccessLimit;
import com.du.service.RdRequestCountService;
import com.fasterxml.jackson.databind.cfg.HandlerInstantiator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Resource
    private RedisUtil redisUtil;
    @Autowired
    private RdRequestCountService requestCountService;
    //用于请求成功保存的ip key
    private String afterKey;
    // ip的key缓存标记
    private final String d_b = "D_B";
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //用户访问成功---增加一次请求成功记录
        RdRequestCount countOk = new RdRequestCount();
        if (afterKey!=null){
            countOk.setIp(afterKey);
            countOk.setSuccess_count(1);
            requestCountService.increaseCount(countOk);
        }
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取限流注解类的参数
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null){
               return false;
            }
            int seconds = accessLimit.seconds(); //规定时间
            int maxCount = accessLimit.maxCount();  //最大访问累加次数
            boolean needLogin = accessLimit.needLogin();

            if (needLogin){
                //判断是否登录
            }

            //获取ip地址
            String ip = request.getRemoteAddr();
            String key = ip+":"+request.getServletPath();
            String s = redisUtil.get(key + d_b);
            if (s!=null&&s.equals("1")){
                //黑名单用户
                response.getWriter().write("请勿恶意刷单、注水！已拉入黑名单！");
                return false;
            }else {
                //查询数据库是否有此ip
                RdRequestCount byIp = requestCountService.selectByIp(key);
                if (byIp == null){ //没有则保存此ip
                    //数据库缓存成功则redis同步key
                    if (requestCountService.initializeIp(key)){
                        //d_b标记为一个缓存key
                        String keydb = key+d_b;
                        //缓存key是否 拉入黑名单
                        if ( byIp!=null  && byIp.getIs_close()!=null){
                            redisUtil.set(keydb,byIp.getIs_close());
                            redisUtil.expire(key,1,TimeUnit.DAYS);
                        }
                    }else {
                        return false;
                    }
                }
            }
            String valus = redisUtil.get(key);
            Integer count = 0;
            afterKey = key;   //保存key留作请求成功后增加一次记录用
            if (valus!=null){
                //获取访问次数
                count = Integer.valueOf(valus);
            }
            //情景一：限定时间内第一次访问
            if (count == 0 || count == -1){
                //初始化并设置过期时间
                redisUtil.set(key,"1");
                redisUtil.expire(key,seconds,TimeUnit.SECONDS);
                return true;
            }

            //情景二：访问次数小于最大次数，则增加一次访问记录
            if (count<maxCount){
                redisUtil.incrBy(key,1);
                return true;
            }
            //情景三：访问次数超出限制时间内最大次数！
            if (count>=maxCount){
                RdRequestCount countNo = new RdRequestCount();
                countNo.setIp(key);
                countNo.setFailure_count(1);
                //增加一次请求失败记录
                requestCountService.increaseCount(countNo);
                //查询请求失败记录
                RdRequestCount failure = requestCountService.selectByIp(key);
                if (failure.getFailure_count()==10){
                    //请求失败记录等于10则拉入黑名单
                    countNo.setIs_close("1");
                    requestCountService.increaseCount(countNo);
                    redisUtil.set(key+d_b,"1");
                }
                response.getWriter().write("请求过于频繁，请稍后！");
                return false;
            }
        }
        return true;
    }
}
