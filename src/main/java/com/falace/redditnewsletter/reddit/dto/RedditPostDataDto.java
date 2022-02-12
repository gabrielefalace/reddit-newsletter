package com.falace.redditnewsletter.reddit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RedditPostDataDto {

    @JsonProperty("title")
    private String title;

    @JsonProperty("score")
    private int votes;

    @JsonProperty("url")
    private String url;

    @JsonProperty("thumbnail")
    private String thumbnail;

}
