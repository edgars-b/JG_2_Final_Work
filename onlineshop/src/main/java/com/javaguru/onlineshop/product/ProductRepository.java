package com.javaguru.onlineshop.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("from Product p join p.carts c where c.id=:id")
    List<Product> findAllProductsInCart(@Param("id") Long id);

    @Query("select sum(p.actualPrice) from Product p join p.carts c where c.id = :id")
    Optional<BigDecimal> getTotalSumOfProductsInCart(@Param("id") Long id);
}
