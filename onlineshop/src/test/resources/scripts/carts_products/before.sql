insert into shopping_carts (name) values ('sewfwgre');

insert into products (name, regular_price, description, category, discount, actual_price, product_availability, warehouse_id)
values ('Test name 1',  1.00, 'test description 1', 'test category 1', 0.00, 1.00, 0, null);

insert into products (name, regular_price, description, category, discount, actual_price, product_availability, warehouse_id)
values ('Test name 2',  1.00, 'test description 2', 'test category 2', 0.00, 1.00, 0 , null);

insert into carts_products (shopping_cart_id, product_id) values (1, 1);