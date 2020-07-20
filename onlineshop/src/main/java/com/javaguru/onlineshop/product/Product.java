package com.javaguru.onlineshop.product;

import com.javaguru.onlineshop.comments.Comment;
import com.javaguru.onlineshop.shoppingcart.ShoppingCart;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column(name = "regular_price")
    private BigDecimal regularPrice;
    private String description;
    private String category;
    private BigDecimal discount;
    @Column(name = "actual_price")
    private BigDecimal actualPrice;
    @ManyToMany(mappedBy = "products")
    private Set<ShoppingCart> carts = new HashSet<>();
    @Column(name = "warehouse_id")
    private Long warehouseID;
    @Column(name = "product_availability")
    private int productAvailability;
    @OneToMany
    @JoinColumn(name = "product_id")
    private List<Comment> comments = new ArrayList<>();

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

    public Set<ShoppingCart> getCarts() {
        return carts;
    }

    public void setCarts(Set<ShoppingCart> carts) {
        this.carts = carts;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    //  Adding and removing things related to Product

    public void addToShoppingCart(ShoppingCart cart) {
        this.carts.add(cart);
        cart.getProducts().add(this);
    }

    public void removeFromShoppingCart(ShoppingCart cart) {
        this.carts.remove(cart);
        cart.getProducts().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productAvailability == product.productAvailability &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(regularPrice, product.regularPrice) &&
                Objects.equals(description, product.description) &&
                Objects.equals(category, product.category) &&
                Objects.equals(discount, product.discount) &&
                Objects.equals(actualPrice, product.actualPrice) &&
                Objects.equals(carts, product.carts) &&
                Objects.equals(warehouseID, product.warehouseID) &&
                Objects.equals(comments, product.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, regularPrice, description, category, discount, actualPrice, carts, warehouseID, productAvailability, comments);
    }

    @Override
    public String toString() {
        return "Product{" +
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
