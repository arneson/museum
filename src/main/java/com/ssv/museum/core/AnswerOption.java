/**
 * Class representing an option (possible answer) for a question
 * @author larssonvictor
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class AnswerOption extends AbstractEntity {
    @Setter
    @Getter
    private String text;

    public AnswerOption() {
    }

    public AnswerOption(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Option{" + "text=" + text+'}';
    }
}
