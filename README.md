# Lottery Assignment API

This project is a simple REST API for managing a lottery system. The API allows users to:
- Create lottery tickets with a specified number of lines.
- Retrieve all tickets or a specific ticket.
- Add additional lines to an existing ticket.
- Check the status of a ticket and sort the results of its lines.

---

## Features

1. **Create Ticket**
    - Generate a new lottery ticket with custom lines.

2. **Retrieve Tickets**
    - Fetch all tickets or retrieve a specific ticket by ID.

3. **Add Lines to a Ticket**
    - Append additional lines to an existing ticket.

4. **Check Ticket Status**
    - Evaluate the status of a ticket and sort its lines based on their results.

---

## API Endpoints

- **Create a Ticket**: `POST /ticket?numberOfLines=<number>`
- **Get All Tickets**: `GET /ticket`
- **Get a Ticket by ID**: `GET /ticket/{id}`
- **Add Lines to a Ticket**: `PUT /ticket/{id}?numberOfLines=<number>`
- **Check Ticket Status**: `PUT /status/{id}`

---

## How to Run

1. **Clone the Repository**
   ```bash
   git clone https://github.com/CseRahul/lottery-assignment.git
   cd lottery-assignment

2. **Build and Run the Application**
Ensure Java 17+ and Maven are installed.
    ```bash
    mvn spring-boot:run
    ```
---

## Access API Documentation

Open Swagger UI: http://localhost:8080/swagger-ui.html

## Tech Stacks
- **Java**
- **JDK 17**
- **Spring Boot**
- **Maven** 
- **Swagger/OpenAPI**
- **lombok**
- **Rest Assured**
- **Used Intellij Idea IDE**


---
### Rahul Kumar, 
#### reader.rahul@gmail.com
#### +91 8936003979

