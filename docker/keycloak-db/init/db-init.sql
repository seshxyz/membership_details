CREATE SCHEMA IF NOT EXISTS provider_schema;
CREATE USER keycloak_provider WITH PASSWORD 'password';
GRANT ALL PRIVILEGES ON SCHEMA provider_schema TO keycloak_provider;