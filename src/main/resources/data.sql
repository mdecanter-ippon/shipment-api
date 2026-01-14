-- Initial Data for the Training
-- Note: SQL date format is YYYY-MM-DD. JSON format will be dd-MM-yyyy.
INSERT INTO shipment (destination, weight, status, shipping_date) VALUES ('New York', 12.5, 'SHIPPED', '2023-10-25');
INSERT INTO shipment (destination, weight, status, shipping_date) VALUES ('Tokyo', 5.0, 'PENDING', '2023-12-01');
INSERT INTO shipment (destination, weight, status, shipping_date) VALUES ('Paris', 2.3, 'DELIVERED', '2023-09-15');
INSERT INTO shipment (destination, weight, status, shipping_date) VALUES ('London', 15.0, 'PENDING', NULL);