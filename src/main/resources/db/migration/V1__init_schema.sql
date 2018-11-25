DROP TABLE IF EXISTS transfer;

CREATE TABLE transfer (
	id bigserial NOT NULL,
	account_from varchar(255) NOT NULL,
	account_to varchar(255) NOT NULL,
	amount numeric(19,2) NOT NULL,
	CONSTRAINT transfer_pkey PRIMARY KEY (id)
);
