-- Insert sample car data with image URLs
-- INSERT IGNORE ensures this is safe to run on every startup (no duplicate errors)

INSERT IGNORE INTO cars (id, brand, model, year, daily_price, fuel_type, type, image_url, status, created_at) VALUES
(1, 'BMW', '3-Series', 2023, 89.99, 'Gasoline', 'Sedan', '/assets/bmw 3.jpg', 'AVAILABLE', NOW()),
(2, 'Toyota', 'Camry', 2023, 64.99, 'Hybrid', 'Sedan', '/assets/camry.jpg', 'AVAILABLE', NOW()),
(3, 'Honda', 'CR-V', 2023, 79.99, 'Gasoline', 'SUV', '/assets/crv.jpg', 'AVAILABLE', NOW()),
(4, 'Ford', 'F-150', 2023, 99.99, 'Gasoline', 'Truck', '/assets/ford f-150.jpg', 'AVAILABLE', NOW()),
(5, 'Tesla', 'Model 3', 2023, 109.99, 'Electric', 'Sedan', '/assets/tesla3.jpg', 'AVAILABLE', NOW());

