package com.falace.redditnewsletter.user;

import com.falace.redditnewsletter.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static com.falace.redditnewsletter.user.UserService.UserFavoritesChange.ADD;
import static com.falace.redditnewsletter.user.UserService.UserFavoritesChange.REMOVE;

@Service
public class UserService {

    enum UserFavoritesChange {
        ADD,
        REMOVE
    }

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }

    // TODO Exception from DB must be handled
    public String createUser(UserDto userDto) {
        User u = new User(userDto.getName(), userDto.getEmail(), userDto.getFavorites());
        userRepository.insert(u);
        return u.getId();
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    private void updateFavoriteChannels(String userId, UserFavoritesChange change, Set<String> channelNames) {
        Optional<User> maybeUser = userRepository.findById(userId);
        if (maybeUser.isEmpty()) {
            throw new IllegalArgumentException("Could not find user with id=" + userId);
        }
        User user = maybeUser.get();
        switch (change) {
            case ADD:
                user.getFavoriteRedditChannels().addAll(channelNames);
                break;
            case REMOVE:
                user.getFavoriteRedditChannels().removeAll(channelNames);
                break;
        }
        userRepository.save(user);
    }

    public void addRedditChannels(String userId, Set<String> channels) {
        updateFavoriteChannels(userId, ADD, channels);
    }

    public void deleteRedditChannels(String userId, Set<String> channels) {
        updateFavoriteChannels(userId, REMOVE, channels);
    }


}
