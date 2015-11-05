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
import android.widget.TextView;
import android.widget.Toast;

import com.finance.projeto.projetofinance.R;

import com.finance.projeto.projetofinance.controllers.activities.adapters.ReceipeAdapter;
import com.finance.projeto.projetofinance.model.entities.Expense;
import com.finance.projeto.projetofinance.model.entities.Receipe;
import com.finance.projeto.projetofinance.model.services.ExpenseBussinessService;
import com.finance.projeto.projetofinance.model.services.ReceipeBussinessService;
import com.melnykov.fab.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Andrea on 26/09/2015.
 */
public class ReceipeActivity extends AppCompatActivity {
    public static final String DETAIL_RECEIPE = "DETAIL_RECEIPE";
    private ListView listViewReceipe;
    private Receipe  selectedReceipe;
    private FloatingActionButton   buttonNewReceipe;
    private Toolbar toolbar;
    private TextView totalReceipe;
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipe_activity);
        bindToolbar();
        bindReceipeList();
        bindFields();
        bindButtonNewReceipe();
    }

    private void bindFields() {
        totalReceipe = (TextView) findViewById(R.id.textViewTotalReceipe);
    }

    private void updateData() {
        Double totalReceipeValue = ReceipeBussinessService.sumValuesThisMonth();
        totalReceipe.setText(getString(R.string.total) + " " + df.format(totalReceipeValue));
    }
    private void bindToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_receipe);
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

    private void bindButtonNewReceipe() {
        buttonNewReceipe = (FloatingActionButton) findViewById(R.id.buttonNewReceipe);
        buttonNewReceipe.attachToListView(listViewReceipe);
        buttonNewReceipe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent redirectToTaskList = new Intent(ReceipeActivity.this, NewReceipeActivity.class);
                redirectToTaskList.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(redirectToTaskList);
            }
        });
    }

    private void bindReceipeList() {
        listViewReceipe = (ListView) findViewById(R.id.listViewReceipe);
        registerForContextMenu(listViewReceipe);
        listViewReceipe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ReceipeAdapter receipeAdapter = (ReceipeAdapter) listViewReceipe.getAdapter();
                selectedReceipe = (Receipe) receipeAdapter.getItem(position);
                return false;
            }
        });

        listViewReceipe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent redirectToDetail = new Intent(ReceipeActivity.this, ReceipeDetailActivity.class);
                redirectToDetail.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                redirectToDetail.putExtra(DETAIL_RECEIPE, (Receipe) parent.getItemAtPosition(position));
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
        List<Receipe> values = ReceipeBussinessService.findAll();
        listViewReceipe.setAdapter(new ReceipeAdapter(this, values));
        ReceipeAdapter adapter = (ReceipeAdapter) listViewReceipe.getAdapter();
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
        Intent goToNewReceipe = new Intent(ReceipeActivity.this, NewReceipeActivity.class);
        goToNewReceipe.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        goToNewReceipe.putExtra(NewReceipeActivity.PARAM_TASK, selectedReceipe);
        startActivity(goToNewReceipe);
    }

    private void onMenuDeleteClick() {
        new AlertDialog.Builder(ReceipeActivity.this)
                .setTitle(R.string.lbl_confirm_delet_receipe)
                .setMessage(R.string.msg_confirm_delete_receipe)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReceipeBussinessService.delete(selectedReceipe);
                        selectedReceipe = null;
                        String message = getString(R.string.msg_delete_sucessfull);
                        onUpdateList();
                        updateData();
                        Toast.makeText(ReceipeActivity.this, message, Toast.LENGTH_LONG).show();

                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }

}
