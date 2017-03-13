package com.github.evgdim.unittestig;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Future;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostsService {
	private final RestTemplate rest;
	public PostsService(RestTemplateBuilder restBuilder) {
		this.rest = restBuilder.build();
	}
	@Async
	public Future<List<Post>> getPosts(){
		return new AsyncResult<List<Post>>
			(Arrays.asList(
					this.rest.getForObject("https://jsonplaceholder.typicode.com/posts", Post[].class)));
	}
}
