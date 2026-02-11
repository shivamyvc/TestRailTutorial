package com.testrail;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseTR {

	private static final String BASE_URL = "https://techwithmavii.testrail.io/index.php";
	protected static RequestSpecification baseRequest;
	private String userEmail = "janesjohnny75@gmail.com";
	private String APIKey = "PrDOBQZbTuwX2P6Mgw2R-H3rhLDH0AEXjis9PiYxx";

	public BaseTR() {
		baseRequest = RestAssured.given().baseUri(BASE_URL).auth().preemptive().basic(userEmail, APIKey);
	}

	public Response POST(String endpoint, Object payLoad) {
		return baseRequest.body(payLoad).post(endpoint);
	}

	public Response POST(Object payLoad) {
		return baseRequest.body(payLoad).post();
	}

	public Response GET() {
		return baseRequest.get();
	}

	public Response GET(String endpoint) {
		return baseRequest.get(endpoint);
	}

}
