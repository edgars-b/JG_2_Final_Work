CREATE TABLE IF NOT EXISTS comments (
  comment_id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  created timestamp DEFAULT CURRENT_TIMESTAMP,
  message varchar(200) not null,
  product_id bigint not null,
  constraint fk_product_id_comment foreign key (product_id) references products (product_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;