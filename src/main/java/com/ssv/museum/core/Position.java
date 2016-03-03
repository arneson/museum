/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.core;

/**
 *
 * @author simonarneson
 */
import com.ssv.museum.persistence.AbstractEntity;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Position extends AbstractEntity{
    @Setter
    @Getter
    private Double longitude;
    @Setter
    @Getter
    private Double latitude;

    public Position() {
    }

    public Position(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Position{" + "long=" + longitude+ "lat=" + latitude+'}';
    }
}
