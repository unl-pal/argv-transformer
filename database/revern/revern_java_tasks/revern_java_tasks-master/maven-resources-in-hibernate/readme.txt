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

1. Build jar with one of profile: dev, test, prod

There are ready scripts with corresponding commands:
build_dev.sh - builds jar in /target folder with db url, user, password from "dev" Maven profile
build_prod.sh - builds jar in /target folder with db url, user, password from "prod" Maven profile

1b. After you build jar, simply run it and see that it works

java -jar target/itis-maven-filtering.jar

Note: it will work only for "dev" profile as you didn't configure your MySQL for "prod" - this way you will check that
different db url/user/pswd was used and Maven filtering really works

2. There is one test that tests UserRepository: it creates one user (always the same) and checks user count has been increased

This should work:
mvn test -Pdev

This should work:
mvn test -Ptest

This should NOT work as there is no "itis_maven_schema_prod" schema in MySQL:
mvn test -Pprod

3. Simply run ExampleMain with exec:java Maven plugin

The program will add ["James Bond",age=25] user and display current count.
This count will increase on each run: this is because we use "hibernate.hbm2ddl.auto"=update.
And it differs from running tests where we use "hibernate.hbm2ddl.auto"=create-drop

For "dev" profile:
./exec_dev.sh

For "prod" profile:
./exec_test.sh