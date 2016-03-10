/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.google.gson.Gson;
import com.ssv.museum.core.Address;
import com.ssv.museum.core.AnswerOption;
import com.ssv.museum.core.Museum;
import com.ssv.museum.core.Question;
import com.ssv.museum.core.Quiz;
import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.MuseumDAO;
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
import javax.ws.rs.HeaderParam;
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
@Path("museum")
public class MuseumREST extends AuthedREST {
    
    //DAO
    MuseumDAO museumDAO = lookupMuseumDAOBean();
    QuizDAO quizDAO = lookupQuizDAOBean();
    //find
    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id,
            @Context Request request) {
        Museum m = museumDAO.find(id);
        if ( m != null) {
            Gson gson = new Gson();
            return Response.ok(gson.toJson(m)).build();
        } else {
            return Response.noContent().build();  // 204
        }
    }
    //findAll
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll(@Context Request request) {
        List<Museum> museums = museumDAO.findAll();
        GenericEntity<Collection<Museum>> ge = new GenericEntity<Collection<Museum>>(museums){};
        if (museums.size()>-1) {
            Gson gson = new Gson();
            return Response.ok(gson.toJson(ge)).build();
        } else {
            return Response.noContent().build();  // 204
        }
    }

    //delete
    @DELETE
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id,
                         @HeaderParam("password") String password,
                         @Context Request request) {
        //to be able to check if exists
        Museum v = museumDAO.find(id);
        if (authMuseum(password,v.getUsername()) && v != null) {
            museumDAO.delete(id);
            Gson gson = new Gson();
            return Response.ok(gson.toJson(v)).build();
        } else {
            return Response.noContent().build();  // 204
        }
    }

    //signup
    @POST
    @Path("/signup")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response signup(JsonObject obj,
                         @Context Request request) {
        try{
            String email = obj.getString("email");
            String username = obj.getString("username");
            String password = obj.getString("password");
            String name = obj.getString("name");
            if (password!=null && password.length()>3 && username.length()>2) {
                password = generateHash(saltPassword(password));
                Museum newMuseum = new Museum(username, password,email,name);
                museumDAO.create(newMuseum);
                Gson gson = new Gson();
                return Response.ok(gson.toJson(newMuseum)).build();
            } else {
                return Response.status(400).build();  // 400
            }
        }catch(NullPointerException e){
            return Response.status(400).build();
        }
    }
    //login
    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response login(JsonObject obj,
                         @Context Request request) {
        try{
            String password = obj.getString("password");
            String username = obj.getString("username");
            if (authMuseum(password,username)) {
                Gson gson = new Gson();
                return Response.ok(gson.toJson(museumDAO.findByUsername(username))).build();
            } else {
                return Response.status(400).build();  // 400
            }
        }catch(NullPointerException e){
            return Response.status(400).build();  // 400
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
       String name = obj.getString("name");
       String username = obj.getString("username");
       String description = obj.getString("description");
       JsonObject jsonAddress = obj.getJsonObject("address");
       Address adr = new Address(name, jsonAddress.getString("city"), jsonAddress.getString("country"));
       String email = obj.getString("email");
       Museum m = museumDAO.find(id);
       if (m != null) {
            if(authMuseum(password,m.getUsername())){
                m.setName(name);
                m.setEmail(email);
                m.setUsername(username);
                m.setDescription(description);
                museumDAO.update(m);
                Gson gson = new Gson();
                return Response.ok(gson.toJson(m)).build();
            } else {
                return Response.noContent().build();  // 204
            }     
       }else{
           return Response.noContent().build();
       }
   }
    //addQuiz
    @POST
    @Path("{id: \\d+}/quizzes")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addQuiz(JsonObject obj,
                        @PathParam("id") Long id,
                        @Context Request request) {
        try{
            String password = obj.getString("password");
            String username = obj.getString("username");
            if(this.authMuseum(password, username)){
                String name = obj.getString("name");
                int points = Integer.parseInt(obj.getString("points"));
                String description = obj.getString("description");
                Quiz newQuiz = new Quiz(name, points, new Date(), description);          
                Museum m = museumDAO.findByUsername(username);
                newQuiz.setMuseum(m);
                quizDAO.create(newQuiz);               
                m.addQuiz(newQuiz);
                museumDAO.update(m);
                if (m != null) {
                    Gson gson = new Gson();
                    return Response.ok(gson.toJson(m)).build();
                } else {
                    return Response.noContent().build();  // 204
                }
            }else{
                return Response.noContent().build(); 
            }
        }catch(NullPointerException e){
            return Response.noContent().build();
        }
        
    }

    
    private MuseumDAO lookupMuseumDAOBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (MuseumDAO) c.lookup("java:global/Museum/MuseumDAO!com.ssv.museum.persistence.MuseumDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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
