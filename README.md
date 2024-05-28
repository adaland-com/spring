# Board Games Rental Service
## Description
Simple Spring App to rent board games currently only backend part

### content v0.3
- AUTH with JWT token, mail
- CRUD operations on entities: Rent, Game

## Tech stack
Java 17, PostgresSQL, Postman 
## How to start?
## Prepare environment variables
[ ] Postgres user: DB_USERNAME, DB_PASSWORD
[ ] Gmail account: MAIL_ADDRESS, MAIL_PASSWORD
[ ] Secret key: SECRET_KEY
# DEV
Install dependecies
`./mvnw clean install`
Run with dev profile
`./mvnw spring-boot:run -Dspring.profiles.active=dev`

