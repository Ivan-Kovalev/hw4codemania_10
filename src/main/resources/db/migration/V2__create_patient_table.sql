CREATE TABLE patient (
                       id SERIAL PRIMARY KEY,
                       doctor_id INTEGER NOT NULL,
                       hospital_ward_id INTEGER NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       diagnosis VARCHAR(255) NOT NULL,
                       age INTEGER NOT NULL,
                       date_of_receipt DATE
);