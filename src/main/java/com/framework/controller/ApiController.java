package com.framework.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 访问路径一定要加前缀 http://localhost:10003/system-mq/index
@RestController
public class ApiController {

    @RequestMapping("index")
    public String index(){
        return "This is API Server!";
    }

//    @RequestMapping("test")
//    public String test(){
//        return "This is Test!";
//    }
}
