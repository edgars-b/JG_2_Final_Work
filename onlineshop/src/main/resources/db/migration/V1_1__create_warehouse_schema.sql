alter table products add column product_availability integer not null;

create table if not exists warehouses (
	warehouse_id bigint primary key not null auto_increment,
	name varchar(100) not null,
	max_capacity integer not null default(5000),
	created timestamp default current_timestamp
);

alter table products add column warehouse_id bigint not null,
add constraint fk_warehouse_id foreign key (warehouse_id) references warehouses (warehouse_id);