/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.persistence;

import com.ssv.museum.core.Visitor;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Data Access Object for Visitor entity
 * Also contains the method findByFbId which is specific
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
        List<Visitor> visitors  = em.createQuery("SELECT v FROM Visitor v WHERE v.fbId = :facebookId", Visitor.class)
                .setParameter("facebookId", facebookId).getResultList();
        if(visitors.isEmpty())
            return null;
        else
            return visitors.get(0);
    }
    
}

