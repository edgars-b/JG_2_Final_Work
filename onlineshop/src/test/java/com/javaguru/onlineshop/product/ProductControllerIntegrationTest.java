package com.javaguru.onlineshop.product;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql(value = "/scripts/products/truncate_products.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/scripts/products/after_restore_product.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProductRepository victim;

    @Test
    public void shouldSaveProduct() throws Exception {
        mock.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductJSON()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/v1/products/1")));
    }

    @Test
    public void shouldFindProductByID() throws Exception {
        victim.save(createdProduct());

        mock.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test name 2"))
                .andExpect(jsonPath("$.regularPrice").value(1))
                .andExpect(jsonPath("$.description").value("test description 2"))
                .andExpect(jsonPath("$.category").value("test category 2"))
                .andExpect(jsonPath("$.discount").value(0))
                .andExpect(jsonPath("$.actualPrice").value(1));
    }

    @Test
    public void shouldFindAllProducts() throws Exception {
        victim.save(createdProduct());
        mock.perform(get("/api/v1/products"))
                .andExpect(status().isOk());
        List<Product> list = victim.findAll();
        assertThat(list).extracting(Product::getId,
                Product::getName,
                Product::getRegularPrice,
                Product::getDescription,
                Product::getCategory,
                Product::getDiscount,
                Product::getActualPrice)
                .containsExactly(tuple(1L, "Test name 2", new BigDecimal("1.00"), "test description 2", "test category 2", new BigDecimal("0"), new BigDecimal("1.00")));
    }

    @Test
    public void shouldDeleteProductByID() throws Exception {
        victim.save(createdProduct());
        mock.perform(delete("/api/v1/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldEditProductByID() throws Exception {
        victim.save(createdProduct());
        mock.perform(put("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductJSON()))
                .andExpect(status().isOk());
    }

    private String createProductJSON() throws JSONException {
        return new JSONObject()
                .put("name", "Test name")
                .put("regularPrice", "1.00")
                .put("description", "test description")
                .put("category", "test category")
                .put("discount", "0")
                .put("actualPrice", "1.00")
                .toString();
    }

    private Product createdProduct(){
        Product product = new Product();
        product.setName("Test name 2");
        product.setRegularPrice(new BigDecimal("1.00"));
        product.setDescription("test description 2");
        product.setCategory("test category 2");
        product.setDiscount(new BigDecimal("0"));
        product.setActualPrice(new BigDecimal("1.00"));
        return product;
    }

}