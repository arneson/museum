/**
 * Class representing an option (possible answer) for a question
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.List;
import javax.persistence.Entity;
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
    private List<Option> options;
    @Setter
    @Getter
    private List<Media> media;
    @Setter
    @Getter
    private Option correctOption;

    public Question() {
    }

    public Question(String question,int points, List<Option> options, Option correctOption) {
        this.question = question;
        this.points = points;
        this.options = options;
        this.correctOption = correctOption;
    }
    public Question(String question, List<Option> options, Option correctOption) {
        this(question, 1,options,correctOption);
    }
    public Question(String question,int points, List<Option> options, Option correctOption, List<Media> media) {
        this(question, points,options,correctOption);
        this.media = media;
    }

    @Override
    public String toString() {
        return "Question{" + "correctAnswer=" + correctOption.toString()+'}';
    }
}
