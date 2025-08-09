# RESTful Accommodation Management System

A Java-based RESTful web service acting as an orchestrator for accommodation room management.  
It integrates external APIs (Weather and Distance services) to enrich room data and provides HTTPS endpoints for room search, booking, cancellation and application history.  

A separate lightweight web client built with HTML, CSS, and JavaScript asynchronously communicates with this service using JSON over HTTPS.

---

## ✨ Project Description

This system allows users to:  
- View available rooms and their details stored in an SQLite database.  
- Apply for rooms, cancel bookings and check application history by user ID.
- Book rooms by submitting user and room ID.
- Cancel bookings via application ID.
- View application status (applied/cancelled) by user ID. 
- Fetch real-time weather information for room locations using the 7 Timer API.  
- Calculate distances and locations between rooms and destinations using the Google Distance API.

The backend and frontend are loosely coupled — the RESTful API endpoints can be independently accessed and tested using tools like Postman, while the client uses Fetch API for asynchronous calls.

---

## 📌 Features

### Room Management
- Store and manage room details, including availability and pricing, in SQLite.

### External API Integration
- Dynamic weather data fetching via **7 Timer API**.
- Real-time distance and location calculation using **Google Distance API**.

### RESTful API
- Secure HTTPS endpoints supporting:
  - Room listing (`GET /rooms`)
  - Room booking (`POST /apply`)
  - Booking cancellation (`POST /cancel`)
  - Booking application history (`GET /history`)

### JSON Communication
- All client-server communication uses JSON for simplicity and efficiency.

### Client
- Lightweight, decoupled frontend built with HTML, CSS, and JavaScript Fetch API.
- Frontend and backend operate independently.

---

## 🛠️ Technologies Used

- **Backend:** Java (JAX-RS for REST, Gson for JSON parsing)  
- **Database:** SQLite  
- **Web Server:** Apache Tomcat (hosting the orchestrator)  
- **Frontend:** HTML, CSS, JavaScript (Fetch API)  
- **External APIs:**  
  - Weather: 7 Timer API  
  - Distance: Google Distance API  
---
### 🗄️ Configure the Database
- Create and configure SQLite database with tables: `Room`, `User`, `Application`.

### 💻 Run the Server
- Deploy backend on Apache Tomcat or your preferred Java EE server.
- Configure API keys for Google Distance API and 7 Timer API as required.

### 🌐 Access the Client
- Open client HTML pages directly in a browser.
- Client uses Fetch API to send requests and receive JSON responses from backend.



