# Smart Study Planner with Adaptive Scheduling

A full-stack web application designed to help students manage their time intelligently. The Smart Study Planner features an adaptive scheduling engine that dynamically adjusts study plans based on task deadlines, confidence levels, and productivity patterns.

## Features

- **Interactive Weekly Grid & Timetable**: Add, view, and manage your subjects and study sessions in real-time.
- **Rule-Based Adaptive Scheduling**: The intelligent engine optimizes your study plan based on deadlines and your individual capacity/confidence levels.
- **Pomodoro Timer**: Integrated productivity timer with browser notifications to help maintain focus.
- **Dynamic Task Adjustments**: Automatically recalculates and adjusts schedules when new tasks are added or completed.

## Tech Stack

- **Backend**: Java, Spring Boot 3.x
- **Database**: MySQL
- **Frontend**: HTML5, Vanilla CSS, Vanilla JavaScript

## Architecture

The application is built with a clear separation of concerns:
- **Presentation Layer**: A responsive modern UI built with purely Vanilla JS/HTML/CSS.
- **Routing & Controllers**: Spring Boot REST APIs handling client requests.
- **Business Logic Layer**: Core algorithms for adaptive scheduling and Pomodoro session tracking.
- **Data Access Layer**: JPA/Hibernate for seamless MySQL database transaction management.

## Prerequisites

- **Java 17+**
- **Maven**
- **MySQL Server** (Running on your local machine or accessible remotely)

## Setup Instructions

1. **Clone the repository**:
   \`\`\`bash
   git clone <repository-url>
   \`\`\`

2. **Database Setup**:
   - Create a new MySQL database named `study_planner` (or configure a custom name in the `application.properties`).
   - Update the database credentials in `src/main/resources/application.properties` to match your local MySQL configuration:
     \`\`\`properties
     spring.datasource.url=jdbc:mysql://localhost:3306/study_planner
     spring.datasource.username=your_mysql_username
     spring.datasource.password=your_mysql_password
     \`\`\`

3. **Build and Run**:
   - For Windows users, you can use the provided batch script:
     \`\`\`cmd
     run.bat
     \`\`\`
   - Alternatively, use Maven to build and launch the server:
     \`\`\`bash
     mvn clean install
     mvn spring-boot:run
     \`\`\`

4. **Access the Application**:
   Open your browser and navigate to `http://localhost:8080`.

## License
MIT License