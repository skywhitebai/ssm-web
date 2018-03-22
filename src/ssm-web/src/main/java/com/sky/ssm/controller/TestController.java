package com.sky.ssm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("test")
public class TestController {

    @Value("123")
    String url;
    @RequestMapping("getUrl")
    @ResponseBody
    public String getUrl(){
        return url;
    }
}
