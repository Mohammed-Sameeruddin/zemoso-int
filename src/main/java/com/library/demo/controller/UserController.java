package com.library.demo.controller;

import com.library.demo.dto.UsersDTO;
import com.library.demo.entity.Roles;
import com.library.demo.entity.Users;
import com.library.demo.service.IssuedBookService;
import com.library.demo.service.RoleService;
import com.library.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private IssuedBookService issuedBookService;

    @Autowired
    private RoleService roleService;
    private static final String ADD_USER_PATH = "add-user";
    private static final String SHOW_USERS_PATH = "api/showUsers";
    private static final String REDIRECT = "redirect:/";

    @GetMapping("/showUsers")
    public String showUsers(Model theModel){
        List<Users> allUsers = userService.getUsers();
        theModel.addAttribute("users",allUsers);
        return "list-users";
    }

    @GetMapping("/addUser")
    public String addUser(Model theModel){
        Users user = new Users();
        theModel.addAttribute("user",user);
        List<Roles> roles = roleService.getRoles();
        theModel.addAttribute("allRoles",roles);
        return ADD_USER_PATH;
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") UsersDTO theUser, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return ADD_USER_PATH;
        }
        if(theUser.getUserId() != 0){
            userService.saveUser(theUser);
            return REDIRECT + SHOW_USERS_PATH;
        }
        List<Users> usersList = userService.getUsers();
        for(Users user : usersList){
            if(user.getUsername().equals(theUser.getUsername())){
                redirectAttributes.addFlashAttribute("error",theUser.getUsername()+ " is already registred");
                return REDIRECT + SHOW_USERS_PATH;
            }
        }
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        theUser.setPassword(bcrypt.encode(theUser.getPassword()));
        theUser.setEnabled(true);
        userService.saveUser(theUser);
        return REDIRECT + SHOW_USERS_PATH;
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("userId") int id, Model theModel){
        Users theUser = userService.getByUserId(id);
        theModel.addAttribute("user",theUser);
        List<Roles> theRoles = roleService.getRoles();
        theModel.addAttribute("allRoles",theRoles);
        return ADD_USER_PATH;
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int id){
        issuedBookService.deleteBooksOfUser(userService.getByUserId(id));
        userService.deleteUser(id);
        return REDIRECT + SHOW_USERS_PATH;
    }
}
