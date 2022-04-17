package com.library.demo.service;

import com.library.demo.entity.Roles;

import java.util.List;

public interface RoleService {
    public List<Roles> getRoles();

    public Roles getRoleById(int id);

    public Roles saveRole(Roles theRole);

    public void deleteRole(int id);
}
