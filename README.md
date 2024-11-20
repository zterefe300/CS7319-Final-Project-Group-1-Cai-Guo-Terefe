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
   ```
3. Start the React application:
   ```bash
   npm run dev
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

## The Differences Between Selected, and Unselected folder

The Selected folder includes our Layered Architecture, whereas the Unselected incudes Client Server Architecture

## Layered Architecture

**Pros**:

- Provides better isolation by separating the application into presentation, business logic and data access
- Better system for client that would require a much larger inventory management system that would be handling multiple requests and data transactions
- By design, it is very modular allowing for easy swap out and updates of layers without affecting other layers. For example, if a client prefers a certain database, we can easily update the database layer and not touch the presentation or business logic layer

**Cons**:

- Each layer needs to be carefully planned, as they depend on one another. As a result, the complexity and time required for development time increase significantly
- Due to its design, layered architecture processes requests through each individual layer, which can introduce latency, especially if the services need to pass through multiple layers.

## Client Server Architecture

**Pros**:

- Capable of handling async tasks through API requests.
- It provides separation between the frontend and backend but lacks separation between business logic and data handling
- Has a better handle on security as it is all done centrally on the server itself, and as such, we only need to authenticate when communicating with the server

**Cons**:

- Hard to maintain since the business logic and data access become more intertwined.
- Changing the backend logic or database system will require significant effort.
- The coupling of the business logic with the data access make it less modular.

## A more in-depth explanation of why we went with Layered Architecture.

| Metrics                                    | Layered Architecture Style                                                                                                                                                                                                                                                                       | Client-Server Architecture Style                                                                                                                                       | Winner        |
| ------------------------------------------ | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------- |
| **Separation of Concerns**                 | - Provides better isolation by separating the application into presentation, business logic, and data access.                                                                                                                                                                                    | - It provides separation between the frontend and backend but lacks separation between business logic and data handling.                                               | Layered style |
| **Scalability**                            | - Allows for vertical scaling. <br> - Better system for clients that would require a much larger inventory management system, handling multiple requests and data transactions. <br> - Backend scaling could be challenging due to tight coupling between business logic and data access layers. | - Backend scaling could be challenging since there is tight coupling between the business logic and data access layers                                                 | Layered style |
| **Maintainability**                        | - Since each layer has a well-defined responsibility, maintaining it is easier. Updates to one layer usually don’t affect other layers.                                                                                                                                                          | - Hard to maintain since the business logic and data access become more intertwined.                                                                                   | Layered style |
| **Flexibility and Modularity**             | - By design, it is very modular, allowing for easy swap out and updates of layers without affecting other layers. <br> - For example, if a client prefers a certain database, we can easily update the database layer and not touch the presentation or business logic layer.                    | - The coupling of the business logic with the data access makes it less modular. <br> - Changing the backend logic or database system will require significant effort. | Layered style |
| **Real-Time Updates and Async Processing** | - Easier to handle async tasks independently between layers. <br> - Gives each layer control over when and how async tasks are handled. <br> - Capable of handling async tasks through API requests.                                                                                             | - For clients with frequent and complex inventory updates, it might not be suitable.                                                                                   | Layered style |
| **Long-Term Growth**                       | - Has huge potential for growth. <br> - Independent layer growth can be supported depending on client needs.                                                                                                                                                                                     | - It will be a challenge to scale the application for clients with complex data access and feature requirements.                                                       | Layered style |
| **Development Speed and Simplicity**       | - Developing a highly modular application could be complex and take time.                                                                                                                                                                                                                        | - Suitable for a team of our size and easy to implement in a short time.                                                                                               | Client-Server |

As we can see from the table above it is clear that the Layered Style is a suitable architecture style for the Inventory Management System project. The Layered style outperforms the client-server style in six metrics out of the seven metrics. Even though the client-server style could be easier to implement, its lack of modularity and scalability makes it less suitable for a project that might require growth and complex real-time functionality. For long-term growth when needed and better maintainability, the Layered Architecture is the better choice.
