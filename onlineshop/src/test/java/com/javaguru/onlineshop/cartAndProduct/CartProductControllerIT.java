package com.javaguru.onlineshop.cartAndProduct;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import com.javaguru.onlineshop.product.Product;
import com.javaguru.onlineshop.product.ProductRepository;
import com.javaguru.onlineshop.shoppingcart.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CartProductControllerIT {

    @Autowired
    private MockMvc mock;

    @Autowired
    private ProductRepository victim;
    @Autowired
    private ShoppingCartRepository victim2;

    @Test
    public void shouldSaveProductInShoppingCart() throws Exception {
        mock.perform(post("/api/v1/basket/product/1/shopping-cart/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAllProductsInCart() throws Exception {
        List<Product> list = victim.findAllProductsInCart(1L);
        mock.perform(get("/api/v1/basket/1"))
                .andExpect(status().isOk());
        assertThat(list).contains();

    }

    @Test
    void shouldGetSumOfProductsInCart() throws Exception {
        BigDecimal expected = victim.getTotalSumOfProductsInCart(1L).orElseThrow(() -> new NotFoundException("Not found cart with ID - " + 1L));
        mock.perform(get("/api/v1/basket/total/1"))
                .andExpect(status().isOk());
        assertEquals(expected, new BigDecimal(""));
    }

    @Test
    void shouldRemoveProductToShoppingCart() throws Exception {
        mock.perform(delete("/api/v1/basket/remove/product/1/shopping-cart/1"))
                .andExpect(status().isOk());
    }

}