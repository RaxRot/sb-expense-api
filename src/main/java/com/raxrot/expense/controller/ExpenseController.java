package com.raxrot.expense.controller;

import com.raxrot.expense.dto.ExpenseDTO;
import com.raxrot.expense.io.ExpenseResponse;
import com.raxrot.expense.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        List<ExpenseDTO> expenseDTOS = expenseService.getAllExpenses();
        List<ExpenseResponse> expenseResponses = expenseDTOS.stream()
                .map(expenseDTO -> modelMapper.map(expenseDTO, ExpenseResponse.class))
                .collect(Collectors.toList());

        log.info("Returning {} expenses", expenseResponses.size());

        return new ResponseEntity<>(expenseResponses, HttpStatus.OK);
    }
}
