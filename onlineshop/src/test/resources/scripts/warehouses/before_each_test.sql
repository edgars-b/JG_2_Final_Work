insert into warehouses (name, max_capacity, occupied_capacity) values ('Test warehouse 1', 5000, 0);

insert into products (name, regular_price, description, category, discount, actual_price, product_availability, warehouse_id) values ('Test name 1',  1.00, 'test description 1', 'test category 1', 0.00, 1.00, 100, 1);
insert into products (name, regular_price, description, category, discount, actual_price, product_availability, warehouse_id) values ('Test name 2',  1.00, 'test description 2', 'test category 2', 0.00, 1.00, 0, null);