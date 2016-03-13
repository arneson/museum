/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.service;

import com.ssv.museum.core.Answer;
import com.ssv.museum.core.AnswerOption;
import com.ssv.museum.core.Position;
import com.ssv.museum.core.Question;
import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.AnswerOptionDAO;
import com.ssv.museum.persistence.QuestionDAO;
import com.ssv.museum.persistence.VisitorDAO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
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
import com.google.gson.Gson;
import com.ssv.museum.core.Museum;
import javax.ws.rs.HeaderParam;

/**
 *
 * @author larssonvictor
 */
@Path("question")
public class QuestionREST extends AuthedREST {
    
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
            Gson gson = new Gson();
            return Response.ok(gson.toJson(question)).build(); // 200
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
            Gson gson = new Gson();
            return Response.ok(gson.toJson(questions)).build(); // 200
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
            Gson gson = new Gson();
            return Response.ok(gson.toJson(question)).build();
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
                          @HeaderParam("password") String password,
                          JsonObject obj,@Context Request request) {
        Question q = questionDAO.find(id);
        Museum m = q.getQuiz().getMuseum();
        if (q != null && m!=null && authMuseum(password,m.getUsername()) ) {
            String question = obj.getString("question");
            int points = obj.getInt("points");
            JsonArray opts = obj.getJsonArray("options");
            AnswerOption correctOption = new AnswerOption(obj.getJsonObject("correct").getString("text"));
            correctOption.setQuestion(q);
            List<AnswerOption> options = new ArrayList<>();
            for(int o = 0;o<opts.size();o++){
                AnswerOption ao = new AnswerOption(opts.getJsonObject(o).getString("text"));
                ao.setQuestion(q);
                options.add(ao);
            }
            
            q.setQuestion(question);
            q.setPoints(points);
            q.setOptions(options);
            q.setCorrectOption(correctOption);
            questionDAO.update(q);
            Gson gson = new Gson();
            return Response.ok(gson.toJson(q)).build();
        } else {
            return Response.noContent().build();  // 204
        }
   }
    
    //answer
    @POST
    @Path("{id: \\d+}/answer")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response answer(JsonObject obj, 
                         @PathParam("id") Long id, 
                         @Context Request request){
        Question question = questionDAO.find(id);
        
        if (question != null) {
            
            long answer_id  = obj.getJsonNumber("answer_id").longValue();
            long visitor_id = obj.getJsonNumber("visitor_id").longValue();
            Position position = new Position();
            if(obj.containsKey("longitude") && obj.containsKey("latitude")){
                position.setLongitude(obj.getJsonNumber("longitude").doubleValue());
                position.setLatitude(obj.getJsonNumber("latitude").doubleValue());
            }
            Visitor visitor = visitorDAO.find(visitor_id);
            AnswerOption answerOption = answerOptionDAO.find(answer_id);
            boolean result = question.checkAnswer(answerOption);
            Answer a = new Answer(answerOption, result, new Date(), position);
            a.setVisitor(visitor);
            visitor.addAnswer(a);
            if(result){
                visitor.addPoints(question.getPoints());
            }
            visitorDAO.update(visitor); 
            Gson gson = new Gson();
            return Response.ok().build();
        } else {
            return Response.noContent().build();  // 204
        }
    }
    //QR
    @GET
    @Path("{id: \\d+}/qr")
    @Produces({MediaType.APPLICATION_JSON})
    public Response qr(@PathParam("id") Long id,
            @Context Request request) {
        Question question = questionDAO.find(id);
        if (question != null) {
            BufferedImage bi = question.getQR();
            if(bi!=null){
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(bi, "png", baos);
                    byte[] imageData = baos.toByteArray();
                    return Response.ok(imageData).build(); // 200
                } catch (IOException ex) {
                    Logger.getLogger(QuestionREST.class.getName()).log(Level.SEVERE, null, ex);
                    return Response.noContent().build();
                }
            }else{
                return Response.noContent().build();
            }
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
