/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.persistence;

import com.ssv.museum.core.AnswerOption;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simonarneson
 */
@Stateless
public class AnswerOptionDAO extends AbstractDAO<AnswerOption, Long> {

    @PersistenceContext
    private EntityManager em;
    
    public AnswerOptionDAO(){
        super(AnswerOption.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

