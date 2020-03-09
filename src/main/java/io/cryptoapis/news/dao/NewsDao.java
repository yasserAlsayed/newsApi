package io.cryptoapis.news.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import io.cryptoapis.news.model.NewsEntity;

@Repository
public interface NewsDao extends  PagingAndSortingRepository<NewsEntity, Long>  , JpaSpecificationExecutor<NewsEntity>{
	
}
