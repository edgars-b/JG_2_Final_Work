package com.javaguru.onlineshop.warehouse;

import com.javaguru.onlineshop.product.ProductDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/warehouses")
public class WarehouseController {

    private final WarehouseService service;

    public WarehouseController(WarehouseService service) {
        this.service = service;
    }

    @GetMapping
    public List<WarehouseDTO> findAllWarehouses() {
        return service.findAll();
    }

    @GetMapping("/find/{id}")
    public WarehouseDTO findWarehouseByID(@PathVariable Long id){
        return service.findByID(id);
    }

    @GetMapping("/{warehouseID}")
    public List<ProductDTO> findAllProducts(@PathVariable Long warehouseID) {
        return service.showAllProductsInWarehouse(warehouseID);
    }

    @GetMapping("/{warehouseID}/product/{productID}")
    public Integer showProductAvailability(@PathVariable Long productID, @PathVariable Long warehouseID){
        return service.showProductAvailability(productID, warehouseID);
    }

    @PostMapping
    public ResponseEntity<Void> saveWarehouse(@Valid @RequestBody WarehouseDTO dto) {
        WarehouseDTO created = service.save(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{warehouseID}/product/{productID}/amount/{amount}")
    public void addProductToWarehouse(@PathVariable Long productID, @PathVariable Long warehouseID, @PathVariable int amount) {
        service.saveProductToWarehouse(productID, warehouseID, amount);
    }

    @PutMapping("/{id}")
    public void updateWarehouse(@PathVariable Long id, @RequestBody WarehouseDTO dto) {
        service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteWarehouse(@PathVariable Long id) {
        service.delete(id);
    }

    @DeleteMapping("/{warehouseID}/product/{productID}")
    public void deleteProductFromWarehouse(@PathVariable Long productID, @PathVariable Long warehouseID) {
        service.removeProductFromWarehouse(productID, warehouseID);
    }
}
