1. You should have MySQL installed


2. Create databases in MySQL

create database if not exists itis_maven_schema_dev;
create database if not exists itis_maven_schema_test;


3. Create db user in MySQL in mysql console

CREATE USER 'dbuser'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON itis_maven_schema_dev.* TO 'dbuser'@'localhost';
GRANT ALL PRIVILEGES ON itis_maven_schema_test.* TO 'dbuser'@'localhost';
FLUSH PRIVILEGES;


=== WHAT YOU CAN DO ===


1. There is one test that tests UserRepository: it creates one user (always the same) and checks user count has been increased

This time no need to specify Maven profiles, all database configuration is taken from database-env.properties file

This should work:
mvn test

2. Simply run ExampleMain with exec:java Maven plugin

The program will add ["Sherlock Holmes",age=35] user and display current count.

./exec_dev.sh

