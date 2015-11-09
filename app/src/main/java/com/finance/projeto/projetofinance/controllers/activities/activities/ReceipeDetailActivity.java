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
import com.finance.projeto.projetofinance.model.entities.Receipe;
import com.finance.projeto.projetofinance.model.persistence.ReceipeRepository;
import com.finance.projeto.projetofinance.model.services.ReceipeBussinessService;

import java.text.DecimalFormat;

public class ReceipeDetailActivity extends AppCompatActivity{
    private TextView textViewDetailName;
    private TextView textViewDetailCategory;
    private TextView textViewDetailValue;
    private TextView textViewDetailForm;
    private TextView textViewDetailMonth;
    private Receipe receipe;
    private Toolbar toolbar;
    private Button buttonEditar;
    private Button   buttonDeletar;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipe_item_detail);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

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
        receipe = ReceipeRepository.getById(receipe.getId());
        bindData();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.receipe = extras.getParcelable(ReceipeActivity.DETAIL_RECEIPE);
        }
        this.receipe = this.receipe == null ? new Receipe() : this.receipe;
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
        textViewDetailName.setText(receipe.getDescription());
        textViewDetailCategory.setText(receipe.getType());
        textViewDetailValue.setText(df.format(receipe.getValue()));
        textViewDetailForm.setText(receipe.getWalletOrBank());

        if(receipe.getMonth().toString().equals("1"))
            textViewDetailMonth.setText("Janeiro");
        if(receipe.getMonth().toString().equals("2"))
            textViewDetailMonth.setText("Fevereiro");
        if(receipe.getMonth().toString().equals("3"))
            textViewDetailMonth.setText("Março");
        if(receipe.getMonth().toString().equals("4"))
            textViewDetailMonth.setText("Abril");
        if(receipe.getMonth().toString().equals("5"))
            textViewDetailMonth.setText("Maio");
        if(receipe.getMonth().toString().equals("6"))
            textViewDetailMonth.setText("Junho");
        if(receipe.getMonth().toString().equals("7"))
            textViewDetailMonth.setText("Julho");
        if(receipe.getMonth().toString().equals("8"))
            textViewDetailMonth.setText("Agosto");
        if(receipe.getMonth().toString().equals("9"))
            textViewDetailMonth.setText("Setembro");
        if(receipe.getMonth().toString().equals("10"))
            textViewDetailMonth.setText("Outubro");
        if(receipe.getMonth().toString().equals("11"))
            textViewDetailMonth.setText("Novembro");
        if(receipe.getMonth().toString().equals("12"))
            textViewDetailMonth.setText("Dezembro");
    }

    private void bindFields() {
        textViewDetailName     = (TextView) findViewById(R.id.textViewDetailNameReceipe);
        textViewDetailCategory = (TextView) findViewById(R.id.textViewDetailCategoryReceipe);
        textViewDetailValue    = (TextView) findViewById(R.id.textViewDetailValueReceipe);
        textViewDetailForm     = (TextView) findViewById(R.id.textViewDetailFormReceipe);
        textViewDetailMonth    = (TextView) findViewById(R.id.textViewDetailMonthReceipe);
    }

    public void bindButtonDelete(){
        buttonDeletar = (Button) findViewById(R.id.buttonDeleteItemReceipe);
        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    private void confirmDelete() {
        new AlertDialog.Builder(ReceipeDetailActivity.this)
                .setTitle(R.string.lbl_confirm_delet_receipe)
                .setMessage(R.string.msg_confirm_delete_receipe)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReceipeBussinessService.delete(receipe);
                        receipe = null;
                        String message = getString(R.string.msg_delete_sucessfull);
                        Toast.makeText(ReceipeDetailActivity.this, message, Toast.LENGTH_LONG).show();
                        ReceipeDetailActivity.this.finish();
                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }

    public void bindButtonEdit(){
        buttonEditar = (Button) findViewById(R.id.buttonEditItemReceipe);
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNewReceipe = new Intent(ReceipeDetailActivity.this, NewReceipeActivity.class);
                goToNewReceipe.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                goToNewReceipe.putExtra(NewReceipeActivity.PARAM_TASK, receipe);
                startActivity(goToNewReceipe);
            }
        });
    }

}
