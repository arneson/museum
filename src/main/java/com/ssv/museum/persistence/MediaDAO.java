/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.persistence;

import com.ssv.museum.core.Media;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simonarneson
 */
@Stateless
public class MediaDAO extends AbstractDAO<Media, Long> {

    @PersistenceContext
    private EntityManager em;
    
    public MediaDAO(){
        super(Media.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

