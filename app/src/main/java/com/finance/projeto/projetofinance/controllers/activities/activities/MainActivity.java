package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Card;
import com.finance.projeto.projetofinance.model.entities.Expense;
import com.finance.projeto.projetofinance.model.services.CardBussinessService;
import com.finance.projeto.projetofinance.model.services.ExpenseBussinessService;
import com.finance.projeto.projetofinance.model.services.ReceipeBussinessService;

import java.text.DecimalFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView currentBalance;
    private TextView bankBalance;
    private TextView walletBalance;
    private TextView textViewLimitValue;
    private TextView textViewCardName;
    private TextView textViewSpentValue;
    private TextView textViewLogCard;
    private TextView textViewExpenseValue;
    private TextView textViewReceipeValue;
    private TextView textViewExpenseCumulative;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindToolbar();
        bindDrawer();
        bindFields();

    }

    private void bindDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_drawer_navigation);
        drawerFragment.setUp(R.id.fragment_drawer_navigation, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
    }

    @Override
    protected void onResume() {
        updateData();
        super.onResume();
    }

    public void bindCard(){
        List<Card> cards = CardBussinessService.findAll();
        Double limitCard = 0.0;

        for(Card card : cards){
            limitCard += card.getLimitValue();
            textViewCardName.setText(card.getName());
        }
        textViewLimitValue.setText(getString(R.string.label_card_total_value) + " " + df.format(limitCard));

        List<Expense> expenses = ExpenseBussinessService.findAll();
        Double spentValue = 0.0;
        for(Expense expense: expenses){
            if(expense.getForm().equals("Cartão"))
                if(expense.getPaid().equals(0))
                    spentValue += expense.getValue();
        }

        textViewSpentValue.setText(getString(R.string.label_amount_spent_card) + " " + df.format(spentValue));

        // SET PORCENTAGEM

        Double percentage = (spentValue / limitCard) * 100;
        Integer setPercentage = 0;

        if(percentage < 100){
            setPercentage = percentage.intValue();
            textViewLogCard.setText("");
        }
        else if(percentage >= 100){
            setPercentage = 100;
            textViewLogCard.setText("Limite do cartão excedido!");
        }

        NumberProgressBar numberProgressBar = (NumberProgressBar) findViewById(R.id.number_progress_bar);
        numberProgressBar.setProgress(setPercentage);
    }

    private void bindFields() {
        currentBalance            = (TextView) findViewById(R.id.textViewCurrentBalance);
        bankBalance               = (TextView) findViewById(R.id.textViewBankBalance);
        walletBalance             = (TextView) findViewById(R.id.textViewWalletBalance);
        textViewLimitValue        = (TextView) findViewById(R.id.textViewLimitValue);
        textViewCardName          = (TextView) findViewById(R.id.textViewNameCard);
        textViewSpentValue        = (TextView) findViewById(R.id.textViewAmountSpent);
        textViewLogCard           = (TextView) findViewById(R.id.textViewLogCard);
        textViewExpenseValue      = (TextView) findViewById(R.id.textViewTotalExpenseValueTotal);
        textViewReceipeValue      = (TextView) findViewById(R.id.textViewTotalReceipeValueTotal);
        textViewExpenseCumulative = (TextView) findViewById(R.id.textViewCumulativeValueNextMonth);
    }

    private void updateData(){

        // CARD RESUMO FINANCEIRO

        Double totalReceipeValue = ReceipeBussinessService.sumValuesThisMonth();
        Double totalExpenseValue = ExpenseBussinessService.sumValuesThisMonth();
        Double setCurrentBalance = totalReceipeValue - totalExpenseValue;
        currentBalance.setText(getString(R.string.label_current_balance) + " " + df.format(setCurrentBalance));

        Double totalReceipeValueBank = ReceipeBussinessService.sumValuesBankThisMonth();
        Double totalExpenseValueBank = ExpenseBussinessService.sumValuesBankThisMonth();
        Double setBankBalance = totalReceipeValueBank - totalExpenseValueBank;
        bankBalance.setText(getString(R.string.label_bank) + " " + df.format(setBankBalance));

        Double totalReceipeValueWallet = ReceipeBussinessService.sumValuesWalletThisMonth();
        Double totalExpenseValueWallet = ExpenseBussinessService.sumValuesWalletThisMonth();
        Double setWalletBalance = totalReceipeValueWallet - totalExpenseValueWallet;
        walletBalance.setText(getString(R.string.label_wallet) + " " + df.format(setWalletBalance));

        Double totalCumulativeNextMonth = ExpenseBussinessService.sumValuesNextMonth();
        textViewExpenseCumulative.setText(getString(R.string.value_cumulative) + " " + df.format(totalCumulativeNextMonth));

        //CARD CARTOES

        bindCard();

        //CARD INFORMAÇÕES

        textViewReceipeValue.setText(getString(R.string.label_total_receipe) + " " + df.format(totalReceipeValue));
        textViewExpenseValue.setText(getString(R.string.label_total_expense) + " " + df.format(totalExpenseValue));
    }


    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_main);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }


}
