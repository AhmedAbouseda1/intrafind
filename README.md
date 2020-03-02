# Intra Find User API

REST API based on Spring Boot containing requests for CRUD operations (Create, Read, Update and Delete) for User.

#### ***Steps to run Intra Find User API :***

#### **Create you own database**
Create empty db and flyway will create the tables and the data
```bash
CREATE DATABASE  IF NOT EXISTS `intrafind_user_db`; 
 ```


#### **Database Configuration**
Intra Find User has 2 profiles docker and local.

Docker profile if you would like to run the application on docker, and local profile to run the application on your local 
configuration file is (application-docker.yml).

Local profile if you would like to run on your local,
configuration file is (application-local.yml).

#### **Choose your profile**
please check application.properties to choose your profile


#### **Build Intra Find User API :**

```bash
 $ mvn clean install
 ```


##### **Run on docker**

Build docker:
```bash
$ docker-compose build
 ```

Up docker:  
```bash
$ docker-compose up
 ```

##### **Run on local**

Run using Terminal :
    
```bash
$ java -jar target/intrafind.jar
 ```


# Swagger Console

Further documentation for the endpoints can be found at: [http://localhost:8085/intrafind/swagger-ui.html](http://localhost:8085/intrafind/swagger-ui.html)
