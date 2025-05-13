package com.raxrot.expense.service;

import com.raxrot.expense.dto.ExpenseDTO;
import com.raxrot.expense.io.ExpenseRequest;

import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO>getAllExpenses();
    ExpenseDTO getExpenseById(String expenseId);
    ExpenseDTO createExpense(ExpenseRequest request);
    ExpenseDTO updateExpense(String expenseId, ExpenseRequest request);
    void deleteExpense(String expenseId);
}
