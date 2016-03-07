/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.MuseumDAO;
import com.ssv.museum.persistence.VisitorDAO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author simonarneson
 */
public abstract class AuthedREST {

    VisitorDAO visitorDAO = lookupVisitorDAOBean();
    MuseumDAO museumDAO = lookupMuseumDAOBean();
    
    private static final String SALT = "javascriptärsvinnice";
    public boolean authVisitor(String at, Long userId){
        
        String fbId = getFacebookUserID(at);
        Visitor v = visitorDAO.findByFbId(fbId);
        if(v.getId().equals(userId)){
            return true;
        }
        return false;
    }
    public boolean authMuseum(String password, String username){
        return generateHash(saltPassword(password)).equals(museumDAO.findByUsername("username").getPassword());
    }
    public static String saltPassword(String input) {
        return SALT + input;
    }
    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                            'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                    byte b = hashedBytes[idx];
                    hash.append(digits[(b & 0xf0) >> 4]);
                    hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
                // handle error here.
        }

        return hash.toString();
    }
    public String getFacebookUserID(String at) {
        FacebookClient facebookClient = new DefaultFacebookClient(at, "4422e837b4f7033bf3a2303807df6e2c");
        User user = facebookClient.fetchObject("me", User.class);
        return user.getId();
    }
    public String getFacebookUsername(String at) {
        FacebookClient facebookClient = new DefaultFacebookClient(at, "4422e837b4f7033bf3a2303807df6e2c");
        User user = facebookClient.fetchObject("me", User.class);
        return user.getName();
    }

    private MuseumDAO lookupMuseumDAOBean() {
        try {
            Context c = new InitialContext();
            return (MuseumDAO) c.lookup("java:global/Museum/MuseumDAO!com.ssv.museum.persistence.MuseumDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private VisitorDAO lookupVisitorDAOBean() {
        try {
            Context c = new InitialContext();
            return (VisitorDAO) c.lookup("java:global/Museum/VisitorDAO!com.ssv.museum.persistence.VisitorDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
