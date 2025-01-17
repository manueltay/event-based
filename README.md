# Test project for event based ingestions

## Pre requisites
1. Kafka
2. MongoDB
3. Java 17

## This includes a module project

### data-api
Allows the user to define the following endpoints

* client
* product

### worker-consumer
Listens to a kafka topic which will then validate the data
using the data-api

If the data is valid will proceed to insert on mongodb
