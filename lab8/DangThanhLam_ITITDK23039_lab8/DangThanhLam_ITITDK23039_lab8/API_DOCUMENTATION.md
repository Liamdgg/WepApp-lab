# Customer API Documentation

## Base URL
`http://localhost:8080/api/customers`


Global headers
- `Content-Type: application/json` (for requests with a body)
- `Accept: application/json`

---

**Endpoints**

### Get All Customers
- Method: GET
- Path: `/api/customers`
- Query parameters:
  - `page` (optional, integer, default 0) — zero-based page index
  - `size` (optional, integer, default 10) — page size
  - `sortBy` (optional, string, e.g. `fullName`, `email`, `customerCode`)
  - `sortDir` (optional, `asc` or `desc`, default `asc`)

Example request (Postman URL):

URL: `http://localhost:8080/api/customers?page=0&size=10&sortBy=fullName&sortDir=asc`

Headers:
- `Accept: */*`
- `User-Agent: Postmate Client`

Example response: 200 OK (paginated)
```json
{
  "content": [
    {
      "id": 1,
      "customerCode": "C001",
      "fullName": "John Doe",
      "email": "john.doe@example.com",
      "phone": "+15551234567",
      "address": "123 Main St",
      "status": "ACTIVE"
    }
  ],
  "pageable": {},
  "totalElements": 5,
  "totalPages": 1,
  "number": 0
}
```

---

### Get Customer by ID
- Method: GET
- Path: `/api/customers/{id}`
- Path variables:
  - `id` (long) — customer primary key

Example (Postman URL):

URL: `http://localhost:8080/api/customers/1`

Headers:
- `Accept: */*`
- `User-Agent: Postmate Client`

Response: 200 OK
```json
{
  "id": 1,
  "customerCode": "C001",
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+15551234567",
  "address": "123 Main St",
  "status": "ACTIVE"
}
```

404 Not Found: returned when the requested customer does not exist.

---

### Create Customer
- Method: POST
- Path: `/api/customers`
- Body: JSON — use `Content-Type: application/json`

Request example:
```json
{
  "customerCode": "C010",
  "fullName": "New Person",
  "email": "new.person@example.com",
  "phone": "+15551234567",
  "address": "100 Example St"
}
```

Postman request (URL + JSON body):

URL: `http://localhost:8080/api/customers`

Headers:
- `Accept: */*`
- `User-Agent: Postmate Client`
- `Content-Type: application/json`

Body (raw JSON):
```json
{
  "customerCode": "C010",
  "fullName": "New Person",
  "email": "new.person@example.com",
  "phone": "+15551234567",
  "address": "100 Example St"
}
```

Response: 201 Created — returns the created `CustomerResponseDTO` JSON

Errors: 400 Bad Request for validation failures (invalid/missing fields), 409 Conflict if `customerCode` or `email` duplicates an existing resource.

---

### Update Customer (PUT)
- Method: PUT
- Path: `/api/customers/{id}`
- Body: full `CustomerRequestDTO` JSON (all required fields must be present)

Request example:
```json
{
  "customerCode": "C001",
  "fullName": "John Updated",
  "email": "john.updated@example.com",
  "phone": "+15559999999",
  "address": "New Address"
}
```

Postman request (URL + JSON body):

URL: `http://localhost:8080/api/customers/1`

Headers:
- `Accept: */*`
- `User-Agent: Postmate Client`
- `Content-Type: application/json`

Body (raw JSON):
```json
{
  "customerCode": "C001",
  "fullName": "John Updated",
  "email": "john.updated@example.com",
  "phone": "+15559999999",
  "address": "New Address"
}
```

Response: 200 OK with updated `CustomerResponseDTO`.
Server error `Required request body is missing` usually indicates the client did not send a JSON body or `Content-Type` was not set to `application/json`. In Postman, choose the `raw` body type and `JSON` format (not the `Headers` raw text) so the request actually contains the JSON payload.

---

### Partial Update (PATCH)
- Method: PATCH
- Path: `/api/customers/{id}`
- Body: partial `CustomerUpdateDTO` JSON — only include the fields to change

Example request (only update fullName):
```json
{
  "fullName": "John Partially Updated"
}
```

Postman request (URL + JSON body):

URL: `http://localhost:8080/api/customers/1`

Headers:
- `Accept: */*`
- `User-Agent: Postmate Client`
- `Content-Type: application/json`

Body (raw JSON):
```json
{
  "fullName": "John Partially Updated"
}
```

Response: 200 OK with updated resource. Validation rules still apply for any fields provided (e.g., email format).

---

### Delete Customer
- Method: DELETE
- Path: `/api/customers/{id}`

Example (Postman URL):

URL: `http://localhost:8080/api/customers/6`

Headers:
- `Accept: */*`
- `User-Agent: Postmate Client`

Response: 204 No Content (or 200 OK depending on implementation). 404 if the customer does not exist.

---

### Search Customers (keyword)
- Method: GET
- Path: `/api/customers/search`
- Query parameters:
  - `keyword` (required) — searches `fullName` and `email` (case-insensitive)

Example (Postman URL):

URL: `http://localhost:8080/api/customers/search?keyword=john`

Headers:
- `Accept: */*`
- `User-Agent: Postmate Client`

Response: 200 OK — array of matching `CustomerResponseDTO` objects.

---

### Filter by Status
- Method: GET
- Path: `/api/customers/status/{status}`
- Path variables:
  - `status` — `ACTIVE` or `INACTIVE` (case-insensitive accepted by controller)

Example (Postman URL):

URL: `http://localhost:8080/api/customers/status/ACTIVE`

Headers:
- `Accept: */*`
- `User-Agent: Postmate Client`

Response: 200 OK — array of customers with the requested status.

Notes: controller converts incoming path variable to the `CustomerStatus` enum; invalid values return 400.

---

## Troubleshooting & Tips
- Postman: when sending JSON body, select `Body -> raw -> JSON` and paste the JSON object. Do not put headers inside the raw JSON body. Ensure the `Content-Type: application/json` header is present (Postman usually sets this automatically when `JSON` is selected).
- If you get `Required request body is missing`, open the Postman Console (View -> Show Postman Console) and inspect the outgoing request payload and headers to confirm the body was sent.
- To avoid validation errors when updating, ensure fields match DTO constraints (e.g., valid email format, phone format if enforced).
- For large datasets, replace in-memory `advanced-search` with DB-side queries and add pagination for scalability.

---

## DTO Schemas (examples)
- CustomerRequestDTO (used for create/update):
```json
{
  "customerCode": "C001",
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+15551234567",
  "address": "123 Main St"
}
```

- CustomerUpdateDTO (used for PATCH — all fields optional):
```json
{
  "fullName": "Optional Updated Name",
  "email": "optional.email@example.com"
}
```

---




