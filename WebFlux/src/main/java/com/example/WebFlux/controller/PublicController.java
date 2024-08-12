package com.example.WebFlux.controller;

import com.example.WebFlux.dto.UserDTO;
import com.example.WebFlux.entity.Role;
import com.example.WebFlux.entity.RoleType;
import com.example.WebFlux.entity.User;
import com.example.WebFlux.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class PublicController {

    private final UserService userService;

    @GetMapping
    public Mono<String> publicMethod(){
        return Mono.just("Public Method Calling");
    }

    @PostMapping("/account")
    public Mono<UserDTO> createUser(@RequestBody UserDTO dto, @RequestParam RoleType roleType) {
        return createAccount(dto, roleType);
    }


    private Mono<UserDTO> createAccount(UserDTO userDTO, RoleType roleType) {
        var user = new User();
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        user.setUsername(userDTO.getUsername());

        System.out.println(Role.from(roleType));

        return userService.createUser(user, Role.from(roleType))
                .map(createdUser -> UserDTO.builder()
                        .username(createdUser.getUsername())
                        .password(createdUser.getPassword())
                        .email(createdUser.getEmail())
                        .build());
    }

}
