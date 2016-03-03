/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.persistence;

import com.ssv.museum.core.Visitor;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author larssonvictor
 */
@Stateless
public class VisitorDAO extends AbstractDAO<Visitor, Long> {

    @PersistenceContext
    private EntityManager em;
    
    public VisitorDAO(){
        super(Visitor.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Visitor findByFbId(String facebookId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

