# Training Instructions

## Lab 1.0: Setup

- Start the base Java project (Spring Boot application).
- Create the `Shipment` resource class.
  > **Hint:** Create a new class named `Shipment` in a `domain` or `model` package. It should have fields like `Long id`, `String destination`, `BigDecimal price`, and `String status`.
- Set up the REST Controller.
  > **Hint:** Create a new class named `ShipmentController` in a `controller` or `web` package.

### Bonus Exercise: Handle with Care

- **Fragile Goods**: Some shipments require special attention. Add a `Boolean fragile` field to the `Shipment` class. Restart the application and check the logs—did Hibernate automatically update the database schema for you? This demonstrates the power of JPA's `ddl-auto` and how it handles different data types.

## Lab 1.1: Controller Definition

**Key Action:**

- Define the class that handles incoming HTTP requests.

**Code Structure and Best Practices:**

- `@RestController`: Essential annotation for telling Spring that this class handles HTTP requests and returns JSON/XML data directly.
- `@RequestMapping`: Defines the base path.
- **Good Practice**: Always include a version (e.g., `/api/v1/shipments`).

### Bonus Exercise: The Teapot Challenge

- **HTTP 418**: Create a method mapped to `/teapot` that returns a `ResponseEntity` with the status `I_AM_A_TEAPOT` (418).
  > **Why?** It's a classic developer easter egg (RFC 2324), but it also teaches you how to manually control the HTTP status code using `ResponseEntity.status(...)`.

## Lab 1.2: Implementation

**Task 1 (GET):**

- Implement `GET /shipments` (return list).
  > **Hint:** The method should return `List<Shipment>`. You might need a Repository or a hardcoded list for now.
- Implement `GET /shipments/{id}` (return single resource).
  > **Hint:** Use `@PathVariable("id")` to extract the ID from the URL.
- **Key Annotation**: `@GetMapping`

**Task 2 (POST):**

- Implement `POST /shipments` to handle resource creation.
  > **Hint:** Use `@RequestBody` to map the JSON body to an `Shipment` object.
- **Key Annotation**: `@PostMapping`
- **Key Requirement**: Ensure this method returns a `201 Created` status code.
  > **Hint:** Return `ResponseEntity<Shipment>` and use `ResponseEntity.status(HttpStatus.CREATED).body(...)` or `ResponseEntity.created(uri).body(...)`.

**Task 3 (Error Handling):**

- Implement basic logic to return a `404 Not Found` if an ID in a GET request does not exist.
  > **Hint:** If using a Repository, `findById(id)` returns an `Optional`. You can use `.orElseThrow(...)` to throw an exception if not found, or check `isPresent()` and return `ResponseEntity.notFound().build()`.

### Bonus Exercises

- **Implement DELETE**: Add an endpoint to delete an shipment by its ID.
  > **Hint:** Use `@DeleteMapping("/{id}")` and the repository's `deleteById` method.
- **Filter by Status**: Add a query parameter to the list endpoint to filter shipments by status (e.g., `GET /shipments?status=PLANNED`).
