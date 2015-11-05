package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Card;
import com.finance.projeto.projetofinance.model.services.CardBussinessService;
import com.finance.projeto.projetofinance.util.FormHelper;

public class NewCardActivity extends AppCompatActivity {
    private Card card;
    private EditText editTextCardName;
    private EditText editTextCardLimitValue;
    private Button buttonSave;
    private Toolbar toolbar;
    public static final String PARAM_TASK = "PARAM_TASK";

    public NewCardActivity(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_card_activity);

        initCard();
        bindToolbar();
        bindFields();
        bindButtonSave();
    }

    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_newCard);

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

    private void initCard() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.card = (Card) extras.getParcelable(PARAM_TASK);
        }
        this.card = this.card == null ? new Card() : this.card;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void bindButtonSave() {
        buttonSave = (Button) findViewById(R.id.buttonAddCard);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String requiredMessage = NewCardActivity.this.getString(R.string.msg_field_required);
                if (!FormHelper.validateRequired(requiredMessage, editTextCardName, editTextCardLimitValue)) {
                    bindCard();
                    CardBussinessService.save(card);

                    Toast.makeText(NewCardActivity.this, getString(R.string.msg_card_create_sucess), Toast.LENGTH_LONG).show();
                    NewCardActivity.this.finish();
                }
            }
        });
    }

    public void bindCard(){
        card.setName(editTextCardName.getText().toString());
        card.setLimitValue(Double.parseDouble(editTextCardLimitValue.getText().toString()));
    }

    private void bindFields() {
        editTextCardName = (EditText) findViewById(R.id.textViewNameNewCard);
        editTextCardName.setText(this.card.getName() == null ? "" : this.card.getName().toString());

        editTextCardLimitValue = (EditText)  findViewById(R.id.editTextCardLimitValue);
        editTextCardLimitValue.setText(this.card.getLimitValue() == null ? "" : this.card.getLimitValue().toString());

    }

}


