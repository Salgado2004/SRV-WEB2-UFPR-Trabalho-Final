CREATE DATABASE manutads;

CREATE TYPE profile_enum AS ENUM ('CUSTOMER','EMPLOYEE');

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    password CHAR(69) NOT NULL,
    profile profile_enum NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL,
    birth_date DATE NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id)
);

CREATE INDEX idx_employee_user_id ON employee(user_id);

CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    cep VARCHAR(8) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    city VARCHAR(32) NOT NULL,
    district VARCHAR(48) NOT NULL,
    street VARCHAR(48) NOT NULL,
    number VARCHAR(10) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE customer (
    id SERIAL PRIMARY KEY,
    user_id INTEGER UNIQUE NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address_id INTEGER NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_user_customer FOREIGN KEY (user_id) REFERENCES "user"(id),
    CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES address(id)
);

CREATE INDEX idx_customer_user_id ON customer(user_id);
CREATE INDEX idx_customer_address_id ON customer(address_id);

CREATE TABLE equip_category (
    id SERIAL PRIMARY KEY,
    category_desc VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE TABLE request (
    id SERIAL PRIMARY KEY,
    equip_desc TEXT NOT NULL,
    defect_desc TEXT NOT NULL,
    budget NUMERIC(10, 2) NOT NULL,
    repair_desc TEXT,
    customer_orientations TEXT,
    equip_category_id INTEGER NOT NULL,
    customer_id INTEGER NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_equip_category FOREIGN KEY (equip_category_id) REFERENCES equip_category(id),
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE INDEX idx_request_equip_category_id ON request(equip_category_id);
CREATE INDEX idx_request_customer_id ON request(customer_id);

CREATE TYPE status_enum AS ENUM ('OPEN','BUDGETED','REJECTED','APPROVED','REDIRECTED','FIXED','PAID','FINALIZED');

CREATE TABLE request_status (
    id SERIAL PRIMARY KEY,
    date_time TIMESTAMP NOT NULL,
    request_id INTEGER NOT NULL,
    sending_employee_id INTEGER,
    in_charge_employee_id INTEGER,
    status status_enum NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    CONSTRAINT fk_request FOREIGN KEY (request_id) REFERENCES request(id),
    CONSTRAINT fk_sending_employee FOREIGN KEY (sending_employee_id) REFERENCES employee(id),
    CONSTRAINT fk_in_charge_employee FOREIGN KEY (in_charge_employee_id) REFERENCES employee(id)
);

CREATE INDEX idx_request_status_request_id ON request_status(request_id);
CREATE INDEX idx_request_status_sending_employee_id ON request_status(sending_employee_id);
CREATE INDEX idx_request_status_in_charge_employee_id ON request_status(in_charge_employee_id);