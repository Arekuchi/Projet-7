package com.nnk.springboot.controllers;

import com.nnk.springboot.repositories.BidListRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidListControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BidListRepository bidListRepository;

    @WithMockUser(authorities = "USER")
    @Test
    public void showBidListTestUser() throws Exception {
        mockMvc.perform(get("/bidList/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void showBidListTestAdmin() throws Exception {
        mockMvc.perform(get("/bidList/list")).andExpect(status().isForbidden());
    }

}
