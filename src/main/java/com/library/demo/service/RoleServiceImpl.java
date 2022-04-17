package com.library.demo.service;

import com.library.demo.entity.Roles;
import com.library.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Roles> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Roles getRoleById(int id) {
        Optional<Roles> role = Optional.of(roleRepository.getById(id));
        return role.get();
    }

    @Override
    public Roles saveRole(Roles theRole) {
        roleRepository.save(theRole);
        return theRole;
    }

    @Override
    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
