# Find Exception Logs By Type

Finds the thrown exceptions by type

**Request URL**

`http://localhost:8080/api/exception-logs/by-type`

**Request Parameters**

`applicationType (string)`
`pageNumber (integer)`
`pageSize (integer)`


**Example Curl**

`curl -X GET "http://localhost:8080/api/exception-logs/by-type?exceptionType=InvalidIdNumberException" -H "accept: */*"`

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
  "totalElements": 1,
  "first": true,
  "numberOfElements": 1,
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



