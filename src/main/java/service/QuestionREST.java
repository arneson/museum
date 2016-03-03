/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.ssv.museum.core.Answer;
import com.ssv.museum.core.AnswerOption;
import com.ssv.museum.core.Position;
import com.ssv.museum.core.Question;
import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.AnswerOptionDAO;
import com.ssv.museum.persistence.QuestionDAO;
import com.ssv.museum.persistence.VisitorDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 *
 * @author larssonvictor
 */
@Path("question")
public class QuestionREST {
    
    //DAO
    QuestionDAO questionDAO = lookupQuestionDAOBean();
    VisitorDAO visitorDAO   = lookupVisitorDAOBean();
    AnswerOptionDAO answerOptionDAO = lookupAnswerOptionDAOBean();
    
    //find
    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id,
            @Context Request request) {
        Question question = questionDAO.find(id);
        if (question != null) {
            return Response.ok(question).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }
    //findAll
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll(@Context Request request) {
        List<Question> questions = questionDAO.findAll();
        GenericEntity<Collection<Question>> ge = new GenericEntity<Collection<Question>>(questions){};
        if (questions.size()>-1) {
            return Response.ok(ge).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }
    //delete
    @DELETE
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id,
                         @Context Request request) {
        //to be able to check if exists
        Question question = questionDAO.find(id);
        if (question != null) {
            questionDAO.delete(id);
            return Response.ok(question).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }

    //create
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response create(JsonObject obj,
                         @Context Request request) {
        
            String question = obj.getString("question");
            int points = obj.getInt("points");
            JsonArray opts = obj.getJsonArray("options");
            AnswerOption correctOption = new AnswerOption(obj.getJsonObject("correct").getString("text"));
            List<AnswerOption> options = new ArrayList<>();
            for(int o = 0;o<opts.size();o++){
                options.add(new AnswerOption(opts.getJsonObject(o).getString("text")));
            }
            
            Question newQuestion = new Question(question, points, options, correctOption);
            questionDAO.create(newQuestion);
        if (newQuestion != null) {
            return Response.ok(newQuestion).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }
    
    //update
   @PUT
   @Path("{id: \\d+}")
   @Produces({MediaType.APPLICATION_JSON})
   @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
   public Response update(@PathParam("id") Long id,
                          JsonObject obj,@Context Request request) {
        Question q = questionDAO.find(id);

        if (q != null) {
            String question = obj.getString("question");
            int points = obj.getInt("points");
            JsonArray opts = obj.getJsonArray("options");
            AnswerOption correctOption = new AnswerOption(obj.getJsonObject("correct").getString("text"));
            List<AnswerOption> options = new ArrayList<>();
            for(int o = 0;o<opts.size();o++){
                options.add(new AnswerOption(opts.getJsonObject(o).getString("text")));
            }
            
            q.setQuestion(question);
            q.setPoints(points);
            q.setOptions(options);
            q.setCorrectOption(correctOption);
            questionDAO.update(q);
            return Response.ok(q).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
   }
    
    //answer
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response answer(JsonObject obj, 
                         @PathParam("id") Long id, 
                         @Context Request request){
        Question question = questionDAO.find(id);
        
        if (question != null) {
            
            long answer_id  = obj.getJsonNumber("answer_id").longValue();
            long visitor_id = obj.getJsonNumber("visitor_id").longValue();
            Visitor visitor = visitorDAO.find(visitor_id);
            AnswerOption answerOption = answerOptionDAO.find(answer_id);
            boolean result = question.checkAnswer(answerOption);
            
            visitor.addAnswer(new Answer(answerOption, result, new Date(), new Position()));
            if(result){
                visitor.addPoints(question.getPoints());
            }
            
            return Response.ok(question).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }

    private VisitorDAO lookupVisitorDAOBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (VisitorDAO) c.lookup("java:global/Museum/VisitorDAO!com.ssv.museum.persistence.VisitorDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private QuestionDAO lookupQuestionDAOBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (QuestionDAO) c.lookup("java:global/Museum/QuestionDAO!com.ssv.museum.persistence.QuestionDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private AnswerOptionDAO lookupAnswerOptionDAOBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AnswerOptionDAO) c.lookup("java:global/Museum/AnswerOptionDAO!com.ssv.museum.persistence.AnswerOptionDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
