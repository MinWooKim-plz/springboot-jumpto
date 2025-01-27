package com.mysite.sbb.answer;


import java.security.Principal;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Controller
@RequestMapping("/answer")
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id, @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal) {
        Question question = questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        Answer answer = answerService.create(question, answerForm.getContent(), userService.getUser(principal.getName()));
        return String.format("redirect:/question/detail/%s#answer_%s", id, answer.getId());
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyAnswer(@PathVariable("id")Integer id, AnswerForm answerForm, Principal principal) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyAnswer(@PathVariable("id")Integer id, AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal) {
        Answer answer = answerService.getAnswer(id);
        if (bindingResult.hasErrors()) return "answer_form";
        if (!answer.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
        answerService.modify(answer, answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteAnswer(@PathVariable("id")Integer id, Principal principal) {
        Answer answer = answerService.getAnswer(id);
        if (!answer.getAuthor().getUsername().equals(principal.getName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        answerService.delete(answer);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }


    @GetMapping("/vote/{id}")
    @PreAuthorize("isAuthenticated()")
    public String voteAnswer(@PathVariable("id")Integer id, Principal principal) {
        Answer answer = answerService.getAnswer(id);
        SiteUser siteUser = userService.getUser(principal.getName());
        answerService.vote(answer, siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", answer.getQuestion().getId(), answer.getId());
    }
}
