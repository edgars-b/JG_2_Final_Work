package com.javaguru.onlineshop.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService victim;

    @MockBean
    private ProductRepository repo;

    @Test
    void shouldSaveProduct() {
        Product product = createdProduct();
        ProductDTO dto = new ProductDTO(product.getId(),
                product.getName(),
                product.getRegularPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getDiscount(),
                product.getActualPrice(),
                product.getWarehouseID(),
                product.getProductAvailability());

        when(repo.save(product)).thenReturn(product);
        assertThat(victim.save(dto)).isEqualToComparingFieldByField(product);
    }

    @Test
    void shouldFindAllProducts() {
        List<ProductDTO> dtoList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        Product product = createdProduct();
        ProductDTO dto = new ProductDTO(product.getId(),
                product.getName(),
                product.getRegularPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getDiscount(),
                product.getActualPrice(),
                product.getWarehouseID(),
                product.getProductAvailability());

        Product product2 = new Product();
        product2.setName("test 2");
        product2.setRegularPrice(new BigDecimal("1.00"));
        product2.setDescription("test 2");
        product2.setCategory("test 2");
        product2.setDiscount(new BigDecimal("0"));
        product2.setActualPrice(new BigDecimal("1.00"));

        ProductDTO dto2 = new ProductDTO(product2.getId(),
                product2.getName(),
                product2.getRegularPrice(),
                product2.getDescription(),
                product2.getCategory(),
                product2.getDiscount(),
                product2.getActualPrice(),
                product2.getWarehouseID(),
                product2.getProductAvailability());

        dtoList.add(dto);
        dtoList.add(dto2);
        productList.add(product);
        productList.add(product2);

        when(repo.findAll()).thenReturn(productList);
        assertThat(victim.findAll()).isEqualToComparingOnlyGivenFields(dtoList);
    }

    @Test
    void shouldFindProductsByID() {
        Product product = createdProduct();
        when(repo.findById(1L)).thenReturn(Optional.of(product));
        victim.findByID(1L);
        assertThat(victim.findByID(1L)).isEqualToComparingFieldByField(product);  // Find by ID called 1st time in this method
        verify(repo, times(2)).findById(1L);  // Find by ID called 2nd time in this method
    }

    @Test
    void shouldEditProduct() {
        Product product = createdProduct();
        when(repo.findById(1L)).thenReturn(Optional.of(product));

        product.setDescription("test desc");
        when(repo.save(product)).thenReturn(product);

        ProductDTO dto = new ProductDTO(product.getId(),
                product.getName(),
                product.getRegularPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getDiscount(),
                product.getActualPrice(),
                product.getWarehouseID(),
                product.getProductAvailability());

        assertThat(victim.update(1L, dto)).isEqualToComparingFieldByField(product);
    }

    @Test
    void shouldDeleteProduct() {
        Product product = createdProduct();
        when(repo.findById(1L)).thenReturn(Optional.of(product));
        victim.delete(1L);
        when(repo.existsById(product.getId())).thenReturn(false);
        assertFalse(repo.existsById(product.getId()));
        verify(repo).deleteById(1L);
    }

    private Product createdProduct() {
        Product product = new Product();
        product.setName("test");
        product.setRegularPrice(new BigDecimal("1.00"));
        product.setDescription("test");
        product.setCategory("test");
        product.setDiscount(new BigDecimal("0"));
        product.setActualPrice(new BigDecimal("1.00"));
        product.setWarehouseID(1L);
        product.setProductAvailability(200);
        return product;
    }

}