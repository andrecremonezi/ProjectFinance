package com.finance.projeto.projetofinance.model.services;

import com.finance.projeto.projetofinance.model.entities.Expense;
import com.finance.projeto.projetofinance.model.entities.Goal;
import com.finance.projeto.projetofinance.model.persistence.ExpenseRepository;
import com.finance.projeto.projetofinance.model.persistence.GoalRepository;

import java.util.List;

/**
 * Created by Andrea on 11/10/2015.
 */
public class GoalBussinessService {
    private GoalBussinessService() {
        super();
    }

    public static List<Goal> findAll() {
        return GoalRepository.getAll();
    }

    public static void save(Goal goal) {
        GoalRepository.save(goal);
    }

    public static void delete(Goal goal){
        GoalRepository.delete(goal.getId());
    }

}
