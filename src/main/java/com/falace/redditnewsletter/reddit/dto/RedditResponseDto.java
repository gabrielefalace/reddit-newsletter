package com.falace.redditnewsletter.reddit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RedditResponseDto {

    @JsonProperty("data")
    private RedditResponseData data;

    @JsonProperty("kind")
    private String kind;

}
