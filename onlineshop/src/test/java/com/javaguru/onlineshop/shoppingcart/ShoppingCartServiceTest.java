package com.javaguru.onlineshop.shoppingcart;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShoppingCartServiceTest {

    @Autowired
    private ShoppingCartService victim;

    @MockBean
    private ShoppingCartRepository repo;

    @Test
    void shouldSaveCart() {
        ShoppingCart cart = createdShoppingCart();
        ShoppingCartDTO dto = new ShoppingCartDTO(cart.getId(), cart.getName());

        when(repo.save(cart)).thenReturn(cart);
        assertThat(victim.save(dto)).isEqualToComparingFieldByField(dto);
    }

    @Test
    void shouldFindAllCarts() {
        List<ShoppingCartDTO> dtoList = new ArrayList<>();
        List<ShoppingCart> productList = new ArrayList<>();

        ShoppingCart cart = createdShoppingCart();
        ShoppingCartDTO dto = new ShoppingCartDTO(cart.getId(), cart.getName());

        ShoppingCart cart2 = new ShoppingCart();
        cart2.setName("test 2");

        ShoppingCartDTO dto2 = new ShoppingCartDTO(cart2.getId(), cart2.getName());

        dtoList.add(dto);
        dtoList.add(dto2);
        productList.add(cart);
        productList.add(cart2);

        when(repo.findAll()).thenReturn(productList);
        assertThat(victim.findAll()).isEqualToComparingOnlyGivenFields(dtoList);
    }

    @Test
    void shouldFindCartByID() {
        ShoppingCart cart = createdShoppingCart();
        when(repo.findById(1L)).thenReturn(Optional.of(cart));
        assertThat(victim.findByID(1L)).isEqualToComparingFieldByField(cart);
    }

    @Test
    void shouldEditCart() {
        ShoppingCart cart = createdShoppingCart();
        when(repo.findById(1L)).thenReturn(Optional.of(cart));

        when(repo.save(cart)).thenReturn(cart);

        ShoppingCartDTO dto = new ShoppingCartDTO(cart.getId(),cart.getName());

        assertThat(victim.update(1L, dto)).isEqualToComparingFieldByField(dto);
    }

    @Test
    void shouldDeleteCart() {
        ShoppingCart cart = createdShoppingCart();
        when(repo.findById(1L)).thenReturn(Optional.of(cart));
        when(repo.existsById(cart.getId())).thenReturn(false);
        assertFalse(repo.existsById(cart.getId()));
    }

    private ShoppingCart createdShoppingCart(){
        ShoppingCart cart = new ShoppingCart();
        cart.setName("Test name for cart");
        return cart;
    }

}