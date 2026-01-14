# Training Instructions

## Lab 1: Basic CRUD (Recap)

- **Setup**: Created `Shipment` entity and `ShipmentController`.
- **Implementation**: Implemented GET and POST endpoints.
- **Bonuses**: "Handle with Care" (fragile field) and "Teapot Challenge".

## Lab 2: Enums and JSON (Recap)

- **Enums**: Created `ShipmentStatus` (`PLANNED`, `CONFIRMED`, `SHIPPED`, `DELIVERED`).
- **JSON**: Used `@JsonValue` for serialization and `@JsonCreator` for deserialization.
- **Bonus**: Custom JSON output (lowercase).

---

## Lab 3: Validation and API Documentation

**Objective**: Implement constraint-based validation on the API input (DTO/POJO) to automatically reject invalid requests, and generate a live, interactive contract (Swagger UI) from your Java code.

### Lab 3.1: Java Bean Validation (Theory)

We use the **Jakarta Bean Validation** standard (e.g., Hibernate Validator) which integrates directly with Spring Boot.

**Key Annotations:**

- `@NotNull`: Must not be null.
- `@Size(min=, max=)`: For strings, collections.
- `@Pattern(regexp=)`: For complex formats (e.g., "EXP-XXXX").
- `@Positive`: For numbers.

**Triggering Validation**: Add the `@Valid` annotation in the Controller method signature, next to `@RequestBody`.

### Lab 3.2: Exercise - Implementing Validation

#### Task 1: Add Validation

Add validation annotations to the fields of your `Shipment` POJO.

1.  Open `Shipment.java`.
2.  Add `@NotBlank` to `destination`.
3.  Add `@Positive` to `weight`.
4.  Add `@NotNull` to `status`.

#### Task 2: Enable Validation

Add the `@Valid` annotation to the `POST /shipments` method in your Controller.

```java
public Shipment createShipment(@Valid @RequestBody Shipment shipment) { ... }
```

#### Task 3: Handle Errors

Implement a Global Exception Handler to catch `MethodArgumentNotValidException` and return a clean JSON error list with a 400 Bad Request status.

1.  Create `GlobalExceptionHandler` with `@RestControllerAdvice`.
2.  Add an `@ExceptionHandler` for `MethodArgumentNotValidException`.
3.  Return a `Map<String, String>` of field errors.

### Lab 3.3: API Documentation (Theory)

We use the **OpenAPI Specification (OAS)** to create a live, interactive contract. **SpringDoc** is the standard library that scans your Spring Boot code to generate this documentation automatically.

**Key Annotations:**

- `@Tag`: Groups endpoints logically (e.g., "Shipment Management").
- `@Operation`: Adds a human-readable summary ("Creates a new shipment").
- `@ApiResponse`: Explicitly documents errors (201, 400, 404) that Spring doesn’t guess.

**Activation**: Simply add the dependency. The documentation is auto-generated at `/swagger-ui.html`.

### Lab 3.4: Exercise - Implementing SpringDoc

#### Task 1: Install

Add the `springdoc-openapi-starter-webmvc-ui` dependency (version 2.8.14+) to your `pom.xml` and restart the application.

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.8.14</version>
</dependency>
```

#### Task 2: Document

Annotate the `ShipmentController`.

1.  Add `@Tag` to the class level: `@Tag(name = "Shipment API", description = "...")`.
2.  Describe the `createShipment` method using `@Operation` and `@ApiResponse` (documenting the 201 and 400 codes).

#### Task 3: Verify

1.  Open Swagger UI: `http://localhost:8080/swagger-ui.html`.
2.  Use the "Try it out" button to send an invalid request.
3.  Verify that the 400 Bad Request error is correctly documented and returned.

### Bonus Exercises (Lab 3)

- **Custom Error Structure**: Instead of a simple Map, return a custom `ApiError` object (timestamp, status, errors, path).
- **Production Ready**: Configure `application.properties` to disable Swagger UI in production (`springdoc.swagger-ui.enabled=false`).
