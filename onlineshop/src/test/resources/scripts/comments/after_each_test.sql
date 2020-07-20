alter table carts_products drop foreign key fk_product_id;
alter table comments drop foreign key fk_product_id_comment;

truncate table shop_db.comments;
truncate table shop_db.products;

ALTER TABLE products AUTO_INCREMENT = 1;
ALTER TABLE comments AUTO_INCREMENT = 1;
alter table carts_products add constraint fk_product_id foreign key (product_id) references products(product_id);
alter table comments add constraint fk_product_id_comment foreign key (product_id) references products(product_id);