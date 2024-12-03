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

ALTER TABLE request ADD COLUMN reject_reason TEXT;

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

ALTER TABLE public.request_status DROP CONSTRAINT fk_sending_employee;
ALTER TABLE public.request_status ADD CONSTRAINT fk_sending_employee FOREIGN KEY (sending_employee_id) REFERENCES employee(user_id);

ALTER TABLE public.request_status DROP CONSTRAINT fk_in_charge_employee;
ALTER TABLE public.request_status ADD CONSTRAINT fk_in_charge_employee FOREIGN KEY (sending_employee_id) REFERENCES employee(user_id);

ALTER TABLE public.request ADD COLUMN reject_reason TEXT;

ALTER TABLE public.request DROP CONSTRAINT fk_customer;

UPDATE request
SET customer_id = customer.user_id
FROM customer
WHERE request.customer_id = customer.id;

ALTER TABLE public.request ADD CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(user_id);

CREATE OR REPLACE FUNCTION proc_detailRequest(r_id BIGINT)
RETURNS TABLE(id INT, equip_desc TEXT, defect_desc TEXT, budget NUMERIC(10,2), repair_desc TEXT, customer_orientations TEXT,
			equip_category_id INT4,customer_id INT4, active BOOL, reject_reason TEXT, category_desc VARCHAR(50), cpf VARCHAR(11),
			phone_number VARCHAR(15), email VARCHAR(255), name VARCHAR(100), surname VARCHAR(100), cep VARCHAR(8), uf VARCHAR(2),
			city VARCHAR(32), district VARCHAR(48), street VARCHAR(48), number VARCHAR(10), rs_id INT, date_time TIMESTAMP,
			status public.status_enum, in_charge_employee_id INT, sending_employee_id INT)
LANGUAGE plpgsql AS
$$
BEGIN
	RETURN QUERY
	SELECT r.*, ec.category_desc, c.cpf, c.phone_number, u.email, u."name", u.surname, a.cep, a.uf, a.city, a.district, a.street,
			a."number", rs.id AS rs_id, rs.date_time, rs.status, rs.in_charge_employee_id, rs.sending_employee_id
	FROM public.request r JOIN public.equip_category ec ON r.equip_category_id = ec.id
	JOIN public.customer c ON r.customer_id = c.user_id JOIN public.address a ON c.address_id = a.id
	JOIN public.user u ON c.user_id = u.id LEFT JOIN public.request_status rs ON rs.request_id = r.id
	WHERE r.id = r_id AND r.active = true ORDER BY rs.date_time;
END;
$$;

CREATE OR REPLACE FUNCTION proc_customerList(c_id BIGINT)
RETURNS TABLE(id INT, equip_desc TEXT, defect_desc TEXT, status public.status_enum, customer_id INT4, customer_name VARCHAR(100),
			customer_surname VARCHAR(100), customer_email VARCHAR(255), customer_phone VARCHAR(15), customer_cpf VARCHAR(11), street VARCHAR(48),
			number VARCHAR(10), district VARCHAR(48), city VARCHAR(32),   uf VARCHAR(2), cep VARCHAR(8), created_at TIMESTAMP)
LANGUAGE plpgsql AS
$$
BEGIN
	RETURN QUERY
	SELECT r.id, r.equip_desc, r.defect_desc, rs.status, c.user_id AS customer_id, u.name AS customer_name, u.surname AS customer_surname,
			u.email AS customer_email, c.phone_number AS customer_phone, c.cpf AS customer_cpf, a.street, a.number, a.district, a.city, a.uf,
			a.cep, rs.date_time AS created_at
	FROM public.request r JOIN (
	                        SELECT DISTINCT ON (request_id) request_id, rs.status, date_time
	                        FROM public.request_status rs
	                        ORDER BY request_id, date_time DESC
	                    ) rs ON r.id = rs.request_id
	JOIN public.customer c ON r.customer_id = c.user_id
	JOIN public.user u ON c.user_id = u.id
	JOIN public.address a ON c.address_id = a.id
	WHERE r.customer_id = c_id AND r.active = TRUE;
END;
$$;

CREATE OR REPLACE FUNCTION proc_employeeList(e_id BIGINT)
RETURNS TABLE(id INT, equip_desc TEXT, defect_desc TEXT, status public.status_enum, customer_id INT4, customer_name VARCHAR(100),
			customer_surname VARCHAR(100), customer_email VARCHAR(255), customer_phone VARCHAR(15), customer_cpf VARCHAR(11), street VARCHAR(48),
			number VARCHAR(10), district VARCHAR(48), city VARCHAR(32),   uf VARCHAR(2), cep VARCHAR(8), created_at TIMESTAMP)
LANGUAGE plpgsql AS
$$
BEGIN
	RETURN QUERY
	SELECT r.id, r.equip_desc, r.defect_desc, rs.status, c.user_id AS customer_id,
           u.name AS customer_name, u.surname AS customer_surname, u.email AS customer_email,
           c.phone_number AS customer_phone, c.cpf AS customer_cpf, a.street, a.number, a.district,
           a.city, a.uf, a.cep, rs.date_time AS created_at
    FROM public.request r
    JOIN (
        SELECT DISTINCT ON (request_id) request_id, rs.status, date_time
        FROM public.request_status rs
        ORDER BY request_id, date_time DESC
    ) rs ON r.id = rs.request_id
    JOIN public.customer c ON r.customer_id = c.user_id
    JOIN public."user" u ON c.user_id = u.id
    JOIN public.address a ON c.address_id = a.id
    WHERE r.id IN
    (SELECT request_id FROM public.request_status rs WHERE
    (rs.status = 'OPEN' AND date_time = (SELECT MAX(date_time) FROM public.request_status WHERE request_id = rs.request_id)) OR
	(in_charge_employee_id = e_id AND date_time = (SELECT MAX(date_time) FROM public.request_status
	WHERE request_id = rs.request_id AND in_charge_employee_id NOTNULL))) AND r.active = TRUE;
END;
$$;

