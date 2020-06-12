package com.javaguru.onlineshop.product;

import com.javaguru.onlineshop.exceptions.NotFoundException;
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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = "/scripts/products/before_each_test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/scripts/products/after_each_test.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class ProductRepositoryIntegrationTest {

    @Autowired
    private ProductRepository victim;

    @Test
    public void shouldFindAllProductsInCart() {
        List<Product> list = victim.findAllProductsInCart(1L);
        assertThat(list)
                .extracting(
                        Product::getId,
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
    public void shouldCalculateSumOfProductsInCart() {
        BigDecimal sum = victim.getTotalSumOfProductsInCart(1L).orElseThrow(() -> new NotFoundException("Not found ID - " + 1));
        assertThat(sum).isEqualTo(new BigDecimal("2.00"));
    }

}