package com.project.reqres;

import java.util.List;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.logger.TestLog;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Get_req extends TestLog {

	Response response_get, response_query;

	
	@BeforeClass
	void GetUser() throws InterruptedException {
		logger.info("********statrt of GetUser class**********");
		RestAssured.baseURI = "https://reqres.in/api";
		RequestSpecification given = RestAssured.given();
		response_get = given.request(Method.GET, "/users/2");
		response_query = RestAssured.given().queryParams("page", "2").get("/users");
		Thread.sleep(1000);
	}

	@Test
	void checkResponseBody() {
		logger.info("*******Inside checkResponseBody*******");
		String responseBody = response_get.getBody().asString();
		logger.info("Response Body ==> " + responseBody);
		Assert.assertTrue(responseBody != null);
	}

	@Test
	void checkStatusCode() {
		logger.info("****Inside checkStatusCode*******");
		int statusCode = response_get.getStatusCode();
		logger.info("StatusCode ==>" + statusCode);
		Assert.assertEquals(statusCode, 200);
	}

	@Test
	void checkStatusLine() {
		logger.info("********Inside checkStatusLine*********");
		String statusLine = response_get.getStatusLine();
		logger.info("StatusLine ==>" + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}

	@Test
	void checkContentType() {
		logger.info("********Inside checkContentType*********");
		String contentType = response_get.header("Content-Type");
		logger.info("Content type is ==>" + contentType);
		Assert.assertEquals(contentType, "application/json; charset=utf-8");
	}

	@Test
	void GetAllUser() {
		logger.info("********Inside User List*********");
		JsonPath jsonPath = response_query.jsonPath();

		logger.info("Page :" + jsonPath.getInt("per_page"));
		logger.info("Per Page :" + jsonPath.getInt("per_page"));
		logger.info("Total :" + jsonPath.getInt("total"));
		logger.info("Total Pages :" + jsonPath.getInt("total_pages"));

		List<Integer> list2 = jsonPath.getList("data.id");
		System.out.println("List size :" + list2.size());
		System.out.println("================================");
		List<Users> list = jsonPath.getList("data", Users.class);
		for (Users user : list) {
			logger.info("User ID :" + user.getId());
			logger.info("First Name :" + user.getFirst_name());
			logger.info("Last Name :" + user.getLast_name());
			logger.info("Email :" + user.getEmail());
		}
	}

}
