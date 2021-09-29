# Delete Customer

Deletes the existing customer

**Request URL**

`http://localhost:8080/api/customers/{id}`

**Path Variable**

`id (long)`



**Curl**

`curl -X DELETE "http://localhost:8080/api/customers/4" -H "accept: */*"`

## Server Responses
### Success

**Code:** `200`

**Response body**
```
Customer deleted with id: 4
```
### Errors
**Code:** `400 BAD REQUEST`

If Customer is not found with the given id.

**Response body**
```json
{
  "status": 400,
  "message": "Customer not found with the Id : 10",
  "timestamp": 1632908652774
}
```


