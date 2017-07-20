package com.org.RealEstateApplication;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.org.RealEstateApplication.controllers.AuthenticationManager;
import com.org.RealEstateApplication.controllers.RealEstateService;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	private MongoClient client=RealEstateService.getClient();
	private DB db;
	DBCollection buyers,properties;
	
	@Test
	public void testIsUserAuthenticated() {
		assertTrue("Authentication failed, plesae verify.",AuthenticationManager.getInstance().isUserAuthenticated("maddy@gmail.com", "maddypwd"));
		assertFalse("uthentication failed, plesae verify.",AuthenticationManager.getInstance().isUserAuthenticated(null, "maddypwd"));
		AuthenticationManager.getInstance().isUserAuthenticated("maddy@gmail.com", "maddypwd");
		assertEquals("Maddy", AuthenticationManager.buyerName);
	}
	
	
	@Test
	public void testMongoDataSet() {
		db = client.getDB("RealEstate");
		properties = db.getCollection("properties");
		buyers = db.getCollection("buyers");
		assertEquals("Please make sure that you have imported the dataset provided in resources/buyers.json in and mongod server is running on 127.0.0.1:27017",2,buyers.find().length());
		assertEquals("Please make sure that you have imported the dataset provided in resources/properties.json in and mongod server is running on 127.0.0.1:27017",9,properties.find().length());
	}
}
