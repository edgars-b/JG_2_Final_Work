package com.javaguru.onlineshop.comments;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@WithMockUser(username = "test login", roles = {"ADMIN"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
        // needed for @BeforeAll setUp() so not to make it static
class CommentControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private CommentRepository victim;

    @Autowired
    private WebApplicationContext context;

    @BeforeAll
    public void setUp() {
        mock = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Sql(value = "/scripts/comments/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/comments/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindAllComments() throws Exception {
        mock.perform(get("/api/v1/comments").with(csrf()))
                .andExpect(status().isOk());
        List<Comment> list = victim.findAll();
        assertThat(list).extracting(Comment::getId,
                                    Comment::getMessage,
                                    Comment::getProductID)
                        .containsExactly(tuple(1L, "test message", 1L),
                                         tuple(2L, "test message 2", 1L));
    }

    @Test
    @Sql(value = "/scripts/comments/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/comments/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindCommentByID() throws Exception {
        mock.perform(get("/api/v1/comments/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.message").value("test message"))
                .andExpect(jsonPath("$.productID").value(1));
        // not checking Timestamp, because working with real database and value all the time will be different, depending upon running the test.
    }

    @Test
    @Sql(value = "/scripts/comments/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/comments/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldSaveComment() throws Exception {
        mock.perform(post("/api/v1/comments").with(csrf()).contentType(MediaType.APPLICATION_JSON).content(createCommentJSON()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/v1/comments/3")));
    }

    @Test
    @Sql(value = "/scripts/comments/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/comments/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldEditCommentByID() throws Exception {
        mock.perform(put("/api/v1/comments/1").with(csrf()).content(createCommentJSON()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = "/scripts/comments/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/comments/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteCommentByID() throws Exception {
        mock.perform(delete("/api/v1/comments/1").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowNotFoundException_Comment() {
        long id = 3L;
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> victim.findById(id).orElseThrow(() -> new NotFoundException("No such comment found. ID - " + id)));
        assertTrue(thrown.getMessage().contains("No such comment found. ID - 3"));
    }


    private String createCommentJSON() throws JSONException {
        return new JSONObject()
                .put("message", "test message 3")
                .put("productID", 1)
                .toString();
    }

}