# Vikash Pandey - Java Developer Portfolio

> **A Complete, Production-Grade Full-Stack Portfolio Application built with Java 17, Spring Boot 3.4.x, Spring Data JPA, Spring Security, Thymeleaf, and MySQL 8.**

---

## 📌 Project Overview

This repository contains the complete personal portfolio and inquiry management web application for **Vikash Pandey** (Java Developer | Spring Boot Developer).

The application is built using a clean, layered monolithic architecture without unnecessary microservice overhead. It pairs a premium, dark-themed responsive frontend with a robust, enterprise-grade Spring Boot backend featuring a public REST API for hiring inquiries and a role-based, password-protected administrator management dashboard.

---

## ✨ Features

### 🌟 Public Portfolio
* **Hero Section:** High-impact introduction with headline, supporting summary, animated initials avatar (`VP`), floating tech badges, quick action CTAs, and verified GitHub & LinkedIn profile links.
* **About Me:** Highlights academic background (MCA, 2025), Java training, REST API development, and database engineering.
* **Technical Skills:** Categorized showcase (Programming, Backend Development, Relational Databases, and Developer Tools) with modern badges and icons.
* **Featured Projects Showcase:** Responsive cards with descriptions, tech stacks, key features, and GitHub links:
  1. **CodeSathi AI:** AI-powered Java code explanation and analysis system.
  2. **Weather Application:** Real-time meteorological data consumer with external API integration.
  3. **Employee Management System:** Enterprise CRUD application backed by Spring Data JPA & MySQL.
  4. **Microservices Practice Project:** Distributed services with Eureka service discovery and REST communication.
  5. **JWT Authentication System:** Stateless authentication, token lifecycle, and BCrypt security.
* **Education & Practical Experience:** Chronological timeline covering MCA (2025), Bachelor's Degree (Gujarat University, 2023), Higher Secondary (Gujarat Board, 2020), and Java development internship training.
* **Resume Section:** Dynamic resume viewer and download link. If the PDF file is pending, a helpful notification guides the owner on where to add the file.
* **Interactive Hire Me Form:**
  - Real-time client-side and server-side Jakarta validation.
  - Asynchronous AJAX submission (no full-page reload).
  - Double-submission lock with dynamic button spinner.
  - Safe backend persistence to MySQL.

### 🛡️ Admin Dashboard & Security
* **Authentication & Authorization:** Powered by Spring Security 6, session-based authentication, and BCrypt password encryption.
* **Automatic Admin Bootstrapping:** Safely creates the initial administrator user on first boot using environment variables.
* **Database Metrics & KPI Stats:** Real-time counters for **Total**, **New**, **Reviewed**, **Contacted**, **Shortlisted**, and **Closed** inquiries.
* **Search & Multi-Criteria Filtering:** Filter recruiter inquiries by status, hiring engagement type, date range, or keyword search across name, company, email, and role.
* **Pagination:** Server-side pagination with query state retention.
* **Inquiry Lifecycle Management:** View full recruiter requirements, update pipeline status, or permanently delete inquiries with CSRF protection and confirmation modals.
* **Email Notifications:** Optional notification support using Spring Mail (notifies the portfolio owner and sends confirmation to the recruiter).

---

## 🛠️ Technology Stack

| Layer | Technologies |
|---|---|
| **Backend** | Java 17+, Spring Boot 3.4.2, Spring MVC, Spring Data JPA, Hibernate ORM, Spring Security 6, Jakarta Bean Validation |
| **Frontend** | Thymeleaf 3, HTML5, CSS3 (Custom Design System), JavaScript (ES6+ AJAX), Bootstrap 5.3.3, Bootstrap Icons 1.11.3 |
| **Database** | MySQL 8.x (Production/Default), H2 (In-Memory Development Profile & Automated Tests) |
| **Build & Testing** | Maven 3.9+, JUnit 5, Mockito, Spring Boot Test, Spring Security Test |

---

## 📂 Project Architecture

