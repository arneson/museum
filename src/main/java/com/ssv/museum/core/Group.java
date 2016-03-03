/**
 * Class representing an option (possible answer) for a question
 * @author simonarneson
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
public class Group extends AbstractEntity {
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    private List<Membership> members = new ArrayList<Membership>();

    public Group() {
    }
    public Group(String name) {
        this.name = name;
    }
    public Group(String name, String description) {
        this(name);
        this.description = description;
    }
    public Group(String name, List<Membership> members) {
        this(name);
        this.members = members;
    }
    public Group(String name,String description, List<Membership> members) {
        this(name,description);
        this.members = members;
    }

    @Override
    public String toString() {
        return "Group{" + "name=" + name+'}';
    }
}
