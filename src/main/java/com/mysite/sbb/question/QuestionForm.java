package com.mysite.sbb.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    @NotBlank(message="제목 적어도 빈 칸 입력")
    @Size(max=200)
    private String subject;

    @NotEmpty(message="내용 입력")
    private String content;
}
