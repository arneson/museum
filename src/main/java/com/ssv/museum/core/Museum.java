/**
 * @author larssonvictor
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Museum extends AbstractEntity {
    @Setter
    @Getter
    private String username;
    @Setter
    @Getter
    private String password;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private Address address;
    @Setter
    @Getter
    private String image;
    @Setter
    @Getter
    private List<Quiz> answers = new ArrayList<Quiz>();

    public Museum() {
    }

    public Museum(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Museum(String username, String password, String name, Address address, String image) {
        this(username, password);
        this.name = name;
        this.address = address;
        this.image = image;
    }
    
    @Override
    public String toString() {
        return "Museum{" + "Museum=" + name+'}';
    }
}
