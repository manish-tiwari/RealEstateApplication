package com.org.RealEstateApplication.controllers;

import java.util.regex.Pattern;

import org.bson.types.BasicBSONList;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class AuthenticationManager {
	public static String buyerName;
	private static AuthenticationManager authenticationManager;
	private AuthenticationManager() {
		
	}
	
	public boolean isUserAuthenticated(String emailId,String pwd) {
		
        BasicDBObject query = new BasicDBObject();
        query.put("_id",emailId );
        DBObject buyer = RealEstateService.buyers.findOne(query);
        
        if (buyer==null) {
			return false;
		}
        if(buyer.get("pwd").equals(pwd)) {
        	buyerName=(String) buyer.get("name");
        	return true;
        }else {
        	return false;
        }
        
	}
	
	public static AuthenticationManager getInstance() {
		
		if (authenticationManager==null) {
			authenticationManager=new AuthenticationManager();
		}
			return authenticationManager;
	}
}
