package com.mysite.sbb.answer;


import java.time.LocalDateTime;
import java.util.Optional;
import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer create(Question question, String content, SiteUser author){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(author);
        answerRepository.save(answer);
        return answer;
    }

    public Answer getAnswer(Integer id){
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) return answer.get();
        else throw new DataNotFoundException("No answer found for id: " + id);
    }

    public void modify(Answer answer, String content){
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public void delete(Answer answer){
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser){
        answer.getVoter().add(siteUser);
        answerRepository.save(answer);
    }
}
