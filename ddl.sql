-- Table: public.foodtrucks

-- DROP TABLE public.foodtrucks;

CREATE TABLE public.foodtrucks
(
  id text NOT NULL,
  name text NOT NULL,
  CONSTRAINT foodtrucks_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.foodtrucks
  OWNER TO web_server;

-- Table: public.truck_location

-- DROP TABLE public.truck_location;

CREATE TABLE public.truck_location
(
  date date NOT NULL,
  foodtruck_id text NOT NULL,
  timestart time without time zone,
  timeend time without time zone,
  coord point NOT NULL,
  description text,
  CONSTRAINT truck_location_pkey PRIMARY KEY (foodtruck_id, date)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.truck_location
  OWNER TO web_server;
