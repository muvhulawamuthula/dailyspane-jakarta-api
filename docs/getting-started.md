# Getting started

Instructions for running what exists in this repository today. Prerequisites and commands match `pom.xml` and `server.xml`.

## Prerequisites

- **JDK 21** (compiler release is 21)
- **Maven 3.9+** (any recent 3.x that can resolve Central)
- Network access for first-time dependency and Liberty runtime download

No database or Docker is required for the current in-memory API.

## Clone and build

```bash
git clone https://github.com/muvhulawamuthula/dailyspane-jakarta-api.git
cd dailyspane-jakarta-api
mvn -q package
```

That produces `target/dailyspane.war` (`finalName` is `dailyspane`).

## Run with Open Liberty (recommended)

The `liberty-maven-plugin` (3.10.2) is configured in `pom.xml`. Dev mode starts Liberty, deploys the WAR, and watches for changes:

```bash
mvn liberty:dev
```

Default HTTP port from `src/main/liberty/config/server.xml`: **9080**.

Context root is `/`, JAX-RS application path is `/api`, so resources live under:

```text
http://localhost:9080/api/...
```

Stop with `Ctrl+C` in the terminal running `liberty:dev`.

### Other useful Liberty goals

```bash
mvn liberty:run          # start without the full dev-reload loop
mvn liberty:stop         # stop a previously started server (if applicable)
```

Exact goal behavior depends on your local Liberty Maven plugin install; prefer `liberty:dev` while iterating.

## Smoke-test the API

### Health

```bash
curl -s http://localhost:9080/api/health | jq .
```

Expected shape:

```json
{
  "status": "UP",
  "app": "dailyspane-jakarta-api"
}
```

### List jobs (starts empty)

```bash
curl -s http://localhost:9080/api/jobs | jq .
```

### Create a job

```bash
curl -s -X POST http://localhost:9080/api/jobs \
  -H 'Content-Type: application/json' \
  -d '{
    "title": "Weekend landscaping",
    "location": "Johannesburg",
    "pay": 850.00,
    "description": "Clear garden beds and haul cuttings."
  }' | jq .
```

You should get HTTP **201** and a body that includes a numeric `id`. A subsequent `GET /api/jobs` returns the list including that post. Restarting Liberty clears the list.

## IDE notes

- Import as a Maven project; set the project SDK to 21.
- Source root: `src/main/java`
- Liberty config consumed by the plugin: `src/main/liberty/config/server.xml`
- CDI descriptor: `src/main/webapp/WEB-INF/beans.xml`

## Troubleshooting

| Symptom | Likely cause |
|---------|----------------|
| Port already in use | Something else bound to 9080; stop it or change `httpPort` in `server.xml` |
| 404 on `/api/...` | Server not up, wrong port, or WAR not deployed—check Liberty console logs |
| Empty jobs after restart | Expected—storage is an in-memory `ArrayList` in `JobService` |
| Inject / CDI failures | Confirm `beans.xml` is packaged and `cdi-4.0` is enabled in `server.xml` |

## What you will not find yet

- `.env` files or datasource config
- Seed scripts
- Test suites (`src/test` is absent)
- Auth headers or API keys

If you need those, they still need to be built—see the roadmap in the [README](../README.md) and constraints in [architecture.md](architecture.md).