package com.example.WebFlux.controller;

import com.example.WebFlux.dto.UserDTO;
import com.example.WebFlux.entity.User;
import com.example.WebFlux.mapper.UserMapper;
import com.example.WebFlux.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping("/test")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<ResponseEntity<String>> getUserInfo(Mono<Principal> principal){
        return principal.map(Principal::getName)
                .map(name -> ResponseEntity.ok("Method getUserInfo is calling. Username is " + name));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<User> updateUser(@PathVariable String id, @RequestBody UserDTO dto) {
        User user = UserMapper.INSTANCE.userDTOToUser(dto);
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'USER')")
    public Mono<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }

}

