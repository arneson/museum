/**
 * @author larssonvictor
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    private String description;
    @Setter
    @Getter
    @OneToOne
    private Address address;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String image;
    @Setter
    @Getter
    @OneToMany
    private List<Quiz> quiz = new ArrayList<>();

    public Museum() {
    }

    public Museum(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public Museum(String username, String password, String email, String name, Address address, String image) {
        this(username, password,email,name);
        this.address = address;
        this.image = image;
    }

    public Museum(String username, String password, String email,String name) {
        this(username,password,email);
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Museum{" + "Museum=" + name+'}';
    }
}
