package com.falace.redditnewsletter.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserFavoritesDto {

    private Set<String> channelsToDelete;

    private Set<String> channelsToAdd;


}
