/**
 * @author simonarneson
 */
package com.ssv.museum.core;

import com.ssv.museum.persistence.AbstractEntity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
    private MemberRole memberRole;
    @ManyToOne()
    @JoinColumn(name="team_id")
    private Team team;
    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name="visitor_id")
    private Visitor visitor;
    @Setter
    @Getter
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateJoined;

    public Membership() {
    }

    public Membership(Visitor user, MemberRole memberRole) {
        this.visitor = user;
        this.memberRole = memberRole;
        this.dateJoined = new Date();
    }

    @Override
    public String toString() {
        return "Membership{" + "visitor=" + visitor.toString()+" role="+memberRole.toString()+'}';
    }
}
