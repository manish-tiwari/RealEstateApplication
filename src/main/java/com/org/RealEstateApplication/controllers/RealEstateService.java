package com.org.RealEstateApplication.controllers;

import java.net.UnknownHostException;
import java.util.regex.Pattern;

import org.bson.types.BasicBSONList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class RealEstateService {
	static MongoClient client;

	public static MongoClient getClient() {
		return client;
	}

	static {

		try {
			client = new MongoClient();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		DB db = client.getDB("RealEstate");
		properties = db.getCollection("properties");
		buyers = db.getCollection("buyers");
	}
	static DBCollection buyers;
	static DBCollection properties;
	public static final int ASCENDING_ORDER = 1;
	public static final int DESCENDING_ORDER = -1;

	public static BasicBSONList sortByPrice(int order) {
		if (ASCENDING_ORDER == order) {
			System.out.println("\nList of properties sorted in Ascending order of price: ");
		} else {
			System.out.println("\nlist of properties sorted in Descending order of price: ");
		}
		BasicDBObject query = new BasicDBObject("price", order);
		DBCursor cursor = properties.find().sort(query);
		BasicBSONList basicBSONList = new BasicBSONList();
		while (cursor.hasNext()) {
			basicBSONList.add(cursor.next());
		}
		return basicBSONList;
	}

	public static BasicBSONList allProperties() {
		DBCursor cursor = properties.find();
		BasicBSONList basicBSONList = new BasicBSONList();
		while (cursor.hasNext()) {
			basicBSONList.add(cursor.next());
		}
		return basicBSONList;

	}

	public static BasicBSONList searchProperties(String area) {
		System.out.println("\nProperties matching your search criteria: \"" + area + "\":");
		BasicDBObject query = new BasicDBObject();
		Pattern regex = Pattern.compile(area, Pattern.CASE_INSENSITIVE);
		query.put("Area", regex);
		DBCursor cursor = properties.find(query);
		BasicBSONList basicBSONList = new BasicBSONList();
		while (cursor.hasNext()) {
			basicBSONList.add(cursor.next());
		}
		return basicBSONList;
	}

	public static BasicBSONList filterProperties(int lowestPrice, int highestPrice) {
		System.out.println("\nProperties between price range Rs." + lowestPrice + " and Rs." + highestPrice + ":");
		BasicDBObject query = new BasicDBObject();
		query.put("price", new BasicDBObject("$gt", lowestPrice).append("$lt", highestPrice));
		DBCursor cursor = properties.find(query);
		BasicBSONList basicBSONList = new BasicBSONList();
		while (cursor.hasNext()) {
			basicBSONList.add(cursor.next());
		}
		return basicBSONList;
	}

}
