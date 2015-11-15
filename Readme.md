### Implementation of news service ###

Used technologies: spring-boot, spring-data-jpa, jersey2
Testing: Junit for unit testing and TestNG for integration tests.

Obs.: For articles searching I'm using a separate service which, because maybe at a later stage someone will like to index
the data in a full text search engine and to perform the search there.

### End points details ###

Details about exposed end points can be found in package com.github.atdi.news.server.resources from subproject server
but also in the postman collection news-service.json.postman_collection located in the root folder.

### Starting the project from command line ###

./gradlew bootRun

### Build project with test coverage check and integration tests ###

./gradlew build jacocoTestReport integrationTest