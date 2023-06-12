INSERT INTO `esgi-rent`.rental_car (id, brand, model, rent_amount, security_deposit_amount, number_of_seats, number_of_doors, has_air_conditioning)
VALUES
    (1, 'Renault', 'Clio', 45.0, 150.0, 5, 4, 1),
    (2, 'Ford', 'Focus', 50.0, 200.0, 5, 4, 1),
    (3, 'BMW', '3 Series', 80.0, 300.0, 5, 4, 1),
    (4, 'Toyota', 'Corolla', 55.0, 250.0, 5, 4, 1),
    (5, 'Audi', 'A4', 90.0, 400.0, 5, 4, 1)
ON DUPLICATE KEY UPDATE
    brand = VALUES(brand),
    model = VALUES(model),
    rent_amount = VALUES(rent_amount),
    security_deposit_amount = VALUES(security_deposit_amount),
    number_of_seats = VALUES(number_of_seats),
    number_of_doors = VALUES(number_of_doors),
    has_air_conditioning = VALUES(has_air_conditioning);
