package com.library.demo.service;

import com.library.demo.entity.Roles;
import com.library.demo.entity.Users;

import java.util.List;

public interface UserService {
    public List<Users> getUsers();

    public Users getByUsername(String username);

    public Users getByUserId(int id);

    public Users saveUser(Users theUser);

    public void deleteUser(int id);
}
