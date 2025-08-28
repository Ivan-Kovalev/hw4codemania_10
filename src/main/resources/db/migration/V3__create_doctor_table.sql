CREATE TABLE doctor (
                       id SERIAL PRIMARY KEY,
                       department_id INTEGER NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       years_of_experience INTEGER NOT NULL,
                       speciality VARCHAR(255) NOT NULL,
                       highest_category BOOLEAN NOT NULL DEFAULT TRUE,
                       graduation_date DATE,
                       employment_date DATE
);