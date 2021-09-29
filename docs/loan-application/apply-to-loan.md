# Apply For Loan

Customer can apply for a loan. If the customer doesn't exist it is created and the application can be processed. A Sms message is sent to customers phone number. Only phone numbers from Turkey are accepted. A customer can apply for loan multiple times. 

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

`curl -X POST "http://localhost:8080/loan-application/apply" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"fullName\": \"Quentin Tarantino\", \"idNumber\": 36071499368, \"phoneNumber\": \"05381234567\", \"salary\": 5000}"`

## Server Responses
### Success

**Code:** `201`

**Response body**
```json
{
  "id": 1,
  "customerIdNumber": 36071499368,
  "resultMessage": "APPROVED",
  "loanAmount": 10000
}
```
### Errors
**Code:** `400 BAD REQUEST`

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
