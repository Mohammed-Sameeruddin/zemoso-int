package com.library.demo.dto;

import com.library.demo.entity.Roles;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UsersDTO {
    private int userId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private boolean enabled;
    private Set<Roles> roles = new HashSet<>();
}
