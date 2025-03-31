# GitHub Repository Info API

A simple Spring Boot application that provides a REST API for retrieving non-fork GitHub repositories of a user, along with their branches and latest commit SHA.

---

## 🔧 Technologies Used

- Java 21
- Spring Boot 3.4.4
- Spring Web
- JUnit 5
- MockMvc
- Gradle

---

## 🚀 Getting Started

### Clone the Repository

git clone https://github.com/Mart6DeLavi/Instagram-Clone

### Build and Run the Project

./gradlew bootRun

The application will start on port 8000.

---

## 📡 API Endpoints

### GET /{username}

Fetches all non-fork repositories for the given GitHub user along with their branches and latest commit SHA.

- Path Parameter:
    - username – GitHub username

Example Request:

GET http://localhost:8000/octocat

Example Response:

[
{
"repositoryName": "Hello-World",
"ownerLogin": "octocat",
"branches": [
{
"name": "main",
"lastCommitsSha": "7fd1a60b01f91b314f59951d231d4c9f72e5e3f1"
}
]
}
]

---

## ❗ Error Handling

404 Not Found

If the GitHub user does not exist:

{
"status": 404,
"message": "User not found"
}

500 Internal Server Error

For any unexpected server-side issues:

{
"status": 500,
"message": "Unexpected error occurred"
}

Note: GitHub API is rate-limited. Unauthenticated requests are limited to 60 requests per hour per IP. Exceeding this limit will result in a 403 Forbidden response.

---

## 🧪 Running Tests

To run integration tests:

./gradlew test

Note: Tests may fail if the GitHub API rate limit is exceeded.

---

## 📁 Project Structure

├── controller/  
│   └── MainController.java  
├── service/  
│   └── MainFunctionalService.java  
├── dto/  
│   ├── response/  
│   └── request/  
├── exception/  
│   ├── UserNotFoundException.java  
│   └── GlobalExceptionHandler.java  
└── test/  
└── MainControllerTest.java
