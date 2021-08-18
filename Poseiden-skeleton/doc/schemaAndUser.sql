# Create schema

CREATE SCHEMA poseidon_dev;
USE poseidon_dev;

# Create user
CREATE USER IF NOT EXISTS 'poseidon_dev'@'localhost' IDENTIFIED BY 'poseidon-dev';

# Grant privileges on schema

GRANT ALL PRIVILEGES ON poseidon_dev.* TO 'poseidon_dev'@'localhost';
FLUSH PRIVILEGES;