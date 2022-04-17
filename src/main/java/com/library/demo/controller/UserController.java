package com.library.demo.controller;

import com.library.demo.entity.Roles;
import com.library.demo.entity.Users;
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
    private RoleService roleService;

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

        return "add-user";
    }

    @PostMapping("/saveUser")
    public String saveUser(@Valid @ModelAttribute("user") Users theUser, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "add-user";
        }
        List<Users> usersList = userService.getUsers();
        for(Users user : usersList){
            if(user.getUsername().equals(theUser.getUsername())){
                redirectAttributes.addFlashAttribute("error",theUser.getUsername()+ " is already registred");
                return "redirect:/api/showUsers";
            }
        }
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        theUser.setPassword(bcrypt.encode(theUser.getPassword()));
        theUser.setEnabled(true);
        userService.saveUser(theUser);
        return "redirect:/api/showUsers";
    }

    @GetMapping("/updateUser")
    public String updateUser(@RequestParam("userId") int id, Model theModel){
        Users theUser = userService.getByUserId(id);
        theModel.addAttribute("user",theUser);
        List<Roles> theRoles = roleService.getRoles();
        theModel.addAttribute("allRoles",theRoles);
        return "add-user";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int id){
        userService.deleteUser(id);
        return "redirect:/api/showUsers";
    }
}
