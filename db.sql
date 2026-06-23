create database ecommerce_db;
use ecommerce_db;

create table Category
(
	id int auto_increment primary key,
    image_name varchar(255),
    is_active bit(1),
    name varchar(255)
);

create table Product
(
	id int auto_increment primary key,
    category varchar(255),
    description varchar(5000),
    image varchar(255),
    price double,
    tille varchar(500),
    discount int,
    discountPrice double,
    isActive boolean
);

create table product_variant
(
	id int auto_increment primary key,
    color varchar(255),
    size varchar(255),
    stock int,
    product_id int,
    foreign key (product_id) references Product(id) on delete cascade
);

create table User_Dtls
(
	id int auto_increment primary key,
    name varchar(255),
    mobileNumber varchar(255),
    address varchar(255),
    email varchar(255),
    city varchar (255),
    state varchar(255),
    pincode varchar(255),
    password varchar(255),
    profileImage varchar(255),
    role varchar(255),
    isEnable boolean,
    accountNonLocked boolean,
    failedAttempt int,
    lockTime DATETIME(6),
    resetToken varchar(255)
);

create table Cart
(
	id int auto_increment primary key,
    user_id int,
    product_id int,
    quantity int,
    totalPrice double,
    totalOrderPrice double,
    color varchar(255), 
    size varchar(255), 
    foreign key (user_id) references User_Dtls(id),
    foreign key (product_id) references Product(id)
);

create table OrderAddress
(
	id int auto_increment primary key,
    firstName varchar(255),
    lastName  varchar(255),
    email varchar(255),
    mobileNo varchar(255),
    address varchar(255),
    city varchar(255),
    state varchar(255),
    pincode varchar(255)
);

create table ProductOrder
(
	id int auto_increment primary key,
    orderId varchar(255),
    orderDate datetime(6),
    product_id int,
    price double,
    quantity int,
    user_id int,
    status varchar(255),
    paymentType varchar(255),
    order_address_id INT,
    color varchar(255),
    size varchar(255),
    foreign key (product_id) references Product(id),
    foreign key (user_id) references User_Dtls(id)
);

create table product_image
(
    id int auto_increment primary key,
    image_name varchar(255),
    product_id int,
    foreign key (product_id) references Product(id) on delete cascade
);


INSERT INTO User_Dtls (name, mobileNumber, address, email, city, state, pincode, password, profileImage, role, isEnable, accountNonLocked, failedAttempt) 
VALUES ('Administrator', '0988888888', 'Hà Nội', 'admin@gmail.com', 'Hà Nội', 'Hà Nội', '100000', '$2a$10$123456789012345678901uiaLpJxTpf6VbfI5NADlsRsfvEm6aq9C', 'default.jpg', 'ROLE_SUPERADMIN', 1, 1, 0);