# Update Customer

Updates the existing customer

**Request URL**

`http://localhost:8080/api/customers/{id}`

**Path Variable**

`id (long)`


**Example Request Body**

```json
{
  "fullName": "Quentin Tarantino",
  "idNumber": 36071499368,
  "phoneNumber": "05381234567",
  "salary": 7000
}
```

**Curl**

`curl -X PUT "http://localhost:8080/api/customers/1" -H "accept: */*" 
-H "Content-Type: application/json" -d "{ \"fullName\": \"Quentin Tarantino\", 
\"idNumber\": 36071499368, \"phoneNumber\": \"05381234567\", \"salary\": 4000}"`

## Server Responses
### Success

**Code:** `200`

**Response body**
```json
{
  "id": 1,
  "fullName": "Quentin Tarantino",
  "idNumber": 36071499368,
  "salary": 4000,
  "phoneNumber": "05381234567"
}
```
### Errors
**Code:** `400 BAD REQUEST`

If Customer not found with given id.

**Response body**
```json
{
  "status": 400,
  "message": "Customer not found with id: 10",
  "timestamp": 1632907387564
}
```
---
If Customer not found with given National ID.

**Response body**
```json
{
  "status": 400,
  "message": "Customer not found with the National ID : 64539745160",
  "timestamp": 1632907573398
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


