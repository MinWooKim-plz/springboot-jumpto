package com.mysite.sbb;


import lombok.Getter;
import lombok.RequiredArgsConstructor; // 생성자가 롬복에 의해 자동으로 생성됨
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class HelloLombok {
    private String hello;
    private int lombok;
}
