package io.cryptoapis.news.model;

import java.time.chrono.ChronoLocalDate;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import io.cryptoapis.news.filter.Converters;
import io.cryptoapis.news.filter.FilterCriteria;
import io.cryptoapis.news.filter.FilterSpecifications;


/**
 * 
 * Specification for News Entity
 * 
 * @author Yasser
 *
 */
@Service
public class NewsFilterSpecification {
	
	
	/**
	 * {@link FilterSpecifications} for Entity {@link NewsEntity} and Field type {@link ChronoLocalDate} (LocalDate)
	 */
	@Autowired
	private FilterSpecifications<NewsEntity, ChronoLocalDate> dateTypeSpecifications;

	/**
	 * {@link FilterSpecifications} for Entity {@link NewsEntity} and Field type {@link String}
	 */
	@Autowired
	private FilterSpecifications<NewsEntity, String> stringTypeSpecifications;

	/**
	 * {@link FilterSpecifications} for Entity {@link NewsEntity} and Field type {@link Integer}
	 * 
	 */
	@Autowired
	private FilterSpecifications<NewsEntity, Integer> integerTypeSpecifications;

	/**
	 * {@link FilterSpecifications} for Entity {@link NewsEntity} and Field type {@link Long}
	 */
	@Autowired
	private FilterSpecifications<NewsEntity, Long> longTypeSpecifications;
	
	
	/**
	 * Converter Functions
	 */
	@Autowired
	private Converters converters;
	
	
	/**
	 * Returns the Specification for Entity {@link NewsEntity} for the given fieldName and filterValue for the field type Date
	 * 
	 * @param fieldName
	 * @param filterValue
	 * @return
	 */
	public Specification<NewsEntity> getDateTypeSpecification(String fieldName, String filterValue) {
		return getSpecification(fieldName, filterValue, converters.getFunction(ChronoLocalDate.class), dateTypeSpecifications);
	}

	/**
	 * Returns the Specification for Entity {@link NewsEntity} for the given fieldName and filterValue for the field type String
	 * @param fieldName
	 * @param filterValue
	 * @return
	 */
	public Specification<NewsEntity> getStringTypeSpecification(String fieldName, String filterValue) {
		return getSpecification(fieldName, filterValue, converters.getFunction(String.class), stringTypeSpecifications);
	}
	
	/**
	 * Returns the Specification for Entity {@link NewsEntity} for the given fieldName and filterValue for the field type Long
	 * 
	 * @param fieldName
	 * @param filterValue
	 * @return
	 */
	public Specification<NewsEntity> getLongTypeSpecification(String fieldName, String filterValue) {
		return getSpecification(fieldName, filterValue, converters.getFunction(Long.class), longTypeSpecifications);
	}
	
	
	/**
	 * Returns the Specification for Entity {@link NewsEntity} for the given fieldName and filterValue for the field type Integer
	 * 
	 * @param fieldName
	 * @param filterValue
	 * @return
	 */
	public Specification<NewsEntity> getIntegerTypeSpecification(String fieldName, String filterValue) {
		return getSpecification(fieldName, filterValue, converters.getFunction(Integer.class), integerTypeSpecifications);
	}

	/**
	 * Generic method to return {@link Specification} for Entity {@link NewsEntity}
	 * 
	 * @param fieldName
	 * @param filterValue
	 * @param converter
	 * @param specifications
	 * @return
	 */
	private <T extends Comparable<T>> Specification<NewsEntity> getSpecification(String fieldName,
			String filterValue, Function<String, T> converter, FilterSpecifications<NewsEntity, T> specifications) {

		if (StringUtils.isNotBlank(filterValue)) {
			
			//Form the filter Criteria
			FilterCriteria<T> criteria = new FilterCriteria<>(fieldName, filterValue, converter);
			return specifications.getSpecification(criteria.getOperation()).apply(criteria);
		}

		return null;

	}
	
}
