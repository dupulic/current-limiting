package com.du.controller;

import com.du.service.AccessLimit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("access")

public class AccessController {

    //3秒内同一个用户访问10此则视为给出提示
    @ResponseBody
    @GetMapping("accessLimit")
    @AccessLimit(seconds = 3,maxCount = 10)
    public String accessLimit(){
        System.out.println("进入了");
        return "it is ok";
    }
}
