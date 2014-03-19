# --- !Ups
CREATE TABLE product_ingredient(
	product_name VARCHAR(50) NOT NULL,
	ingredient_name VARCHAR(50) NOT NULL,
	quantity INT NOT NULL,
	unit_abbreviation VARCHAR(2) NOT NULL,
	PRIMARY KEY (product_name, ingredient_name),
  FOREIGN KEY k_product_name(product_name) REFERENCES product(name),
  FOREIGN KEY k_ingredient_name(ingredient_name) REFERENCES ingredient(name),
  FOREIGN KEY k_unit_abbreviation(unit_abbreviation) REFERENCES unit(abbreviation)
);

INSERT INTO `product_ingredient` (`product_name`, `ingredient_name`, `quantity`, `unit_abbreviation`)
  VALUES
  ('Almond delight','Butter',400,'g'),
  ('Almond delight','Chopped almonds',279,'g'),
  ('Almond delight','Cinnamon',10,'g'),
  ('Almond delight','Flour',400,'g'),
  ('Almond delight','Sugar',270,'g'),
  ('Amneris','Butter',250,'g'),
  ('Amneris','Eggs',35,'cl'),
  ('Amneris','Marzipan',750,'g'),
  ('Amneris','Potato starch',25,'g'),
  ('Amneris','Wheat flour',25,'g'),
  ('Berliner','Butter',250,'g'),
  ('Berliner','Chocolate',50,'g'),
  ('Berliner','Eggs',50,'g'),
  ('Berliner','Flour',350,'g'),
  ('Berliner','Icing sugar',100,'g'),
  ('Berliner','Vanilla sugar',50,'g'),
  ('Nut cookie','Bread crumbs',125,'g'),
  ('Nut cookie','Chocolate',50,'g'),
  ('Nut cookie','Egg whites',35,'cl'),
  ('Nut cookie','Fine-ground nuts',750,'g'),
  ('Nut cookie','Ground, roasted nuts',625,'g'),
  ('Nut cookie','Sugar',375,'g'),
  ('Nut ring','Butter',450,'g'),
  ('Nut ring','Flour',450,'g'),
  ('Nut ring','Icing sugar',190,'g'),
  ('Nut ring','Roasted, chopped nuts',225,'g'),
  ('Tango','Butter',200,'g'),
  ('Tango','Flour',300,'g'),
  ('Tango','Sodium bicarbonate',4,'g'),
  ('Tango','Sugar',250,'g'),
  ('Tango','Vanilla',2,'g');

# --- !Downs
DROP TABLE product_ingredient;