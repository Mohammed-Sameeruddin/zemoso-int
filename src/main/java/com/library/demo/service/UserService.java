package com.library.demo.service;

import com.library.demo.dto.UsersDTO;
import com.library.demo.entity.Users;

import java.util.List;

public interface UserService {
    public List<Users> getUsers();

    public Users getByUsername(String username);

    public Users getByUserId(int id);

    public Users saveUser(UsersDTO theUser);

    public void deleteUser(int id);

    public UsersDTO getUserDTO(Users user);

    public Users getEntity(UsersDTO usersDTO);
}
