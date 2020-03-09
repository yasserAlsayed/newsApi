package io.cryptoapis.news.controller.v1;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cryptoapis.news.exception.NotFoundException;
import io.cryptoapis.news.model.NewsEntity;
import io.cryptoapis.news.service.NewsService;

@RestController
@RequestMapping(value = "/v1/news")
public class NewsController {

	@Autowired
	private NewsService newsService;

	@GetMapping
	public ResponseEntity<List<NewsEntity>> listNews(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		List<NewsEntity> list = newsService.listNews(pageNo, pageSize, sortBy);

		return new ResponseEntity<List<NewsEntity>>(list, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<NewsEntity> getNewsById(@PathVariable("id") Long id) throws NotFoundException {
		NewsEntity entity = newsService.getNewsById(id);

		return new ResponseEntity<NewsEntity>(entity, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<NewsEntity> createOrUpdateNews(NewsEntity news) throws NotFoundException {
		NewsEntity updated = newsService.createOrUpdateNews(news);
		return new ResponseEntity<NewsEntity>(updated, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteNewsById(@PathVariable("id") Long id) throws NotFoundException {
		newsService.deleteNewsById(id);
		return HttpStatus.FORBIDDEN;
	}
}
