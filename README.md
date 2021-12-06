# TEnmo
_Duration: 6 Days_

## Description
A RESTful API that allows authorized users to send virtual money between users using the command line. (Pair Programming)
- Utilized Spring Boot to create a RESTful API that accepted requests from clients to view, update, and transfer balances while following an MVC design pattern and implementing the DAO pattern with an interface to a PostgreSQL database

## Technologies Used
Project was built with:
- Java
- Spring Boot
- Spring JDBC
- PostgreSQL

## Feature List
Explore the API using Postman. You can access the following endpoints:

- POST: http://localhost:8080/login
- POST: http://localhost:8080/register
- GET: http://localhost:8080/getAccountBalance
- GET: http://localhost:8080/getUsers
- POST: http://localhost:8080/createTransfer
- GET: http://localhost:8080/getTransfers
- GET: http://localhost:8080/getTransfers/{transferId}

## Setup & Deployment

### Prerequisites
To run this project, make sure you have the following software installed:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/)
- [PostgreSQL](https://www.postgresql.org/)

Recommended technologies:

- [IntelliJ](https://www.jetbrains.com/idea/)
- [DbVisualizer](https://www.dbvis.com/)
- [Postman](https://www.postman.com/)

### Setup
{ TODO }

## Acknowledgements
Project was completed with [Justin](https://github.com/justinparker9) through pair programming at [Tech Elevator](https://www.techelevator.com/).

## Status
Project is complete.
