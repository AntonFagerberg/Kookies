# --- !Ups
CREATE TABLE product(
	name VARCHAR(50) PRIMARY KEY
);

INSERT INTO `product` (`name`)
  VALUES
  ('Almond delight'),
  ('Amneris'),
  ('Berliner'),
  ('Nut cookie'),
  ('Nut ring'),
  ('Tango');

# --- !Downs
DROP TABLE product;