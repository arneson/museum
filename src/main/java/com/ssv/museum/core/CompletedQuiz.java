
package com.ssv.museum.core;

/**
 * Class representing a CompletedQuiz, which creaed when i Visitor has answered
 * all questions in a quiz
 * @author larssonvictor
 */
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
public class CompletedQuiz extends AbstractEntity{
    @Setter
    @Getter
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCompleted;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="VISITOR_ID")
    private Visitor visitor;
    @Setter
    @Getter
    private Double percantage;
    @Setter
    @Getter
    @OneToOne
    private Quiz quiz;

    public CompletedQuiz() {
    }

    public CompletedQuiz(Date dateCompleted, Double percantage) {
        this.dateCompleted = dateCompleted;
        this.percantage = percantage;
    }

    @Override
    public String toString() {
        return "Completed Quiz{" + "Quiz=" + quiz.getName()+ " Percantage=" + percantage+ " Date completed=" + dateCompleted +'}';
    }
}
