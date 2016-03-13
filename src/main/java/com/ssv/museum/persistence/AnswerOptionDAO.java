
package com.ssv.museum.persistence;

import com.ssv.museum.core.AnswerOption;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Data Access Object for AnswerOption entity
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

