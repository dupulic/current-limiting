package com.du.controller;

import com.du.component.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    private RedisUtil redisutil;

    @GetMapping("/hello")
    @ResponseBody
    public String t(){
        redisutil.set("sdf","sdfsdf");
        return "hello";
    }

}
