/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ssv.museum.service;

import com.google.gson.Gson;
import com.ssv.museum.core.MemberRole;
import com.ssv.museum.core.Membership;
import com.ssv.museum.core.Team;
import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.TeamDAO;
import com.ssv.museum.persistence.VisitorDAO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@Path("team")
public class TeamREST {
    
    //DAO
    TeamDAO teamDAO      = lookupTeamDAOBean();
    VisitorDAO vistorDAO = lookupVisitorDAOBean();
    
    //find
    @GET
    @Path("{id: \\d+}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") Long id,
            @Context Request request) {
        Team team = teamDAO.find(id);
        if (team != null) {
            Gson gson = new Gson();
            return Response.ok(gson.toJson(team)).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }
    //findAll
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response findAll(@Context Request request) {
        List<Team> teams = teamDAO.findAll();
        GenericEntity<Collection<Team>> ge = new GenericEntity<Collection<Team>>(teams){};
        if (teams.size()>-1) {
            Gson gson = new Gson();
            return Response.ok(gson.toJson(teams)).build(); // 200
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
        Team team = teamDAO.find(id);
        if (team != null) {
            teamDAO.delete(id);
            Gson gson = new Gson();
            return Response.ok(gson.toJson(team)).build(); // 200
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
        String description = obj.getString("description");
        Team newTeam = new Team(name, description, new ArrayList<Membership>());
        teamDAO.create(newTeam);
        if (newTeam != null) {
            Gson gson = new Gson();
            return Response.ok(gson.toJson(newTeam)).build(); // 200
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
       String description = obj.getString("description");
       Team t = teamDAO.find(id);
       if (t != null) {
           t.setName(name);
           t.setDescription(description);
           teamDAO.update(t);
           Gson gson = new Gson();
            return Response.ok(gson.toJson(t)).build(); // 200
       } else {
           return Response.noContent().build();  // 204
       }
   }
    
    //addMemeber
    @POST
    @Path("/members")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addMember(JsonObject obj, 
                         @PathParam("id") Long id, 
                         @Context Request request){
        Team team = teamDAO.find(id);
        if (team != null) {
            long visitor_id = obj.getJsonNumber("visitor_id").longValue();
            Visitor visitor = vistorDAO.find(visitor_id);
            String role = obj.getString("memberRole");
            
            MemberRole m_role = MemberRole.MEMBER;
            switch(role){
                case "member":
                    m_role = MemberRole.MEMBER;
                    break;
                case "admin":
                    m_role = MemberRole.ADMIN;
                    break;
            }
            Membership nm = new Membership(visitor, m_role);
            nm.setTeam(team);
            team.addMember(nm);
            Gson gson = new Gson();
            return Response.ok(gson.toJson(team)).build(); // 200
        } else {
            return Response.noContent().build();  // 204
        }
    }
    
    
    private TeamDAO lookupTeamDAOBean() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TeamDAO) c.lookup("java:global/Museum/TeamDAO!com.ssv.museum.persistence.TeamDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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
