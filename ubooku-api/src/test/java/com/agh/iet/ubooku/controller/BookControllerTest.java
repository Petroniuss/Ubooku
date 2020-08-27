package com.agh.iet.ubooku.controller;

import com.agh.iet.ubooku.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;


@RunWith(SpringJUnit4ClassRunner.class)
public class BookControllerTest {

    @Mock
    BookService bookService;

    @InjectMocks
    BookController bookController;


    private MockMvc mock;

    @Before
    public void setUp() {
        mock = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test()
    public void getBookFailsWhenRandomId() throws Exception {

        String id = UUID.randomUUID().toString();


        mock.perform(MockMvcRequestBuilders
                .get("/api/books/" + id + "/get"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}