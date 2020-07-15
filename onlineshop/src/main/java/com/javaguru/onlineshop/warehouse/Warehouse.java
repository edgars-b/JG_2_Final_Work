package com.javaguru.onlineshop.warehouse;

import com.javaguru.onlineshop.product.Product;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id", nullable = false)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany
    @JoinColumn(name = "warehouse_id")
    private Set<Product> products;
    @Column(name = "max_capacity")
    private int maxCapacity;
    @Column(name = "occupied_capacity")
    private int occupiedCapacity;


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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public int getOccupiedCapacity() {
        return occupiedCapacity;
    }

    public void setOccupiedCapacity(int occupiedCapacity) {
        this.occupiedCapacity = occupiedCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return Objects.equals(id, warehouse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", products=" + products +
                ", maxCapacity=" + maxCapacity +
                ", occupiedCapacity=" + occupiedCapacity +
                '}';
    }
}
