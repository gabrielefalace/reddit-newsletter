package com.falace.redditnewsletter.newsletter;

import com.falace.redditnewsletter.reddit.dto.RedditPostData;
import com.falace.redditnewsletter.reddit.RedditService;
import com.falace.redditnewsletter.user.User;
import com.falace.redditnewsletter.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class NewsletterController {

    private final UserService userService;

    private final RedditService redditService;

    @Autowired
    public NewsletterController(UserService userService, RedditService redditService) {
        this.userService = userService;
        this.redditService = redditService;
    }

    @GetMapping(value = {"/user/{userId}/newsletter/"})
    public String index(Model model, @PathVariable String userId) {
        User u = userService.getUser(userId);
        model.addAttribute("userName", u.getName());

        Map<String, List<RedditPostData>> postsByTopic = redditService.fetchAllRedditPosts(u.getFavoriteRedditChannels());

        model.addAttribute("topics", postsByTopic);
        return "index";
    }

}
