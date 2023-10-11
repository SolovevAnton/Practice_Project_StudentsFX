# Practice_Project_StudentsFX
Front part for the students servlet project


## Overview
StudentsClient is the client-side component of the [StudentsServlets project](https://github.com/SolovevAnton/Practice_Project_StudentsServlet), designed to interact with the server-side APIs for managing student and auto data. This project leverages various technologies and tools to provide a user-friendly interface for viewing, updating, and deleting student and auto entities.
Technologies Used

    JavaFx: for the front part creaqtion
    Apache Tomcat: The project can be deployed onr Apache Tomcat,  Java application server.
    Servlet API: Java Servlets are used for handling HTTP requests and responses.
    MySQL DB: MySQL is employed as the database management system for storing student and auto data.
    JDBC: Java Database Connectivity is used for database interactions.
    Jackson: The Jackson library is used for JSON data serialization and deserialization

## Features
Supports all CRUD operations, for both students and cars entitites
Creates a teble with all students in DB
by double click on student - shows table with all cars for that student

## Screenshots
![image](https://github.com/SolovevAnton/Practice_Project_StudentsFX/assets/121192850/ea4edeca-f767-4c0f-a46b-232a0db3cbca)


![image](https://github.com/SolovevAnton/Practice_Project_StudentsFX/assets/121192850/c15fadb5-40c6-46fa-ba18-9ed70742ab3c)

![image](https://github.com/SolovevAnton/Practice_Project_StudentsFX/assets/121192850/4ff05010-30bc-4333-b4d9-f25622116452)

## Description
1. Data Model Consistency: StudentsClient features a meticulously designed data model that mirrors the data structure used in the StudentsServlets project. This alignment ensures seamless data consistency and efficient communication between the client and server components.

2. Client Repositories: Within this project, you'll find well-defined client-side repositories. These repositories act as intermediaries between the client application and the server-side APIs. Each repository method is thoughtfully crafted to facilitate smooth interaction with the server.

3. Response Management: The client repository methods are equipped with robust response handling mechanisms. They gracefully manage responses received from the server. Whether it's retrieving student or auto data, these methods efficiently parse the responses and return model data objects. In cases of server-side errors, they respond gracefully by throwing IllegalArgumentException.

## Getting Started

To run the StudentsClient project:

1. Clone or download the [StudentsServlets](https://github.com/SolovevAnton/Practice_Project_StudentsServlet) and StudentsClient projects from the GitHub repository.
2. Import both projects into your preferred Java IDE.
3.  Set up a MySQL database with the necessary tables for student and auto data. Update the database connection details in the StudentsClient project accordingly.
4. Deploy the StudentsServlets project on a Tomcat server.
5. Launch the StudentsClient application.



Feel free to explore and contribute to this project. For any questions or collaboration opportunities, please reach out

Thank you for your interest in my portfolio!
