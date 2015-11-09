package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Goal;
import com.finance.projeto.projetofinance.model.services.GoalBussinessService;
import com.finance.projeto.projetofinance.util.FormHelper;

public class NewGoalActivity extends AppCompatActivity {
    private String[] typeCategory = new String[] {"Academia","Alimentação","Contas","Estudos","Ferramentas","Video Game","Investimento","Lazer","Moradia","Roupas","Saúde","Telefone","Transporte","Viagem","Outros"};
    private Spinner spinnerType;
    private Goal goal;
    private EditText editTextValue;
    private Button buttonSave;
    private Toolbar toolbar;
    public static final String PARAM_TASK = "PARAM_TASK";

    public NewGoalActivity(){
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_goal_activity);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        initGoal();
        bindToolbar();
        bindSpinnerTypeCategory();
        bindFields();
        bindButtonSave();
    }

    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_newGoal);
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

    private void initGoal() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.goal = (Goal) extras.getParcelable(PARAM_TASK);
        }
        this.goal = this.goal == null ? new Goal() : this.goal;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void bindButtonSave() {
        buttonSave = (Button) findViewById(R.id.buttonAddGoal);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String requiredMessage = NewGoalActivity.this.getString(R.string.msg_field_required);

                if (!FormHelper.validateRequired(requiredMessage, editTextValue)) {
                    bindGoal();
                    GoalBussinessService.save(goal);
                    Toast.makeText(NewGoalActivity.this, getString(R.string.msg_goal_create_sucess), Toast.LENGTH_LONG).show();
                    NewGoalActivity.this.finish();
                }
            }
        });
    }

    public void bindGoal(){
        goal.setValue(Double.parseDouble(editTextValue.getText().toString()));
        goal.setType(spinnerType.getSelectedItem().toString());
    }

    private void bindFields() {
        editTextValue = (EditText) findViewById(R.id.editTextValueGoal);
        editTextValue.setText(this.goal.getValue() == null ? "" : this.goal.getValue().toString());
    }

    public void bindSpinnerTypeCategory(){
        spinnerType   = (Spinner)  findViewById(R.id.spinnerCategoryGoal);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typeCategory);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType = (Spinner) findViewById(R.id.spinnerCategoryGoal);
        spinnerType.setAdapter(adapterCategory);

        int position = adapterCategory.getPosition(goal.getType());
        spinnerType.setSelection(position);
    }

}
