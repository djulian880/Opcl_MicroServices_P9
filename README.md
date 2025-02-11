# Medilabo Solutions
Openclassrooms project number 9

<!-- ABOUT THE PROJECT -->
## About The Project

Microservices application for the startup Medilabo Solutions. Helps a doctor writing notes about patient and assess a diabet report based on key terms searched in the notes.
This is a Spring Boot application for project number 9 of [Openclassrooms](https://openclassrooms.com/) java développer formation.

Project goals:
* Create a client UI for the application.
* Create a microservice for SQL data access.
* Create a microservice for NoSQL data access.
* Create a microservice for diabetes assessment.
* Deploy the app in docker containers.

## Architecture Diagram
![architecture-diagram](https://github.com/djulian880/Opcl_MicroServices_P9/blob/dev/architecture%20microservices.png)

### Built With

* Java 17
* Docker

<!-- GREEN CODE IMPROVEMENTS -->
## Green code improvements

The following aspects could be improved in order to fulfill the green code recommandations:
- Profile the code to improve the execution
- Improve the microservices notes, this is the slowest
- Improve the compilation speed
- The NotePatient model class includes the name of patient, this is redundant with the patient class, could be removed

