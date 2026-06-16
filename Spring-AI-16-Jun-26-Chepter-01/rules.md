# Agent Coding Rules

This project follows an Agent-Agnostic Spec-Driven Development (SSD) structure. Any AI agent (Cursor, Gemini, Copilot, Claude) should strictly adhere to these rules before making changes.

## Core Rules
1. **Source of Truth**: Always read `spec.md` for the current feature requirements and checklist. Always read `.docs/architecture.md` for project structure guidelines.
2. **Tech Stack**: 
   - Java 25
   - Spring Boot 4.1.0
   - Spring AI 2.0.0
   - Maven
3. **Coding Standards**:
   - Use constructor injection instead of `@Autowired` on fields.
   - Use standard Spring stereotypes (`@RestController`, `@Service`, `@Repository`).
   - All REST APIs should be placed under `/api/...` unless otherwise specified.
   - Prefer modern Java syntax (Records for DTOs, `var`, pattern matching, unnamed variables `_` where applicable).
4. **Best Practices**:
   - **Centralized Exception Handling**: Use `@RestControllerAdvice` and `@ExceptionHandler` to manage errors globally and return standardized JSON error responses (e.g., using `ProblemDetail`).
   - **Appropriate Logging**: Use SLF4J (or `@Slf4j` if Lombok is added) to log significant events, incoming requests, and exceptions at appropriate log levels. Do not swallow exceptions without logging.
   - **Spring AI 2.0 Patterns**: Use the modern `ChatClient.Builder` fluid API for interactions instead of older direct `ChatModel` calls when building business logic.
   - **Java 25 & Spring Boot 4**: Leverage Virtual Threads (if enabled) for blocking AI API calls to maximize throughput. Use structured concurrency when firing multiple AI prompts simultaneously.
5. **No Assumptions**: If the specification in `spec.md` is ambiguous, ask the user before writing complex code.
6. **Dependencies**: Do not add new dependencies to `pom.xml` unless explicitly approved by the user or specified in `spec.md`.
