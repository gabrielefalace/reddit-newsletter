package com.falace.redditnewsletter.user;

import com.falace.redditnewsletter.reddit.dto.RedditPostDataDto;
import com.falace.redditnewsletter.reddit.RedditService;
import com.falace.redditnewsletter.user.dto.UserDto;
import com.falace.redditnewsletter.user.dto.UserFavoritesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    private final RedditService redditService;

    @Autowired
    public UserController(UserService userService, RedditService redditService) {
        this.userService = userService;
        this.redditService = redditService;
    }

    @GetMapping("/user/{userId}")
    public UserDto getUser(@PathVariable String userId) {
        User user = userService.getUser(userId);
        return new UserDto(user.getName(), user.getEmail(), user.getFavoriteRedditChannels());
    }

    @PostMapping("/user")
    public String createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    //TODO improve: technically is more of a PATCH. Solving support issues was taking time.
    @PutMapping("/user/{userId}/favorites")
    public void updateFavoriteRedditChannels(@PathVariable String userId, @RequestBody UserFavoritesDto redditChannels) {
        userService.addRedditChannels(userId, redditChannels.getChannelsToAdd());
        userService.deleteRedditChannels(userId, redditChannels.getChannelsToDelete());
    }

    @GetMapping("/user/{userId}/favorites")
    public Map<String, List<RedditPostDataDto>> getPostsFromFavoriteChannels(@PathVariable String userId) {
        User user = userService.getUser(userId);
        Map<String, List<RedditPostDataDto>> postsByChannel = new LinkedHashMap<>();
        user.getFavoriteRedditChannels().forEach(
                channel -> {
                    postsByChannel.put(channel, redditService.fetchRedditPosts(channel));
                }
        );
        return postsByChannel;
    }

}
