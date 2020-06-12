package com.javaguru.onlineshop.cartAndProduct;

import com.javaguru.onlineshop.product.Product;
import com.javaguru.onlineshop.product.ProductDTO;
import com.javaguru.onlineshop.product.ProductRepository;
import com.javaguru.onlineshop.shoppingcart.ShoppingCart;
import com.javaguru.onlineshop.shoppingcart.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CartProductServiceTest {

    @MockBean
    private CartProductService victim;

    @MockBean
    private ProductRepository productRepo;
    @MockBean
    private ShoppingCartRepository cartRepo;
    @MockBean
    private Product mockProduct;
    @MockBean
    private ShoppingCart mockCart;

    @Test
    void shouldAddProductToCart() {
        mockCart = createdCart();
        mockProduct = createdProduct();
        victim.addProductToCart(mockProduct.getId(), mockCart.getId());
        verify(victim).addProductToCart(mockProduct.getId(), mockCart.getId());
    }

    @Test
    void shouldRemoveProductFromCart() {
        mockCart = createdCart();
        mockProduct = createdProduct();
        victim.removeProductFromCart(mockProduct.getId(), mockCart.getId());
        verify(victim).removeProductFromCart(mockProduct.getId(), mockCart.getId());
    }

    @Test
    void shouldFindAllProductsInCart() {
        List<ProductDTO> dtoList = new ArrayList<>();

        ProductDTO dto = new ProductDTO();
        dto.setName("test name");
        dto.setRegularPrice(new BigDecimal("1.00"));
        dto.setDescription("test des");
        dto.setCategory("test cat");
        dto.setDiscount(new BigDecimal("0"));
        dto.setActualPrice(new BigDecimal("1.00"));

        ProductDTO dto2 = new ProductDTO();
        dto2.setName("test name 2");
        dto2.setRegularPrice(new BigDecimal("1.00"));
        dto2.setDescription("test des 2");
        dto2.setCategory("test cat 2");
        dto2.setDiscount(new BigDecimal("0"));
        dto2.setActualPrice(new BigDecimal("1.00"));

        dtoList.add(dto);
        dtoList.add(dto2);

        when(victim.findAllProductsInShoppingCart(1L)).thenReturn(dtoList);
        List<ProductDTO> list = victim.findAllProductsInShoppingCart(1L);
        assertThat(list).isEqualTo(dtoList);

    }

    @Test
    void shouldCalculateProductSumInCart() {
        List<ProductDTO> dtoList = new ArrayList<>();

        ProductDTO dto = new ProductDTO();
        dto.setName("test name");
        dto.setRegularPrice(new BigDecimal("1.00"));
        dto.setDescription("test des");
        dto.setCategory("test cat");
        dto.setDiscount(new BigDecimal("0"));
        dto.setActualPrice(new BigDecimal("1.00"));

        ProductDTO dto2 = new ProductDTO();
        dto2.setName("test name 2");
        dto2.setRegularPrice(new BigDecimal("1.00"));
        dto2.setDescription("test des 2");
        dto2.setCategory("test cat 2");
        dto2.setDiscount(new BigDecimal("0"));
        dto2.setActualPrice(new BigDecimal("1.00"));

        dtoList.add(dto);
        dtoList.add(dto2);
        BigDecimal sum = new BigDecimal("2.00");

        when(victim.getTotalSumOfProductsInCart(1L)).thenReturn(new BigDecimal("2.00"));
        assertThat(victim.getTotalSumOfProductsInCart(1L)).isEqualTo(sum);
    }

    private Product createdProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Testing product and cart");
        product.setRegularPrice(new BigDecimal("1.00"));
        product.setDescription("test des");
        product.setCategory("test cat");
        product.setDiscount(new BigDecimal("0"));
        product.setActualPrice(new BigDecimal("1.00"));
        return product;
    }

    private ShoppingCart createdCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setName("Testing cart and product");
        return cart;
    }

}