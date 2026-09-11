CREATE TABLE vehicles (
    id BIGSERIAL PRIMARY KEY,

    name VARCHAR(255),
    location VARCHAR(100),

    year INTEGER,
    kilometers_driven INTEGER,

    fuel_type VARCHAR(50),
    transmission VARCHAR(50),
    owner_type VARCHAR(50),

    mileage DOUBLE PRECISION,
    engine DOUBLE PRECISION,
    power DOUBLE PRECISION,

    seats INTEGER,

    new_price DOUBLE PRECISION,
    price DOUBLE PRECISION
);