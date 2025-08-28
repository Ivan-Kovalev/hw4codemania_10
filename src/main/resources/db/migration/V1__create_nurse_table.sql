CREATE TABLE nurse (
                       id SERIAL PRIMARY KEY,
                       doctor_id INTEGER NOT NULL,
                       name VARCHAR(255) NOT NULL,
                       years_of_experience INTEGER NOT NULL,
                       graduation_date DATE,
                       employment_date DATE
);