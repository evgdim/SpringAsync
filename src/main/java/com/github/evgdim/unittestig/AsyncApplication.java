package com.github.evgdim.unittestig;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableAsync
public class AsyncApplication extends AsyncConfigurerSupport {

	public static void main(String[] args) {
		SpringApplication.run(AsyncApplication.class, args);
	}
	@Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("GithubLookup-");
        executor.initialize();
        return executor;
    }
	
	@Bean
	public CommandLineRunner runner(PostsService pServ){
		return new CommandLineRunner() {
			
			@Override
			public void run(String... arg0) throws Exception {
				Future<List<Post>> posts = pServ.getPosts();
				// do stuff
				posts.get().stream().forEach(p -> System.out.println(p));
			}
		};
	}
}
