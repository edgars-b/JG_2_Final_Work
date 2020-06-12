package com.javaguru.onlineshop.warehouse;

import com.javaguru.onlineshop.product.Product;
import com.javaguru.onlineshop.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class WarehouseServiceTest {

    @Autowired
    private WarehouseService victim;

    @MockBean
    private ProductRepository repoProduct;
    @MockBean
    private WarehouseRepository repoWarehouse;

    @Test
    public void shouldFindWarehouseByID() {
        Warehouse warehouse = createdWarehouse();
        when(repoWarehouse.findById(1L)).thenReturn(Optional.of(warehouse));
        victim.findByID(1L);  // 1
        assertThat(victim.findByID(1L)).isEqualToComparingFieldByField(warehouse); // 2
        verify(repoWarehouse, times(2)).findById(1L);  // Find by ID called 2 times in this whole method
    }

    @Test
    public void shouldSaveWarehouse() {
        Warehouse warehouse = createdWarehouse();
        WarehouseDTO dto = new WarehouseDTO(warehouse.getId(), warehouse.getName(), warehouse.getMaxCapacity(), warehouse.getOccupiedCapacity());
        when(repoWarehouse.save(warehouse)).thenReturn(warehouse);
        assertThat(victim.save(dto)).isEqualToComparingFieldByField(warehouse);
    }

    @Test
    public void shouldSaveProductToWarehouse() {
        Warehouse warehouse = createdWarehouse();
        warehouse.setId(1L);
        Product product = createdProduct();
        product.setId(1L);
        when(repoWarehouse.findById(1L)).thenReturn(Optional.of(warehouse));
        when(repoProduct.findById(1L)).thenReturn(Optional.of(product));
        victim.saveProductToWarehouse(1L, 1L, 200);
        assertThat(product.getProductAvailability()).isEqualTo(200);
        assertThat(product.getWarehouseID()).isEqualTo(1L);
    }

    @Test
    public void shouldUpdateWarehouse() {
        Warehouse warehouse = createdWarehouse();
        when(repoWarehouse.findById(1L)).thenReturn(Optional.of(warehouse));
        warehouse.setName("Test it 2");
        when(repoWarehouse.save(warehouse)).thenReturn(warehouse);
        WarehouseDTO dto = new WarehouseDTO(warehouse.getId(), warehouse.getName(), warehouse.getMaxCapacity(), warehouse.getOccupiedCapacity());
        assertThat(victim.update(1L, dto)).isEqualToComparingFieldByField(warehouse);
    }

    @Test
    public void shouldDeleteWarehouse() {
        Warehouse warehouse = createdWarehouse();
        warehouse.setId(1L);
        when(repoWarehouse.findById(1L)).thenReturn(Optional.of(warehouse));
        victim.delete(1L);
        verify(repoWarehouse, times(1)).deleteById(1L);
    }

    @Test
    public void shouldFindAllProductsInWarehouse() {
        List<Product> list = new ArrayList<>();
        Product product = createdProduct();
        product.setWarehouseID(1L);
        product.setProductAvailability(100);
        list.add(product);
        when(repoWarehouse.showAllProductsInWarehouse(1L)).thenReturn(list);
        victim.showAllProductsInWarehouse(1L);
        assertThat(list).extracting(Product::getName,
                Product::getRegularPrice,
                Product::getDescription,
                Product::getCategory,
                Product::getDiscount,
                Product::getActualPrice,
                Product::getProductAvailability,
                Product::getWarehouseID)
                .containsExactly(tuple("test name", new BigDecimal("1.00"), "test des", "test cat", new BigDecimal("0"), new BigDecimal("1.00"), 100, 1L));
        verify(repoWarehouse).showAllProductsInWarehouse(1L);
    }

    @Test
    public void shouldShowProductAvailabilityInWarehouse() {
        Warehouse warehouse = createdWarehouse();
        warehouse.setId(1L);
        Product product = createdProduct();
        product.setWarehouseID(1L);
        product.setProductAvailability(100);
        when(repoWarehouse.findById(1L)).thenReturn(Optional.of(warehouse));
        when(repoProduct.findById(1L)).thenReturn(Optional.of(product));
        when(repoWarehouse.showProductAvailability(1L, 1L)).thenReturn(100);
        int result = victim.showProductAvailability(1L, 1L);
        assertEquals(100, result);
        verify(repoWarehouse, times(1)).showProductAvailability(1L, 1L);
    }

    @Test
    public void shouldRemoveProductFromWarehouse() {
        Warehouse warehouse = createdWarehouse();
        warehouse.setId(1L);
        Product product = createdProduct();
        product.setId(1L);
        when(repoWarehouse.findById(1L)).thenReturn(Optional.of(warehouse));
        when(repoProduct.findById(1L)).thenReturn(Optional.of(product));
        victim.removeProductFromWarehouse(1L, 1L);
        assertThat(product.getWarehouseID()).isEqualTo(null);
    }

    private Warehouse createdWarehouse() {
        Warehouse warehouse = new Warehouse();
        warehouse.setName("Test warehouse 1");
        warehouse.setMaxCapacity(5000);
        warehouse.setProducts(new HashSet<>());
        warehouse.setOccupiedCapacity(0);
        return warehouse;
    }

    private Product createdProduct() {
        Product product = new Product();
        product.setName("test name");
        product.setRegularPrice(new BigDecimal("1.00"));
        product.setDescription("test des");
        product.setCategory("test cat");
        product.setDiscount(new BigDecimal("0"));
        product.setActualPrice(new BigDecimal("1.00"));
        return product;
    }
}