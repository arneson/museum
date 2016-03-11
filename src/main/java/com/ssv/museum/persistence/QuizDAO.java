/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.persistence;

import com.ssv.museum.core.Quiz;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    public HashMap<Long, Integer> getAnswerStatisics(Long id) {
       HashMap<Long, Integer> stats = new HashMap<>();
       Query query = em.createNativeQuery("SELECT ao.ID,COUNT(*) FROM ANSWER as a INNER JOIN ANSWEROPTION as ao ON ao.ID =  a.ANSWEROPTION_ID INNER JOIN QUESTION as q ON q.ID = ao.QUESTION_ID AND q.QUIZ_ID=? GROUP BY ao.ID");
       query.setParameter(1, id);
       List<Object[]> list = query.getResultList();
       for (Object[] obj : list) {
            Long answerOptionId = (long)(obj[0]);
            Integer count = (int)(obj[1]);
            stats.put(answerOptionId, count);
       }
       return stats;
    }
    
}

