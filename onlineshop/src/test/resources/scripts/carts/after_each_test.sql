alter table carts_products drop foreign key fk_shopping_cart_id;
truncate table shopping_carts;

alter table carts_products add constraint fk_shopping_cart_id foreign key (shopping_cart_id) references shopping_carts(shopping_cart_id);