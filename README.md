# Authentication App (Spring Boot + JWT)

This is a simple Spring Boot application that implements JWT-based authentication and authorization. It uses MySQL as the database and includes functionality for registration, login, and protected routes.

---

## 🔧 Tech Stack

- **Java 17+**
- **Spring Boot**
- **Spring Security**
- **JWT (io.jsonwebtoken)**
- **MySQL**
- **Maven**
- **Lombok**

---

## 🚀 Features

- ✅ User registration
- ✅ User login with JWT generation
- ✅ JWT validation via filters
- ✅ Protected API endpoints
- ✅ Stateless authentication
- ✅ Token in `Authorization` header or Cookie support
- ✅ Email extraction from JWT
- ✅ Context path configurable (`/auth-api`)

---

## 📂 Project Structure

src/main/java/in/milind/authenticationapp/
│
├── controller/ # REST Controllers
│ └── ProfileController.java
│
├── service/ # Business logic
│ └── AuthenticationServiceImpl.java
│
├── filter/ # JWT Filter
│ └── JwtRequestFilter.java
│
├── util/ # JWT Utility
│ └── JwtUtil.java
│
├── io/ # DTOs (Request/Response)
│ ├── ProfileRequest.java
│ └── ProfileResponse.java
│
└── config/ # Spring Security Configuration
└── SecurityConfig.java


---

## ⚙️ Configuration

Update the following in `application.properties`:

```properties
spring.application.name=authenticationapp
spring.datasource.url=jdbc:mysql:///authentication_app
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update

jwt.secret.key=your-secret-key

server.servlet.context-path=/auth-api

| Method | Endpoint                   | Description           | Auth Required |
| ------ | -------------------------- | --------------------- | ------------- |
| POST   | `/auth-api/register`       | Register a new user   | ❌             |
| POST   | `/auth-api/login`          | Login & get JWT       | ❌             |
| GET    | `/auth-api/test`           | Test secured endpoint | ✅             |
| POST   | `/auth-api/reset-password` | Reset password (WIP)  | ❌             |


---

## 🧰 How to Run
!. Clone the repository

git clone https://github.com/your-username/authenticationapp.git
cd authenticationapp

2. Start MySQL and create a database named:
CREATE DATABASE authentication_app;

3. Build & Run
mvn spring-boot:run


---

## 🔐 Authorization
Pass JWT in the Authorization header:
Authorization: Bearer <token>

Or as a cookie named jwt



---

## 📌 Notes
JWT token is valid for 10 hours.

Spring Security is configured to allow public access to /login, /register, /reset-password, and /logout.

---

## 📄 License
This project is for learning purposes. You are free to modify and use it in your own projects.


---

Let me know if you'd like:
- A **Postman collection** added
- **Token refresh** functionality
- **Email support** integrated for welcome/reset emails
