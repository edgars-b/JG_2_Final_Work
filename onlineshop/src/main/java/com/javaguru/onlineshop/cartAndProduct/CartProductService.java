package com.javaguru.onlineshop.cartAndProduct;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import com.javaguru.onlineshop.product.Product;
import com.javaguru.onlineshop.product.ProductDTO;
import com.javaguru.onlineshop.product.ProductRepository;
import com.javaguru.onlineshop.shoppingcart.ShoppingCart;
import com.javaguru.onlineshop.shoppingcart.ShoppingCartRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartProductService {

    private final ProductRepository productRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public CartProductService(ProductRepository productRepository, ShoppingCartRepository shoppingCartRepository) {
        this.productRepository = productRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    public void addProductToCart(Long productID, Long cartID) {
        Product product = productRepository.findById(productID).orElseThrow(() -> new NotFoundException("Product not found - ID: " + productID));
        ShoppingCart cart = shoppingCartRepository.findById(cartID).orElseThrow(() -> new NotFoundException("Shopping cart not found - ID: " + cartID));
        product.addToShoppingCart(cart);
        productRepository.save(product);
    }

    public void removeProductFromCart(Long productID, Long cartID) {
        Product product = productRepository.findById(productID).orElseThrow(() -> new NotFoundException("Product not found - ID: " + productID));
        ShoppingCart cart = shoppingCartRepository.findById(cartID).orElseThrow(() -> new NotFoundException("Shopping cart not found - ID: " + cartID));
        product.removeFromShoppingCart(cart);
        productRepository.delete(product);
    }

    public List<ProductDTO> findAllProductsInShoppingCart(@RequestParam Long id) {
        ShoppingCart cart = shoppingCartRepository.findById(id).orElseThrow(() -> new NotFoundException("No such cart found. ID - " + id));
        Long foundID = cart.getId();
        List<ProductDTO> list = productRepository.findAllProductsInCart(foundID)
                .stream()
                .map(product -> new ProductDTO(product.getId(),
                        product.getName(),
                        product.getRegularPrice(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getDiscount(),
                        product.getActualPrice()))
                .collect(Collectors.toList());
        return list;
    }

    public BigDecimal getTotalSumOfProductsInCart(Long id) {
        BigDecimal sum = productRepository.getTotalSumOfProductsInCart(id).orElseThrow(() -> new NotFoundException("Not found such cart - ID: " + id));
        return sum;
    }
}
