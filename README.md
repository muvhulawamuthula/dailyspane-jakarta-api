# DailySpane Jakarta API

Work-in-progress job-marketplace backend on **Jakarta EE 11** and **Open Liberty**.

This repository is an early learning / series scaffold: a deployable WAR with a health check and a minimal in-memory jobs API. It is **not** a production marketplace yet—there is no persistence, authentication, validation layer, or automated tests.

## Status

| Area | State |
|------|--------|
| Runtime | Open Liberty (`liberty-maven-plugin` 3.10.2) |
| Spec baseline | Jakarta EE 11 API (`jakarta.jakartaee-api` 11.0.0, provided) |
| Java | 21 |
| Packaging | WAR (`dailyspane.war`), context root `/` |
| Health | Implemented — `GET /api/health` |
| Jobs API | Partial — in-memory `GET`/`POST /api/jobs` only |
| Persistence | Not started (data lives in an `ApplicationScoped` list) |
| Auth / roles | Not started |
| Tests / CI | Not present |

## Stack (what is actually wired)

From `pom.xml` and `src/main/liberty/config/server.xml`:

- **JAX-RS** (`restfulWS-3.1`) — application path `/api` via `DailySpaneApplication`
- **CDI 4.0** — annotated bean discovery (`WEB-INF/beans.xml`)
- **JSON-B 3.0** — JSON request/response binding
- HTTP listener on **9080** (`host="0.0.0.0"`)

## API surface (current)

Base URL when Liberty is running locally: `http://localhost:9080/api`

### Health

```http
GET /api/health
```

Example response:

```json
{
  "status": "UP",
  "app": "dailyspane-jakarta-api"
}
```

### Jobs (in-memory)

```http
GET  /api/jobs
POST /api/jobs
Content-Type: application/json
```

`JobPost` fields: `id` (server-assigned), `title`, `location`, `pay` (`BigDecimal`), `description`.

`POST` returns `201 Created` with the created entity. There is no get-by-id, update, or delete. Restarting the server clears all jobs.

## Project layout

```
pom.xml
src/main/java/com/dailyspane/
  DailySpaneApplication.java   # @ApplicationPath("/api")
  health/HealthResource.java   # GET /health
  jobs/JobPost.java            # plain DTO
  jobs/JobResource.java        # JAX-RS resource
  jobs/JobService.java         # CDI @ApplicationScoped store
src/main/liberty/config/server.xml
src/main/webapp/WEB-INF/beans.xml
```

## Quick start

See [docs/getting-started.md](docs/getting-started.md). Short version:

```bash
mvn liberty:dev
# then
curl -s http://localhost:9080/api/health
```

## Architecture

See [docs/architecture.md](docs/architecture.md) for how the pieces fit together and what is intentionally out of scope for this stage.

## Roadmap (honest)

Near-term work that would make this a real backend, in rough order:

1. Persist jobs (Jakarta Persistence + a real datastore)
2. Expand jobs CRUD and input validation
3. Authentication / authorization
4. Automated tests and CI
5. Observability beyond a static health map

Until those land, treat this repo as a Jakarta EE bootstrap for a job marketplace—not a finished product.

## License

No license file is present in the repository yet.