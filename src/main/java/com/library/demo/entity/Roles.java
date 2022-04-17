package com.library.demo.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Roles {

    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;

    @Column(name="role")
    private String role;

    public Roles(String role){
        this.role = role;
    }
}
