package com.falace.redditnewsletter.user.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private String name;
    private String email;
    private Set<String> favorites;
}
