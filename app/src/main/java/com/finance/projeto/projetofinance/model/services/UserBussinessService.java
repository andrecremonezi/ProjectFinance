package com.finance.projeto.projetofinance.model.services;

import com.finance.projeto.projetofinance.model.entities.User;
import com.finance.projeto.projetofinance.model.persistence.UserRepository;

public class UserBussinessService {

    private UserBussinessService() {
        super();
    }

    public static void save(User user) {
        UserRepository.save(user);
    }

    public static void delete(User user){
        UserRepository.delete(user.getId());
    }

}
