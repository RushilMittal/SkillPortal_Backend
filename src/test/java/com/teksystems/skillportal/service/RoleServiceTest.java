package com.teksystems.skillportal.service;

import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.repository.AdminRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class RoleServiceTest {
    @Mock
    public AdminRoleRepository adminRoleRepository;
    @InjectMocks
    public RoleService roleService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void addRoles() {
        AdminRoles adminRoles = new AdminRoles("1", "gadang@teksystems.com");
        roleService.addRoles(adminRoles);
        verify(adminRoleRepository, times(1)).save(any(AdminRoles.class));
    }

    @Test
    public void getAdminRoles() {
        AdminRoles adminRoles = new AdminRoles("1", "gadang@teksystems.com");
        List<AdminRoles> adminRolesList = new ArrayList<>();
        adminRolesList.add(adminRoles);
        when(adminRoleRepository.findAll()).thenReturn(adminRolesList);

        List<AdminRoles> expected = roleService.getAdminRoles();
        assertThat(1, is(expected.size()));
    }

    @Test
    public void deleteRole() {
        roleService.deleteRole("1");
        verify(adminRoleRepository, times(1)).delete(anyString());
    }
}