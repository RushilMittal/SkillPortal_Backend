package com.cdac.skillportal.service;

import org.junit.Before;
import org.junit.Test;


import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RequestWrapperTest {

    private Map<String, String[]> modifiableParameters;
    private Map<String, String[]> invalidModifiableParameters;
    private Map<String, String[]> allParameters = null;

    RequestWrapper requestWrapper ;
    RequestWrapper invalidRequestWrapper;

    @Before
    public void setUp() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);

        modifiableParameters = new TreeMap<>();
        modifiableParameters.put("Token" , new String[]{"abcdefg"});

        invalidModifiableParameters = new TreeMap<>();

        requestWrapper = new RequestWrapper(request,modifiableParameters);
        invalidRequestWrapper = new RequestWrapper(request,invalidModifiableParameters);
    }

    @Test
    public void getParameter() {
        String expected = requestWrapper.getParameter("Token");
        assertThat("abcdefg",is(expected));
    }
    @Test
    public void getParameterInvalid() {
        String expected = invalidRequestWrapper.getParameter("Token");
        assertThat(null,is(expected));
    }


    @Test
    public void getParameterMap() {

        Map<String, String[]> expected = requestWrapper.getParameterMap();
        assertThat(true,is(expected.containsKey("Token")));
    }

    @Test
    public void getParameterNames() {
        Enumeration<String> expected = requestWrapper.getParameterNames();
        assertThat(true,is(expected.hasMoreElements()));
    }

    @Test
    public void getParameterValues() {
        String[] expected = requestWrapper.getParameterValues("Token");
        assertThat("abcdefg",is(expected[0]));
    }
}