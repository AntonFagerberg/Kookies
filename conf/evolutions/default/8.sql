# --- !Ups
CREATE TABLE block(
  start TIMESTAMP NOT NULL,
  end TIMESTAMP NOT NULL,
  product_name VARCHAR(50) NOT NULL,
  PRIMARY KEY (product_name, start, end),
  FOREIGN KEY K_product_name(product_name) REFERENCES product(name)
);

# --- !Downs
DROP TABLE block;