/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.ssv.museum.core.Visitor;
import com.ssv.museum.persistence.VisitorDAO;

/**
 *
 * @author simonarneson
 */
public abstract class AuthedREST {
    public boolean authVisitor(String accessToken, Long userId){
        //fetch facebookData
        VisitorDAO vd = new VisitorDAO();
        String fbId = "";
        Visitor v = vd.findByFbId(fbId);
        if(v.getId().equals(userId)){
            return true;
        }
        return false;
    }
    public boolean authMuseum(String password, String username){
        return true;
    }
}
