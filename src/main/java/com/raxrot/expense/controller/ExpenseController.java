package com.raxrot.expense.controller;

import com.raxrot.expense.dto.ExpenseDTO;
import com.raxrot.expense.io.ExpenseRequest;
import com.raxrot.expense.io.ExpenseResponse;
import com.raxrot.expense.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ModelMapper modelMapper;

    public ExpenseController(ExpenseService expenseService, ModelMapper modelMapper) {
        this.expenseService = expenseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/expenses")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {
        log.info("GET /expenses called");
        List<ExpenseDTO> dtos = expenseService.getAllExpenses();
        List<ExpenseResponse> responses = dtos.stream()
                .map(dto -> modelMapper.map(dto, ExpenseResponse.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable String id) {
        log.info("GET /expenses/{} called", id);
        ExpenseDTO dto = expenseService.getExpenseById(id);
        return new ResponseEntity<>(modelMapper.map(dto, ExpenseResponse.class), HttpStatus.OK);
    }

    @PostMapping("/expenses")
    public ResponseEntity<ExpenseResponse> createExpense(@RequestBody ExpenseRequest request) {
        log.info("POST /expenses called");
        ExpenseDTO dto = expenseService.createExpense(request);
        return new ResponseEntity<>(modelMapper.map(dto, ExpenseResponse.class), HttpStatus.CREATED);
    }

    @PutMapping("/expenses/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(@PathVariable String id, @RequestBody ExpenseRequest request) {
        log.info("PUT /expenses/{} called", id);
        ExpenseDTO dto = expenseService.updateExpense(id, request);
        return new ResponseEntity<>(modelMapper.map(dto, ExpenseResponse.class), HttpStatus.OK);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
        log.info("DELETE /expenses/{} called", id);
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
