/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.ssv.museum.persistence.AnswerOptionDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author simonarneson
 */

@Named
@ApplicationScoped
public class TestMuseumWrapper {
    //@Inject will not work!!! Because of interfaces+EBJs or similar
    // If interfaces removed it will work
    @Inject
    private AnswerOptionDAO answerOptionDAO;

    public TestMuseumWrapper() {
        Logger.getAnonymousLogger().log(Level.INFO, "TestMuseum alive");
    }
   
    public AnswerOptionDAO getAnswerOptionDAO(){
        return answerOptionDAO;
    }
}
