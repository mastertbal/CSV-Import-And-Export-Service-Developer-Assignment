# PROJECT DESCRIPTION
---

This project is a backend application that imports students data from a csv file and stores it inside a database, export the student data in the database to a csv file also.

## TECH STACK
* Maven
* Java verion 17
* Spring boot 4.0
* Spring Validaition IO
* Spring Web
* Spring Data JPA
* Spring H2 in-memory Database
* opencsv

## HOW TO RUN THE PROJECT
1. Make sure at least Java 17 is installed on your system
2. Clone the repository into your local system using the following link: https://github.com/mastertbal/CSV-Import-And-Export-Service-Developer-Assignment.git
3. Open the cloned project in your IDE
4. Build the program
5. Run the program

## EXPECTED CSV FORMAT AND COLUMN DEFINITION
There is an endpoint to export the student data in the database into a csv file. The name of the file will be students.csv
The columns in the csv file are:
1. AGE
2. COURSE
3. EMAIL
4. FIRSTNAME
5. ID
6. LASTNAME

## FILE SIZE LIMIT AND EXPECTED FILE TYPE
The file size limit is 2MB as specified in the application.properties like so:
      # set max file size upload
      spring.servlet.multipart.max-file-size=2MB

The expected file type is .csv. Any other file type will throw an exception

## HOW TO ACCESS SWAGGER UI
You can access swagger UI using this link: http://localhost:8080/swagger-ui/index.html

## ANY ASSUMPTION AND DESIGN DESICION MADE
One of the design decision i made is to indicate the column name that its value could not be fetched. The failure output is like so:

```
        {
            "row": 7,
            "column": "Email",
            "reason": "Invalid email format"
        }
```

## RESTFUL ENDPONT DETAILS
| METHOD | URL                                                  | PURPOSE                                                                         |
| ------ |------------------------------------------------------|---------------------------------------------------------------------------------|
| POST    | http://localhost:8080/api/v1/students/create-student | Create a student entity and persist it in the database                          |
| POST    | http://localhost:8080/api/v1/students/import         | Import a csv file containing student data and persisting them into the database |
| GET    | http://localhost:8080/api/v1/students                | Get all students in the database                                                |
| GET    | http://localhost:8080/api/v1/students/{id}           | Get a student using the student id                                              |
| GET    | http://localhost:8080/api/v1/students/export         | Get all students in the database and export them into a csv file                |

