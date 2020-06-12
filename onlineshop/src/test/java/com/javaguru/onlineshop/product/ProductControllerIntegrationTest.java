package com.javaguru.onlineshop.product;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProductRepository victim;

    @Test
    @Sql(value = "/scripts/products/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/products/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldSaveProduct() throws Exception {
        mock.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductJSON()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/v1/products/3"))); // @Sql before already inserted 2 objects so expecting endsWith id=3
    }

    @Test
    @Sql(value = "/scripts/products/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/products/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindProductByID() throws Exception {

        mock.perform(get("/api/v1/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test name 1"))
                .andExpect(jsonPath("$.regularPrice").value(1))
                .andExpect(jsonPath("$.description").value("test description 1"))
                .andExpect(jsonPath("$.category").value("test category 1"))
                .andExpect(jsonPath("$.discount").value(0))
                .andExpect(jsonPath("$.actualPrice").value(1))
                .andExpect(jsonPath("$.productAvailability").value(0))
                .andExpect(jsonPath("$.warehouseID").value(nullValue()));
    }

    @Test
    @Sql(value = "/scripts/products/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/products/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindAllProducts() throws Exception {
        mock.perform(get("/api/v1/products"))
                .andExpect(status().isOk());
        List<Product> list = victim.findAll();
        assertThat(list).extracting(Product::getId,
                Product::getName,
                Product::getRegularPrice,
                Product::getDescription,
                Product::getCategory,
                Product::getDiscount,
                Product::getActualPrice,
                Product::getProductAvailability,
                Product::getWarehouseID)
                .containsExactly(tuple(1L, "Test name 1", new BigDecimal("1.00"), "test description 1", "test category 1", new BigDecimal("0.00"), new BigDecimal("1.00"), 0, null),
                        tuple(2L, "Test name 2", new BigDecimal("1.00"), "test description 2", "test category 2", new BigDecimal("0.00"), new BigDecimal("1.00"), 0, null));
    }

    @Test
    @Sql(value = "/scripts/products/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/products/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteProductByID() throws Exception {
        mock.perform(delete("/api/v1/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = "/scripts/products/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/products/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldEditProductByID() throws Exception {
        mock.perform(put("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createProductJSON()))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowNotFoundException_Product() {
        long id = 3L;
        NotFoundException thrown = assertThrows(NotFoundException.class, () -> victim.findById(id).orElseThrow(() -> new NotFoundException("No such product found. ID - " + id)));
        assertTrue(thrown.getMessage().contains("No such product found. ID - 3"));
    }

    private String createProductJSON() throws JSONException {
        return new JSONObject()
                .put("name", "Test name")
                .put("regularPrice", "1.00")
                .put("description", "test description")
                .put("category", "test category")
                .put("discount", "0")
                .put("actualPrice", "1.00")
                .put("productAvailability", 0)
                .put("warehouseID", null)
                .toString();
    }

}