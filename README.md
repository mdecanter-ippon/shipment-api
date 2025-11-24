# API REST with Spring Boot Training

Welcome to this training repository. The goal is to build, test, validate, and secure a complete REST API using Spring Boot, Java 21, and Docker.

This project (`shipment-api-service`) serves as the starting skeleton.

## 1. 🛠️ Software Prerequisites

Before starting the first Lab, please ensure the following tools are installed and functional on your machine.

### 1.1. Development Tools

- **Git:** For version control.
- **Java 21 (JDK):** Required to run the application locally in your IDE.
- **IntelliJ IDEA:** (Recommended) Community or Ultimate Edition.

### 1.2. Docker Environment

The simplest way to get a consistent environment across macOS, Windows, and Linux is to use Docker Desktop.

1.  **Install Docker Desktop:**

    - Download and install **Docker Desktop** for your operating system (macOS, Windows, or Linux) from the official Docker website.
    - This single installation provides the Docker Engine, the `docker` CLI, and the `docker compose` (V2) plugin.

2.  **Verify Installation:**
    - After installation, run `docker --version` and `docker compose version` (with a space) in your terminal to ensure they are working.

## 2. ⚙️ Initial Configuration (One-Time Setup)

### 2.1. Configure Git

Ensure Git is configured with your name and email. (Required so your commits have the correct author).

```bash
git config --global user.name "Your Name"
git config --global user.email "your.email@example.com"
```

### 2.2. Start the Docker Engine

Launch the **Docker Desktop** application. You must see its icon (the whale) in your taskbar, indicating it is running.

## 3. 🚀 Project Startup (For Each Lab)

Follow these steps to launch the environment for LAB 1.

1.  **Clone the Repository:**

    ```
    git clone [YOUR_GIT_REPO_URL]
    cd shipment-api-service
    ```

2.  **Check out the Lab Branch:**
    _The `lab1-start` branch contains the project skeleton, ready to go._

    ```
    git checkout lab1-start
    ```

3.  **Launch External Services (Database):**
    _We are launching the database (`training-database`) and the mock API (`external-legacy-api`) in the background._
    _Note: We use the modern V2 command (with a space)._

    ```
    docker compose up -d training-database external-legacy-api
    ```

    _Wait a few seconds for the database to initialize._

4.  **Open the Project in IntelliJ:**
    Open the `shipment-api-service` folder in IntelliJ IDEA.

5.  **Run the Application (Locally):**
    - Find the `ShipmentApiServiceApplication.java` file.
    - Click the "Run" (play) icon next to the class.
    - The `application.properties` is already configured to connect to the Docker database on `localhost:5432`.

## 4. 🚑 Troubleshooting

**Q: My app fails with `Connection to localhost:5432 refused` when I run it.**  
**A:** The database container isn't running. You missed step 3. Run `docker-compose up -d training-database` _before_ running the application from IntelliJ.
