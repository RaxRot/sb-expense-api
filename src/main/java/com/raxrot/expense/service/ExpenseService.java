package com.raxrot.expense.service;

import com.raxrot.expense.dto.ExpenseDTO;

import java.util.List;

public interface ExpenseService {
    List<ExpenseDTO>getAllExpenses();
}
