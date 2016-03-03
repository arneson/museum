/**
 * Class representing an option (possible answer) for a question
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Option extends AbstractEntity {
    @Setter
    @Getter
    private String text;

    public Option() {
    }

    public Option(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Option{" + "text=" + text+'}';
    }
}
