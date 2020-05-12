package com.javaguru.onlineshop.product;

import com.javaguru.onlineshop.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(product -> new ProductDTO(product.getId(),
                        product.getName(),
                        product.getRegularPrice(),
                        product.getDescription(),
                        product.getCategory(),
                        product.getDiscount(),
                        product.getActualPrice()))
                .collect(Collectors.toList());
    }

    public ProductDTO findByID(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("No such product found. ID - " + id));
        return new ProductDTO(product.getId(),
                product.getName(),
                product.getRegularPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getDiscount(),
                product.getActualPrice());
    }

    public ProductDTO save(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setRegularPrice(dto.getRegularPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setDiscount(dto.getDiscount());
        product.setActualPrice(dto.calculateActualPrice());
        repository.save(product);
        return new ProductDTO(product.getId(),
                product.getName(),
                product.getRegularPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getDiscount(),
                product.getActualPrice());
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException("No such product found. ID - " + id));
        product.setName(dto.getName());
        product.setActualPrice(dto.getActualPrice());
        product.setDescription(dto.getDescription());
        product.setCategory(dto.getCategory());
        product.setDiscount(dto.getDiscount());
        product.setActualPrice(dto.calculateActualPrice());
        repository.save(product);
        return new ProductDTO(product.getId(),
                product.getName(),
                product.getRegularPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getDiscount(),
                product.getActualPrice());
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
