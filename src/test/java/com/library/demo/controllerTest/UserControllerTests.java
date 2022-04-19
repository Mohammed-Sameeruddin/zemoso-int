package com.library.demo.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.library.demo.controller.UserController;
import com.library.demo.entity.Users;
import com.library.demo.service.IssuedBookService;
import com.library.demo.service.RoleService;
import com.library.demo.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTests {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    private UserService userService;

    @Mock
    private RoleService roleService;

    @Mock
    private IssuedBookService issuedBookService;

    @InjectMocks
    private UserController userController;
    Users user1 = new Users("username","test123","9898989898","username@gmail.com",true);
    Users user2 = new Users("admin","admin123","7897807808","admin@gmail.com",true);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void showUsersTest() throws Exception {
        List<Users> usersList = new ArrayList<>(Arrays.asList(user1,user2));

        Mockito.when(userService.getUsers()).thenReturn(usersList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/showUsers").contentType(MediaType.ALL))
                .andExpect(status().isOk());
    }

    @Test
    void saveUserTest() throws Exception {
        user1.setUserId(1);
        Mockito.when(userService.saveUser(userService.getUserDTO(user1))).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/saveUser")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username","username")
                .param("password","test123")
                .param("phone","9898989898")
                .param("email","username@gmail.com")
                .param("enabled", String.valueOf(true))
                .sessionAttr("user",new Users()))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/api/showUsers"))
                .andExpect(redirectedUrl("/api/showUsers"))
                .andExpect(model().attribute("user",nullValue()));

        ArgumentCaptor<Users> formObjectArgument = ArgumentCaptor.forClass(Users.class);
        Mockito.verify(userService,Mockito.times(1)).saveUser(userService.getUserDTO(formObjectArgument.capture()));

        Users formObject = formObjectArgument.getValue();

        assertEquals("username",formObject.getUsername());
    }

    @Test
    void addUserTest() throws Exception{
        user1.setUserId(1);
        Mockito.when(userService.saveUser(userService.getUserDTO(user1))).thenReturn(user1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/addUser")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username","username")
                        .param("password","test123")
                        .param("phone","9898989898")
                        .param("email","username@gmail.com")
                        .param("enabled", String.valueOf(true))
                        .sessionAttr("user",new Users()))
                .andExpect(status().isOk())
                .andExpect(view().name("add-user"))
                .andExpect(model().attribute("user",notNullValue()));
    }

    @Test
    void updateUserTest() throws Exception{
        user1.setUserId(1);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/updateUser")
                        .contentType(MediaType.TEXT_HTML)
                        .param("userId", String.valueOf(1)))
                .andExpect(view().name("add-user"));
    }

    @Test
    void deleteTest() throws Exception{
        user1.setUserId(1);
        Mockito.when(userService.getByUserId(user1.getUserId())).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/deleteUser")
                        .param("userId", String.valueOf(1)))
                .andExpect(status().isMovedTemporarily())
                .andExpect(view().name("redirect:/api/showUsers"));
    }
}
