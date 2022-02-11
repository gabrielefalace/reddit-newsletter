package com.falace.redditnewsletter.reddit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RedditPost {

    @JsonProperty("data")
    private RedditPostData data;

}
