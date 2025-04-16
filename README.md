# BPI Exam
A Java Spring Boot Application that Calculates Schedule of a Project Plan

# Setup
- Clone the Repository
- Build the Project (mvn clean install)
- Run the project via IDE Application Config or via terminal using "mvn spring-boot:run"

# Database Details
- Datasource URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (leave it blank)

---
**NOTE**

While the application is running, Go to http://localhost:8080/h2-console in your browser.

---

# API Endpoints
### Task
- GET Task (using ID)
- GET All Task
- POST Create Task
- PATCH Update Task
- DELETE Task
- GET Task Dependencies (Gets the task/s dependent on the given ID)
  
### Project Plan
- GET Project Plan (using ID)
- GET All Project Plan
- POST Create Project Plan
- PATCH Update Project Plan
- DELETE Project Plan
- GET Calculate Schedule (using ID)
- GET All Calculate Schedule

# For API Sample
- Please refer to the uploaded postman collection
