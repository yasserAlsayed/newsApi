package io.cryptoapis.news.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.cryptoapis.news.dao.NewsDao;
import io.cryptoapis.news.exception.NotFoundException;
import io.cryptoapis.news.model.NewsEntity;

@Service
public class NewsService {

	@Autowired
	private NewsDao repository;

	public List<NewsEntity> listNews(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = sortByParams(pageNo, pageSize, sortBy);
		Page<NewsEntity> pagedResult = repository.findAll(paging);
		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<NewsEntity>();
		}
	}

	private Pageable sortByParams(Integer pageNo, Integer pageSize, String sortBy) {
		String[] params=sortBy.split(",");
		if(params.length>1) {
			Sort paramsSort =null;
			for (String param:params) {
				if(param.startsWith("-") || param.endsWith("-")  ) {
					param=param.replaceAll("-", "").trim();
					Sort paramSort=Sort.by(param).descending();
					paramsSort= (paramsSort==null)?paramSort:paramsSort.and(paramSort);
				}else if(param.startsWith("+") || param.endsWith("+") || !param.contains("+")) {
					param=param.replaceAll("\\+", "").trim();
					Sort paramSort=Sort.by(param).ascending();
					paramsSort= (paramsSort==null)?paramSort:paramsSort.and(paramSort);
				}
			}
			return PageRequest.of(pageNo, pageSize, paramsSort);
		}else {
			return PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		}
	}

	public NewsEntity getNewsById(Long id) throws NotFoundException {
		Optional<NewsEntity> news = repository.findById(id);
		if (news.isPresent()) {
			return news.get();
		} else {
			throw new NotFoundException("No News Data exist for given id");
		}
	}

	public NewsEntity createOrUpdateNews(NewsEntity entity) throws NotFoundException {
		Optional<NewsEntity> news = repository.findById(entity.getId());
		if (news.isPresent()) {
			NewsEntity newEntity = news.get();
			newEntity.setDate(entity.getDate());
			newEntity.setTitle(entity.getTitle());
			newEntity.setText(entity.getText());
			newEntity.setDescription(entity.getDescription());
			newEntity = repository.save(newEntity);
			return newEntity;
		} else {
			entity = repository.save(entity);
			return entity;
		}
	}

	public void deleteNewsById(Long id) throws NotFoundException {
		Optional<NewsEntity> news = repository.findById(id);
		if (news.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new NotFoundException("No News Data for given id");
		}
	}
}
