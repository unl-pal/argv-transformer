create database if not exists shop;

use shop;

create table roles(
	id int (10) AUTO_INCREMENT,
	role varchar(20) NOT NULL,
	PRIMARY KEY (id)
);

create table users (
	id int (10) AUTO_INCREMENT,
	role_id int (10) NOT NULL,
	login varchar(20) NOT NULL UNIQUE,
	first_name varchar(20) NOT NULL,
	last_name varchar(20) NOT NULL,
	email varchar(30) NOT NULL,
	password varchar(20) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (role_id) REFERENCES roles(id)
);

create table subs(
	id int (10) AUTO_INCREMENT,
	user_id int (10) NOT NULL,
	name varchar(50) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id)
);

create table types(
	id int (10) AUTO_INCREMENT,
	name varchar(20) NOT NULL,
	PRIMARY KEY (id)
);

create table brands(
	id int (10) AUTO_INCREMENT,
	name varchar(20) NOT NULL,
	PRIMARY KEY (id)
);

create table products(
	id int (10) AUTO_INCREMENT,
	type_id int (10) NOT NULL,
	brand_id int (10) NOT NULL,
	price float (10,2) NOT NULL,
	name varchar(20) NOT NULL,
	text varchar(100) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (type_id) REFERENCES types(id),
	FOREIGN KEY (brand_id) REFERENCES brands(id)
);

create table statuses(
	id int (10) AUTO_INCREMENT,
	name varchar(20) NOT NULL,
	PRIMARY KEY (id)
);

create table deliveries(
	id int (10) AUTO_INCREMENT,
	name varchar(20) NOT NULL,
	PRIMARY KEY (id)
);

create table payments(
	id int (10) AUTO_INCREMENT,
	name varchar(20) NOT NULL,
	PRIMARY KEY (id)
);

create table orders(
	id int (10) AUTO_INCREMENT,
	user_id int (10) NOT NULL,
	payment_id int (10) NOT NULL,
	delivery_id int (10) NOT NULL,
	status_id int (10),
	status_message varchar(100),
	created DATETIME NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (user_id) REFERENCES users(id),
	FOREIGN KEY (payment_id) REFERENCES payments(id),
	FOREIGN KEY (delivery_id) REFERENCES deliveries(id),
	FOREIGN KEY (status_id) REFERENCES statuses(id)
);

create table orderItems(
	id int (10) AUTO_INCREMENT,
	order_id int (10) NOT NULL,
	product_id int (10) NOT NULL,
	count int (10) NOT NULL,
	price float (10,2) NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (order_id) REFERENCES orders(id),
	FOREIGN KEY (product_id) REFERENCES products(id)
);

INSERT INTO roles VALUES(DEFAULT, 'user');
INSERT INTO roles VALUES(DEFAULT, 'admin');
INSERT INTO roles VALUES(DEFAULT, 'super admin');

INSERT INTO types VALUES(DEFAULT, 'backpack');
INSERT INTO types VALUES(DEFAULT, 'tent');
INSERT INTO types VALUES(DEFAULT, 'sleepeng bag');

INSERT INTO brands VALUES(DEFAULT, 'Deuter');
INSERT INTO brands VALUES(DEFAULT, 'Tatonka');
INSERT INTO brands VALUES(DEFAULT, 'Millet');

INSERT INTO products VALUES(DEFAULT, '1', '1', '100.00', 'Aircontact 75', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');
INSERT INTO products VALUES(DEFAULT, '1', '1', '200.00', 'Aircontact 65', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');
INSERT INTO products VALUES(DEFAULT, '3', '1', '300.00', 'Sphere 850', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');

INSERT INTO products VALUES(DEFAULT, '2', '2', '100.00', 'Shelter 2', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');
INSERT INTO products VALUES(DEFAULT, '2', '2', '200.00', 'Shelter 3', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');
INSERT INTO products VALUES(DEFAULT, '1', '2', '300.00', 'Bizon 90', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');

INSERT INTO products VALUES(DEFAULT, '1', '3', '100.00', 'Khumbu 75', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');
INSERT INTO products VALUES(DEFAULT, '3', '3', '200.00', 'Base 1100', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');
INSERT INTO products VALUES(DEFAULT, '3', '3', '300.00', 'Base XP 1700', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt');

INSERT INTO statuses VALUES(DEFAULT, 'received');
INSERT INTO statuses VALUES(DEFAULT, 'confirmed');
INSERT INTO statuses VALUES(DEFAULT, 'formed');
INSERT INTO statuses VALUES(DEFAULT, 'sent');
INSERT INTO statuses VALUES(DEFAULT, 'completed');
INSERT INTO statuses VALUES(DEFAULT, 'canceled');

INSERT INTO deliveries VALUES(DEFAULT, 'address');
INSERT INTO deliveries VALUES(DEFAULT, 'storage');
INSERT INTO deliveries VALUES(DEFAULT, 'mail');

INSERT INTO payments VALUES(DEFAULT, 'cash');
INSERT INTO payments VALUES(DEFAULT, 'card');

INSERT INTO users VALUES(DEFAULT, '1', 'Vano', 'John', 'Carter', 'test@test.com', 'lolipop89');
INSERT INTO users VALUES(DEFAULT, '2', 'Admin', 'John', 'Carter', 'test@test.com', 'lolipop89');

commit;