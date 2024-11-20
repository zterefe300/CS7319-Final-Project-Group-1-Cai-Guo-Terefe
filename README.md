# CS7319-Final-Project-Group-1-Cai-Guo-Terefe

# Compilation and Implementation platform
Our implementation uses the following.
- Language: Java(23), React, SQL(for the unselected one)
- Framework: SpringBoot(Java Framework), MyBatis(for selected one, db framework)
- Platform: Docker(for creating mysql db), Maven

# How to compile the code(for selected and unselected)
## Prerequisites

Ensure the following tools are installed on your system:

1. **Docker**
2. **Java Development Kit (JDK)** (Java 23)
3. **Node.js** and **npm**

---

## Steps to Run the Application

### 1. Set Up and Run Docker

1. Navigate to the selected/unselected directory where the `docker-compose.yml` file is located.
2. Start the MySQL database by running:
   ```bash
   docker-compose up -d
   ```
3. Verify that the MySQL container is running:
   ```bash
   docker ps
   ```
   Ensure the database containers are up and running under the name selected or unselected depending on which one you are running.

---

### 2. Start the Backend

1. Navigate to the backend module directory using the command below or manually(Inside Selected or UnSelected folder):
   ```bash
   cd backend/src/main/java/com/selected/inventory_dashboard
   ```
    ```bash
   cd backend/src/main/java/com/unselected/inventory_dashboard
   ```
2. Locate the main application file: `InventorySelectedApplication.java` or `InventoryUnSelectedApplication.java`. It is typically found under:
   ```
   src/main/java/com/selected/inventory_dashboard/
   ```
3. Run the backend server using one of the following methods:
    - **Using an IDE (e.g., IntelliJ or Eclipse)**:
        - Open the project in the IDE.
        - Locate the `InventorySelectedApplication.java` or `InventoryUnSelectedApplication.java`.
        - Run it as a Spring Boot application.
    - **Using the Command Line**:
      ```bash
      ./mvnw spring-boot:run
      ```
4. Ensure the backend is running on port **8080** for selected and on port 8081 for unselected. Verify by opening:
   ```
   http://localhost:8080
   ```

---

### 3. Build and Run the Frontend

1. Navigate to the frontend module directory using the command below or manually(Inside the Selected or Unselected folder):
   ```bash
   cd frontend
   ```
2. Install dependencies and build the project:
   ```bash
   npm install
   npm run build
   ```
3. Start the React application:
   ```bash
   npm dev
   ```
   This will typically start the application on port **5173** and open it in your default browser. If not, navigate manually to:
   ```
   http://localhost:5173
   ```

---

### 4. Verify the Application

- Confirm the backend APIs are functional by visiting:
  ```
  http://localhost:8080(selected) 
  ```
  ```
  http://localhost:8081(unselected) 
  ```
- Check the frontend is rendering correctly at:
  ```
  http://localhost:5173
  ```
- Test the application end-to-end to ensure everything works as expected.

---

## Additional Notes

- **Stopping Docker Containers**:
  To stop and remove the Docker containers, use:
  ```bash
  docker-compose down
  ```
- **Frontend Build for Deployment**:
  ```bash
  npm run build
  ```
  The static files will be generated in the `build` directory.

---

You're now ready to use the application!

