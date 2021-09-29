# Get All Customers

Finds all customer in the database.

**Request URL**

`http://localhost:8080/api/customers`



**Curl**

`curl -X GET "http://localhost:8080/api/customers" -H "accept: */*"`

## Server Responses
### Success

**Code:** `200`

**Response body**
```json
[
  {
    "id": 1,
    "fullName": "Arthur Dent",
    "idNumber": 53436704844,
    "salary": 4000,
    "phoneNumber": "5452582569"
  },
  {
    "id": 2,
    "fullName": "Quentin Tarantino",
    "idNumber": 82661129498,
    "salary": 4000,
    "phoneNumber": "5312582569"
  },
  {
    "id": 3,
    "fullName": "Morty Sanchez",
    "idNumber": 12174025338,
    "salary": 5000,
    "phoneNumber": "5869282569"
  }
]
```



