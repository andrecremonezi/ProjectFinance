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
import com.finance.projeto.projetofinance.model.entities.Goal;
import com.finance.projeto.projetofinance.model.persistence.GoalRepository;
import com.finance.projeto.projetofinance.model.services.GoalBussinessService;

import java.text.DecimalFormat;

public class GoalDetailActivity extends AppCompatActivity{
    private TextView textViewDetailName;
    private TextView textViewDetailValue;
    private Goal goal;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_detail);

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

        goal = GoalRepository.getById(goal.getId());
        bindData();
    }

    private void init() {
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            this.goal = extras.getParcelable(GoalActivity.DETAIL_GOAL);
        }
        this.goal = this.goal == null ? new Goal() : this.goal;
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
        textViewDetailName.setText(goal.getType());
        textViewDetailValue.setText(df.format(goal.getValue()));
    }

    private void bindFields() {
        textViewDetailName     = (TextView) findViewById(R.id.textViewDetailNameGoal);
        textViewDetailValue    = (TextView) findViewById(R.id.textViewDetailValueGoal);
    }

    public void bindButtonDelete(){
        Button buttonDeletar = (Button) findViewById(R.id.buttonDeleteItemGoal);
        buttonDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    private void confirmDelete() {
        new AlertDialog.Builder(GoalDetailActivity.this)
                .setTitle(R.string.lbl_confirm_delet_goal)
                .setMessage(R.string.msg_confirm_delete_goal)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GoalBussinessService.delete(goal);
                        goal = null;
                        String message = getString(R.string.msg_delete_sucessfull);
                        Toast.makeText(GoalDetailActivity.this, message, Toast.LENGTH_LONG).show();
                        GoalDetailActivity.this.finish();
                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }

    public void bindButtonEdit(){
        Button buttonEditar = (Button) findViewById(R.id.buttonEditItemGoal);

        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToNewGoal = new Intent(GoalDetailActivity.this, NewGoalActivity.class);
                goToNewGoal.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                goToNewGoal.putExtra(NewGoalActivity.PARAM_TASK, goal);
                startActivity(goToNewGoal);
            }
        });
    }

}
