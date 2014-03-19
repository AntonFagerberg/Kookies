# --- !Ups
CREATE TABLE pallet(
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_name VARCHAR(50) NOT NULL,
  production_timestamp TIMESTAMP NOT NULL,
  order_id INT REFERENCES `order`(id),
  FOREIGN KEY k_product_name(product_name) REFERENCES product(name)
);

# --- !Downs
DROP TABLE pallet;