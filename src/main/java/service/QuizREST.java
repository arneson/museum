/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.ssv.museum.core.AnswerOption;
import com.ssv.museum.core.Question;
import com.ssv.museum.core.Quiz;
import com.ssv.museum.persistence.QuizDAO;
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
 * @author simonarneson
 */
@Path("quiz")
public class QuizREST {

    QuizDAO quizDAO = lookupQuizDAOBean();
    //find
    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id,
            @Context Request request) {
        Quiz quiz = quizDAO.find(id);
        if (quiz != null) {
            return Response.ok(quiz).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }
    //findAll
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll(@Context Request request) {
        List<Quiz> quizzes = quizDAO.findAll();
        GenericEntity<Collection<Quiz>> ge = new GenericEntity<Collection<Quiz>>(quizzes){};
        if (quizzes.size()>-1) {
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
        Quiz quiz = quizDAO.find(id);
        if (quiz != null) {
            quizDAO.delete(id);
            return Response.ok(quiz).build(); // 200
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
        String name = obj.getString("name");
        int points = obj.getJsonNumber("points").intValue();
        String description = obj.getString("description");
        Quiz newQuiz = new Quiz(name, points, new Date(), description);
        quizDAO.create(newQuiz);
        if (newQuiz != null) {
            return Response.ok(newQuiz).build(); // 200
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
       String name = obj.getString("name");
       int points = obj.getJsonNumber("points").intValue();
       String description = obj.getString("description");
       Quiz q = quizDAO.find(id);
       if (q != null) {
           q.setName(name);
           q.setPoints(points);
           q.setDescription(description);
           quizDAO.update(q);
           return Response.ok(q).build(); // 200
       } else {
           return Response.noContent().build();  // 204
       }
   }
    
    //addQuestion
    @POST
    @Path("{id: \\d+}/questions")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addQuestion(JsonObject obj,
                        @PathParam("id") Long id,
                        @Context Request request) {
        int points = obj.getJsonNumber("points").intValue();
        String description = obj.getString("description");
        JsonArray opts = obj.getJsonArray("options");
        AnswerOption correctOption = new AnswerOption(obj.getJsonObject("correct").getString("text"));
        List<AnswerOption> options = new ArrayList<>();
        for(int o = 0;o<opts.size();o++){
            options.add(new AnswerOption(opts.getJsonObject(o).getString("text")));
        }
        Quiz quiz = quizDAO.find(id);
        quiz.addQuestion(new Question(description,points, options, correctOption));
        quizDAO.update(quiz);
        if (quiz != null) {
            return Response.ok(quiz).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }


    private QuizDAO lookupQuizDAOBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (QuizDAO) c.lookup("java:global/Museum/QuizDAO!com.ssv.museum.persistence.QuizDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