```
PersonalPort/
├── pom.xml
├── README.md
├── .gitignore
├── src/
│   ├── main/
│   │   ├── java/com/vikash/portfolio/
│   │   │   ├── PortfolioApplication.java
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   │   ├── PortfolioWebController.java
│   │   │   │   └── AdminController.java
│   │   │   ├── restcontroller/
│   │   │   │   └── HiringInquiryRestController.java
│   │   │   ├── dto/
│   │   │   │   ├── ApiResponseDto.java
│   │   │   │   ├── HiringInquiryRequestDto.java
│   │   │   │   ├── HiringInquiryResponseDto.java
│   │   │   │   ├── InquiryStatsDto.java
│   │   │   │   └── StatusUpdateRequestDto.java
│   │   │   ├── entity/
│   │   │   │   ├── AdminUser.java
│   │   │   │   ├── HiringInquiry.java
│   │   │   │   ├── HiringType.java
│   │   │   │   └── InquiryStatus.java
│   │   │   ├── repository/
│   │   │   │   ├── AdminUserRepository.java
│   │   │   │   └── HiringInquiryRepository.java
│   │   │   ├── service/
│   │   │   │   ├── EmailNotificationService.java
│   │   │   │   ├── HiringInquiryService.java
│   │   │   │   └── impl/
│   │   │   │       ├── EmailNotificationServiceImpl.java
│   │   │   │       └── HiringInquiryServiceImpl.java
│   │   │   ├── security/
│   │   │   │   ├── AdminUserInitializer.java
│   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   └── SecurityConfig.java
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       └── ResourceNotFoundException.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-dev.properties
│   │       ├── static/
│   │       │   ├── css/style.css
│   │       │   ├── js/main.js
│   │       │   ├── js/hiring-form.js
│   │       │   └── assets/
│   │       │       └── Vikash-Pandey-Resume.pdf (place PDF here)
│   │       └── templates/
│   │           ├── index.html
│   │           ├── access-denied.html
│   │           ├── error.html
│   │           ├── admin/
│   │           │   ├── login.html
│   │           │   ├── dashboard.html
│   │           │   └── inquiry-details.html
│   │           └── fragments/
│   │               ├── head.html
│   │               ├── navbar.html
│   │               └── footer.html
│   └── test/
│       ├── java/com/vikash/portfolio/
│       │   ├── PortfolioApplicationTests.java
│       │   ├── restcontroller/HiringInquiryRestControllerTest.java
│       │   ├── security/SecurityAccessTest.java
│       │   └── service/HiringInquiryServiceTest.java
│       └── resources/
│           └── application-test.properties
```

---

## ⚙️ Prerequisites & Setup

### 1. Requirements
* **Java Development Kit (JDK):** Version 17 or higher (tested on JDK 17, 21, and 26).
* **Apache Maven:** Version 3.8+ (or use IDE bundled Maven).
* **MySQL Server:** Version 8.0+ (optional if running with `--spring.profiles.active=dev`).

### 2. MySQL Database Setup
Open MySQL Workbench, command line, or phpMyAdmin and create the database:

```sql
CREATE DATABASE IF NOT EXISTS vikash_portfolio_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

---

## 🔐 Environment Variables & Configuration

The application uses safe environment variable fallbacks in `src/main/resources/application.properties`:

| Environment Variable | Description | Default Value |
|---|---|---|
| `PORT` | Web server listening port | `8088` |
| `DB_URL` | MySQL JDBC Connection URL | `jdbc:mysql://localhost:3306/vikash_portfolio_db` |
| `DB_USERNAME` | MySQL database username | `root` |
| `DB_PASSWORD` | MySQL database password | *(empty)* |
| `ADMIN_USERNAME` | Administrator login username | `admin` |
| `ADMIN_PASSWORD` | Administrator initial password | `Admin@Vikash2026!` |
| `ADMIN_EMAIL` | Administrator contact email | `admin@vikashpandey.dev` |
| `ENABLE_EMAIL_NOTIFICATION` | Enable SMTP email delivery | `false` |
| `SMTP_HOST` | SMTP server host | `smtp.gmail.com` |
| `SMTP_PORT` | SMTP server port | `587` |
| `SMTP_USERNAME` | SMTP account email | *(empty)* |
| `SMTP_PASSWORD` | SMTP app password | *(empty)* |
| `PORTFOLIO_OWNER_EMAIL` | Email to receive inquiry notifications | `vikashpandey.dev@gmail.com` |

---

## 🚀 How to Run the Application

### Option A: Quick Run with In-Memory Database (Zero MySQL Setup)
If MySQL is not currently running on your system, you can start the application immediately with the `dev` profile:

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
Or run the packaged JAR:
```powershell
java -jar target/personal-portfolio-1.0.0.jar --spring.profiles.active=dev
```

### Option B: Run with MySQL (Production Mode)
Ensure MySQL is running, then run:

```powershell
# Set your MySQL credentials (if required)
$env:DB_USERNAME="root"
$env:DB_PASSWORD="YourPasswordHere"

# Run Spring Boot
mvn spring-boot:run
```

Once started, open your browser and navigate to:
* **Portfolio Home:** `http://localhost:8088/`
* **Admin Login:** `http://localhost:8088/admin/login`
  - **Username:** `admin`
  - **Password:** `Admin@Vikash2026!`

---

## 💻 Running in IDEs

