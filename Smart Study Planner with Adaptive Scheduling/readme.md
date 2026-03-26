# 🚀 Smart Study Planner: Adaptive Scheduling Engine

A high-performance, **Rule-Based Adaptive Scheduling System** designed to optimize student productivity through intelligent time-drift learning and dynamic priority recalculation. Built with a modern Java/Spring Boot backend and a premium, responsive Vanilla JS frontend.

---

## 🌟 Core Disruptive Features

### 🧠 1. Adaptive Rescheduling Engine
The backend doesn't just store tasks; it manages your life.
*   **48-Hour Missed Task Scanning**: Automatically identifies the most optimal free gap within the next 48 hours for any missed session.
*   **Overload Protection**: If no gaps exist, the engine identifies the lowest-priority task in your schedule and replaces it with the high-priority missed task.
*   **Explainable Decision Logs**: Every schedule movement is logged in the `RescheduleLogs` table with human-readable reasons for full transparency.

### 📉 2. Performance & Drift Learning
The system grows as you grow.
*   **Cumulative Drift Tracking**: Calculates the deviation between *Planned* and *Actual* study time.
*   **Auto-Correcting Durations**: If you consistently take longer on a subject (e.g., Physics), the engine automatically increases the target duration for future Physics sessions based on a 7-day rolling average.

### ⚡ 3. 5-Factor Priority Matrix
Tasks are ranked using a sophisticated adaptive formula:
`Priority = (Deadline Weight) + (Missed Count * 10) + ((5 - Confidence) * 8) + Time Decay + Drift Factor`
*   **Time Decay**: Boosting priority for subjects you haven't studied recently to prevent knowledge starvation.

### 🎨 4. Premium Deep Work Workspace (Focus Mode)
A distraction-free, 3-column interactive environment:
*   **Active Drafting Board**: Full-screen HTML5 Canvas with technical grid backgrounds for sketching diagrams or solving equations.
*   **Resources & Points**: Capture key breakthroughs and attach reference files (PDF/Images) mid-session.
*   **Intelligent Sync**: Post-session finalization captures Focus Scores and Confidence Levels to feed the adaptive engine.
*   **Fully Responsive**: Specialized layouts for Desktop (`1fr:2fr:1fr`), Tablet (`2-column`), and Mobile (`Vertical Stack`).

---

## 🛠️ Tech Stack

*   **Backend**: Java 17, Spring Boot 3.x (Antigravity routing patterns)
*   **Database**: MySQL 8.0 (Auto-schema initialization via `schema.sql`)
*   **Frontend**: HTML5, CSS3 (Modern Grid/Flexbox), Vanilla JavaScript
*   **Design**: Cyberpunk Dark Theme, Glassmorphism, Responsive UI/UX

---

## 🏗️ Architecture & Schema
The project maintains a strict separation of concerns:
*   **`PriorityEngine.java`**: Core mathematical models for task weighting.
*   **`RescheduleService.java`**: The logic behind the 48h scanning and task replacement.
*   **`FocusSessionService.java`**: Orchestrates the sync between manual focus inputs and the database.

---

## 🚥 Quick Start (Windows)

1.  **MySQL Setup**: Ensure MySQL is running on port `3306`.
2.  **Configuration**: Update `src/main/resources/application.yml` with your local MySQL password.
3.  **One-Click Start**: Run `run.bat` in the project root.
    *   *Note: This script automatically handles Maven installation and database schema creation.*
4.  **Access**: Open **[http://localhost:8080](http://localhost:8080)**.

---

## 🔒 Session Integrity
Focus Mode includes a built-in safety lock. Closing a session before the timer ends or without finalizing will trigger an integrity warning and automatically record the progress as **PARTIAL**, ensuring your adaptive metrics remain accurate.

---
**Developed with ❤️ by Antigravity**