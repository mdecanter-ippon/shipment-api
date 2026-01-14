-- Initial Data for the Training
-- Note: SQL date format is YYYY-MM-DD. JSON format will be dd-MM-yyyy.
INSERT INTO shipment (destination, weight, status, shipping_date, investigation_code) VALUES ('New York', 12.5, 'SHIPPED', '2023-10-25', 'SECRET-NY-001');
INSERT INTO shipment (destination, weight, status, shipping_date, investigation_code) VALUES ('Tokyo', 5.0, 'PENDING', '2023-12-01', 'SECRET-TOK-999');
INSERT INTO shipment (destination, weight, status, shipping_date, investigation_code) VALUES ('Paris', 2.3, 'DELIVERED', '2023-09-15', 'SECRET-PAR-777');
INSERT INTO shipment (destination, weight, status, shipping_date, investigation_code) VALUES ('London', 15.0, 'PENDING', NULL, 'SECRET-LON-555');