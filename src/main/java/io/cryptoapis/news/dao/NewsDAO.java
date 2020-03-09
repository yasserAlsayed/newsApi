package io.cryptoapis.news.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.cryptoapis.news.api.ApiModelNews;
import io.cryptoapis.news.api.ApiModelPageAndSort;
import io.cryptoapis.news.dto.NewsDto;
import io.cryptoapis.news.model.NewsEntity;

/**
 * 
 * Data access layer for Entity 'News'
 * 
 * @author Yasser
 *
 */
@Service
public class NewsDAO {

	private final NewsDao newssRepository;

	/**
	 * Prefer Constructor Injection over field injection
	 * 
	 * @param newssRepository
	 */
	public NewsDAO(NewsDao newssRepository) {
		super();
		this.newssRepository = newssRepository;
	}

	/**
	 * 
	 * Get the list of News entities based on the specification and page request and
	 * convert that to {@link NewsDto}
	 * 
	 * @param spec        - Specification for the Entity 'NewsEntity'
	 * @param pageRequest - PageRequest
	 * @return
	 */
	public ApiModelNews getNews(Specification<NewsEntity> spec, PageRequest pageRequest) {

		Validate.notNull(spec, "Specification can't be null");
		Validate.notNull(pageRequest, "pageRequest can't be null");

		ApiModelNews newsResponse = new ApiModelNews();

		// Get Page info from NewsRepository
		Page<NewsEntity> newsPage = newssRepository.findAll(spec, pageRequest);

		ApiModelPageAndSort pagingResponse = new ApiModelPageAndSort();

		// Set the flag to indicate next page exists
		pagingResponse.setHasNextPage(newsPage.hasNext());

		// Set the flag to indicate previous page exists
		pagingResponse.setHasPreviousPage(newsPage.hasPrevious());

		// Set the total number of records for the given Filter Specification
		pagingResponse.setTotalNumberOfRecords(newsPage.getTotalElements());

		// Set the total number of pages for the given filter specification and
		// pagerequests
		pagingResponse.setTotalNumberOfPages(newsPage.getTotalPages());

		// Page numbers are indexed from 0 but to the consume we follow start index as 1
		pagingResponse.setPageNumber(pageRequest.getPageNumber() + 1);

		// Number of records per page
		pagingResponse.setPageSize(pageRequest.getPageSize());

		newsResponse.setPaging(pagingResponse);

		// Get the News List from the Page
		List<NewsEntity> news = newsPage.getContent();

		// Map the data to ApiModelNews using lambda function
		newsResponse.setData(news.stream().map(this::getNews).collect(Collectors.toList()));

		return newsResponse;

	}

	/**
	 * Mapper from {@link News} to {@link NewsData}
	 * 
	 * 
	 * @param entity
	 * @return
	 */
	private NewsDto getNews(NewsEntity entity) {
		NewsDto res = new NewsDto();

		res.setTitle(entity.getTitle());
		res.setDescription(entity.getDescription());
		res.setDate(entity.getDate());
		res.setText(entity.getText());

		return res;

	}
}
