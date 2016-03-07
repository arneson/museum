/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.ssv.museum.core.Address;
import com.ssv.museum.core.Museum;
import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.MuseumDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    //find
    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id,
            @Context Request request) {
        Museum m = museumDAO.find(id);
        if ( m != null) {
            return Response.ok(m).build(); // 200
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
            return Response.ok(v).build(); // 200
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
        String name = obj.getString("name");
        String username = obj.getString("username");
        String password = obj.getString("password");
        if (password!=null && password.length()>3 && username.length()>2) {
            password = generateHash(saltPassword(password));
            Museum newMuseum = new Museum(username, password,name);
            museumDAO.create(newMuseum);
            return Response.ok(newMuseum).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }
    //login
    @POST
    @Path("/login")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response login(JsonObject obj,
                         @Context Request request) {
        String password = obj.getString("password");
        String username = obj.getString("username");
        if (authMuseum(password,username)) {
            return Response.ok(museumDAO.findByUsername(username)).build(); // 200
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
                return Response.ok(m).build(); // 200
            } else {
                return Response.noContent().build();  // 204
            }     
       }else{
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
}
