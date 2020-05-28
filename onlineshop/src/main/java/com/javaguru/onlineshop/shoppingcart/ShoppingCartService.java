package com.javaguru.onlineshop.shoppingcart;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartService {

    private final ShoppingCartRepository repository;

    public ShoppingCartService(ShoppingCartRepository repository) {
        this.repository = repository;
    }

    public List<ShoppingCartDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(cart -> new ShoppingCartDTO(cart.getId(), cart.getName()))
                .collect(Collectors.toList());
    }

    public ShoppingCartDTO findByID(Long id) {
        ShoppingCart cart = repository.findById(id).orElseThrow(() -> new NotFoundException("No such cart found. ID - " + id));
        return new ShoppingCartDTO(cart.getId(), cart.getName());
    }

    public ShoppingCartDTO save(ShoppingCartDTO cartDTO) {
        ShoppingCart cart = new ShoppingCart();
        cart.setName(cartDTO.getName());
        repository.save(cart);
        return new ShoppingCartDTO(cart.getId(), cart.getName());
    }

    public ShoppingCartDTO update(Long id, ShoppingCartDTO dto) {
        ShoppingCart cart = repository.findById(id).orElseThrow(() -> new NotFoundException("No such cart found. ID -" + id));
        cart.setName(dto.getName());
        repository.save(cart);
        return new ShoppingCartDTO(cart.getId(), cart.getName());
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException("No such cart found. ID -" + id));
        repository.deleteById(id);
    }

}
