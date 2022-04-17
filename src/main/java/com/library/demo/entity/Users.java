package com.library.demo.entity;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Users {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(name="username")
    @NotNull(message="is required")
    @Size(min=3,message="length should be atleast 3")
    private String username;

    @Column(name="password")
    @NotNull(message="is required")
    @Size(min=4,message="length should be atleast 4")
    private String password;

    @Column(name="phone")
    @NotNull(message="is required")
    @Size(min=10,max=10,message="length should be exact 10")
    private String phone;

    @Column(name="email")
    @NotNull(message="is required")
    @Email(message= "email should be valid")
    private String email;

    @Column(name="enabled")
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<IssuedBook> booksList;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Roles> roles = new HashSet<>();

    public Users(String username,String password,String phone,String email,boolean enabled){
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.enabled = enabled;
    }

    public void addRole(Roles role){
        if(this.roles == null){
            this.roles = new HashSet<>();
        }

        this.roles.add(role);
    }

    public String getUserRoles(){
        String result = "";
        for(Roles rol : roles){
            result = result + " " + rol.getRole();
        }
        return result;
    }

}
