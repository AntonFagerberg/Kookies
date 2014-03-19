# --- !Ups
CREATE TABLE ingredient(
	name VARCHAR(50) PRIMARY KEY,
  quantity INT NOT NULL DEFAULT 0
);

INSERT INTO `ingredient` (`name`)
  VALUES
  ('Bread crumbs'),
  ('Butter'),
  ('Chocolate'),
  ('Chopped almonds'),
  ('Cinnamon'),
  ('Egg whites'),
  ('Eggs'),
  ('Fine-ground nuts'),
  ('Flour'),
  ('Ground, roasted nuts'),
  ('Icing sugar'),
  ('Marzipan'),
  ('Potato starch'),
  ('Roasted, chopped nuts'),
  ('Sodium bicarbonate'),
  ('Sugar'),
  ('Vanilla'),
  ('Vanilla sugar'),
  ('Wheat flour');

# --- !Downs
DROP TABLE ingredient;