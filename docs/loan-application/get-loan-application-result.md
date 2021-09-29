# Get Loan Application Result

Loan application result can be queried with National ID Number.

**Request URL**

`http://localhost:8080/loan-application/result`

**Request Parameter**

`idNumber (long)`


**Curl**

`curl -X GET "http://localhost:8080/loan-application/result?idNumber=36071499368" -H "accept: */*"`

## Server Responses
### Success

**Code:** `200`

**Response body**
```json
[
  {
    "id": 1,
    "customerIdNumber": 36071499368,
    "resultMessage": "APPROVED",
    "loanAmount": 10000
  }
]
```
### Errors
**Code:** `400 BAD REQUEST`

If loan application not found with given National ID.

**Response body**
```json
{
  "status": 400,
  "message": "Loan application not found with the National Id : 36071499361",
  "timestamp": 1632923487304
}
```

---
If given National ID number is not valid.

**Response body**
```json
{
  "status": 400,
  "message": "Enter a valid National Id number!",
  "timestamp": 1632923673158
}
```


