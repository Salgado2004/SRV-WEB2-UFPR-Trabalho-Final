-- DROP SCHEMA public;

CREATE SCHEMA public AUTHORIZATION pg_database_owner;

COMMENT ON SCHEMA public IS 'standard public schema';

-- DROP TYPE public.status_enum;

CREATE TYPE public.status_enum AS ENUM (
	'OPEN',
	'BUDGETED',
	'REJECTED',
	'APPROVED',
	'REDIRECTED',
	'FIXED',
	'PAID',
	'FINALIZED');

-- DROP SEQUENCE public.address_id_seq;

CREATE SEQUENCE public.address_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.customer_id_seq;

CREATE SEQUENCE public.customer_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.employee_id_seq;

CREATE SEQUENCE public.employee_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.equip_category_id_seq;

CREATE SEQUENCE public.equip_category_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.request_id_seq;

CREATE SEQUENCE public.request_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.request_status_id_seq;

CREATE SEQUENCE public.request_status_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;
-- DROP SEQUENCE public.user_id_seq;

CREATE SEQUENCE public.user_id_seq
	INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	START 1
	CACHE 1
	NO CYCLE;-- public.address definição

-- Drop table

-- DROP TABLE public.address;

CREATE TABLE public.address (
	id serial4 NOT NULL,
	cep varchar(8) NOT NULL,
	uf varchar(2) NOT NULL,
	city varchar(32) NOT NULL,
	district varchar(48) NOT NULL,
	street varchar(48) NOT NULL,
	"number" varchar(10) NOT NULL,
	active bool DEFAULT true NULL,
	CONSTRAINT address_pkey PRIMARY KEY (id)
);


-- public.equip_category definição

-- Drop table

-- DROP TABLE public.equip_category;

CREATE TABLE public.equip_category (
	id serial4 NOT NULL,
	category_desc varchar(50) NOT NULL,
	active bool DEFAULT true NULL,
	CONSTRAINT equip_category_pkey PRIMARY KEY (id)
);


-- public."user" definição

-- Drop table

-- DROP TABLE public."user";

CREATE TABLE public."user" (
	id serial4 NOT NULL,
	email varchar(255) NOT NULL,
	"name" varchar(100) NOT NULL,
	surname varchar(100) NOT NULL,
	"password" bpchar(69) NOT NULL,
	active bool DEFAULT true NULL,
	profile varchar NOT NULL,
	CONSTRAINT user_email_key UNIQUE (email),
	CONSTRAINT user_pkey PRIMARY KEY (id)
);


-- public.customer definição

-- Drop table

-- DROP TABLE public.customer;

CREATE TABLE public.customer (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	cpf varchar(11) NOT NULL,
	phone_number varchar(15) NOT NULL,
	address_id int4 NOT NULL,
	active bool DEFAULT true NULL,
	CONSTRAINT costumer_unique_user_ir UNIQUE (user_id),
	CONSTRAINT customer_cpf_key UNIQUE (cpf),
	CONSTRAINT customer_pkey PRIMARY KEY (id),
	CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES public.address(id),
	CONSTRAINT fk_user_customer FOREIGN KEY (user_id) REFERENCES public."user"(id)
);
CREATE INDEX idx_customer_address_id ON public.customer USING btree (address_id);
CREATE INDEX idx_customer_user_id ON public.customer USING btree (user_id);


-- public.employee definição

-- Drop table

-- DROP TABLE public.employee;

CREATE TABLE public.employee (
	id serial4 NOT NULL,
	user_id int4 NOT NULL,
	birth_date date NOT NULL,
	active bool DEFAULT true NULL,
	CONSTRAINT employee_pkey PRIMARY KEY (id),
	CONSTRAINT fk_unique_user_id UNIQUE (user_id),
	CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES public."user"(id)
);
CREATE INDEX idx_employee_user_id ON public.employee USING btree (user_id);


-- public.request definição

-- Drop table

-- DROP TABLE public.request;

