# nibbs-transfer
Java web application which simulates NIBSS Instant Payments (NIP) transfer operation.

# Server port and route
localhost:8080/rest/service

#Endpoints

## Accept and Process Transfer
/transfer
#### Request Body
`{
"sourceUserId":"350315e9-d90f-43f0-8226-5e5de0cced3f",
"destinationUserId": "ef78b37f-f62d-41fe-8f94-cc31683a3de1",
"amount":10000
}`

##Get list of transactions with optional parameters: status, userId, date range
/transactions
#### Optional Parameters
status
userid
startdate
enddate

**NB: Date format = yyyy-MM-dd**

##Get Summary of transactions for a specified day
/summary
####Parameter
date

**NB: Date format = yyyy-MM-dd**