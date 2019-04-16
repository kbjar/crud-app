package com.aquent.crudapp.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    /**
     * Basic examples just to throw in some tests
     * TODO: more tests!
     */

    @Autowired
    private MockMvc mvc;

    @Test
    public void testHomeRedirectToPersonList() throws Exception {
        this.mvc.perform(get("/")).andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/person/list"));
    }

    @Test
    public void testViewPersonListGet() throws Exception {
        this.mvc.perform(get("/person/list")).andExpect(status().isOk())
                .andExpect(view().name("person/list"));
    }

}