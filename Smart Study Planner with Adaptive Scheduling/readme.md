# Smart Student Planner

A full-stack web application designed to help students manage their time intelligently. The Smart Student Planner features a weekly study grid and a focus mode to optimize productivity patterns.

## Features

- **Interactive Weekly Grid & Timetable (`index.html`)**: Add, view, and manage your subjects and study sessions in real-time.
- **Dedicated Focus Mode (`focus.html`)**: Features an integrated Pomodoro-style productivity timer to help you maintain deep focus without browser distractions.
- **Rule-Based Adaptive Scheduling**: The intelligent backend automatically receives your topics, notes, deadlines, and confidence levels, persisting them seamlessly.
- **Zero-Config Database Initialization**: The API is configured to automatically create the root database schema and its internal tables (`schema.sql`) when the server spins up for the first time.
- **Embedded Automated Startup**: No need to install Maven manually on Windows! Simply run `run.bat` to automatically download a portable Maven environment and spin up the server with a single click.

## Tech Stack

- **Backend**: Java 17, Spring Boot 3.x
- **Database**: MySQL Server
- **Frontend**: Pure HTML5, Vanilla CSS, Vanilla JavaScript (No React/Angular dependency)

## Architecture

The application is built with a clear separation of concerns to ensure simple debugging and extending capabilities:
- **Presentation Layer**: A responsive modern UI built with purely Vanilla JS/HTML/CSS located in `src/main/resources/static`.
- **Routing & Controllers**: Spring Boot REST APIs handling client GET, POST, PUT, and DELETE requests.
- **Data Access Layer**: JPA/Hibernate for seamless MySQL database transaction management on top of the auto-initialized SQL setup.

## Prerequisites

- **Java 17+** (Ensure `java -version` returns 17 or above).
- **MySQL Server** running dynamically on `localhost:3306`.

## Setup Instructions

### 1. Database Configuration
The application handles scaffolding automatically, but you must make sure it has the proper master credentials to do so:
- Open `smart study planner/smart-student-planner/src/main/resources/application.yml`
- Locate the `datasource` block:
  ```yaml
  datasource:
    url: jdbc:mysql://localhost:3306/smart_student_planner?useSSL=false&serverTimezone=UTC&createDatabaseIfNotExist=true
    username: root
    password: YOUR_MYSQL_PASSWORD  # Change this to match your local MySQL root password!
  ```
*Note: Because `createDatabaseIfNotExist=true` is present, you do not need to manually create the `smart_student_planner` database. Simply update the password inline!*

### 2. Running The Sub-Project
Navigate into the backend server directory:
```bash
cd "smart study planner/smart-student-planner"
```

#### For Windows Users (Easiest Method):
Double-click or execute the provided batch script:
```cmd
run.bat
```
*(This script will download a lightweight portable version of Maven into `.maven/` automatically in the background and launch the full project).*

#### For Mac / Linux / Experienced Users:
If you already have Maven configured globally on your machine, simply run:
```bash
mvn clean install
mvn spring-boot:run
```

### 3. Accessing The Real-Time Client
Once you see `Started SmartStudentPlannerApplication in X seconds` printed in the console:
- Open your browser.
- Navigate to **[http://localhost:8080](http://localhost:8080)** to view the dashboard!

## License
MIT License