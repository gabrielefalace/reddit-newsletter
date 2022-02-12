package com.falace.redditnewsletter.reddit;

import com.falace.redditnewsletter.reddit.dto.RedditPostDto;
import com.falace.redditnewsletter.reddit.dto.RedditPostDataDto;
import com.falace.redditnewsletter.reddit.dto.RedditResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class RedditService {

    @Value("${reddit.numberOfPosts}")
    private int numberOfPosts;

    @Value("${reddit.app.id}")
    private String redditAppId;

    private static final String REDDIT_URL = "https://www.reddit.com/r/";

    public Map<String, List<RedditPostDataDto>> fetchAllRedditPosts(Set<String> channels) {
        Map<String, List<RedditPostDataDto>> postsByChannel = new LinkedHashMap<>();
        channels.forEach(
                channel -> {
                    postsByChannel.put(channel, fetchRedditPosts(channel));
                }
        );
        return postsByChannel;
    }

    public List<RedditPostDataDto> fetchRedditPosts(String channel) {

        String userAgent = "web:" + redditAppId + ":v1 (by /r/gabrifal)";

        RestTemplate template = new RestTemplate();
        HttpHeaders subredditRequestHeaders = new HttpHeaders();
        subredditRequestHeaders.set("User-Agent", userAgent);
        HttpEntity<MultiValueMap<String, String>> subredditRequest = new HttpEntity<>(subredditRequestHeaders);

        ResponseEntity<RedditResponseDto> response = template.exchange(
                REDDIT_URL + channel + "/top.json?limit=" + numberOfPosts + "&t=day",
                HttpMethod.GET,
                subredditRequest,
                RedditResponseDto.class);

        if (response.getBody() != null) {
            return response.getBody().getData().getPosts().stream().map(RedditPostDto::getData).collect(Collectors.toList());
        } else {
            throw new IllegalStateException("Cannot get posts from reddit for channel: " + channel);
        }
    }

    public int getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(int numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public String getRedditAppId() {
        return redditAppId;
    }

    public void setRedditAppId(String redditAppId) {
        this.redditAppId = redditAppId;
    }
}
