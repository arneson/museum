/**
 * Class representing an Answer, the relation between an AnswerOption and a 
 * Visitor. It is created when a Visitor answers a Question
 * @author larssonvictor
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Answer extends AbstractEntity {
    @Setter
    @Getter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="ANSWEROPTION_ID")
    private AnswerOption answerOption;
    @Setter
    @Getter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="VISITOR_ID")
    private Visitor visitor;
    @Setter
    @Getter
    private boolean wasCorrect;
    @Setter
    @Getter
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateAnswered;
    @Setter
    @Getter
    @OneToOne
    private Position position;

    public Answer() {
    }

    public Answer(AnswerOption option, boolean wasCorrect, Date dateAnswered, Position position) {
        this.answerOption = option;
        this.wasCorrect   = wasCorrect;
        this.dateAnswered = dateAnswered;
        this.position     = position;
    }

    @Override
    public String toString() {
        return "Answer{" + "Answer=" + answerOption.toString()+ "Correct="+ wasCorrect+'}';
    }
}
