/**
 * @author larssonvictor
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Visitor extends AbstractEntity {
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String fbId;
    @Setter
    @Getter
    private String city;
    @Setter
    @Getter
    private String mail;
    @Setter
    @Getter
    private String imageUrl;
    @Setter
    @Getter
    private int totalPoints;
    @Setter
    @Getter
    @OneToMany
    private List<Answer> answers = new ArrayList<>();
    @Setter
    @Getter
    @OneToMany
    private List<CompletedQuiz> completedQuizzes = new ArrayList<>();

    public Visitor() {
    }

    public Visitor(String username, String fbId) {
        this.username = username;
        this.fbId = fbId;
        this.totalPoints = 0;
    }

    public Visitor(String username, String fbId, String city, String mail, String imageUrl, List<Answer> answers) {
        this(username, fbId);
        this.city = city;
        this.mail = mail;
        this.imageUrl = imageUrl;
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "User{" + "User=" + username +'}';
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void addPoints(int points) {
       totalPoints += points;
    }
}
