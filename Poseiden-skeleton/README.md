![Projet 7 du parcours DA Java OpenClassrooms](src/main/resources/static/img/poseidon.png)


### DB script : 
* doc/schemaAndUser.sql ()<br>
* doc/data.sql (poseidon_dev)<br>
* src/test/ressources/setUpTestDataBase.sql (poseidon_test)<br>

### Start application

* copy/clone this project on your computer.
* run sql query from doc/schemaAndUser.sql
* run sql query from doc/data.sql
* run sql query from src/test/ressources/setUpTestDataBase.sql
* run the following maven command on your terminal : cd {yourFolderName}/JavaDA_PROJECT7_RESTAPI/Poseiden-skeleton/
  && mvn spring-boot:run
* Access endpoints on localhost:8080/

## Technical:

1. MySl : 8
2. Java : 11
3. Maven : 3.6.0
4. SpringBoot : 2.5.3 
5. Thymeleaf
6. Bootstrap v.4.3.1


## Setup with Intellij IDE
1. Create project from Initializr: File > New > project > Spring Initializr
2. Add lib repository into pom.xml
3. Add folders
    - Source root: src/main/java
    - View: src/main/resources
    - Static: src/main/resource/static
4. Create database with name "demo" as configuration in application.properties
5. Run sql script to create table doc/data.sql

## Implement a Feature
1. Create mapping domain class and place in package com.nnk.springboot.domain
2. Create repository class and place in package com.nnk.springboot.repositories
3. Create controller class and place in package com.nnk.springboot.controllers
4. Create view files and place in src/main/resource/templates

## Write Unit Test
1. Create unit test and place in package com.nnk.springboot in folder test > java

## Security
1. Create user service to load user from  database and place in package com.nnk.springboot.services
2. Add configuration class and place in package com.nnk.springboot.config
