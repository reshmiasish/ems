create table registration(id int PRIMARY KEY NOT NULL AUTO_INCREMENT, email varchar(50), name varchar(50), apikey varchar(100));
create table login_account(user_name varchar(30) NOT NULL PRIMARY KEY, password varchar(50) NOT NULL, client_id varchar(30) NOT NULL UNIQUE, client_secret varchar(50) NOT NULL);
create table role(role_id int PRIMARY KEY NOT NULL, role_name varchar(15), role_description varchar(30));
create table department(department_id int PRIMARY KEY NOT NULL, department_name varchar(20), department_desc varchar(30));
create table employee(employee_id int PRIMARY KEY NOT NULL AUTO_INCREMENT, first_name varchar(25) NOT NULL, last_name varchar(25), department_id int NOT NULL, birth_date date, join_date date, role_id int NOT NULL, gender varchar(10), salary long, email varchar(50) NOT NULL, CONSTRAINT department_id FOREIGN KEY (department_id) REFERENCES department(department_id) ON DELETE NO ACTION ON UPDATE CASCADE, CONSTRAINT role_id FOREIGN KEY (role_id) REFERENCES role(role_id) ON DELETE NO ACTION ON UPDATE CASCADE);