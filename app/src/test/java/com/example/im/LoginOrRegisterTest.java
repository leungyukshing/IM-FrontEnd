package com.example.im;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginOrRegisterTest {
    private LoginOrRegister obj;

    @Before
    public void setObj() {
        this.obj = new LoginOrRegister();
    }

    @Test
    public void validateEmail() {
        assertTrue(obj.validateEmail("lyc@gmail.com"));
        assertTrue(obj.validateEmail("lyc@outlook.com"));
        assertTrue(obj.validateEmail("lyc@qq.com"));
        assertTrue(obj.validateEmail("lyc@163.com"));
        assertFalse(obj.validateEmail("123.com"));
        assertFalse(obj.validateEmail("123@@gmail.com"));
    }
}