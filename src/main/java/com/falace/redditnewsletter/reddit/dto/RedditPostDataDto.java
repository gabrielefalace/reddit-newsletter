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

    @JsonProperty("permalink")
    private String permalink;

    @JsonProperty("thumbnail")
    private String thumbnail;

}
