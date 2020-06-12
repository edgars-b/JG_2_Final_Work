package com.javaguru.onlineshop.shoppingcart;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ShoppingCartControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ShoppingCartRepository victim;

    @Test
    @Sql(value = "/scripts/carts/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/carts/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldSaveCart() throws Exception {
        mock.perform(post("/api/v1/shopping-carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdCartJSON()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/v1/shopping-carts/3")));
    }

    @Test
    @Sql(value = "/scripts/carts/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/carts/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindCartByID() throws Exception {
        mock.perform(get("/api/v1/shopping-carts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test name 1"));
    }

    @Test
    @Sql(value = "/scripts/carts/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/carts/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindAllCarts() throws Exception {
        mock.perform(get("/api/v1/shopping-carts"))
                .andExpect(status().isOk());
        List<ShoppingCart> list = victim.findAll();
        assertThat(list).extracting(ShoppingCart::getId, ShoppingCart::getName)
                .containsExactly(tuple(1L, "Test name 1"), tuple(2L, "Test name 2"));
    }

    @Test
    @Sql(value = "/scripts/carts/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/carts/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldEditCartByID() throws Exception {
        mock.perform(put("/api/v1/shopping-carts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdCartJSON()))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = "/scripts/carts/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/carts/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteCart() throws Exception {
        mock.perform(delete("/api/v1/shopping-carts/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowNotFoundException_ShoppingCart() {
        long id = 3L;
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> victim.findById(id).orElseThrow(() -> new NotFoundException("No such cart found. ID - " + id)));
        assertTrue(thrown.getMessage().contains("No such cart found. ID - 3"));
    }

    private String createdCartJSON() throws JSONException {
        return new JSONObject().put("name", "Test name 3").toString();
    }
}