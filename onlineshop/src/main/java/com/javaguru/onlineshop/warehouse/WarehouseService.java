package com.javaguru.onlineshop.warehouse;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import com.javaguru.onlineshop.product.Product;
import com.javaguru.onlineshop.product.ProductDTO;
import com.javaguru.onlineshop.product.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

    private final WarehouseRepository repository;
    private final ProductRepository productRepository;

    public WarehouseService(WarehouseRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public WarehouseDTO findByID(Long id) {
        Warehouse warehouse = repository.findById(id).orElseThrow(() -> new NotFoundException("No such warehouse found: ID - " + id));
        return new WarehouseDTO(warehouse.getId(),
                warehouse.getName(),
                warehouse.getMaxCapacity(),
                warehouse.getOccupiedCapacity());
    }

    public WarehouseDTO save(WarehouseDTO dto) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(dto.getName());
        warehouse.setMaxCapacity(dto.getMaxCapacity());
        warehouse.setOccupiedCapacity(dto.getOccupiedCapacity());
        repository.save(warehouse);
        return new WarehouseDTO(warehouse.getId(),
                warehouse.getName(),
                warehouse.getMaxCapacity(),
                warehouse.getOccupiedCapacity());
    }

    public void saveProductToWarehouse(Long productID, Long warehouseID, int amount){
        Product foundProduct = productRepository.findById(productID).orElseThrow(() -> new NotFoundException("No such product found: ID - " + productID));
        Warehouse foundWarehouse = repository.findById(warehouseID).orElseThrow(() -> new NotFoundException("No such warehouse found: ID - " + warehouseID));
        foundProduct.setProductAvailability(amount);
        foundProduct.setWarehouseID(foundWarehouse.getId());
        foundWarehouse.getProducts().add(foundProduct);
        productRepository.save(foundProduct);
    }

    public WarehouseDTO update(Long id, WarehouseDTO dto) {
        Warehouse warehouse = repository.findById(id).orElseThrow(() -> new NotFoundException("No such warehouse found: ID - " + id));
        warehouse.setName(dto.getName());
        warehouse.setMaxCapacity(dto.getMaxCapacity());
        warehouse.setOccupiedCapacity(dto.getOccupiedCapacity());
        repository.save(warehouse);
        return new WarehouseDTO(warehouse.getId(),
                warehouse.getName(),
                warehouse.getMaxCapacity(),
                warehouse.getOccupiedCapacity());
    }

    public void delete(Long id) {
        repository.findById(id).orElseThrow(() -> new NotFoundException("No such warehouse found: ID - " + id));
        repository.deleteById(id);
    }

    public List<ProductDTO> showAllProductsInWarehouse(Long warehouseID) {
        return repository.showAllProductsInWarehouse(warehouseID)
                .stream()
                .map(product -> new ProductDTO(product.getId(),
                        product.getName(),
                        product.getRegularPrice(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getDiscount(),
                        product.getActualPrice(),
                        product.getWarehouseID(),
                        product.getProductAvailability()))
                .collect(Collectors.toList());
    }

    public int showProductAvailability(Long productID, Long warehouseID) {
        productRepository.findById(productID).orElseThrow(() -> new NotFoundException("No such product found: ID - " + productID));
        return repository.showProductAvailability(productID, warehouseID);
    }

    public void removeProductFromWarehouse(Long productID, Long warehouseID) {
        Product product = productRepository.findById(productID).orElseThrow(() -> new NotFoundException("No such product found: ID - " + productID));
        Warehouse warehouse = repository.findById(warehouseID).orElseThrow(() -> new NotFoundException("No such warehouse found: ID - " + warehouseID));
        warehouse.getProducts().remove(product);
        productRepository.save(product);
    }
}
