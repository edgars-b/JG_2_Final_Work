alter table products drop foreign key fk_warehouse_id;
alter table carts_products drop foreign key fk_product_id;

truncate table warehouses;
truncate table products;

ALTER TABLE products AUTO_INCREMENT = 1;
alter table warehouses auto_increment = 1;

alter table products add constraint fk_warehouse_id foreign key (warehouse_id) references warehouses(warehouse_id);
alter table carts_products add constraint fk_product_id foreign key (product_id) references products(product_id);