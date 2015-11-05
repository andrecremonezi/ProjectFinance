package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Expense;
import com.finance.projeto.projetofinance.model.services.ExpenseBussinessService;
import com.finance.projeto.projetofinance.util.FormHelper;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewExpenseActivity extends AppCompatActivity{
    private String[] forms = new String[] {"Carteira","Cartão","Banco"};
    private String[] typeCategory = new String[] {"Academia","Alimentação","Contas","Estudos","Ferramentas","Video Game","Investimento","Lazer","Moradia","Roupas","Saúde","Telefone","Transporte","Viagem","Outros"};
    private String[] months = new String[] {"Apenas este mês","Apenas próximo mês","Nos próximos 3 meses","Nos próximos 6 meses","Nos próximos 12 meses"};
    private EditText editTextDescription;
    private EditText editTextValue;
    private Spinner  spinnerType;
    private Spinner spinnerForm;
    private Spinner spinnerMonth;
    private Expense expense;
    private Button buttonSave;
    private Toolbar toolbar;
    private RadioGroup radioGroup;
    public static final String PARAM_TASK = "PARAM_TASK";

    public NewExpenseActivity(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_expense_activity);

        initExpense();
        bindToolbar();
        bindSpinnerTypeCategory();
        bindSpinnerForm();
        bindSpinnerMonth();
        bindFields();
        bindButtonSave();
    }

    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_newExpense);

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

    private void initExpense() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.expense = extras.getParcelable(PARAM_TASK);
        }

        this.expense = this.expense == null ? new Expense() : this.expense;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void bindButtonSave() {
        buttonSave = (Button) findViewById(R.id.buttonAddExpense);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requiredMessage = NewExpenseActivity.this.getString(R.string.msg_field_required);
                if (!FormHelper.validateRequired(requiredMessage, editTextDescription, editTextValue)) {

                    //1 mes
                    if(spinnerMonth.getSelectedItem().toString().equals("Apenas este mês")) {
                        for(int i = 1; i <= 1; i++) {
                            bindExpense(i);
                            ExpenseBussinessService.save(expense);
                        }
                    }

                    //2 meses
                    if(spinnerMonth.getSelectedItem().toString().equals("Apenas próximo mês")) {
                        for(int i = 2; i <= 2; i++) {
                            bindExpense(i);
                            ExpenseBussinessService.save(expense);
                        }
                    }

                    //3 meses
                    if(spinnerMonth.getSelectedItem().toString().equals("Nos próximos 3 meses")) {
                        for(int i = 1; i <= 3; i++) {
                            bindExpense(i);
                            ExpenseBussinessService.save(expense);
                        }
                    }

                    //6 meses
                    if(spinnerMonth.getSelectedItem().toString().equals("Nos próximos 6 meses")) {
                        for(int i = 1; i <= 6; i++) {
                            bindExpense(i);
                            ExpenseBussinessService.save(expense);
                        }
                    }

                    //12 meses
                    if(spinnerMonth.getSelectedItem().toString().equals("Nos próximos 12 meses")) {
                        for(int i = 1; i <= 12
                                ; i++) {
                            bindExpense(i);
                            ExpenseBussinessService.save(expense);
                        }
                    }


                    Toast.makeText(NewExpenseActivity.this, getString(R.string.msg_expense_create_sucess), Toast.LENGTH_LONG).show();
                    NewExpenseActivity.this.finish();
                }
            }
        });
    }

    private void bindExpense(Integer month) {
        expense.setDescription(editTextDescription.getText().toString());
        expense.setValue(Double.parseDouble(editTextValue.getText().toString()));
        expense.setType(spinnerType.getSelectedItem().toString());
        expense.setForm(spinnerForm.getSelectedItem().toString());

        Date date = new Date();
        GregorianCalendar dataCal = new GregorianCalendar();
        dataCal.setTime(date);

        expense.setMonth(dataCal.get(Calendar.MONTH) + month);


        int op = radioGroup.getCheckedRadioButtonId();

        if(op == R.id.rY)
            expense.setPaid(1);
        else
            expense.setPaid(0);
    }

    private void bindFields() {
        editTextDescription = (EditText) findViewById(R.id.editTextNewDescriptionExpense);
        editTextDescription.setText(this.expense.getDescription() == null ? "" : this.expense.getDescription().toString());

        editTextValue = (EditText) findViewById(R.id.editTextNewValueExpense);
        editTextValue.setText(this.expense.getValue() == null ? "" : this.expense.getValue().toString());

        radioGroup = (RadioGroup) findViewById(R.id.rgopcoes);
    }

    public void bindSpinnerMonth(){
        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, months);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth = (Spinner)  findViewById(R.id.spinnerNewMonthExpense);
        spinnerMonth.setAdapter(adapterMonth);

        if(expense.getMonth() != null){
            spinnerMonth.setEnabled(false);
        }
    }

    public void bindSpinnerForm(){
        ArrayAdapter<String> adapterForm = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, forms);
        adapterForm.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerForm = (Spinner)  findViewById(R.id.spinnerNewFormExpense);
        spinnerForm.setAdapter(adapterForm);

        int position = adapterForm.getPosition(expense.getForm());
        spinnerForm.setSelection(position);
    }

    public void bindSpinnerTypeCategory(){
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType = (Spinner) findViewById(R.id.spinnerNewTypeExpense);
        spinnerType.setAdapter(adapterCategory);

        int position = adapterCategory.getPosition(expense.getType());
        spinnerType.setSelection(position);
    }

}
