package com.javaguru.onlineshop.warehouse;

import com.javaguru.onlineshop.product.Product;
import org.json.JSONArray;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.hamcrest.Matchers.endsWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // for @BeforeAll setUp() to not male it static
@WithMockUser(username = "test login", roles = {"ADMIN"})
class WarehouseControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mock;

    @Autowired
    private WarehouseRepository victim;

    @BeforeAll
    public void setup() {
        mock = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldSaveWarehouse() throws Exception {
        mock.perform(post("/api/v1/warehouses").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdWarehouseJSON()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/v1/warehouses/2")));
    }

    @Test
    @Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldEditWarehouseByID() throws Exception {
        mock.perform(put("/api/v1/warehouses/1").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(createdWarehouseJSON()))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteWarehouseByID() throws Exception {
        mock.perform(delete("/api/v1/warehouses/1").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindWarehouseByID() throws Exception {
        mock.perform(get("/api/v1/warehouses/find/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test warehouse 1"))
                .andExpect(jsonPath("$.maxCapacity").value("5000"))
                .andExpect(jsonPath("$.occupiedCapacity").value("0"));
    }

    @Test
    @Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldFindAllProducts() throws Exception {
        mock.perform(post("/api/v1/warehouses/1/product/2/amount/200").with(csrf()))
                .andExpect(status().isOk());
        mock.perform(get("/api/v1/warehouses/1"))
                .andExpect(status().isOk());
        List<Product> list = victim.showAllProductsInWarehouse(1L);
        assertThat(list)
                .extracting(Product::getId,
                        Product::getName,
                        Product::getRegularPrice,
                        Product::getDescription,
                        Product::getCategory,
                        Product::getDiscount,
                        Product::getActualPrice,
                        Product::getProductAvailability,
                        Product::getWarehouseID)
                .containsExactly(tuple(1L, "Test name 1", new BigDecimal("1.00"), "test description 1", "test category 1", new BigDecimal("0.00"), new BigDecimal("1.00"), 100, 1L),
                        tuple(2L, "Test name 2", new BigDecimal("1.00"), "test description 2", "test category 2", new BigDecimal("0.00"), new BigDecimal("1.00"), 200, 1L));
    }

    @Test
    @Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldAddProductToWarehouse() throws Exception {
        mock.perform(post("/api/v1/warehouses/1/product/2/amount/100").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldShowProductAvailability() throws Exception {
        mock.perform(get("/api/v1/warehouses/1/product/1").with(csrf()))
                .andExpect(status().isOk());
        int result = victim.showProductAvailability(1L, 1L);
        assertEquals(100, result);
    }

    @Test
    @Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void shouldDeleteProductFromWarehouse() throws Exception {
        mock.perform(delete("/api/v1/warehouses/1/product/1").with(csrf()))
                .andExpect(status().isOk());
    }

    private String createdWarehouseJSON() throws JSONException {
        JSONArray productList = new JSONArray();
        JSONObject product = new JSONObject();
        productList.put(0, product);
        return new JSONObject().put("name", "Test name 2")
                .put("products", productList)
                .put("maxCapacity", 5000)
                .put("occupiedCapacity", 200)
                .toString();
    }

}