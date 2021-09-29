# Find Exception Logs By Date

Finds the thrown exceptions by date

**Request URL**

`http://localhost:8080/api/exception-logs`

**Request Parameters**

`applicationDate (string)`
`pageNumber (integer)`
`pageSize (integer)`


**Example Curl**

`curl -X GET "http://localhost:8080/api/exception-logs?exceptionDate=29%2F09%2F2021" -H "accept: */*"`

## Server Responses
### Success

**Code:** `200`

**Response body**
```json
{
  "content": [
    {
      "id": 1,
      "exceptionType": "InvalidIdNumberException",
      "date": "2021-09-29",
      "message": "Enter a valid National Id number!"
    },
    {
      "id": 2,
      "exceptionType": "LoanApplicationNotFoundException",
      "date": "2021-09-29",
      "message": "Loan application not found with the National Id : 49185966930"
    }
  ],
  "pageable": {
    "sort": {
      "unsorted": true,
      "sorted": false,
      "empty": true
    },
    "pageNumber": 0,
    "pageSize": 10,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 2,
  "first": true,
  "numberOfElements": 2,
  "size": 10,
  "number": 0,
  "sort": {
    "unsorted": true,
    "sorted": false,
    "empty": true
  },
  "empty": false
}
```



