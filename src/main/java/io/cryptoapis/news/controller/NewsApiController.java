package io.cryptoapis.news.controller;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cryptoapis.news.api.ApiModelNews;
import io.cryptoapis.news.dao.NewsDAO;
import io.cryptoapis.news.model.NewsEntity;
import io.cryptoapis.news.model.NewsFilterSpecification;
import io.cryptoapis.news.page.PageRequestBuilder;

@RestController
@RequestMapping(value = "/news")
public class NewsApiController   {

	private final NewsDAO newsDAO;

	private final NewsFilterSpecification newsFilterSpecification;

	/**
	 * 
	 * Prefer Constructor Injection over field injection 
	 * 
	 * @param newsDAO
	 * @param newsFilterSpecification
	 */
	public NewsApiController(NewsDAO newsDAO, NewsFilterSpecification newsFilterSpecification) {
		this.newsDAO = newsDAO;
		this.newsFilterSpecification = newsFilterSpecification;
	}

	@GetMapping
	public ResponseEntity<ApiModelNews> getNewss(
			@Valid @RequestParam(value = "title", required = false) String title,
			@Valid @RequestParam(value = "date", required = false) String date,
			@Valid @RequestParam(value = "description", required = false) String description,
			@Valid @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
			@Valid @RequestParam(value = "sort", required = false) String sort) {
		//Forms the specification for attributes by Filter specification builder from NewsFilterSpecification
		//We can mix and match and, or, not conditions
		Specification<NewsEntity> specs = Specification
				//Exposed attributes in API swagger spec doesn't need to be same as Database table column names.
				.where(newsFilterSpecification.getStringTypeSpecification("title", title))
				.and(newsFilterSpecification.getStringTypeSpecification("description", description))
				.and(newsFilterSpecification.getDateTypeSpecification("date", date));
		//This represents the Page config with sorting
		PageRequest pageRequest = PageRequestBuilder.getPageRequest(pageSize, pageNumber, sort);	
		//Call the DAO with specifications and pagerequest
		ApiModelNews news = newsDAO.getNews(specs, pageRequest);
		//Return the sorting criteria back so that the consumer can pass the same sorting or of different sorting based on the usecases.
		news.getPaging().setSortingCriteria(sort);
		return new ResponseEntity<>(news, HttpStatus.OK);

	}

}
