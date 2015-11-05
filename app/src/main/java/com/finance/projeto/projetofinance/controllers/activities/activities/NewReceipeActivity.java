package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Receipe;
import com.finance.projeto.projetofinance.model.services.ReceipeBussinessService;
import com.finance.projeto.projetofinance.util.FormHelper;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewReceipeActivity extends AppCompatActivity {
    private String[] walletOrBank = new String[] {"Carteira","Banco"};
    private String[] typeCategory = new String[] {"Salário","Aluguel","Investimentos","Outros"};
    private EditText editTextDescription;
    private EditText editTextValue;
    private Spinner  spinnerType;
    private Spinner  spinnerWallatOrBank;
    private Receipe  receipe;
    private Button buttonSave;
    private Toolbar toolbar;
    public static final String PARAM_TASK = "PARAM_TASK";

    public NewReceipeActivity(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_receipe_activity);
        initReceipe();
        bindToolbar();
        bindSpinnerTypeCategory();
        bindSpinnerNewWalletOrBank();
        bindFields();
        bindButtonSave();
    }

    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_newReceipe);
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

    private void initReceipe() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.receipe = (Receipe) extras.getParcelable(PARAM_TASK);
        }

        this.receipe = this.receipe == null ? new Receipe() : this.receipe;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void bindButtonSave() {
        buttonSave = (Button) findViewById(R.id.buttonAddReceipe);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String requiredMessage = NewReceipeActivity.this.getString(R.string.msg_field_required);

                if (!FormHelper.validateRequired(requiredMessage, editTextDescription, editTextValue)) {
                    bindRececeipe();
                    ReceipeBussinessService.save(receipe);
                    Toast.makeText(NewReceipeActivity.this, getString(R.string.msg_receipe_create_sucess), Toast.LENGTH_LONG).show();
                    NewReceipeActivity.this.finish();
                }
            }
        });
    }

    private void bindRececeipe() {
        receipe.setDescription(editTextDescription.getText().toString());
        receipe.setValue(Double.parseDouble(editTextValue.getText().toString()));
        receipe.setWalletOrBank(spinnerWallatOrBank.getSelectedItem().toString());
        receipe.setType(spinnerType.getSelectedItem().toString());

        Date date = new Date();
        GregorianCalendar dataCal = new GregorianCalendar();
        dataCal.setTime(date);

        receipe.setMonth(dataCal.get(Calendar.MONTH) + 1);
    }

    private void bindFields() {
        editTextDescription = (EditText) findViewById(R.id.editTextNewDescriptionReceipe);
        editTextDescription.setText(this.receipe.getDescription() == null ? "" : this.receipe.getDescription().toString());

        editTextValue       = (EditText) findViewById(R.id.editTextNewValueReceipe);
        editTextValue.setText(this.receipe.getValue() == null ? "" : this.receipe.getValue().toString());
    }

    public void bindSpinnerNewWalletOrBank(){
        ArrayAdapter<String> adapterWalletOrbank = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, walletOrBank);
        adapterWalletOrbank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWallatOrBank = (Spinner)  findViewById(R.id.spinnerNewWalletOrBank);
        spinnerWallatOrBank.setAdapter(adapterWalletOrbank);

        int position = adapterWalletOrbank.getPosition(receipe.getWalletOrBank());
        spinnerWallatOrBank.setSelection(position);
    }

    public void bindSpinnerTypeCategory(){
        spinnerType = (Spinner) findViewById(R.id.spinnerNewTypeReceipe);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType = (Spinner) findViewById(R.id.spinnerNewTypeReceipe);
        spinnerType.setAdapter(adapterCategory);

        int position = adapterCategory.getPosition(receipe.getType());
        spinnerType.setSelection(position);
    }

}
