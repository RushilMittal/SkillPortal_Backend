package com.cdac.skillportal.service;

import com.cdac.skillportal.model.AdminRoles;
import com.cdac.skillportal.repository.AdminRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AdminRoleServiceTest {
    @Mock
    public AdminRoleRepository adminRoleRepository;

    @InjectMocks
    public AdminRoleService adminRoleService;

    @Before
    public void setUp() throws Exception {


        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void isAdmin() {
        AdminRoles adminRoles = new AdminRoles("1", "admin");

        when(adminRoleRepository.findByUserRole(anyString())).thenReturn(adminRoles);

        boolean expected = adminRoleService.isAdmin("admin");
        assertThat(true, is(expected));
    }

    @Test
    public void addRoles() {

        adminRoleService.addRoles();
        verify(adminRoleRepository, times(1)).save(any(AdminRoles.class));
    }
}