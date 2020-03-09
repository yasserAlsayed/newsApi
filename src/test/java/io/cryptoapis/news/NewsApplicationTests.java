package io.cryptoapis.news;

import java.time.LocalDate;
import java.time.LocalDateTime;

//////////////////////////////////////

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

import io.cryptoapis.news.api.ApiModelNews;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NewsApplicationTests {

	@Value("${local.server.port}")
	private int port;

	
	@Test
	public void testGetNewsWithTitleFilter() {
		
		//title equal filter with sort by title asc and date desc
		String title="The Coronavirus May Be The Best Reason Yet To Own Bitcoin";
		Response response = RestAssured
				.given().port(port).when()
				.get("/news?title=eq:"+title+"&sort=title,-date&pageSize=5&pageNumber=1")
				.peek();

		Assert.assertEquals(200, response.getStatusCode());
		
		ApiModelNews responseObj = response.getBody().as(ApiModelNews.class);
		
		Assert.assertNotNull(responseObj);
		Assert.assertNotNull(responseObj.getData());
		Assert.assertNotNull(responseObj.getPaging());
		Assert.assertEquals(1, responseObj.getData().size());
		Assert.assertTrue(responseObj.getData().stream().allMatch(data -> title.equals(data.getTitle()) ));
		

		response = RestAssured
				.given().port(port).when()
				.get("/news?title=like:The Most&sort=+date&pageSize=5&pageNumber=1")
				.peek();

		Assert.assertEquals(200, response.getStatusCode());
		
		 responseObj = response.getBody().as(ApiModelNews.class);
		
		Assert.assertNotNull(responseObj);
		Assert.assertNotNull(responseObj.getData());
		Assert.assertNotNull(responseObj.getPaging());
		Assert.assertEquals(1, responseObj.getData().size());
		Assert.assertTrue(responseObj.getData().stream().allMatch(data -> data.getTitle().contains("The Most") ));
		
	}
	
	@Test
	public void testGetNewsWithDateFilter() {
		String date="07-03-2020";
		// Date = 2020-03-05 22:05:00, pageSize 5
		Response response = RestAssured
				.given().port(port).when()
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 5)
				.queryParam("sort", "+date,title")
				.queryParam("date", "eq:"+date)
				.get("/news")
				.peek();

		Assert.assertEquals(200, response.getStatusCode());
		
		ApiModelNews responseObj = response.getBody().as(ApiModelNews.class);
		
		Assert.assertNotNull(responseObj);
		Assert.assertNotNull(responseObj.getData());
		Assert.assertNotNull(responseObj.getPaging());
		Assert.assertEquals(1, responseObj.getData().size());
		Assert.assertTrue(responseObj.getData().stream().allMatch(data -> data.getDate() .equals(LocalDate.parse(date))));
		

		// Date > "2020-03-05 22:00:35", pageSize 4
		response = RestAssured
				.given().port(port).when()
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 4)
				.queryParam("sort", "-date")
				.queryParam("dae", "gt:"+date)
				.get("/news")
				.peek();

		Assert.assertEquals(200, response.getStatusCode());
		
		responseObj = response.getBody().as(ApiModelNews.class);
		
		Assert.assertNotNull(responseObj);
		Assert.assertNotNull(responseObj.getData());
		Assert.assertNotNull(responseObj.getPaging());
		Assert.assertEquals(2, responseObj.getData().size());
		Assert.assertTrue(responseObj.getData().stream().allMatch(data -> data.getDate().isAfter(LocalDate.parse(date))))	;

		
		// Date > "2020-03-05 22:00:35", pageSize 4
				response = RestAssured
						.given().port(port).when()
						.queryParam("pageNumber", 1)
						.queryParam("pageSize", 4)
						.queryParam("sort", "-date")
						.queryParam("dae", "lt:"+date)
						.get("/news")
						.peek();

				Assert.assertEquals(200, response.getStatusCode());
				
				responseObj = response.getBody().as(ApiModelNews.class);
				
				Assert.assertNotNull(responseObj);
				Assert.assertNotNull(responseObj.getData());
				Assert.assertNotNull(responseObj.getPaging());
				Assert.assertEquals(17, responseObj.getData().size());
				Assert.assertTrue(responseObj.getData().stream().allMatch(data -> data.getDate().isBefore(LocalDate.parse(date))))	;
		
		// Date between "2020-03-05 20:00:00" and "2020-03-05 22:00:00", pageSize 4
		response = RestAssured
				.given().port(port).when()
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 4)
				.queryParam("sort", "-date")
				.queryParam("date", "btn:2020-03-05 20:00:00,2020-03-05 22:00:00")
				.get("/news")
				.peek();

		Assert.assertEquals(200, response.getStatusCode());
		
		responseObj = response.getBody().as(ApiModelNews.class);
		
		Assert.assertNotNull(responseObj);
		Assert.assertNotNull(responseObj.getData());
		Assert.assertNotNull(responseObj.getPaging());
		Assert.assertEquals(16, responseObj.getData().size());
		Assert.assertTrue(responseObj.getData().stream().findFirst().filter(e->e.getTitle().contains("Chihuahua: YouTuber")).isPresent());
		Assert.assertTrue(responseObj.getData().stream().allMatch(data -> 
				data.getDate().isAfter(LocalDate.parse("2020-03-01"))&&
				data.getDate().isBefore(LocalDate.parse("2020-03-05")
				)))	;
	}
	
	
	@Test
	public void testGetNewsWithDateAndTileFilter() {
		// Date between:2020-03-05 20:00:00,2020-03-05 22:00:00 and Title contains 'BitCoin', pageSize 5
		Response response = RestAssured
				.given().port(port).when()
				.queryParam("pageNumber", 1)
				.queryParam("pageSize", 4)
				.queryParam("sort", "-date")
				.queryParam("date", "btn:2020-03-05 20:00:00,2020-03-05 22:00:00")
				.queryParam("title", "like:Bitcin")
				.get("/news")
				.peek();

		Assert.assertEquals(200, response.getStatusCode());
		
		ApiModelNews responseObj = response.getBody().as(ApiModelNews.class);
		
		Assert.assertNotNull(responseObj);
		Assert.assertNotNull(responseObj.getData());
		Assert.assertNotNull(responseObj.getPaging());
		Assert.assertEquals(1, responseObj.getData().size());
		Assert.assertTrue(responseObj.getData().stream().allMatch(data -> data.getDate().equals(LocalDateTime.parse("2020-03-05 20:00:17"))));
	}

}