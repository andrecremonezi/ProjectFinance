package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.controllers.activities.adapters.ExpenseAdapter;
import com.finance.projeto.projetofinance.model.entities.Expense;
import com.finance.projeto.projetofinance.model.services.ExpenseBussinessService;
import com.melnykov.fab.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {
    public static final String DETAIL_EXPENSE = "DETAIL_EXPENSE";
    private ListView listViewExpense;
    private Expense selectedExpense;
    private TextView valueTotal;
    private TextView valueNextMonth;
    private TextView valueNextsMonths;
    private FloatingActionButton buttonNewExpense;
    private Toolbar toolbar;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_activity);

        //binds
        bindToolbar();
        bindExpenseList();
        bindFields();
        bindButtonNewExpense();
    }

    private void bindFields() {
        valueTotal =(TextView)findViewById(R.id.textViewListExpenseValueTotal);
        valueNextMonth = (TextView) findViewById(R.id.textViewTotalNextMonth);
        valueNextsMonths = (TextView) findViewById(R.id.textViewTotalNextsMonths);
    }

    private void updateData() {
        Double totalExpenseValue = ExpenseBussinessService.sumValuesThisMonth();
        valueTotal.setText(getString(R.string.total_this_month) + " " + df.format(totalExpenseValue));

        Double totalNextMonthExpenseValue = ExpenseBussinessService.sumValuesNextMonth();
        valueNextMonth.setText(getString(R.string.total_next_month) + " " + df.format(totalNextMonthExpenseValue));

        Double totalNextsMonthsExpenseValue = ExpenseBussinessService.sumValuesNextsMonths();
        valueNextsMonths.setText(getString(R.string.total_nexts_months) + " " + df.format(totalNextsMonthsExpenseValue));
    }

    public void bindToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_expense);

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

    private void bindButtonNewExpense() {
        buttonNewExpense = (FloatingActionButton ) findViewById(R.id.buttonNewExpense);
        buttonNewExpense.attachToListView(listViewExpense);
        
        buttonNewExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirectToTaskList = new Intent(ExpenseActivity.this, NewExpenseActivity.class);
                redirectToTaskList.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(redirectToTaskList);
            }
        });
    }

    private void bindExpenseList() {
        listViewExpense = (ListView) findViewById(R.id.listViewExpense);
        registerForContextMenu(listViewExpense);

        listViewExpense.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseAdapter adapter = (ExpenseAdapter) listViewExpense.getAdapter();
                selectedExpense = (Expense) adapter.getItem(position);
                return false;
            }
        });

        listViewExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent redirectToDetail = new Intent(ExpenseActivity.this, ItemDetailActivity.class);
                redirectToDetail.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                redirectToDetail.putExtra(DETAIL_EXPENSE, (Expense) parent.getItemAtPosition(position));
                startActivity(redirectToDetail);
            }
        });
    }

    @Override
    protected void onResume() {
        onUpdateList();
        updateData();

        super.onResume();
    }

    private void onUpdateList() {
        List<Expense> values = ExpenseBussinessService.findAll();
        listViewExpense.setAdapter(new ExpenseAdapter(this, values));

        ExpenseAdapter adapter = (ExpenseAdapter) listViewExpense.getAdapter();
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
            Intent goToNewExpense = new Intent(ExpenseActivity.this, NewExpenseActivity.class);
            goToNewExpense.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            goToNewExpense.putExtra(NewExpenseActivity.PARAM_TASK, selectedExpense);
            startActivity(goToNewExpense);
        }

    private void onMenuDeleteClick() {
        new AlertDialog.Builder(ExpenseActivity.this)
                .setTitle(R.string.lbl_confirm_delet_expense)
                .setMessage(R.string.msg_confirm_delete_expense)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ExpenseBussinessService.delete(selectedExpense);
                        selectedExpense = null;
                        String message = getString(R.string.msg_delete_sucessfull);
                        onUpdateList();
                        updateData();
                        Toast.makeText(ExpenseActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }

}
