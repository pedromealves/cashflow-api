# Cashflow API

A RESTful API for personal finance management built as an exercise for Java and Spring Boot backend development skills.

The goal of this project was to implement a backend with layered architecture, business rule validation, exception handling, DTO pattern, and database queries.

**Live demo:** https://pedromealves.github.io/cashflow-front/index.html

**Frontend:** https://github.com/pedromealves/cashflow-front

> The API runs on Render's free tier. The first request after a period of inactivity may take ~3 minutes due to cold start. Demo data resets on each service restart.

---

## Tech Stack

- Java 21
- Spring Boot 4
- Spring Data JPA + Hibernate 7
- PostgreSQL 18 (local) / H2 in memory (live demo)
- Maven

---

## Architecture 

Layered REST architecture.

- **Controller** - Handless HTTP requests, converts DTOs, and returns correct status codes
- **Service** - Validates business rules before delegating to the repository
- **Repository** - Database communication via Spring Data JPA
- **DTOs** - `TransactionRequestDTO` (input) and `TransactionResponseDTO` (output) keep the entity separated from the HTTP layer.

---

| Method   | Endpoint                | Description                  |
|----------|-------------------------|------------------------------|
| `GET`    | `/transactions`         | List all transactions        |
| `POST`   | `/transactions`         | Create a transaction         |
| `PUT`    | `/transactions/{id}`    | Update a transaction         |
| `DELETE` | `/transactions/{id}`    | Delete a transaction         |
| `GET`    | `/transactions/search`  | Search with optional filters |
| `GET`    | `/transactions/summary` | Financial summary            |

### Search filters (all optional, combinable)

| Parameter  | Type   | Values                              |
|------------|--------|-------------------------------------|
| `category` | Enum   | `FOOD`, `LEISURE`, `HEALTH`, `SALARY` |
| `type`     | Enum   | `INCOME`, `EXPENSE`                 |
| `keyword`  | String | Searches within description (case-insensitive) |

### Financial summary response

```json
{
  "income": 6000.00,
  "expense": 1000.00,
  "balance": 5000.00
}
```

## Business Rules
 
- amount must be a positive value - returns `400 Bad Request` if violated
- `description` cannot be blank - returns 400 Bad Request` if violated
- `date` is optional on creation - defaults to today if not provided
- Update and delete on a non existent ID - returns `404 Not Found`

## Running Locally

**Requirements:** Java 21, Maven, PostgreSQL

```bash
# 1. Clone the repository

git clone https://github.com/pedromealves/cashflow-api

# 2. Create the database

You can use the pgAdmin PostgreSQL Admin Tool or the PostgreSQL interactive terminal to create the database

# 3. Configure credentials

Make sure the application.properties data match your PostgreSQL credentials. You can use application.properties.example as a starting point.

# 4. Run

mvnw spring-boot:run

```

## Demo Video

https://github.com/user-attachments/assets/2f501f9b-3734-475b-a18d-3a5cee14fc06