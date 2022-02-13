package com.falace.redditnewsletter.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@NoArgsConstructor
@Document
public class User {

    //This is always an ObjectId type in the DB, is just mapped as a string for convenience.
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String name;

    private Set<String> favoriteRedditChannels;

    public User(String name, String email, Set<String> favorites) {
        this.name = name;
        this.email = email;
        this.favoriteRedditChannels = favorites;
    }
}
