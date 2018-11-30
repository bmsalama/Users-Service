CREATE TABLE users (
  id varchar(255) PRIMARY KEY,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  gender varchar(10) NOT NULL,
  address varchar(255),
  age integer NOT NULL,
  mobile_number varchar(50),
  email varchar(50),
  notes varchar(255)
);
