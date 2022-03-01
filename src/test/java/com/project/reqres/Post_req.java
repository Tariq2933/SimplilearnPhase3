package com.project.reqres;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.logger.TestLog;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Post_req extends TestLog{
	static Response response_put;
	@BeforeClass
	void addUser() {
		
		RestAssured.baseURI = "https://reqres.in/";
		RequestSpecification given = RestAssured.given();
		// Put
		JSONObject reqobject = new JSONObject();
		reqobject.put("name", "Tariq");
		reqobject.put("job", "Strategist");

		given.header("content-type", "application/json");
		given.body(reqobject.toJSONString());

		response_put = given.request(Method.POST, "api/users");
		
		System.out.println("Response Body: "+response_put.getBody().asString());
		System.out.println("Status Code: "+response_put.getStatusCode());
		System.out.println("Status Line: "+response_put.getStatusLine());
		System.out.println("================================");
		
	}
	@Test
	void checkStatusCode() {
		logger.info("****Inside checkStatusCode*******");
		int statusCode = response_put.getStatusCode();
		logger.info("StatusCode ==>" + statusCode);
		Assert.assertEquals(statusCode, 201);
	}

	@Test
	void checkStatusLine() {
		logger.info("********Inside checkStatusLine*********");
		String statusLine = response_put.getStatusLine();
		logger.info("StatusLine ==>" + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 201 Created");
	}

	@Test
	void checkContentType() {
		logger.info("********Inside checkContentType*********");
		String contentType = response_put.header("Content-Type");
		logger.info("Content type is ==>" + contentType);
		//Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}
	
	@Test
	void checkResponseBody() {
		logger.info("*******Inside checkResponseBody*******");
		String responseBody = response_put.getBody().asString();
		logger.info("Response Body ==> " + responseBody);
		//Assert.assertTrue(responseBody != null);
	}
}
