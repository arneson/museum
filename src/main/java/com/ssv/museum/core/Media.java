/**
 * Class representing an media, ex. image or video
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
public class Media extends AbstractEntity {
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
