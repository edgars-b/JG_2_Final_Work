package com.javaguru.onlineshop.shoppingcart;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService service;

    public ShoppingCartController(ShoppingCartService service) {
        this.service = service;
    }

    @GetMapping
    public List<ShoppingCartDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ShoppingCartDTO findByID(@PathVariable Long id) {
        return service.findByID(id);
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ShoppingCartDTO dto) {
        ShoppingCartDTO created = service.save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody ShoppingCartDTO dto) {
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}
