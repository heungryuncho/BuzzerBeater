# Coding Conventions & Architecture Standards

## 1. Tech Stack Standards
- **Language:** Java 17+ (Prefer Record classes for DTOs).
- **Framework:** Spring Boot 3.x.
- **Build Tool:** Gradle (Groovy DSL).
- **Database:** MySQL/MariaDB (JPA & Hibernate).
- **Testing:** JUnit 5, Mockito, AssertJ.

---

## 2. Architecture Rules (Layered Architecture)
Strictly follow the separation of concerns:
- **Controller:** Handles HTTP requests/responses only. Validation logic (`@Valid`) goes here.
- **Service:** Contains all business logic. Transaction management (`@Transactional`) starts here.
- **Repository:** Handles DB interaction only.
- **DTO Rule:** NEVER return Entity objects in the Controller. Always map Entities to DTOs (Request/Response) inside the Service or using a Mapper.

---

## 3. Backend Coding Standards

### 3.1. Transaction Management
- Apply `@Transactional(readOnly = true)` at the class level for Service components.
- Apply `@Transactional` explicitly on methods that modify data (CUD).
- Avoid long-running transactions (e.g., external API calls inside a transaction).

### 3.2. JPA & Performance (N+1 Prevention)
- Do not use `EAGER` loading. All `@ManyToOne` and `@OneToOne` must be `LAZY`.
- When querying entities with associations, use `Fetch Join`, `@EntityGraph`, or `BatchSize` to prevent the N+1 problem.
- Prefer `QueryDSL` or `JPQL` for complex dynamic queries over heavy Java-side filtering.

### 3.3. Exception Handling
- Do not throw generic `Exception` or `RuntimeException`.
- Create custom exceptions (e.g., `TicketNotFoundException`) extending a base `BusinessException`.
- Use `@RestControllerAdvice` to handle exceptions globally and return standardized `ErrorResponse` DTOs.

---

## 4. Security & Quality
- **Secrets:** Never hardcode API keys, passwords, or tokens. Use `@Value` or `@ConfigurationProperties` to read from `application.yml`.
- **Validation:** Use Bean Validation (`@NotNull`, `@Email`, etc.) on DTOs.
- **Logging:** Use `Slf4j`. Do not use `System.out.println`. Log critical errors with stack traces; log business events at INFO level.
- **Tests:** Business logic must have Unit Tests. Critical flows (e.g., payment, reservation) must have Integration Tests.

---

## 5. Documentation (JavaDoc & Swagger)
- Public methods in Services must have JavaDoc explaining *what* it does, not *how*.
- Controller methods must have Swagger annotations (`@Operation`, `@ApiResponse`) to auto-generate API docs.

---

## 6. Git Commit Conventions (Conventional Commits)
Follow the standard `type: subject` format.

- **feat**: New feature (새로운 기능 추가)
- **fix**: Bug fix (버그 수정)
- **docs**: Documentation changes (문서 수정)
- **style**: Formatting, semi-colons, no code change (코드 포맷팅, 오타 수정)
- **refactor**: Refactoring production code (리팩토링)
- **test**: Adding missing tests, refactoring tests (테스트 코드 추가/수정)
- **chore**: Build tasks, package manager configs (빌드, 패키지 매니저 설정)
