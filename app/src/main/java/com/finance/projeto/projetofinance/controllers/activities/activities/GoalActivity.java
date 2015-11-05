package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.controllers.activities.adapters.GoalAdapter;
import com.finance.projeto.projetofinance.model.entities.Goal;
import com.finance.projeto.projetofinance.model.services.GoalBussinessService;
import com.melnykov.fab.FloatingActionButton;
import java.util.List;

public class GoalActivity extends AppCompatActivity {
    public static final String DETAIL_GOAL = "DETAIL_GOAL";
    private ListView listViewGoal;
    private Goal selectedGoal;
    private FloatingActionButton buttonNewGoal;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_activity);

        bindToolbar();
        bindGoalList();
        bindButtonNewGoal();
    }

    public void bindToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_goal);

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

    private void bindButtonNewGoal() {
        buttonNewGoal = (FloatingActionButton) findViewById(R.id.buttonNewGoal);
        buttonNewGoal.attachToListView(listViewGoal);

        buttonNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToTaskList = new Intent(GoalActivity.this, NewGoalActivity.class);
                redirectToTaskList.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(redirectToTaskList);
            }
        });
    }

    private void bindGoalList() {
        listViewGoal = (ListView) findViewById(R.id.listViewGoal);
        registerForContextMenu(listViewGoal);

        listViewGoal.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                GoalAdapter adapter = (GoalAdapter) listViewGoal.getAdapter();
                selectedGoal = (Goal) adapter.getItem(position);
                return false;
            }
        });

        listViewGoal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent redirectToDetail = new Intent(GoalActivity.this, GoalDetailActivity.class);
                redirectToDetail.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                redirectToDetail.putExtra(DETAIL_GOAL, (Goal) parent.getItemAtPosition(position));
                startActivity(redirectToDetail);
            }
        });
    }

    @Override
    protected void onResume() {
        onUpdateList();

        super.onResume();
    }

    private void onUpdateList() {
        List<Goal> values = GoalBussinessService.findAll();
        listViewGoal.setAdapter(new GoalAdapter(this, values));

        GoalAdapter adapter = (GoalAdapter) listViewGoal.getAdapter();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context_list, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_excluir:
                onMenuDeleteClick();
                break;

            case R.id.menu_editar:
                onMenuUpdateClick();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void onMenuUpdateClick() {
        Intent goToNewGoal = new Intent(GoalActivity.this, NewGoalActivity.class);
        goToNewGoal.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        goToNewGoal.putExtra(NewExpenseActivity.PARAM_TASK, selectedGoal);
        startActivity(goToNewGoal);
    }

    private void onMenuDeleteClick() {
        new AlertDialog.Builder(GoalActivity.this)
                .setTitle(R.string.lbl_confirm_delet_expense)
                .setMessage(R.string.msg_confirm_delete_expense)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GoalBussinessService.delete(selectedGoal);
                        selectedGoal = null;
                        String message = getString(R.string.msg_delete_sucessfull);
                        onUpdateList();
                        Toast.makeText(GoalActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }

}
