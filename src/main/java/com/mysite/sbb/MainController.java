package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @ResponseBody // -> index'라는 문자열을 리턴 / 생략 시 index라는 이름의 템플릿 파일을 찾음.
    @GetMapping("/sbb")
    public String index() {
        return "index";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/question/list";
    }
}
