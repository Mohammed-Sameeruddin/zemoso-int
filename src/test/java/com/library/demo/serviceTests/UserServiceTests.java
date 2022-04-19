package com.library.demo.serviceTests;

import com.library.demo.entity.Users;
import com.library.demo.repository.UserRepository;
import com.library.demo.service.UserService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTests {
	@MockBean
	private UserRepository userRepository;
	@Autowired
	private UserService userService;

	@Test
	void getUsersTest(){
		Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
		Users user2 = new Users("admin","admin123","7897807808","admin@gmail.com",true);
		List<Users> usersList = new ArrayList<>(Arrays.asList(user1,user2));
		when(userRepository.findAll()).thenReturn(usersList);
		assertEquals(2,userService.getUsers().size());
	}

	@Test
	void getUserTest(){
		Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
		when(userRepository.getUserByUsername("username")).thenReturn(user1);
		assertEquals(user1,userService.getByUsername("username"));
	}

	@Test
	void getByUserId(){
		Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
		user1.setUserId(5);
		when(userRepository.getById(5)).thenReturn(user1);
		assertEquals(user1,userService.getByUserId(5));
	}

	@Test
	void saveUserTest(){
		Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
		when(userRepository.save(user1)).thenReturn(user1);
		assertEquals(user1.getUsername(),userService.saveUser(userService.getUserDTO(user1)).getUsername());
		assertEquals(user1.getUsername(),userService.getEntity(userService.getUserDTO(user1)).getUsername());
	}

	@Test
	void deleteUserTest(){
		Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
		user1.setUserId(10);
		userRepository.delete(user1);
		userService.deleteUser(user1.getUserId());
		verify(userRepository,times(1)).deleteById(user1.getUserId());
	}



}
