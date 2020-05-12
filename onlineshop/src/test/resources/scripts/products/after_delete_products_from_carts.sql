truncate table carts_products;
delete from products where product_id = 1;
delete from products where product_id = 2;
ALTER TABLE products AUTO_INCREMENT = 1;