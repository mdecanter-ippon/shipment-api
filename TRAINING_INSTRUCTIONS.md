# Training Instructions

## Lab 1: Basic CRUD (Recap)

In this lab, you established the foundation of the Shipment API.

### 1.0 Setup

- **Domain**: Created `Shipment` entity with fields: `id`, `destination`, `weight`, `status`.
- **DTOs**:
    - `ShipmentSummaryDto` with fields: `id`, `destination`, `weight`
    - `ShipmentDetailDto` with fields: `id`, `destination`, `weight`, `status`
- **Mapper**: Created `ShipmentMapper` to convert between entities and DTOs.
- **Service**: Created `ShipmentService` to handle business logic.
- **Controller**: Created `ShipmentController` with `@RestController`.

### 1.1 Implementation

- **Endpoints**:
    - Implemented `GET /api/v1/shipments`
    - Implemented `GET /api/v1/shipments/{id}`
    - Implemented `POST /api/v1/shipments`
- **DTO Usage**:
    - `GET /shipments` returns `ShipmentSummaryDto`
    - `GET /shipments/{id}` returns `ShipmentDetailDto`
    - `POST /shipments` accepts and returns `ShipmentDetailDto`
- **Error Handling**:
    - Returned 404 if ID not found.


## Lab 2: JSON Mapping and Data Binding

**Goal**: Implement automated mapping of JSON data to Java objects (DTOs) and vice-versa.

**Why?**

- **Integrity**: Consistent mapping ensures data integrity.
- **Contract**: DTOs define the API contract.
- **Efficiency**: Automated mapping avoids manual parsing.

## Lab 2.1: POJO–JSON Bridge

We will use **Jackson annotations on DTOs** to control JSON serialization and deserialization.

### Step 1: Protect against unknown fields

Clients might send extra fields that the API does not expect.

1. Open `ShipmentDetailDto`.
2. Add the `@JsonIgnoreProperties` annotation at class level.

```java
@Entity
@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)  // <--- Add this
```

#### Step 2: Custom Field Mapping

Sometimes the JSON keys (snake_case) don't match our Java variable names (camelCase). Use `@JsonProperty` to map them.

1. Map the `id` field to `shipment_id`.
2. Map the `destination` field to `destination_city`.
3. Map the `weight` field to `weight_kg`.

```java
@JsonProperty("shipment_id")
private Long id;

@JsonProperty("destination_city")
private String destination;

@JsonProperty("weight_kg")
private Double weight;
```
> **Why?** This decouples your internal Java naming conventions from the external public API contract.

### Bonus Exercise (2.1): The Legacy Alias
Sometimes, clients send data with old field names.

1.  Add `@JsonAlias("weight_lbs")` to your `weight` field.
2.  Try sending a POST request with `weight_lbs` instead of `weight_kg`.
3.  Verify that it is correctly mapped to the `weight` Java field.

---

## Lab 2.2: Serialization & Deserialization

### Task 1: Deserialization (The Request)

**Goal**: Verify that the server correctly reads JSON sent by the client.

1.  **Action**: Create a `POST` request to `http://localhost:8080/api/v1/shipments`.
2.  **Body**: Send the following JSON:
    ```json
    {
      "destination_city": "New York",
      "weight_kg": 12.5,
      "status": "PENDING",
      "useless_field": "ignore_me"
    }
    ```
3.  **Verification**:
    - The server should return `201 Created`.
    - The response should contain the data you sent (mapped back to Java fields).
    - The `useless_field` should be ignored (thanks to `@JsonIgnoreProperties`).

#### Task 2: Serialization (The Response)

**Goal**: Verify that the server generates the correct JSON format.

1.  **Action**: Create a `GET` request to `http://localhost:8080/api/v1/shipments`.
2.  **Verification**: Check the JSON response keys.
    - You should see `shipment_id`, `destination_city`, and `weight_kg`.
    - You should **NOT** see `id`, `destination`, or `weight`.
    
> **Hint**: If you see `id` instead of `shipment_id`, ensure you imported `@JsonProperty` from `com.fasterxml.jackson.annotation`.

3. Verify:
    - Response status is `201 Created`
    - Unknown field is ignored

#### Task 3: Using Enums for Status

**Goal**: Improve type safety by replacing the String status with an Enum.

1.  **Create Enum**: Create `ExpeditionStatus` enum with values `PLANNED`, `CONFIRMED`, `SHIPPED`, `DELIVERED`.
2.  **Refactor**: Update `Shipment` class to use `ExpeditionStatus` instead of `String`.
3.  **Verify**: Try to create a shipment with an invalid status (e.g., "LOST"). It should fail (400 Bad Request).

#### Task 4: Hiding Sensitive Data

**Goal**: Prevent sensitive internal data from being exposed in the API response.

1.  **Add Field**: Add a `String investigationCode` field to your `Shipment` class.
2.  **Annotate**: Add `@JsonIgnore` to this field.
3.  **Verify**:
    - Set a value for `investigationCode` (e.g., "SECRET-123").
    - Call `GET /shipments`.
    - Ensure `investigationCode` is **NOT** present in the JSON response.

#### Task 5: Custom Date Formatting

**Goal**: Control how dates are serialized in the API response.

1.  Add a `LocalDate shippingDate` field to `Shipment`.
2.  Use `@JsonFormat(pattern = "dd-MM-yyyy")` to enforce a specific format.
3.  Verify in Bruno that the date appears as "25-12-2023".
