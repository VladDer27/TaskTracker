package com.example.WebFlux.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "authorities")
public class Role {
    @Id
    private Long id;

    private RoleType authority;

    @ReadOnlyProperty
    private User user;

    public GrantedAuthority toAuthority(){
        return new SimpleGrantedAuthority(authority.name());
    }

    public static Role from(RoleType type){
        var role = new Role();
        role.setAuthority(type);
        return role;
    }
}
