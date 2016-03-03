/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Base class for all entities, defines id, equal

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
    
    // Must have default ctor (make it package private)
    protected AbstractEntity() {
    }

    protected AbstractEntity(Long id) {
        this.id = id;
    }

}
