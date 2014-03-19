# --- !Ups
CREATE TABLE order_product(
  order_id INT NOT NULL,
  product_name VARCHAR(50) NOT NULL,
  pallet_quantity INT NOT NULL,
  PRIMARY KEY (order_id, product_name),
  FOREIGN KEY k_order_id(order_id) REFERENCES `order`(id),
  FOREIGN KEY k_product_name(product_name) REFERENCES product(name)
);

# --- !Downs
DROP TABLE order_product;