# DailySpane Jakarta API

Backend for a job marketplace, built on Jakarta EE and Open Liberty. Work in progress.

## Stack

- Java 21
- Jakarta EE 11 API (JAX-RS 3.1, CDI 4.0, JSON-B 3.0)
- Open Liberty, via `liberty-maven-plugin`
- Maven, packaged as a WAR

## Getting started

Prerequisites: JDK 21 and Maven 3.9+.

```bash
git clone https://github.com/muvhulawamuthula/dailyspane-jakarta-api.git
cd dailyspane-jakarta-api
mvn liberty:dev
```

`liberty:dev` downloads Open Liberty on the first run, starts the server on port **9080** and redeploys on every save.

To build the WAR without starting a server:

```bash
mvn package
```

## API

All endpoints are under `/api`.

| Method | Path          | Description                    |
|--------|---------------|--------------------------------|
| GET    | `/api/health` | Liveness check                 |
| GET    | `/api/jobs`   | List job posts                 |
| POST   | `/api/jobs`   | Create a job post (returns 201) |

```bash
curl http://localhost:9080/api/health

curl -X POST http://localhost:9080/api/jobs \
  -H 'Content-Type: application/json' \
  -d '{"title":"Plumber","location":"Johannesburg","pay":450.00,"description":"Fix a leaking geyser"}'

curl http://localhost:9080/api/jobs
```

Jobs are kept in memory and reset when the server restarts.

## Project layout

```
src/main/java/com/dailyspane/
├── DailySpaneApplication.java   JAX-RS application, mounted at /api
├── health/HealthResource.java
└── jobs/
    ├── JobPost.java             model
    ├── JobResource.java         REST endpoints
    └── JobService.java          in-memory store
src/main/liberty/config/server.xml
```

## Roadmap

- Persistence with Jakarta Persistence
- Bean Validation on job posts
- Job lookup, update and delete endpoints
- Tests
