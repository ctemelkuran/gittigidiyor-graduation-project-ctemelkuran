# Save Customer

Saves a new customer to the database.

**Request URL**

`http://localhost:8080/api/customers`

**Example Request Value**

```json
{
  "fullName": "Quentin Tarantino",
  "idNumber": 36071499368,
  "phoneNumber": "05381234567",
  "salary": 5000
}
```

**Curl**

`curl -X POST "http://localhost:8080/api/customers" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"fullName\": \"Quentin Tarantino\", \"idNumber\": 36071499368, \"phoneNumber\": \"05381234567\", \"salary\": 3000}"`

## Server Responses
### Success

**Code:** `201`

**Response body**
```json
{
  "id": 1,
  "fullName": "Quentin Tarantino",
  "idNumber": 36071499368,
  "phoneNumber": "05381234567",
  "salary": 5000
}
```
### Error
**Code:** `400`

**Response body**
```json
{
  "status": 400,
  "message": "Customer with National Id: 36071499368 is already exists!",
  "timestamp": 0
}
```
