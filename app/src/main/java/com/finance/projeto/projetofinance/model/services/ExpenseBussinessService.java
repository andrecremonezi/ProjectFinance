package com.finance.projeto.projetofinance.model.services;

import com.finance.projeto.projetofinance.model.entities.Expense;
import com.finance.projeto.projetofinance.model.persistence.ExpenseRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ExpenseBussinessService {

    private ExpenseBussinessService() {
        super();
    }

    public static List<Expense> findAll() {
        return ExpenseRepository.getAll();
    }

    public static void save(Expense expense) {
        ExpenseRepository.save(expense);
    }

    public static void delete(Expense expense){
        ExpenseRepository.delete(expense.getId());
    }

    public static int thisMonth(){
        Date date = new Date();
        GregorianCalendar dataCal = new GregorianCalendar();
        dataCal.setTime(date);

        return dataCal.get(Calendar.MONTH) + 1;
    }



    // MES ATUAL


    public static Double sumValuesThisMonth() {
        String month = String.valueOf(thisMonth());

        List<Expense> expenses = ExpenseBussinessService.findAll();

        Double sum = 0.0;

        for (Expense ex : expenses) {
            if(ex.getMonth().toString().equals(month))
                sum += ex.getValue();
        }

        return sum;
    }

    public static Double sumValuesBankThisMonth() {
        String month = String.valueOf(thisMonth());
        List<Expense> expences = ExpenseBussinessService.findAll();

        Double sum = 0.0;

        for (Expense ex : expences) {
           if(ex.getForm().equals("Banco") && ex.getMonth().toString().equals(month))
             sum += ex.getValue();
        }

        return sum;
    }

    public static Double sumValuesWalletThisMonth() {
        String month = String.valueOf(thisMonth());
        List<Expense> expences = ExpenseBussinessService.findAll();
        Double sum = 0.0;

        for (Expense ex : expences) {
            if(ex.getForm().equals("Carteira") && ex.getMonth().toString().equals(month))
              sum += ex.getValue();
        }

        return sum;
    }


    public static Double amountSpentThisMonth(String type){
        String month = String.valueOf(thisMonth());

        List<Expense> expenses = ExpenseBussinessService.findAll();
        Double sumAmountSpent = 0.0;

        for(Expense ex : expenses){
            if(ex.getType().equals(type) && ex.getMonth().toString().equals(month)){
                sumAmountSpent += ex.getValue();
            }
        }
        return sumAmountSpent;
    }






    // PROXIMO MES




    public static int nextMonth(){
        Date date = new Date();
        GregorianCalendar dataCal = new GregorianCalendar();
        dataCal.setTime(date);

        return dataCal.get(Calendar.MONTH) + 2;
    }


    public static Double sumValuesNextMonth() {
        String month = String.valueOf(nextMonth());

        List<Expense> expenses = ExpenseBussinessService.findAll();

        Double sum = 0.0;

        for (Expense ex : expenses) {
            if(ex.getMonth().toString().equals(month))
                sum += ex.getValue();
        }

        return sum;
    }


    //Proximos meses

    public static Double sumValuesNextsMonths() {
        String nextsMonths = String.valueOf(nextMonth());

        List<Expense> expenses = ExpenseBussinessService.findAll();

        Double sum = 0.0;

        for (Expense ex : expenses) {
            if(ex.getMonth() > Integer.valueOf(nextsMonths))
                sum += ex.getValue();
        }

        return sum;
    }


    /*
    categorias

    public static Integer percenteByCategory(String category){
        Double total = ExpenseBussinessService.sumValues();
        Double totalCategory = ExpenseBussinessService.amountSpent(category);
        Double percentage = (totalCategory / total) * 100;

        Integer setPercentage = 0;

        if(percentage <= 100){
            setPercentage = percentage.intValue();
        }
        else if(percentage > 100){
            setPercentage = 100;
        }

        return setPercentage;
    }
        */

}
