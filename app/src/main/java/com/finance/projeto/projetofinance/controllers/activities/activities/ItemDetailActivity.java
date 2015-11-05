package com.finance.projeto.projetofinance.controllers.activities.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Expense;
import com.finance.projeto.projetofinance.model.persistence.ExpenseRepository;
import com.finance.projeto.projetofinance.model.services.ExpenseBussinessService;
import java.text.DecimalFormat;

public class ItemDetailActivity extends AppCompatActivity {
    private TextView textViewDetailName;
    private TextView textViewDetailCategory;
    private TextView textViewDetailValue;
    private TextView textViewDetailForm;
    private TextView textViewDetailMonth;
    private TextView textViewPaid;
    private Expense  expense;
    private Toolbar  toolbar;
    private Button   buttonEditar;
    private Button   buttonDeletar;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        init();
        bindToolbar();
        bindFields();
        bindButtonEdit();
        bindButtonDelete();
        bindData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        expense = ExpenseRepository.getById(expense.getId());
        bindData();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.expense = extras.getParcelable(ExpenseActivity.DETAIL_EXPENSE);
        }
        this.expense = this.expense == null ? new Expense() : this.expense;
    }

    public void bindToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_item);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void bindData() {
        textViewDetailName.setText(expense.getDescription());
        textViewDetailCategory.setText(expense.getType());
        textViewDetailValue.setText(df.format(expense.getValue()));
        textViewDetailForm.setText(expense.getForm());

        if(expense.getMonth().toString().equals("1"))
            textViewDetailMonth.setText("Janeiro");
        if(expense.getMonth().toString().equals("2"))
            textViewDetailMonth.setText("Fevereiro");
        if(expense.getMonth().toString().equals("3"))
            textViewDetailMonth.setText("Março");
        if(expense.getMonth().toString().equals("4"))
            textViewDetailMonth.setText("Abril");
        if(expense.getMonth().toString().equals("5"))
            textViewDetailMonth.setText("Maio");
        if(expense.getMonth().toString().equals("6"))
            textViewDetailMonth.setText("Junho");
        if(expense.getMonth().toString().equals("7"))
            textViewDetailMonth.setText("Julho");
        if(expense.getMonth().toString().equals("8"))
            textViewDetailMonth.setText("Agosto");
        if(expense.getMonth().toString().equals("9"))
            textViewDetailMonth.setText("Setembro");
        if(expense.getMonth().toString().equals("10"))
            textViewDetailMonth.setText("Outubro");
        if(expense.getMonth().toString().equals("11"))
            textViewDetailMonth.setText("Novembro");
        if(expense.getMonth().toString().equals("12"))
            textViewDetailMonth.setText("Dezembro");

        int colorPaid = 0;
        if(expense.getPaid().toString().equals("1")) {
            colorPaid = android.graphics.Color.parseColor("#4CAF50");
            textViewPaid.setText("Pago");
            textViewPaid.setTextColor(colorPaid);
        }else {
            colorPaid = android.graphics.Color.parseColor("#F44336");
            textViewPaid.setText("Não pago");
            textViewPaid.setTextColor(colorPaid);

        }
    }

    private void bindFields() {
        textViewDetailName     = (TextView) findViewById(R.id.textViewDetailName);
        textViewDetailCategory = (TextView) findViewById(R.id.textViewDetailCategory);
        textViewDetailValue    = (TextView) findViewById(R.id.textViewDetailValue);
        textViewDetailForm     = (TextView) findViewById(R.id.textViewDetailForm);
        textViewDetailMonth    = (TextView) findViewById(R.id.textViewDetailMonth);
        textViewPaid           = (TextView) findViewById(R.id.textViewPaid);
    }

    public void bindButtonDelete(){
        buttonDeletar = (Button) findViewById(R.id.buttonDeleteItem);
        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    private void confirmDelete() {
        new AlertDialog.Builder(ItemDetailActivity.this)
                .setTitle(R.string.lbl_confirm_delet_expense)
                .setMessage(R.string.msg_confirm_delete_expense)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExpenseBussinessService.delete(expense);
                        expense = null;
                        String message = getString(R.string.msg_delete_sucessfull);
                        Toast.makeText(ItemDetailActivity.this, message, Toast.LENGTH_LONG).show();
                        ItemDetailActivity.this.finish();
                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
     }

    public void bindButtonEdit(){
        buttonEditar = (Button) findViewById(R.id.buttonEditItem);

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNewExpense = new Intent(ItemDetailActivity.this, NewExpenseActivity.class);
                goToNewExpense.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                goToNewExpense.putExtra(NewExpenseActivity.PARAM_TASK, expense);
                startActivity(goToNewExpense);
            }
        });
    }

}
