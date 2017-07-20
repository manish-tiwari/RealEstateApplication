package com.org.RealEstateApplication.controllers;

import static spark.Spark.get;
import static spark.Spark.post;

import spark.Request;

public class ManageSession {
	static private ManageSession manageSession;
    public static ManageSession getManageSessionInstance() {
    	if(manageSession==null) {
    		manageSession=new ManageSession();
    	}
		return manageSession;
	}

	private static final String SESSION_NAME = "username";

    public void login(Request request,String emailId,String pwd) {
    	
    	String name=null;
    		if (AuthenticationManager.getInstance().isUserAuthenticated(emailId, pwd)) {
				 name=AuthenticationManager.buyerName;
			}else {
				AuthenticationManager.buyerName=null;
			}
            if (name != null) {
                request.session().attribute(SESSION_NAME, name);
            }
        }

    public void logout(Request request) {
    	request.session().removeAttribute(SESSION_NAME);
    }
    }


