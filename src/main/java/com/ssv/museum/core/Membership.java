/**
 * Class representing an option (possible answer) for a question
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

@Entity
@XmlRootElement
public class Membership extends AbstractEntity {
    @Setter
    @Getter
    private Role role;
    @Setter
    @Getter
    @ManyToOne
    private Visitor user;
    @Setter
    @Getter
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJoined;

    public Membership() {
    }

    public Membership(Visitor user, Role role) {
        this.user = user;
        this.role = role;
        this.dateJoined = new Date();
    }

    @Override
    public String toString() {
        return "Membership{" + "user=" + user.toString()+" role="+role.toString()+'}';
    }
}
