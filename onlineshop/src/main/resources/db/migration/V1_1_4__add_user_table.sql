CREATE TABLE IF NOT EXISTS user_logins (
  user_login_id bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  login varchar(100) NOT NULL UNIQUE,
  password varchar(100) NOT NULL,
  is_active boolean not null default 0,
  created timestamp DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS user_roles (
  user_login_id bigint NOT NULL,
  roles varchar(255) default 'USER' not null,
  constraint fk_user_login_id foreign key (user_login_id) references user_logins (user_login_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;