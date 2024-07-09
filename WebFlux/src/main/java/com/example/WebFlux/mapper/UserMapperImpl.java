package com.example.WebFlux.mapper;

import com.example.WebFlux.dto.UserDTO;
import com.example.WebFlux.entity.User;

public class UserMapperImpl implements UserMapper{
    @Override
    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    @Override
    public UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }
}
