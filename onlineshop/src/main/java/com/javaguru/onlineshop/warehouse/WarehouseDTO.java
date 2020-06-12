package com.javaguru.onlineshop.warehouse;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class WarehouseDTO {

    private Long id;
    @NotEmpty(message = "Warehouse name can not be empty.")
    @Size(min = 3, message = "Warehouse name must consist at least of 3 characters.")
    private String name;
    private int maxCapacity;
    private int occupiedCapacity;

    public WarehouseDTO() {
    }

    public WarehouseDTO(Long id, String name, int maxCapacity, int occupiedCapacity) {
        this.id = id;
        this.name = name;
        this.maxCapacity = maxCapacity;
        this.occupiedCapacity = occupiedCapacity;
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

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getOccupiedCapacity() {
        return occupiedCapacity;
    }

    public void setOccupiedCapacity(int occupiedCapacity) {
        this.occupiedCapacity = occupiedCapacity;
    }

    @Override
    public String toString() {
        return "WarehouseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxWarehouseCapacity=" + maxCapacity +
                ", occupiedCapacity=" + occupiedCapacity +
                '}';
    }
}
