/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.persistence;

import com.ssv.museum.core.Question;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simonarneson
 */
@Stateless
public class QuestionDAO extends AbstractDAO<Question, Long> {

    @PersistenceContext
    private EntityManager em;
    
    public QuestionDAO(){
        super(Question.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

