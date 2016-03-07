/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.VisitorDAO;
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
@Path("visitor")
public class VisitorREST extends AuthedREST {
    
    //DAO
    VisitorDAO visitorDAO = lookupVisitorDAOBean();
    
    //find
    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id,
            @HeaderParam("access-token") String at,
            @Context Request request) {
        Visitor v = visitorDAO.find(id);
        if (authVisitor(at,id) && v != null) {
            return Response.ok(v).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }

    //delete
    @DELETE
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response delete(@PathParam("id") Long id,
                         @HeaderParam("access-token") String at,
                         @Context Request request) {
        //to be able to check if exists
        Visitor v = visitorDAO.find(id);
        if (authVisitor(at,id) && v != null) {
            visitorDAO.delete(id);
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
        String at = obj.getString("access_token");
        String facebookId = getFacebookUserID(at);
        String name = getFacebookUsername(at);
        if (facebookId!=null) {
            Visitor newVisitor = new Visitor(name, facebookId);
            visitorDAO.create(newVisitor);
            return Response.ok(newVisitor).build(); // 200
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
        String at = obj.getString("access_token");
        String facebookId = getFacebookUserID(at);
        if (facebookId!=null) {
            Visitor v = visitorDAO.findByFbId(facebookId);
            //if no account exists, create one by internal redirect to signup
            if(v==null){
                return signup(obj,request);
            }else{
                return Response.ok(v).build(); // 200
            }
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
                          @HeaderParam("access-token") String at,
                          JsonObject obj,@Context Request request) {
       String username = obj.getString("username");
       String city = obj.getString("city");
       String email = obj.getString("email");
       if(authVisitor(at,id)){
            Visitor v = visitorDAO.find(id);
            if (v != null) {
                v.setCity(city);
                v.setMail(email);
                v.setUsername(username);
                visitorDAO.update(v);
                return Response.ok(v).build(); // 200
            } else {
                return Response.noContent().build();  // 204
            }     
       }else{
           return Response.noContent().build();
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
}
