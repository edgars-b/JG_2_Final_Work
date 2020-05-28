package com.javaguru.onlineshop.warehouse;

import com.javaguru.onlineshop.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {

    @Query("from Product where warehouseID =:warehouseID")
    List<Product> showAllProductsInWarehouse(@Param("warehouseID") Long warehouseID);

    @Query("select p.productAvailability from Product p where p.warehouseID = :warehouseID and id = :productID")
    Integer showProductAvailability(@Param("productID") Long productID, @Param("warehouseID") Long warehouseID);
}
