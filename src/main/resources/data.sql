-- Insert sample car data with image URLs
-- This file helps initialize your database with cars that have proper image paths

INSERT INTO cars (brand, model, year, daily_price, fuel_type, type, image_url, status, created_at) VALUES
('BMW', '3-Series', 2023, 89.99, 'Gasoline', 'Sedan', '/assets/bmw 3.jpg', 'AVAILABLE', NOW()),
('Toyota', 'Camry', 2023, 64.99, 'Hybrid', 'Sedan', '/assets/camry.jpg', 'AVAILABLE', NOW()),
('Honda', 'CR-V', 2023, 79.99, 'Gasoline', 'SUV', '/assets/crv.jpg', 'AVAILABLE', NOW()),
('Ford', 'F-150', 2023, 99.99, 'Gasoline', 'Truck', '/assets/ford f-150.jpg', 'AVAILABLE', NOW()),
('Tesla', '3', 2023, 109.99, 'Electric', 'Sedan', '/assets/tesla3.jpg', 'AVAILABLE', NOW());
