/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.persistence;

import com.ssv.museum.core.Quiz;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author simonarneson
 */
@Stateless
public class QuizDAO extends AbstractDAO<Quiz, Long> {

    @PersistenceContext
    private EntityManager em;
    
    public QuizDAO(){
        super(Quiz.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Quiz> getQuizzesByUser(Long id) {
        return null;
    }
    
}

