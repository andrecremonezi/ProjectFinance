package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Card;
import com.finance.projeto.projetofinance.model.persistence.CardRepository;
import com.finance.projeto.projetofinance.model.services.CardBussinessService;

import java.text.DecimalFormat;

public class CardDetailActivity extends AppCompatActivity {
    private TextView textViewDetailName;
    private TextView textViewDetailValue;
    private Card card;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        //binds
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

        card = CardRepository.getById(card.getId());
        bindData();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.card = extras.getParcelable(CardActivity.DETAIL_CARD);
        }
        this.card = this.card == null ? new Card() : this.card;
    }

    public void bindToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        textViewDetailName.setText(card.getName());
        textViewDetailValue.setText(df.format(card.getLimitValue()));
    }

    private void bindFields() {
        textViewDetailName     = (TextView) findViewById(R.id.textViewDetailNameCard);
        textViewDetailValue    = (TextView) findViewById(R.id.textViewDetailValueCard);
    }

    public void bindButtonDelete(){
        Button buttonDeletar = (Button) findViewById(R.id.buttonDeleteItemCard);
        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    private void confirmDelete() {
        new AlertDialog.Builder(CardDetailActivity.this)
                .setTitle(R.string.lbl_confirm_delet_card)
                .setMessage(R.string.msg_confirm_delete_card)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CardBussinessService.delete(card);
                        card = null;
                        String message = getString(R.string.msg_delete_sucessfull);
                        Toast.makeText(CardDetailActivity.this, message, Toast.LENGTH_LONG).show();
                        CardDetailActivity.this.finish();
                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }

    public void bindButtonEdit(){
        Button buttonEditar = (Button) findViewById(R.id.buttonEditItemCard);
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNewCard = new Intent(CardDetailActivity.this, NewCardActivity.class);
                goToNewCard.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                goToNewCard.putExtra(NewCardActivity.PARAM_TASK, card);
                startActivity(goToNewCard);
            }
        });
    }

}
