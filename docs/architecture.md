# Architecture

Grounded in the code on `master` as of this documentation update. Nothing here invents services, databases, or endpoints that are not in the tree.

## Intent

DailySpane is meant to become a job-marketplace API. Today it is a thin Jakarta EE WAR on Open Liberty with:

1. A JAX-RS application root at `/api`
2. A static health resource
3. An in-memory jobs resource backed by a CDI bean

## Runtime topology

```
Client
  │  HTTP :9080
  ▼
Open Liberty
  featureManager: restfulWS-3.1, cdi-4.0, jsonb-3.0
  webApplication: dailyspane.war @ contextRoot "/"
  │
  ▼
DailySpaneApplication (@ApplicationPath "/api")
  ├── HealthResource  → GET  /api/health
  └── JobResource     → GET|POST /api/jobs
         │ @Inject
         ▼
      JobService (@ApplicationScoped)
         └── List<JobPost> + AtomicLong id generator
```

Configured in `src/main/liberty/config/server.xml`. The HTTP endpoint binds `0.0.0.0:9080`. There is no HTTPS endpoint, datasource, or security realm in that file.

## Layering (as implemented)

| Layer | Classes | Notes |
|-------|---------|--------|
| Application | `DailySpaneApplication` | Empty `Application` subclass; resources are discovered |
| HTTP / REST | `HealthResource`, `JobResource` | JAX-RS; JSON via `@Produces` / `@Consumes` |
| Domain DTO | `JobPost` | Mutable POJO with getters/setters; not a JPA entity |
| Application service | `JobService` | Holds state in memory; no interface, no repository |

There is no separate persistence, security, or messaging layer yet.

## CDI

`WEB-INF/beans.xml` sets `bean-discovery-mode="annotated"` (CDI 4.0). `JobService` is the only `@ApplicationScoped` bean. `JobResource` obtains it with `@Inject`. `HealthResource` has no injections.

## Jobs data model

`JobPost` fields:

- `Long id` — assigned in `JobService.create` via `AtomicLong`
- `String title`
- `String location`
- `BigDecimal pay`
- `String description`

No Bean Validation annotations, no equals/hashCode, no immutability. `findAll()` returns the live list reference (callers can mutate the store if they keep the list).

## Request flow (create job)

1. Client `POST /api/jobs` with a JSON body matching `JobPost` (minus or ignoring `id`).
2. JSON-B deserializes into `JobPost`.
3. `JobResource.createJob` delegates to `JobService.create`.
4. Service increments the id, appends to an `ArrayList`, returns the same instance.
5. Resource responds with `201` and the entity as JSON.

There is no transaction boundary, optimistic locking, or uniqueness check.

## Deliberate non-goals (current stage)

These are **not** present and should not be assumed from the README series framing alone:

- JPA / JDBC / connection pools
- Authentication, JWT, or role checks
- OpenAPI / MicroProfile Health (the health endpoint is a plain JAX-RS resource, not MP Health)
- Pagination, filtering, or search on jobs
- Get-by-id / update / delete
- Integration or unit tests
- Multi-module Maven layout
- Container image or Kubernetes manifests

## Evolution path

When persistence is introduced, the natural seam is `JobService`: replace the `ArrayList` with a repository (or inject an `EntityManager`) while keeping `JobResource` as the HTTP adapter. Until then, treat process restart as a full data wipe.