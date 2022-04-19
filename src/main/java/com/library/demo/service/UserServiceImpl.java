package com.library.demo.service;

import com.library.demo.dto.UsersDTO;
import com.library.demo.entity.Users;
import com.library.demo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ModelMapper modelMapper;

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
    public Users saveUser(UsersDTO theUser) {
        Users user = convertDtoToEntity(theUser);
        userRepository.save(user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UsersDTO getUserDTO(Users user){
        return convertEntityToDto(user);
    }

    @Override
    public Users getEntity(UsersDTO usersDTO){
        return convertDtoToEntity(usersDTO);
    }

    public UsersDTO convertEntityToDto(Users users){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        UsersDTO usersDTO;
        usersDTO = modelMapper.map(users, UsersDTO.class);
        return usersDTO;
    }

    public Users convertDtoToEntity(UsersDTO usersDTO){
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        Users users;
        users = modelMapper.map(usersDTO,Users.class);
        return users;
    }

}
