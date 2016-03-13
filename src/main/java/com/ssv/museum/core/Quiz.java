/**
 * Class representing an option (possible answer) for a question
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Quiz extends AbstractEntity {
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private int points;
    @Setter
    @Getter
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date created;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="MUSEUM_ID")
    private Museum museum;
    @Setter
    @Getter
    @OneToMany(mappedBy="quiz", fetch=FetchType.LAZY)
    private List<Question> questions = new ArrayList<>();
            
    public Quiz() {
    }
    public Quiz(String name, int points, Date created, String description) {
        this.name = name;
        this.points = points;
        this.created = created;
        this.description = description;
    }
    public Quiz(String name, int points, Date created, String description, List<Question> questions) {
        this(name,points,created,description);
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "Quiz{" + "name=" + name+'}';
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }
}
