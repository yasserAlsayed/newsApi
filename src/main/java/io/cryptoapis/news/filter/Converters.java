package io.cryptoapis.news.filter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class Converters {

	private Map<Class<?>, Function<String, ? extends Comparable<?>>> map = new HashMap<>();

	@PostConstruct
	public void init() {
		map.put(String.class, s -> s);
		map.put(Long.class, Long::valueOf);
		map.put(Integer.class, Integer::valueOf);
		map.put(LocalDateTime.class, (text)->{ 
			return  LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		});
		// Add more converters
	}

	@SuppressWarnings("unchecked")
	public <T extends Comparable<T>> Function<String, T> getFunction(Class<?> classObj) {
		return (Function<String, T>) map.get(classObj);
	}

}
