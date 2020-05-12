package com.javaguru.onlineshop.shoppingcart;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(value = "/scripts/carts/truncate_carts.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/scripts/carts/after_restore_carts.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ShoppingCartControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ShoppingCartRepository victim;

    @Test
    public void shouldSaveCart() throws Exception {
        mock.perform(post("/api/v1/shopping-carts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdCartJSON()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/v1/shopping-carts/1")));
    }

    @Test
    public void shouldFindCartByID() throws Exception {
        victim.save(createdCart());
        mock.perform(get("/api/v1/shopping-carts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test name"));
    }

    @Test
    public void shouldFindAllCarts() throws Exception {
        victim.save(createdCart());
        mock.perform(get("/api/v1/shopping-carts"))
                .andExpect(status().isOk());
        List<ShoppingCart> list = victim.findAll();
        assertThat(list).extracting(ShoppingCart::getId, ShoppingCart::getName)
                .containsExactly(tuple(1L, "Test name"));
    }

    @Test
    public void shouldEditCartByID() throws Exception {
        victim.save(createdCart());
        mock.perform(put("/api/v1/shopping-carts/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdCartJSON()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteCart() throws Exception {
        victim.save(createdCart());
        mock.perform(delete("/api/v1/shopping-carts/1"))
                .andExpect(status().isOk());
    }

    private String createdCartJSON() throws JSONException {
        return new JSONObject().put("name", "Test name").toString();
    }

    private ShoppingCart createdCart(){
        ShoppingCart cart = new ShoppingCart();
        cart.setName("Test name");
        return cart;
    }
}