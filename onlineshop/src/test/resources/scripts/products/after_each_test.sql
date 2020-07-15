alter table carts_products drop foreign key fk_product_id;
alter table carts_products drop foreign key fk_shopping_cart_id;

truncate table shop_db.products;
truncate table shop_db.carts_products;
truncate table shop_db.shopping_carts;

ALTER TABLE products AUTO_INCREMENT = 1;
alter table carts_products add constraint fk_product_id foreign key (product_id) references products(product_id);
alter table carts_products add constraint fk_shopping_cart_id foreign key (shopping_cart_id) references shopping_carts(shopping_cart_id);