package io.cryptoapis.news.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import io.cryptoapis.news.model.NewsEntity;

/**
 * 
 * Spring JPA Repository with {@link JpaSpecificationExecutor}
 * 
 * @author Yasser
 *
 */
@Repository
public interface NewsRepository extends CrudRepository<NewsEntity, Long>, JpaSpecificationExecutor<NewsEntity> {

}
