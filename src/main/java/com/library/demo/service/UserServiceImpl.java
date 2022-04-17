package com.library.demo.service;

import com.library.demo.entity.Users;
import com.library.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Users getByUsername(String username) {
        Optional<Users> user = Optional.of(userRepository.getUserByUsername(username));
        return user.get();
    }

    @Override
    public Users getByUserId(int id){
        Optional<Users> user = Optional.of(userRepository.getById(id));
        return user.get();
    }

    @Override
    public Users saveUser(Users theUser) {
        userRepository.save(theUser);
        return theUser;
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
