# --- !Ups
CREATE TABLE `order`(
  id INT AUTO_INCREMENT PRIMARY KEY,
  customer_name VARCHAR(50) NOT NULL,
  expecting_date DATE NOT NULL,
  delivery_timestamp TIMESTAMP,
  load_bill_id INT,
  FOREIGN KEY k_customer_name(customer_name) REFERENCES customer(name),
  FOREIGN KEY k_load_bill_id(load_bill_id) REFERENCES load_bill(id)
);

# --- !Downs
DROP TABLE `order`;