# --- !Ups
CREATE TABLE ingredient(
	name VARCHAR(50) PRIMARY KEY,
  quantity INT NOT NULL DEFAULT 0
);

INSERT INTO `ingredient` (`name`, quantity)
  VALUES
  ('Bread crumbs', '500000'),
  ('Butter', '500000'),
  ('Chocolate', '500000'),
  ('Chopped almonds', '500000'),
  ('Cinnamon', '500000'),
  ('Egg whites', '500000'),
  ('Eggs', '500000'),
  ('Fine-ground nuts', '500000'),
  ('Flour', '500000'),
  ('Ground, roasted nuts', '500000'),
  ('Icing sugar', '500000'),
  ('Marzipan', '500000'),
  ('Potato starch', '500000'),
  ('Roasted, chopped nuts', '500000'),
  ('Sodium bicarbonate', '500000'),
  ('Sugar', '500000'),
  ('Vanilla', '500000'),
  ('Vanilla sugar', '500000'),
  ('Wheat flour', '500000');

# --- !Downs
DROP TABLE ingredient;