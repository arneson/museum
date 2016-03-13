
package com.ssv.museum.persistence;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Base class for all entities, defines id

 * @author simonarneson
 */
@MappedSuperclass
@EqualsAndHashCode( of="id")
public abstract class AbstractEntity implements Serializable {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //@Version
    //private Long version;
    
    protected AbstractEntity() {
    }

    protected AbstractEntity(Long id) {
        this.id = id;
    }

}
