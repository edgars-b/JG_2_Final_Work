CREATE TABLE IF NOT EXISTS products (
  product_id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(100) NOT NULL UNIQUE,
  regular_price decimal(19,2) NOT NULL,
  description varchar(200),
  category varchar(50),
  discount decimal(19,2),
  actual_price decimal(19,2),
  created timestamp DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

create table if not exists shopping_carts (
	shopping_cart_id bigint primary key not null auto_increment,
	name varchar(100),
	created timestamp default current_timestamp
) auto_increment = 1;

create table if not exists carts_products(
	shopping_cart_id bigint not null,
	product_id bigint not null,
	primary key(shopping_cart_id, product_id),
	constraint fk_product_id foreign key (product_id) references products (product_id),
	constraint fk_shopping_cart_id foreign key (shopping_cart_id) references shopping_carts (shopping_cart_id)
);