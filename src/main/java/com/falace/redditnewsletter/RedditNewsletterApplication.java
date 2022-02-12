package com.falace.redditnewsletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = {ErrorMvcAutoConfiguration.class})
@EnableMongoRepositories
public class RedditNewsletterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditNewsletterApplication.class, args);
    }

}
