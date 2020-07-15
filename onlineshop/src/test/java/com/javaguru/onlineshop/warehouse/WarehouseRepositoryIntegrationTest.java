package com.javaguru.onlineshop.warehouse;

import com.javaguru.onlineshop.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Sql(value = "/scripts/warehouses/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/scripts/warehouses/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class WarehouseRepositoryIntegrationTest {

    @Autowired
    private WarehouseRepository victim;

    @Test
    public void shouldShowAllProductsInWarehouse() {
        List<Product> list = victim.showAllProductsInWarehouse(1L);
        assertThat(list).extracting(Product::getId,
                Product::getName,
                Product::getRegularPrice,
                Product::getDescription,
                Product::getCategory,
                Product::getDiscount,
                Product::getActualPrice,
                Product::getProductAvailability,
                Product::getWarehouseID)
                .containsExactly(tuple(1L, "Test name 1", new BigDecimal("1.00"), "test description 1", "test category 1", new BigDecimal("0.00"), new BigDecimal("1.00"), 100, 1L));
    }

    @Test
    public void shouldShowProductAvailability() {
        int result = victim.showProductAvailability(1L, 1L);
        assertEquals(100, result);
    }

}