package com.org.RealEstateApplication;

import static com.org.RealEstateApplication.utils.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.get;

import org.bson.types.BasicBSONList;

import com.org.RealEstateApplication.controllers.RealEstateService;
import com.org.RealEstateApplication.utils.ResponseError;

import spark.Request;
import spark.Response;
import spark.servlet.SparkApplication;

public class App implements SparkApplication {
	public static void main(String[] args) {

		setUpEndpoints();
	}

	private static void setUpEndpoints() {
		after("/properties", (request, response) -> {
			response.type("application/json");
		});

		get("/properties", (request, response) -> RealEstateService.allProperties(), json());
		after("/properties/sort/" + RealEstateService.ASCENDING_ORDER, (request, response) -> {
			response.type("application/json");
		});
		get("/properties/sort/" + RealEstateService.ASCENDING_ORDER,
				(request, response) -> RealEstateService.sortByPrice(RealEstateService.ASCENDING_ORDER), json());
		after("/properties/sort/" + RealEstateService.DESCENDING_ORDER, (request, response) -> {
			response.type("application/json");
		});
		get("/properties/sort/" + RealEstateService.DESCENDING_ORDER,
				(request, response) -> RealEstateService.sortByPrice(RealEstateService.DESCENDING_ORDER), json());

		get("/properties/searchByArea", (req, res) -> {
			String area = req.queryParams("area");
			if(req.queryString()==null) {
				return new ResponseError("Please enter the area to search.").getMessage();
			}
			if(area==null ) {
				return new ResponseError("No area matching '%s' found", area).getMessage();
			}
			BasicBSONList propertiesMatchingParam = RealEstateService.searchProperties(area);
			if (propertiesMatchingParam != null) {
				res.type("application/json");
				return propertiesMatchingParam;
			}
			res.status(400);
			res.type("text/html");
			return new ResponseError("No area matching '%s' found", area).getMessage();
		}, json());
		
		get("/properties/filter/price",(req,res)->{
			int minPrice=0,maxPrice=999999999;
			try {
				minPrice=Integer.parseInt(req.queryParams("min"));
				maxPrice=Integer.parseInt(req.queryParams("max"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return new ResponseError(" Please enter a valid price range").getMessage();
				
			}
			BasicBSONList filteredProperties = RealEstateService.filterProperties(minPrice, maxPrice);
			if (filteredProperties != null) {
				res.type("application/json");
				return filteredProperties;
			}
			return new ResponseError("Something went wrong").getMessage();
		});

	}

	// @after((req, res) -> res.type("application/json"));
	public void init() {
		get("/", App::welcome);
		// get("/properties", (request,response) -> RealEstateService.allProperties());
	}

	public static String welcome(Request req, Response res) {
		return "<html><h1>Welcome to the online Real Estate portal(Pune)</h1></html>";
	}

}