### In IntelliJ IDEA:
1. Open IntelliJ IDEA -> `File` -> `Open` -> Select the `PersonalPort` folder.
2. Allow Maven to import dependencies automatically.
3. Locate `src/main/java/com/vikash/portfolio/PortfolioApplication.java`.
4. Right-click and choose **Run 'PortfolioApplication'**.
5. *(Optional)* In Run Configurations, add VM options or Program Arguments: `--spring.profiles.active=dev` if running without a local MySQL server.

### In Eclipse / Spring Tool Suite (STS):
1. `File` -> `Import` -> `Existing Maven Projects`.
2. Browse to the `PersonalPort` directory and click `Finish`.
3. Right-click the project -> `Run As` -> `Spring Boot App`.

---

## 🧪 Running Automated Tests

Run the full JUnit 5 and Spring Boot test suite:

```powershell
mvn clean test
```

All 17 automated tests run against an isolated in-memory test database and verify:
- Complete Spring context bootstrapping.
- Inquiry submission with valid data.
- Validation constraint enforcement (blank names, invalid emails, missing consent).
- Database persistence and transaction rollback.
- Role-based Spring Security authorization and access control.
- Inquiry status transitions and metric computations.

---

## 📄 Adding Your Resume PDF

1. Prepare your resume in PDF format.
2. Rename the file to:
   ```
   Vikash-Pandey-Resume.pdf
   ```
3. Place it inside:
   ```
   src/main/resources/static/assets/Vikash-Pandey-Resume.pdf
   ```
4. Rebuild the application (`mvn compile` or restart your IDE). The Resume section will automatically activate the **"View Resume"** and **"Download Resume"** buttons.

---

## 📬 REST API Documentation

### Public Endpoint: Submit Hiring Inquiry
* **URL:** `/api/hiring-inquiries`
* **Method:** `POST`
* **Content-Type:** `application/json`

#### Sample Request Body:
```json
{
  "fullName": "Sarah Connor",
  "companyName": "Cyberdyne Systems",
  "workEmail": "s.connor@cyberdyne.io",
  "contactNumber": "+1 555-0199",
  "hiringType": "FULL_TIME",
  "jobRole": "Senior Java Backend Engineer",
  "salaryBudget": "$90,000 - $110,000",
  "jobDescription": "We are looking for a skilled Java & Spring Boot engineer to design robust REST microservices and lead database optimizations.",
  "additionalMessage": "Flexible start date and remote work supported.",
  "consent": true
}
```

#### Success Response (`201 Created`):
```json
{
  "success": true,
  "message": "Thank you for reaching out! Your hiring inquiry has been submitted successfully. I will review the details and get back to you.",
  "data": {
    "id": 1,
    "fullName": "Sarah Connor",
    "companyName": "Cyberdyne Systems",
    "workEmail": "s.connor@cyberdyne.io",
    "contactNumber": "+1 555-0199",
    "hiringType": "FULL_TIME",
    "hiringTypeDisplayName": "Full-Time Job",
    "jobRole": "Senior Java Backend Engineer",
    "salaryBudget": "$90,000 - $110,000",
    "status": "NEW",
    "statusDisplayName": "New",
    "createdAt": "2026-10-27T12:41:55"
  }
}
```

---

## 📤 Uploading to GitHub

To publish this project to your GitHub account:

```powershell
cd C:\Users\vikash\.gemini\antigravity\scratch\PersonalPort

# 1. Initialize Git repository
git init

# 2. Add all files
git add .

# 3. Create initial commit
git commit -m "feat: complete full-stack Java developer portfolio for Vikash Pandey"

# 4. Set main branch
git branch -M main

# 5. Link to your GitHub repository
git remote add origin https://github.com/vikashpandey770/PersonalPort.git

# 6. Push code to GitHub
git push -u origin main
```

---

## ❓ Troubleshooting Common Issues

* **Port 8088 / 8080 already in use:**
  Change the port at runtime by specifying:
  ```powershell
  mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
  ```
* **MySQL Connection Refused:**
  Ensure MySQL service is started, or start with `--spring.profiles.active=dev` for an immediate zero-setup development environment.
* **CSRF Error during Admin Form Submission:**
  Thymeleaf automatically embeds `_csrf` tokens in all HTML forms. Ensure you submit requests using the web interface or include the `X-CSRF-TOKEN` header when testing via REST clients.

---

## 👨‍💻 Author

**Vikash Pandey**
* **Role:** Java Developer | Spring Boot Developer
* **GitHub:** [vikashpandey770](https://github.com/vikashpandey770)
* **LinkedIn:** [vikash-p-2532aa258](https://www.linkedin.com/in/vikash-p-2532aa258/)
