alter table carts_products drop foreign key fk_shopping_cart_id;
alter table carts_products drop foreign key fk_product_id;

truncate table shopping_carts;
truncate table products;
truncate table carts_products;

ALTER TABLE shopping_carts AUTO_INCREMENT = 1;
ALTER TABLE products AUTO_INCREMENT = 1;

alter table carts_products add constraint fk_product_id foreign key (product_id) references products(product_id);
alter table carts_products add constraint fk_shopping_cart_id foreign key (shopping_cart_id) references shopping_carts(shopping_cart_id);