CREATE TABLE public.request (
	id serial4 NOT NULL,
	equip_desc text NOT NULL,
	defect_desc text NOT NULL,
	budget numeric(10, 2) NOT NULL,
	repair_desc text NULL,
	customer_orientations text NULL,
	equip_category_id int4 NOT NULL,
	customer_id int4 NOT NULL,
	active bool DEFAULT true NULL,
	reject_reason text NULL,
	CONSTRAINT request_pkey PRIMARY KEY (id),
	CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES public.customer(user_id),
	CONSTRAINT fk_equip_category FOREIGN KEY (equip_category_id) REFERENCES public.equip_category(id)
);
CREATE INDEX idx_request_customer_id ON public.request USING btree (customer_id);
CREATE INDEX idx_request_equip_category_id ON public.request USING btree (equip_category_id);


-- public.request_status definição

-- Drop table

-- DROP TABLE public.request_status;

CREATE TABLE public.request_status (
	id serial4 NOT NULL,
	date_time timestamp NOT NULL,
	request_id int4 NOT NULL,
	sending_employee_id int4 NULL,
	in_charge_employee_id int4 NULL,
	status public.status_enum NOT NULL,
	active bool DEFAULT true NULL,
	CONSTRAINT request_status_pkey PRIMARY KEY (id),
	CONSTRAINT fk_in_charge_employee FOREIGN KEY (sending_employee_id) REFERENCES public.employee(user_id),
	CONSTRAINT fk_request FOREIGN KEY (request_id) REFERENCES public.request(id),
	CONSTRAINT fk_sending_employee FOREIGN KEY (sending_employee_id) REFERENCES public.employee(user_id)
);
CREATE INDEX idx_request_status_request_id ON public.request_status USING btree (request_id);
CREATE INDEX idx_request_status_sending_employee_id ON public.request_status USING btree (sending_employee_id);



-- DROP FUNCTION public.proc_customerlist(int8);

CREATE OR REPLACE FUNCTION public.proc_customerlist(c_id bigint)
 RETURNS TABLE(id integer, equip_desc text, defect_desc text, status status_enum, customer_id integer, customer_name character varying, customer_surname character varying, customer_email character varying, customer_phone character varying, customer_cpf character varying, street character varying, number character varying, district character varying, city character varying, uf character varying, cep character varying, created_at timestamp without time zone)
 LANGUAGE plpgsql
AS $function$
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
$function$
;

-- DROP FUNCTION public.proc_detailrequest(int8);

CREATE OR REPLACE FUNCTION public.proc_detailrequest(r_id bigint)
 RETURNS TABLE(id integer, equip_desc text, defect_desc text, budget numeric, repair_desc text, customer_orientations text, equip_category_id integer, customer_id integer, active boolean, reject_reason text, category_desc character varying, cpf character varying, phone_number character varying, email character varying, name character varying, surname character varying, cep character varying, uf character varying, city character varying, district character varying, street character varying, number character varying, rs_id integer, date_time timestamp without time zone, status status_enum, in_charge_employee_id integer, sending_employee_id integer)
 LANGUAGE plpgsql
AS $function$
BEGIN
	RETURN QUERY
	SELECT r.*, ec.category_desc, c.cpf, c.phone_number, u.email, u."name", u.surname, a.cep, a.uf, a.city, a.district, a.street, 
			a."number", rs.id AS rs_id, rs.date_time, rs.status, rs.in_charge_employee_id, rs.sending_employee_id 
	FROM public.request r JOIN public.equip_category ec ON r.equip_category_id = ec.id 
	JOIN public.customer c ON r.customer_id = c.user_id JOIN public.address a ON c.address_id = a.id 
	JOIN public.user u ON c.user_id = u.id LEFT JOIN public.request_status rs ON rs.request_id = r.id 
	WHERE r.id = r_id AND r.active = true ORDER BY rs.date_time;
END;
$function$
;

-- DROP FUNCTION public.proc_employeelist(int8);

CREATE OR REPLACE FUNCTION public.proc_employeelist(e_id bigint)
 RETURNS TABLE(id integer, equip_desc text, defect_desc text, status status_enum, customer_id integer, customer_name character varying, customer_surname character varying, customer_email character varying, customer_phone character varying, customer_cpf character varying, street character varying, number character varying, district character varying, city character varying, uf character varying, cep character varying, created_at timestamp without time zone)
 LANGUAGE plpgsql
AS $function$
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
$function$
;