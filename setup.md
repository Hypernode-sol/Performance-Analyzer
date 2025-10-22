# Setup Instructions for Performance Optimization System

Follow these steps to set up the Performance Optimization System and ensure it runs seamlessly.

## Prerequisites
- **Java Development Kit (JDK)** 11 or higher
- **Node.js** and **npm** for the frontend dashboard
- A supported **database** (e.g., MySQL or PostgreSQL)
- A **web server** for hosting APIs (e.g., Apache Tomcat, Spring Boot)

---

## Backend Setup

### 1. Clone the Repository
```bash
$ git clone https://github.com/yourusername/performance-optimization.git
$ cd performance-optimization
```

### 2. Configure Properties
Modify `PerformanceConfig.properties` to suit your network's requirements:
```properties
cpuHighThreshold=80.0
cpuLowThreshold=40.0
monitoringInterval=60
optimizationInterval=300
```

### 3. Build the Project
Use Maven or Gradle to build the backend project:
```bash
$ mvn clean install
```

### 4. Run the Backend
Deploy the compiled backend application to your web server or run it locally:
```bash
$ java -jar target/performance-optimization.jar
```

---

## Frontend Setup

### 1. Navigate to the Dashboard Directory
```bash
$ cd frontend/dashboard
```

### 2. Install Dependencies
```bash
$ npm install
```

### 3. Run the Development Server
```bash
$ npm start
```
Access the dashboard at [http://localhost:3000](http://localhost:3000).

### 4. Build for Production
To create an optimized build:
```bash
$ npm run build
```
Serve the built files with any static file server or integrate them into your backend.

---

## Database Setup

### 1. Create the Database
Set up a database for storing performance metrics and logs. For example, with MySQL:
```sql
CREATE DATABASE performance_system;
```

### 2. Apply Migrations
Use your preferred migration tool to initialize the schema:
```bash
$ ./scripts/apply-migrations.sh
```

---

## API Configuration

Ensure the API endpoints in `PerformanceDashboard.js` are aligned with your backend server:
```javascript
const apiEndpoint = "/api/performance";  // Replace with your backend URL
```

---

## Deployment

### Backend
Deploy the backend application to a production server (e.g., AWS, Heroku, or Google Cloud).

### Frontend
Host the built frontend files on a CDN or integrate them into your backend server.

---

## Monitoring and Maintenance
- Regularly monitor logs for errors and performance anomalies.
- Periodically update dependencies and configurations as needed.

For further assistance, refer to the [documentation](docs/README.md) or contact the development team.
