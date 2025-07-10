

# 🔐 Spring Boot JWT Authentication API

This is a **Spring Boot** based **RESTful API** that implements **secure user authentication** using **JWT (JSON Web Tokens)**. The application provides endpoints to **register** and **log in** users and allows access to protected endpoints only when a valid JWT is present in the request header.

## 📌 Table of Contents

* [Features](#features)
* [Tech Stack](#tech-stack)
* [Architecture Overview](#architecture-overview)
* [How JWT Works in This Project](#how-jwt-works-in-this-project)
* [Endpoints](#endpoints)
* [Getting Started](#getting-started)
* [Testing with Postman](#testing-with-postman)
* [Folder Structure](#folder-structure)
* [Future Enhancements](#future-enhancements)
* [License](#license)

---

## ✅ Features

* 🔒 User Registration with encrypted passwords using BCrypt
* 🔑 User Login with credential validation
* 🪪 JWT Token Generation on successful login
* 📩 Token-based authentication for protected routes
* 🚫 Stateless authentication (no session management)
* 🛡️ Integrated Spring Security filters
* 🧪 Ready to test with Postman or any HTTP client
* 💬 Clean separation of concerns (Controller, Service, Utility, Filter layers)

---

## 💻 Tech Stack

| Layer        | Technology                       |
| ------------ | -------------------------------- |
| Language     | Java 17                          |
| Framework    | Spring Boot 3+ / Spring Security |
| Security     | JWT (io.jsonwebtoken)            |
| Build Tool   | Maven                            |
| Database     | MySQL (or H2, if preferred)      |
| ORM          | Spring Data JPA                  |
| Testing Tool | Postman / Swagger UI (optional)  |

---

## 🧠 Architecture Overview

```plaintext
Client → /register & /login
            ↓
     Spring Boot REST API
        ↓            ↓
UserController   AuthController
        ↓            ↓
  UserService   JwtUtil (JWT Generation)
        ↓
 MySQL Database

 🔁 Filter Layer (JwtFilter) intercepts incoming requests, extracts and validates JWT token, and sets authentication context
```

---

## 🔐 How JWT Works in This Project

1. **User Registers** — User sends email and password to `/register`.
2. **Password is hashed** using `BCryptPasswordEncoder` and saved to DB.
3. **User Logs In** via `/login` — If valid, server generates a **JWT token**.
4. **Client uses token** in `Authorization: Bearer <token>` header for subsequent requests.
5. **JwtFilter** intercepts all requests (except `/login` & `/register`), extracts the token, and validates it.
6. **Spring Security** checks the authenticated user and grants/denies access.

---

## 🚀 Endpoints

| Endpoint    | Method | Description                         | Auth Required |
| ----------- | ------ | ----------------------------------- | ------------- |
| `/register` | POST   | Register a new user                 | ❌ No          |
| `/login`    | POST   | Login with credentials, returns JWT | ❌ No          |
| `/hello`    | GET    | Example secured endpoint            | ✅ Yes         |

---

## 🔧 Getting Started

### Prerequisites

* Java 17+
* Maven
* MySQL Server (or H2 if you switch)

### Setup Instructions

1. **Clone the repo**

```bash
git clone https://github.com/your-username/spring-jwt-auth-api.git
cd spring-jwt-auth-api
```

2. **Configure `application.properties`**

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. **Run the project**

```bash
mvn spring-boot:run
```

---

## 🔬 Testing with Postman

### 🔐 1. Register a User

* **POST** `http://localhost:8080/register`
* **Body** (JSON):

```json
{
  "email": "testuser@example.com",
  "password": "password123"
}
```

---

### 🔑 2. Login

* **POST** `http://localhost:8080/login`
* **Body** (JSON):

```json
{
  "email": "testuser@example.com",
  "password": "password123"
}
```

* **Response**:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

### 🔓 3. Access Secured Endpoint

* **GET** `http://localhost:8080/hello`
* **Headers**:

```
Authorization: Bearer <your-token-here>
```

---

## 📁 Folder Structure

```bash
src/
└── main/
    └── java/
        └── com.example.demo/
            ├── Controllers/           # Auth and secured endpoints
            ├── Configuration/         # Spring Security configuration
            ├── Entities/              # User entity
            ├── Repositories/          # Spring Data JPA interfaces
            ├── Service/               # UserDetailsService impl
            ├── Utilities/             # JWT utils and filter
    └── resources/
        └── application.properties     # DB and app configuration
```

---

## 💡 Future Enhancements

* Add **Refresh Tokens**
* Use **Role-Based Authorization**
* Switch to **JWT 0.11.5** with `jjwt-api`, `jjwt-impl`, `jjwt-jackson`
* Add **Swagger** for API documentation
* Add **email verification** or **account lockout policy**

---


