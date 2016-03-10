/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.core;

/**
 *
 * @author larssonvictor
 */
import com.ssv.museum.persistence.AbstractEntity;
import java.util.Date;
import javax.persistence.Entity;
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
    @ManyToOne()
    @JoinColumn(name="visitor_id")
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
