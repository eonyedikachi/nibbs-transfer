package com.nip.transfer.controller;

import com.nip.transfer.service.TransactionSummaryService;
import com.nip.transfer.service.UserService;
import com.nip.transfer.models.pojo.request.Transfer;
import com.nip.transfer.models.response.Response;
import com.nip.transfer.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/rest/service")
public class TransferController {

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionSummaryService transactionSummaryService;

/*    @PostMapping("/user")
    public ResponseEntity<Response> addUser(@RequestBody UserRegistration userRegistration){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now( ))
                        .data(of("User: ", userService.addUser(userRegistration)))
                        .status(OK)
                        .message(OK.getReasonPhrase() + ": User Successfully Added.")
                        .statusCode(OK.value())
                        .build()
        );
    }*/


    @PostMapping("/transfer")
    public ResponseEntity<Response> processTransfer(@RequestBody Transfer transfer){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Transfer ", transactionService.processTransaction(transfer)))
                        .message(OK.getReasonPhrase() + ": Transfer Successful.")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @GetMapping("/transactions")
    public ResponseEntity<Response> getTransactions(@RequestParam (value = "status", required = false) String status, @RequestParam (value = "userid", required = false) String userId, @RequestParam (value = "startdate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate, @RequestParam (value ="enddate", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Transactions ", transactionService.getAllTransactions(status, userId, startDate, endDate)))
                        .message(OK.getReasonPhrase() + ": Get all transactions")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/summary")
    public ResponseEntity<Response> getTransactionSummary(@RequestParam ("date") @DateTimeFormat(pattern="yyyy-MM-dd")  LocalDate date){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Transactions Summary ", transactionSummaryService.createSummary(date)))
                        .message(OK.getReasonPhrase() + ": Summary of all Transactions for " + date)
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

 /*   @GetMapping("/users")
    public ResponseEntity<Response> getUsers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .data(of("Users ", userService.getAllUsers()))
                        .message(OK.getReasonPhrase() + ": Get all users")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }*/

}