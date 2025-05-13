package com.raxrot.expense.service.impl;

import com.raxrot.expense.dto.ExpenseDTO;
import com.raxrot.expense.entity.Expense;
import com.raxrot.expense.exception.ExpenseException;
import com.raxrot.expense.io.ExpenseRequest;
import com.raxrot.expense.repository.ExpenseRepository;
import com.raxrot.expense.service.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ModelMapper modelMapper;
    private final ExpenseRepository expenseRepository;
    public ExpenseServiceImpl(ModelMapper modelMapper, ExpenseRepository expenseRepository) {
        this.modelMapper = modelMapper;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<ExpenseDTO> getAllExpenses() {
        log.info("Fetching all expenses from repository");

        List<Expense> expenses = expenseRepository.findAll();
        List<ExpenseDTO> expenseDTOs = expenses.stream()
                .map(e -> modelMapper.map(e, ExpenseDTO.class))
                .collect(Collectors.toList());

        log.info("Mapped {} expenses to DTOs", expenseDTOs.size());

        return expenseDTOs;
    }

    @Override
    public ExpenseDTO getExpenseById(String expenseId) {
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ExpenseException("Expense not found: " + expenseId));
        return modelMapper.map(expense, ExpenseDTO.class);
    }

    @Override
    public ExpenseDTO createExpense(ExpenseRequest request) {
        Expense expense = modelMapper.map(request, Expense.class);
        expense.setExpenseId(UUID.randomUUID().toString());
        Expense saved = expenseRepository.save(expense);
        return modelMapper.map(saved, ExpenseDTO.class);
    }

    @Override
    public ExpenseDTO updateExpense(String expenseId, ExpenseRequest request) {
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ExpenseException("Expense not found: " + expenseId));

        expense.setName(request.getName());
        expense.setNote(request.getNote());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setAmount(request.getAmount());

        Expense updated = expenseRepository.save(expense);
        return modelMapper.map(updated, ExpenseDTO.class);
    }

    @Override
    public void deleteExpense(String expenseId) {
        Expense expense = expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new ExpenseException("Expense not found: " + expenseId));
        expenseRepository.delete(expense);
    }
}
