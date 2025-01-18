package com.mysite.sbb;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @GetMapping(path = "/hello")
    @ResponseBody
    public String helloWorld() {
        return "Hello, mw!";
    }
}
