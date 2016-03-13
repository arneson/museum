/**
 * Class representing an Address
 * @author larssonvictor
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Address extends AbstractEntity {
    @Setter
    @Getter
    private String street;
    @Setter
    @Getter
    private String city;
    @Setter
    @Getter
    private String country;
    @Setter
    @Getter
    @OneToOne
    private Position position;

    public Address() {
    }
    
    public Address(String street, String city, String country) {
        this.street = street;
        this.city = city;
        this.country = country;
    }
    
    public Address(String street, String city, String country, Position position) {
        this(street,city,country);
        this.position = position;
    }

    @Override
    public String toString() {
        return "Address{" + "Street=" + street+" City="+ city + " Country="+ country+'}';
    }
}
