package com.javaguru.onlineshop.shoppingcart;

import javax.validation.constraints.NotEmpty;

public class ShoppingCartDTO {

    private Long id;
    @NotEmpty(message = "Shopping cart name must not be empty.")
    private String name;

    public ShoppingCartDTO() {
    }

    public ShoppingCartDTO(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "ShoppingCartDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
