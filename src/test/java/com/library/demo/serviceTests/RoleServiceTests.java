package com.library.demo.serviceTests;

import com.library.demo.entity.Roles;
import com.library.demo.repository.RoleRepository;
import com.library.demo.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class RoleServiceTests {
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @Test
    void getRolesTest(){
        Roles role1 = new Roles("Student");
        Roles role2 = new Roles("Admin");
        List<Roles> rolesList = new ArrayList<>(Arrays.asList(role1,role2));
        when(roleRepository.findAll()).thenReturn(rolesList);
        assertEquals(2,roleService.getRoles().size());

    }

    @Test
    void getRoleByIdTest(){
        Roles role = new Roles("Student");
        when(roleRepository.getById(1)).thenReturn(role);
        assertEquals(role,roleService.getRoleById(1));
        assertEquals("Student",roleService.getRoleById(1).getRole());
    }

    @Test
    void saveRoleTest(){
        Roles role = new Roles("Admin");
        when(roleRepository.save(role)).thenReturn(role);
        assertEquals(role,roleService.saveRole(role));
    }

    @Test
    void deleteRoleTest(){
        Roles role = new Roles("Admin");
        roleRepository.delete(role);
        roleService.deleteRole(role.getRoleId());
        verify(roleRepository,times(1)).deleteById(role.getRoleId());
    }
}
