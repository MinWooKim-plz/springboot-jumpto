package com.mysite.sbb;

import com.mysite.sbb.question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionService questionService;


    @Test
    void testJpa() {
        for (int i = 1; i < 304; i++)
        {
            String subject = String.format("테스트 데이터:제목[%03d]", i);
            String content = String.format("[%03d]", i);
            questionService.create(subject, content);
        }

    }


}

