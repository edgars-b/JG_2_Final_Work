package com.javaguru.onlineshop.cartAndProduct;

import com.javaguru.onlineshop.product.ProductDTO;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/basket")
public class CartProductController {

    private final CartProductService service;

    public CartProductController(CartProductService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public List<ProductDTO> findAllProductsInCart(@PathVariable Long id) {
        return service.findAllProductsInShoppingCart(id);
    }

    @GetMapping("/total/{id}")
    public BigDecimal getSumOfProductsInCart(@PathVariable Long id) {
        return service.getTotalSumOfProductsInCart(id);
    }

    @PostMapping("/product/{productID}/shopping-cart/{shoppingCartID}")
    public void addProductToShoppingCart(@PathVariable Long productID, @PathVariable Long shoppingCartID) {
        service.addProductToCart(productID, shoppingCartID);
    }

    @DeleteMapping("/remove/product/{productID}/shopping-cart/{shoppingCartID}")
    public void removeProductFromShoppingCart(@PathVariable Long productID, @PathVariable Long shoppingCartID) {
        service.removeProductFromCart(productID, shoppingCartID);
    }
}
