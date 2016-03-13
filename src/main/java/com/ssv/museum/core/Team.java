/**
 * Class representing an option (possible answer) for a question
 * @author simonarneson
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
public class Team extends AbstractEntity {
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String description;
    @Setter
    @Getter
    @OneToMany(mappedBy="team")
    private List<Membership> members = new ArrayList<>();

    public Team() {
    }
    public Team(String name) {
        this.name = name;
    }
    public Team(String name, String description) {
        this(name);
        this.description = description;
    }
    public Team(String name, List<Membership> members) {
        this(name);
        this.members = members;
    }
    public Team(String name,String description, List<Membership> members) {
        this(name,description);
        this.members = members;
    }
    
    public void addMember(Membership newMember){
        members.add(newMember);
    }

    @Override
    public String toString() {
        return "Team{" + "name=" + name+'}';
    }
}
