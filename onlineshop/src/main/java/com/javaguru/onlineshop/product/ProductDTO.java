package com.javaguru.onlineshop.product;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductDTO {

    private Long id;
    @NotEmpty(message = "Product name must not be empty.")
    @Size(min = 3, max = 20, message = "Product name must be between 3 and 20 characters long.")
    private String name;
    @PositiveOrZero(message = "Price cannot be negative.")
    private BigDecimal regularPrice;
    private String description;
    private String category;
    @Min(value = 0, message = "Discount must be 0 or more")
    @Max(value = 100, message = "Discount must be 100 or less")
    private BigDecimal discount;
    private BigDecimal actualPrice;
    private Long warehouseID;
    @PositiveOrZero(message = "Product availability cannot be negative")
    private int productAvailability;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, BigDecimal regularPrice, String description, String category, BigDecimal discount, BigDecimal actualPrice, Long warehouseID, int productAvailability) {
        this.id = id;
        this.name = name;
        this.regularPrice = regularPrice;
        this.description = description;
        this.category = category;
        this.discount = discount;
        this.actualPrice = actualPrice;
        this.warehouseID = warehouseID;
        this.productAvailability = productAvailability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(BigDecimal regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Long getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(Long warehouseID) {
        this.warehouseID = warehouseID;
    }

    public int getProductAvailability() {
        return productAvailability;
    }

    public void setProductAvailability(int productAvailability) {
        this.productAvailability = productAvailability;
    }

    public BigDecimal calculateActualPrice() {
        if (discount.equals(BigDecimal.ZERO)) {
            return actualPrice = regularPrice;
        } else {
            return actualPrice = regularPrice.subtract(regularPrice.multiply(discount).divide(new BigDecimal("100"),
                    2,
                    RoundingMode.HALF_UP));
        }
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", regularPrice=" + regularPrice +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", discount=" + discount +
                ", actualPrice=" + actualPrice +
                ", productAvailability=" + productAvailability +
                '}';
    }
}
