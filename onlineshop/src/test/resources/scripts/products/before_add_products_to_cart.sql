ALTER TABLE products AUTO_INCREMENT = 2;
insert into shopping_carts (name) values ('sewfwgre');
insert into carts_products (shopping_cart_id , product_id ) values (1,1);
insert into products (name, regular_price, description, category, discount, actual_price)
values ('Test name 2',  1.00, 'test description 2', 'test category 2', 0.00, 1.00);
insert into carts_products (shopping_cart_id , product_id ) values (1,2);