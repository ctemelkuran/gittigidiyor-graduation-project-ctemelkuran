# Save Customer

Saves a new customer to the database.

**Request URL**

`http://localhost:8080/api/customers`

**Example Request Body**

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
### Errors
**Code:** `400`

If given National ID number already exists.

**Response body**
```json
{
  "status": 400,
  "message": "Customer with National Id: 36071499368 is already exists!",
  "timestamp": 1632907495337
}
```
---
If given National ID number is not valid.

**Response body**
```json
{
  "status": 400,
  "message": "Enter a valid National ID number!",
  "timestamp": 1632907762880
}
```
