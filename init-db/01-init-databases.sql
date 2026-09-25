-- Runs only on a fresh data volume (Postgres ignores this once PGDATA is populated).
-- Recreates the role, databases and schemas the services expect.

CREATE ROLE ecommerce WITH LOGIN PASSWORD 'ecommerce';

CREATE DATABASE order_db        OWNER ecommerce;
CREATE DATABASE payment_db      OWNER ecommerce;
CREATE DATABASE inventory_db    OWNER ecommerce;

-- payment-service and inventory-service pin a non-public schema via
-- spring.jpa.properties.hibernate.default_schema, and Hibernate will not
-- create a missing schema itself, so they are created up front.

\connect payment_db
CREATE SCHEMA IF NOT EXISTS payment AUTHORIZATION ecommerce;

\connect inventory_db
CREATE SCHEMA IF NOT EXISTS inventory AUTHORIZATION ecommerce;
