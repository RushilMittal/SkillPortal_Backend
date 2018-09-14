package com.teksystems.skillportal.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoException;
import com.teksystems.skillportal.helper.ConfigurationStrings;
import com.teksystems.skillportal.model.AdminRoles;
import com.teksystems.skillportal.service.RoleService;
import com.teksystems.skillportal.service.TokenValidationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleControllerTest {

    @Mock
    TokenValidationService tokenValidationService;
    @Mock
    RoleService roleService;

    @InjectMocks
    RoleController roleController;

    private MockMvc mockMvc;
    private static final MediaType APPLICATION_JSON_UTF8 =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    Charset.forName("utf8"));

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn("101");
        this.mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    @Test
    public void checkEmployeeId() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");

        boolean expected = roleController.checkEmployeeId(request, response);

        assertThat(true, is(expected));
    }

    @Test
    public void checkEmployeeIdUnAuthorized() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn(null);

        boolean expected = roleController.checkEmployeeId(request, response);

        assertThat(false, is(expected));
    }

    @Test
    public void checkEmployeeIdEmployeeIdNotPresent() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);
        boolean expected = roleController.checkEmployeeId(request, response);

        assertThat(false, is(expected));
    }

    @Test(expected = IOException.class)
    public void checkEmployeeIdIOException() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);

        doThrow(IOException.class).when(response).sendError(anyInt(), anyString());

        roleController.checkEmployeeId(request, response);

        assertThat(500, is(response.getStatus()));

    }


    @Test
    public void getAllAdminRoles() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        when(roleService.getAdminRoles()).thenReturn(getAdminRolesList());

        ResultActions resultAction = mockMvc.perform(
                get("/role/adminRoles")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].userRole", is("abc@teksystems.com")))
                .andExpect(jsonPath("$[1].userRole", is("def@teksystems.com")));
    }

    @Test
    public void getAllAdminRolesInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);
        ResultActions resultAction = mockMvc.perform(
                get("/role/adminRoles")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void getAllAdminRolesMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        when(roleService.getAdminRoles()).thenThrow(MongoException.class);

        ResultActions resultAction = mockMvc.perform(
                get("/role/adminRoles")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
    }


    @Test
    public void addAdminRole() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        doNothing().when(roleService).addRoles(any(AdminRoles.class));


        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/role/addAdminRole")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getAdminRoles()))

        );

        resultAction.andExpect(status().isOk());
    }

    @Test
    public void addAdminRoleInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);

        doNothing().when(roleService).addRoles(any(AdminRoles.class));


        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/role/addAdminRole")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getAdminRoles()))

        );

        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void addAdminRoleMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        doThrow(MongoException.class).when(roleService).addRoles(any(AdminRoles.class));


        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ResultActions resultAction = mockMvc.perform(
                post("/role/addAdminRole")
                        .header("Authorization", "empId:101")
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsBytes(getAdminRoles()))

        );

        resultAction.andExpect(status().isInternalServerError());
    }

    @Test
    public void deleteRole() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        doNothing().when(roleService).deleteRole(anyString());

        ResultActions resultAction = mockMvc.perform(
                get("/role/deleteRole?id=1")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isOk());
    }

    @Test
    public void deleteRoleInvalid() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");
        when(tokenValidationService.extractEmployeeId(any(HttpServletRequest.class))).thenReturn(null);

        doNothing().when(roleService).deleteRole(anyString());

        ResultActions resultAction = mockMvc.perform(
                get("/role/deleteRole?id=1")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteRoleMongoException() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(ConfigurationStrings.AUTHORIZATION)).thenReturn("Authorization", "empId:101");


        doThrow(MongoException.class).when(roleService).deleteRole(anyString());

        ResultActions resultAction = mockMvc.perform(
                get("/role/deleteRole?id=1")
                        .header("Authorization", "empId:101")
        );
        resultAction.andExpect(status().isInternalServerError());
    }

    private AdminRoles getAdminRoles() {
        return new AdminRoles("1", "abc@teksystems.com");
    }

    private AdminRoles getAdminRoles1() {
        return new AdminRoles("2", "def@teksystems.com");
    }

    private List<AdminRoles> getAdminRolesList() {
        List<AdminRoles> toReturn = new ArrayList<>();
        toReturn.add(getAdminRoles());
        toReturn.add(getAdminRoles1());
        return toReturn;

    }

}