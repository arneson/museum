/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.persistence;

import com.ssv.museum.core.Museum;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simonarneson
 */
@Stateless
public class MuseumDAO extends AbstractDAO<Museum, Long> {

    @PersistenceContext
    private EntityManager em;
    
    public MuseumDAO(){
        super(Museum.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Museum findByUsername(String username) {
         return em.createQuery("SELECT * FROM museum WHERE username = "+username, Museum.class)
                .getSingleResult();
    }
    
}

