# --- !Ups
INSERT INTO `block` (`start`, `end`, `product_name`)
  VALUES
  ('2014-03-18 15:15:00','2014-03-18 15:45:00','Nut ring');

INSERT INTO `load_bill` (`id`)
  VALUES
  (1);

INSERT INTO `order` (`id`, `customer_name`, `expecting_date`, `delivery_timestamp`, `load_bill_id`)
  VALUES
  (1,'Bjudkakor AB','2014-03-19','2014-03-19 15:34:34',1),
  (2,'Finkakor AB','2014-03-19','0000-00-00 00:00:00',1),
  (3,'Gästkakor AB','2014-05-29',NULL,NULL);

INSERT INTO `order_product` (`order_id`, `product_name`, `pallet_quantity`)
  VALUES
  (1,'Berliner',2),
  (1,'Tango',2),
  (2,'Tango',1),
  (3,'Nut ring',5);

INSERT INTO `pallet` (`id`, `product_name`, `production_timestamp`, `order_id`)
  VALUES
  (1,'Berliner','2014-03-19 15:33:22',1),
  (2,'Berliner','2014-03-19 15:33:35',1),
  (3,'Berliner','2014-03-19 15:31:03',NULL),
  (4,'Berliner','2014-03-19 15:31:05',NULL),
  (5,'Tango','2014-03-19 15:33:36',1),
  (6,'Tango','2014-03-19 15:33:37',1),
  (7,'Tango','2014-03-19 15:33:38',2),
  (8,'Nut Ring','2014-03-18 15:31:20',NULL),
  (9,'Nut Ring','2014-03-18 15:31:22',NULL),
  (10,'Nut Ring','2014-03-19 15:39:48',3),
  (11,'Nut Ring','2014-03-19 15:39:49',3);

# --- !Downs
TRUNCATE TABLE pallet;
TRUNCATE TABLE order_product;
TRUNCATE TABLE `order`;
TRUNCATE TABLE load_bill;
TRUNCATE TABLE `block`;