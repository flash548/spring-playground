package com.galvanize.demo;

import com.galvanize.demo.LessonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)

@SpringBootTest
@AutoConfigureMockMvc
public class LessonControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    LessonRepository repository;

    @Test
    public void getLessions() throws Exception {

        // POST an item
        this.mvc.perform(post("/lessons/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"title\": \"this is yet another title2\",\n" +
                        "  \"delivered_on\": \"2020-03-01\"\n" +
                        "}"))
                .andExpect(status().isOk());

        // POST an item
        this.mvc.perform(post("/lessons/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"title\": \"this is yet another title\",\n" +
                        "  \"delivered_on\": \"2020-01-01\"\n" +
                        "}"))
                .andExpect(status().isOk());
        // GET an item
        MockHttpServletRequestBuilder request = get("/lessons/all").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        // check
        this.mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", equalTo("this is yet another title2") ));
    }

    @Test
    @Transactional
    @Rollback
    public void addLessons() throws Exception {
        // POST an item
        this.mvc.perform(post("/lessons/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"title\": \"this is yet another title\",\n" +
                        "  \"delivered_on\": \"2020-01-01\"\n" +
                        "}"))
                .andExpect(status().isOk());
        // GET the item
        this.mvc.perform(get("/lessons/all")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title", is("this is yet another title2")));
    }
}
