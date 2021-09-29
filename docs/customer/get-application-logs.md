# Find Loan Applications of Customers

Finds the previous loan applications of customers according to date

**Request URL**

`http://localhost:8080/api/customers/get-applications-by-date`

**Path Variables**

`applicationDate (string)`
`pageNumber (integer)`
`pageSize (integer)`


**Curl**

`curl -X GET "http://localhost:8080/api/customers/get-applications-by-date?applicationDate=29%2F09%2F2021" -H "accept: */*"`

## Server Responses
### Success

**Code:** `200`

**Response body**
```json
{
  "content": [
    {
      "id": 1,
      "customerIdNumber": 36071499368,
      "loanResultMessage": "APPROVED",
      "loanAmount": 10000,
      "loanApplicationDate": "2021-09-29",
      "clientIpAddress": "0:0:0:0:0:0:0:1",
      "clientUrl": "/loan-application/apply",
      "sessionActivityId": "31CA4EE03BC47D34717770EA48523703"
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 1,
  "totalPages": 1,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "first": true,
  "numberOfElements": 1,
  "empty": false
}
```



