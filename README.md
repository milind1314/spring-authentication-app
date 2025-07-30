
# 🔐 Full-Stack Authentication System

A secure and responsive full-stack authentication app built using **React.js** for the frontend and **Spring Boot** for the backend. Features include email-based OTP verification, password reset, JWT-based login, and protected routes.

---

## 🚀 Features

- ✅ User Login with JWT Authentication
- 📩 Email-based OTP verification
- 🔒 Password Reset functionality
- 🔐 Secure cookies with `withCredentials`
- 📦 React Context API for global state management
- 💅 Bootstrap-styled responsive UI
- 🌐 Axios for API calls with proper error handling
- 🛡️ Backend built with Spring Boot and MongoDB

---

## 🛠️ Tech Stack

### Frontend
- React.js
- React Router DOM
- Axios
- React Toastify
- Bootstrap 5
- Context API

### Backend
- Spring Boot
- Spring Security
- MongoDB
- Java Mail Sender (for OTP)
- JWT (JSON Web Tokens)

---

## 📂 Project Structure

```

frontend/
├── public/
├── src/
│   ├── assets/
│   ├── context/
│   ├── pages/
│   ├── App.jsx
│   ├── App.css
│   ├── index.jsx
│   └── util/constants.js
backend/
├── src/main/java/
│   └── in/milind/authenticationapp/
│       ├── controller/
│       ├── config/
│       ├── filter/
│       ├── model/
│       ├── repo/
│       ├── service/
│       └── util/
└── application.properties

````

---


## 🔧 Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/fullstack-auth-app.git
cd fullstack-auth-app
````

### 2. Setup Backend

```bash
cd backend
# Configure your MongoDB and mail credentials in application.properties
mvn spring-boot:run
```

### 3. Setup Frontend

```bash
cd frontend
npm install
npm run dev
```

---

## ✅ Environment Variables

Update `frontend/src/util/constants.js`:

```js
export const AppConstants = {
  BACKEND_URL: 'http://localhost:8080/auth-api',
}
```

Update backend `application.properties`:

```properties
spring.data.mongodb.uri=your_mongo_uri
spring.mail.username=your_email
spring.mail.password=your_password
jwt.secret=your_jwt_secret
```

---

## 🙌 Acknowledgements

* React Team
* Spring Boot & Spring Security Docs
* Bootstrap 5
* Toastify

```

---

Would you like me to generate the `LICENSE` file too or help with deployment steps (e.g., on Vercel and Render)?
```
