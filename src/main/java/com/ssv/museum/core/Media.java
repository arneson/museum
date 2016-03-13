/**
 * Class representing a media, ex. image or video
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Media extends AbstractEntity {
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="QUESTION_ID")
    private Question question;
    @Setter
    @Getter
    private String type;
    @Setter
    @Getter
    private String url;

    public Media() {
    }

    public Media(String type, String url) {
        this.type = type;
        this.url = url;
    }

    @Override
    public String toString() {
        return "Media{" + "type=" + type+" url=" + url+'}';
    }
}
