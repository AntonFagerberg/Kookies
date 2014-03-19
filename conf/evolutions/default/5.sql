# --- !Ups
CREATE TABLE customer(
  name VARCHAR(50) PRIMARY KEY,
  address VARCHAR(50) NOT NULL
);

INSERT INTO `customer` (`name`, `address`)
  VALUES
  ('Bjudkakor AB','Ystad'),
  ('Finkakor AB','Helsingborg'),
  ('Gästkakor AB','Hässleholm'),
  ('Kaffebröd AB','Landskrona'),
  ('Kalaskakor AB','Trelleborg'),
  ('Partykakor AB','Kristianstad'),
  ('Skånekakor AB','Perstorp'),
  ('Småbröd AB','Malmö');

# --- !Downs
DROP TABLE customer;