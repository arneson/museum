/**
 * Class representing an option (possible answer) for a question
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Question extends AbstractEntity {
    @Setter
    @Getter
    private String question;
    @Setter
    @Getter
    private int points;
    @Setter
    @Getter
    @OneToMany
    private List<AnswerOption> options = new ArrayList<>();
    @Setter
    @Getter
    @OneToMany
    private List<Media> media =  new ArrayList<>();
    @Setter
    @Getter
    @OneToOne
    private AnswerOption correctOption;

    public Question() {
    }

    public Question(String question,int points, List<AnswerOption> options, AnswerOption correctOption) {
        this.question = question;
        this.points = points;
        this.options = options;
        this.correctOption = correctOption;
    }
    public Question(String question, List<AnswerOption> options, AnswerOption correctOption) {
        this(question, 1,options,correctOption);
    }
    public Question(String question,int points, List<AnswerOption> options, AnswerOption correctOption, List<Media> media) {
        this(question, points,options,correctOption);
        this.media = media;
    }
    
    public boolean checkAnswer(AnswerOption answer){
        return correctOption == answer;
    }

    @Override
    public String toString() {
        return "Question{" + "correctAnswer=" + correctOption.toString()+'}';
    }
}
