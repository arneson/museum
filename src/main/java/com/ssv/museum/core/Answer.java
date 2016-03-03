/**
 * Class representing an option (possible answer) for a question
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Answer extends AbstractEntity {
    @Setter
    @Getter
    private Option option;
    @Setter
    @Getter
    private boolean result;
    @Setter
    @Getter
    private Date date;
    @Setter
    @Getter
    private Position position;

    public Answer() {
    }

    public Answer(Option option, boolean result, Date date, Position position) {
        this.option   = option;
        this.result   = result;
        this.date     = date;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Answer{" + "Answer=" + option.toString()+'}';
    }
}
