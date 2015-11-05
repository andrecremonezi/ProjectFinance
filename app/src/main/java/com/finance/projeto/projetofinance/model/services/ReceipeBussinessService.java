package com.finance.projeto.projetofinance.model.services;

import com.finance.projeto.projetofinance.model.entities.Receipe;
import com.finance.projeto.projetofinance.model.persistence.ReceipeRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ReceipeBussinessService {


    private ReceipeBussinessService() {
        super();
    }

    public static List<Receipe> findAll() {
        return ReceipeRepository.getAll();
    }

    public static void save(Receipe receipe) {
        ReceipeRepository.save(receipe);
    }

    public static void delete(Receipe receipe){
        ReceipeRepository.delete(receipe.getId());
    }

    /// MES ATUAL

    public static Double sumValuesThisMonth() {
        List<Receipe> receipes = ReceipeBussinessService.findAll();

        Double sum = 0.0;

        for (Receipe ex : receipes) {
            sum += ex.getValue();
        }

        return sum;
    }

    public static Double sumValuesBankThisMonth() {
        List<Receipe> receipes = ReceipeBussinessService.findAll();

        Double sum = 0.0;

        for (Receipe re : receipes) {
            if(re.getWalletOrBank().equals("Banco"))
                sum += re.getValue();
        }

        return sum;
    }

    public static Double sumValuesWalletThisMonth() {
        List<Receipe> receipes = ReceipeBussinessService.findAll();

        Double sum = 0.0;

        for (Receipe re : receipes) {
            if(re.getWalletOrBank().equals("Carteira"))
                sum += re.getValue();
        }

        return sum;
    }

}
