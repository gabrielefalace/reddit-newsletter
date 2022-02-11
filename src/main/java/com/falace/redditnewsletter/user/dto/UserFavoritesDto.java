package com.falace.redditnewsletter.user.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserFavoritesDto {

    private Set<String> channelsToDelete;

    private Set<String> channelsToAdd;

    public UserFavoritesDto(Set<String> channelsToDelete, Set<String> channelsToAdd) {
        if (!isIntersectionEmpty(channelsToDelete, channelsToAdd)) {
            throw new IllegalArgumentException("Channels to add and to delete should have no common channel");
        }
        this.channelsToDelete = channelsToDelete;
        this.channelsToAdd = channelsToAdd;
    }

    public void setChannelsToDelete(Set<String> channelsToDelete) {
        if (!isIntersectionEmpty(channelsToDelete, this.channelsToAdd)) {
            throw new IllegalArgumentException("Channels to add and to delete should have no common channel");
        }
        this.channelsToDelete = channelsToDelete;
    }


    public void setChannelsToAdd(Set<String> channelsToAdd) {
        if (!isIntersectionEmpty(channelsToAdd, this.channelsToDelete)) {
            throw new IllegalArgumentException("Channels to add and to delete should have no common channel");
        }
        this.channelsToAdd = channelsToAdd;
    }

    private <T> boolean isIntersectionEmpty(Set<T> aSet, Set<T> anotherSet) {
        Set<T> common = new HashSet<>(aSet);
        common.retainAll(anotherSet);
        return common.isEmpty();
    }

}
