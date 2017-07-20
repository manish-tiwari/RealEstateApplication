package com.org.RealEstateApplication;

import static com.org.RealEstateApplication.utils.JsonUtil.json;
import static spark.Spark.after;
import static spark.Spark.get;

import java.io.StringWriter;

import org.bson.types.BasicBSONList;

import com.org.RealEstateApplication.controllers.AuthenticationManager;
import com.org.RealEstateApplication.controllers.ManageSession;
import com.org.RealEstateApplication.controllers.RealEstateService;
import com.org.RealEstateApplication.utils.ResponseError;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.servlet.SparkApplication;

public class App implements SparkApplication {
	public static void main(String[] args) {
		/*
		 * Note: please first import the dataset provided in resource folder in mongodb
		 * Java version 1.8
		 * MongoDB 3.4 
		 * Rest dependencies it will automatically download since it is a maven project
		 * Commands for importing: mongoimport --db RealEstate --collection buyers
		 * --drop --file buyers.json mongoimport --db RealEstate --collection properties
		 * --drop --file properties.json
		 */
		get("/", App::welcome);
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
			if (req.queryString() == null) {
				return new ResponseError("Please enter the area to search.").getMessage();
			}
			if (area == null) {
				return new ResponseError("No area matching '%s' found", area).getMessage();
			}
			BasicBSONList propertiesMatchingParam = RealEstateService.searchProperties(area);
			if (propertiesMatchingParam != null && !propertiesMatchingParam.isEmpty()) {
				res.type("application/json");
				return propertiesMatchingParam;
			}
			res.type("text/html");
			return new ResponseError("No area matching '%s' found", area).getMessage();
		}, json());

		get("/properties/filter/price", (req, res) -> {
			int minPrice = 0, maxPrice = 999999999;
			try {
				minPrice = Integer.parseInt(req.queryParams("min"));
				maxPrice = Integer.parseInt(req.queryParams("max"));
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

		get("/login", (req, res) -> {
			String emailId = req.queryParams("email");
			String pwd = req.queryParams("pwd");
			ManageSession.getManageSessionInstance().login(req, emailId, pwd);
			res.redirect("/");
			if (AuthenticationManager.buyerName != null) {
				return "Welcome " + AuthenticationManager.buyerName;
			} else {
				return new ResponseError("Please enter the area to search.").getMessage();
			}
		});

	}

	public void init() {

		// get("/properties", (request,response) -> RealEstateService.allProperties());
	}

	public static StringWriter welcome(Request req, Response res) {

		final Configuration configuration = new Configuration(new Version(2, 3, 0));
		configuration.setClassForTemplateLoading(App.class, "/");

		System.out.println("in get request /...");
		StringWriter writer = new StringWriter();

		try {
			Template formTemplate = configuration.getTemplate("form.ftl");

			formTemplate.process(null, writer);
		} catch (Exception e) {
			Spark.halt(500);
		}

		return writer;

	}

}
