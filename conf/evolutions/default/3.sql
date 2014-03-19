# --- !Ups
CREATE TABLE unit(
	abbreviation VARCHAR(2) PRIMARY KEY,
	name VARCHAR(20) NOT NULL UNIQUE
);

INSERT INTO `unit` (`abbreviation`, `name`)
  VALUES
  ('ml','milliliters'),
  ('g','grams');

# --- !Downs
DROP TABLE unit;