# Training Instructions

## Lab 1: Basic CRUD (Recap)

- **Setup**: Created `Shipment` entity and `ShipmentController`.
- **Implementation**: Implemented GET and POST endpoints.
- **Bonuses**: "Handle with Care" (fragile field) and "Teapot Challenge".

## Lab 2: Enums and JSON (Recap)

- **Enums**: Created `ShipmentStatus` (`PLANNED`, `CONFIRMED`, `SHIPPED`, `DELIVERED`).
- **JSON**: Used `@JsonValue` for serialization and `@JsonCreator` for deserialization.
- **Bonus**: Custom JSON output (lowercase).

## Lab 3: Validation (Recap)

- **Validation**: Added `@NotBlank`, `@Min`, `@NotNull` to `Shipment`.
- **Controller**: Enabled validation with `@Valid`.
- **Error Handling**: Created `GlobalExceptionHandler` to handle `MethodArgumentNotValidException`.
- **Bonus**: Custom error structure.

---

## Lab 4: External API Integration

**Objective**: Implement a Java service that acts as a client to consume an external API, focusing on robust error handling and timeout configuration.

### Lab 4.1: The Modern HttpClient (Theory)

We will use the standard `java.net.http.HttpClient` (Java 11+).

**Key Steps:**

1.  **Build the Client**: `HttpClient.newBuilder().connectTimeout(...).build()`
2.  **Build the Request**: `HttpRequest.newBuilder().uri(...).GET().build()`
3.  **Send the Client**: `client.send(request, BodyHandlers.ofString())`

_(Note: If using Spring WebFlux, `WebClient` is the preferred non-blocking option, but `HttpClient` is great for standard synchronous needs)._

### Lab 4.2: Exercise - Implementing the Client

We will use a public test API: `https://jsonplaceholder.typicode.com/todos/1`

#### Task 1: Build the Service

Create a new Java Service to handle external calls.

1.  Create a class `ExternalApiService` in your `service` package.
2.  Annotate it with `@Service`.
3.  Add a method `public String fetchTodo()` (we will return the raw JSON string for now).

#### Task 2: Implement the Call

Use `HttpClient` to call the target API.

1.  Inside `fetchTodo`, create an `HttpClient`.
2.  Create an `HttpRequest` targeting `https://jsonplaceholder.typicode.com/todos/1`.
3.  Send the request and return the body string.

```java
HttpClient client = HttpClient.newHttpClient();
HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
        .GET()
        .build();
HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
return response.body();
```

> **Hint**: You will need to handle `IOException` and `InterruptedException`. You can wrap them in a `RuntimeException` for this lab.

#### Task 3: Configure Timeouts

Production systems need timeouts to avoid hanging forever.

1.  Modify your `HttpClient` creation to include a **connect timeout** (e.g., 5 seconds).
2.  Modify your `HttpRequest` builder to include a **request timeout** (e.g., 5 seconds).

```java
HttpClient client = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build();
// ...
.timeout(Duration.ofSeconds(5))
```

#### Task 4: Handle Remote Errors

What if the API is down or returns 404?

1.  Check `response.statusCode()`.
2.  If it is not 2xx (e.g., `response.statusCode() >= 300`), throw a custom exception `RemoteApiFailedException`.
3.  **Verify**: Change the URL to a non-existent endpoint (e.g., `/todos/99999`) and ensure your exception is thrown.

### Bonus Exercise (Lab 4)

- **Typed Response**: Instead of returning `String`, create a `Todo` record (userId, id, title, completed) and use `ObjectMapper` to deserialize the JSON response into a Java object.
