mysql -u root -pmanager

create database bigbasket_db;

use bigbasket_db;

create table users(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50),
email VARCHAR(100),
mobile VARCHAR(100),
password VARCHAR(100),
role VARCHAR(50)
);

create table addresses(
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT,
address_line1 VARCHAR(100),
city VARCHAR(50),
state VARCHAR(50),
postal_code VARCHAR(10),
country VARCHAR(50),
FOREIGN KEY (user_id) REFERENCES users(id)
);


UPDATE users 
JOIN addresses ON users.id = addresses.user_id 
SET users.name = 'New Name',     
users.email = 'newemail@example.com',     
users.mobile = 'new-mobile-number',     
addresses.address_line1 = 'New Address Line 1',     
addresses.city = 'New City',     
addresses.state = 'New State',     
addresses.postal_code = '77878',     
addresses.country = 'New Country' 
WHERE users.id = 2;

UPDATE users 
JOIN addresses ON users.id = addresses.user_id 
SET users.name = ?,     
users.email = ?,     
users.mobile = ?,     
addresses.address_line1 = ?,     
addresses.city = ?,     
addresses.state = ?,     
addresses.postal_code = ?,     
addresses.country = ?
WHERE users.id = ?;



create table categories(
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50),
image VARCHAR(255)
);

create table subcategories(
id INT AUTO_INCREMENT PRIMARY KEY,
category_id INT,
name VARCHAR(50),
FOREIGN KEY (category_id) REFERENCES categories(id)
);

create table products(
id INT AUTO_INCREMENT PRIMARY KEY,
category_id INT,
subcategory_id INT,
name VARCHAR(100),
price DECIMAL(10, 2),
description TEXT,
image VARCHAR(255),
FOREIGN KEY (category_id) REFERENCES categories(id),
FOREIGN KEY (subcategory_id) REFERENCES subcategories(id)
);

create table orders(
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT,
product_id INT,
quantity INT,
order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
delivery_status VARCHAR(50),
FOREIGN KEY (user_id) REFERENCES users(id),
FOREIGN KEY (product_id) REFERENCES products(id)
);

select product_id,name, price, delivery_status,order_date from products join  orders
on products.id = orders.product_id 

select orders.id as order_id,email,payment_mode,product_id,quantity,products.name, price, delivery_status,order_date 
from products 
join  orders on products.id = orders.product_id
join users on users.id = orders.user_id
join payment_info on payment_info.user_id =orders.user_id
join addresses on addresses.user_id = orders.user_id

select orders.id as order_id,product_id,products.name, price, delivery_status,order_date,users.email,quantity
from products 
join  orders on products.id = orders.product_id
join users on users.id = orders.user_id




select products.id,email, payment_mode, city, products.name, price,quantity 
from users
join payment_info on payment_info.user_id = users.id
join addresses on addresses.user_id = payment_info.user_id
join orders on orders.user_id = addresses.user_id
join products on products.id = orders.product_id 
where product_id=?

SELECT country.country_name_eng, city.city_name, customer.customer_name
FROM country
INNER JOIN city ON city.country_id = country.id
INNER JOIN customer ON customer.city_id = city.id;



create table payment_info(
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT,
payment_mode VARCHAR(50),
FOREIGN KEY (user_id) REFERENCES users(id)
);

create table ratings(
id INT AUTO_INCREMENT PRIMARY KEY,
product_id INT,
user_id INT,
rating FLOAT,
review_text VARCHAR(550),
FOREIGN KEY (product_id) REFERENCES products(id),
FOREIGN KEY (user_id) REFERENCES users(id)
);


