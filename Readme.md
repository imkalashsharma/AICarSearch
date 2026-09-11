# AI Car Search

A natural-language used-car search API built as part of the CARS24 Backend Engineering Assessment for the Backend Engineer position.

The application allows users to search for used cars using natural language, for example:

"Automatic SUV under 10 lakh"

Google Gemini converts the query into structured search filters, and the Spring Boot application performs the actual search against PostgreSQL.

## Features

- Natural-language car search using Gemini

- Filters for model, body type, fuel type, transmission, price, kilometres, year and seats

- PostgreSQL database with JPA/Hibernate

- Flyway database migrations

- Bulk CSV vehicle import

- Global exception handling

- Docker support

- Production deployment using Render

## Architecture

User Query
↓
Vehicle Controller
↓
Search Service
↓
Gemini → SearchFilter
↓
Vehicle Repository
↓
PostgreSQL
↓
Vehicle Results

Gemini is used only to understand the user's intent and create structured filters. It does not generate SQL or decide which vehicles to return.

AI handles ambiguity. Java handles business logic. PostgreSQL handles data.


## Dataset

The project uses the vehicle dataset taken from Kaggle.

The original CSV did not contain every field required by the application's database schema. I used AI assistance during the data-preparation stage to clean and transform the dataset into the application's schema, normalize values, add the required body_type classification from model names, and prepare representative values for missing fields required by the demo.

The refined dataset was then imported into PostgreSQL through the application's CSV import endpoint.

The enriched fields should be treated as demo/assessment data rather than production-verified vehicle specifications.

## Getting Started

1. Clone the repository

```
git clone https://github.com/imkalashsharma/AICarSearch.git
cd AICarSearch
```

2. Configure environment variables


```
DB_URL=jdbc:postgresql://<host>:5432/<database>
DB_USER=<username>
DB_PASSWORD=<password>
GEMINI_API_KEY=<your-gemini-api-key>
```

For production, these values should be configured through the hosting platform rather than committed to Git.

3. Run with Maven

```
./mvnw clean package -DskipTests
java -jar target/*.jar
```

Or:

```
./mvnw spring-boot:run
```

4. Run with Docker

```
docker compose up --build
```
The application starts on:

```
http://localhost:8080
```

PostgreSQL is configured as an external database, so Docker Compose runs the application container and connects to the configured PostgreSQL instance.

## Spring Profiles

Production uses the prod profile:

SPRING_PROFILES_ACTIVE=prod

Production database and Gemini credentials are supplied through environment variables.

## API

### Search vehicles

```
POST /v1/search
```

Example:

{
"query": "automatic SUV under 10 lakh"
}

### Import vehicles

```
POST /v1/vehicles/import
```

Upload the vehicle CSV as a multipart file.

## Database

The application uses PostgreSQL with Flyway migrations.

The main table is:

```
vehicles
```
with fields for vehicle name, location, body type, year, kilometres driven, fuel type, transmission, ownership, mileage, engine, power, seats, new price and price.

## Deployment

The application is containerized using Docker and deployed on Render.

```
GitHub
↓
Render
↓
Docker
↓
Spring Boot
↓
Render PostgreSQL
```
