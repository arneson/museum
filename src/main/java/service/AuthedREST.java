/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.MuseumDAO;
import com.ssv.museum.persistence.VisitorDAO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private MuseumDAO lookupMuseumDAOBean() {
        try {
            Context c = new InitialContext();
            return (MuseumDAO) c.lookup("java:global/com.mycompany_Museum_war_1.0-SNAPSHOT/MuseumDAO!com.ssv.museum.persistence.MuseumDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private VisitorDAO lookupVisitorDAOBean() {
        try {
            Context c = new InitialContext();
            return (VisitorDAO) c.lookup("java:global/com.mycompany_Museum_war_1.0-SNAPSHOT/VisitorDAO!com.ssv.museum.persistence.VisitorDAO");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
