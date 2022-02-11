package com.falace.redditnewsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class RedditNewsletterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditNewsletterApplication.class, args);
    }

}
