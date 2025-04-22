# 🌐 SWIFT Codes API

A RESTful API for managing and retrieving SWIFT (Bank Identifier) codes data from a structured database. This application parses SWIFT data from a file, stores it efficiently, and exposes endpoints to retrieve or manipulate the data based on individual codes or country information.

---

## 📌 Project Overview

SWIFT codes are unique identifiers for banks and their branches, used for international wire transfers. This application transforms offline spreadsheet data into a fast, queryable system accessible via REST API.

---

## 🚀 Features

- ✅ Parse and ingest SWIFT code data from a file  
- ✅ Identify headquarter vs. branch codes  
- ✅ Store in a performant PostgreSQL database  
- ✅ Provide country and code-specific queries  
- ✅ Add and delete SWIFT entries via API  

---

## 🛠️ Setup Instructions

### 1. **Clone the repository**

```bash
git clone https://github.com/Kacper1130/SWIFT-application.git
cd SWIFT-application
```

### 2. **Prepare Environment Variables**
Edit config.env in the root directory and add the following:
```env
POSTGRES_USERNAME=your_username
POSTGRES_PASSWORD=your_password
```

### 3. **Run the Application with Docker**
```bash
docker compose --env-file config.env up --build
```

### 4. **Access the Application**
Once the Docker containers are running, the API will be available at: `http://localhost:8080`

## API Endpoints

### Retrieve SWIFT Code Details

**GET** `/v1/swift-codes/{swift-code}`

- Returns:
  - Branch info (if branch)
  - HQ info + list of branches (if HQ)

---

### Get SWIFT Codes by Country

**GET** `/v1/swift-codes/country/{countryISO2code}`

- Returns all codes (HQ + branches) for the specified country

---

### Add a New SWIFT Code

**POST** `/v1/swift-codes`

**Request Example:**

```json
{
  "address": "123 Main St",
  "bankName": "Bank of Example",
  "countryISO2": "US",
  "countryName": "UNITED STATES",
  "isHeadquarter": true,
  "swiftCode": "EXAMPL12XXX"
}
```

**Response:**
```json
{
  "message": "SWIFT code added successfully."
}
```

### Delete a SWIFT Code

**DELETE** `/v1/swift-codes/{swift-code}`

**Response:**
```json
{
  "message": "SWIFT code deleted successfully."
}
```

### Load data
- Parses SWIFT data from the data.csv file and stores it in the database.
**POST** `/v1/swift-codes/load-data`

## Testing

You can test endpoints using:
- Postman
- curl

Before testing, ensure that the app has successfully parsed and stored some initial SWIFT data by using the Load Data endpoint.
