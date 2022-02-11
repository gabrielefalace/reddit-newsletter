package com.falace.redditnewsletter;

import com.falace.redditnewsletter.user.*;
import com.falace.redditnewsletter.user.dto.UserDto;
import com.falace.redditnewsletter.user.dto.UserFavoritesDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedditNewsletterApplicationTests {

    @LocalServerPort
    int port = 0;

    private static final String BASE_URL = "http://localhost";
    private static final User testUser = new User("Homer Simpson", "homer.simpson@mail.com", Set.of("bowling"));
    private static final UserDto testUserDto = new UserDto("Homer Simpson", "homer.simpson@mail.com", Set.of("bowling"));
    private static final RestTemplate restTemplate = new RestTemplate();

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    UserRepository userRepo;

    @AfterEach
    public void cleanUp(){
        userRepo.deleteAll();
    }

    @Test
    public void shouldCreateUser() {
        String userEndpoint = BASE_URL + ":" + port + "/user";

        String id = restTemplate.postForObject(userEndpoint, testUserDto, String.class);
        assertNotNull(id);
        assertTrue(id.length() > 0);

        Optional<User> maybeFetchedUser = userRepo.findById(id);
        assertTrue(maybeFetchedUser.isPresent());
        assertEquals(maybeFetchedUser.get().getName(), testUserDto.getName());
        assertEquals(maybeFetchedUser.get().getEmail(), testUserDto.getEmail());
    }

    @Test
    public void shouldFetchUser() {
        String userEndpoint = BASE_URL + ":" + port + "/user";
        userRepo.insert(testUser);
        UserDto retrievedUser = restTemplate.getForObject(userEndpoint + "/" + testUser.getId(), UserDto.class);
        assertNotNull(retrievedUser);
        assertEquals(testUser.getName(), retrievedUser.getName());
    }

    @Test
    public void shouldUpdateFavorites() {
        String userEndpoint = BASE_URL + ":" + port + "/user";
        userRepo.insert(testUser);

        UserFavoritesDto favoritesDto = new UserFavoritesDto(Set.of("bowling"), Set.of("donuts"));
        restTemplate.put(userEndpoint + "/" + testUser.getId() + "/favorites/", favoritesDto);

        Optional<User> maybeFetchedUser = userRepo.findById(testUser.getId());
        assertTrue(maybeFetchedUser.isPresent());
        assertEquals(1, maybeFetchedUser.get().getFavoriteRedditChannels().size());
        assertTrue(maybeFetchedUser.get().getFavoriteRedditChannels().contains("donuts"));
    }

}
