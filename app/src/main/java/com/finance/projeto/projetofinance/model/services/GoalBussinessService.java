package com.finance.projeto.projetofinance.model.services;

import com.finance.projeto.projetofinance.model.entities.Goal;
import com.finance.projeto.projetofinance.model.persistence.GoalRepository;

import java.util.List;

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
