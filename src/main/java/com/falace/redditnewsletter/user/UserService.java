package com.falace.redditnewsletter.user;

import com.falace.redditnewsletter.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
    }

    public String createUser(UserDto userDto) {
        User u = new User(userDto.getName(), userDto.getEmail(), userDto.getFavorites());
        userRepository.insert(u);
        return u.getId();
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    public void updateFavoriteChannels(String userId, Set<String> channelsToDelete, Set<String> channelsToAdd) {
        if (!isIntersectionEmpty(channelsToAdd, channelsToDelete)) {
            throw new IllegalArgumentException("You can't both add and delete a same channel!");
        }
        Optional<User> maybeUser = userRepository.findById(userId);
        if (maybeUser.isEmpty()) {
            throw new IllegalArgumentException("Could not find user with id=" + userId);
        }
        User user = maybeUser.get();
        user.getFavoriteRedditChannels().removeAll(channelsToDelete);
        user.getFavoriteRedditChannels().addAll(channelsToAdd);
        userRepository.save(user);
    }

    private <T> boolean isIntersectionEmpty(Set<T> aSet, Set<T> anotherSet) {
        Set<T> common = new HashSet<>(aSet);
        common.retainAll(anotherSet);
        return common.isEmpty();
    }
}
