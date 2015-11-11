package com.finance.projeto.projetofinance.controllers.activities.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.controllers.activities.adapters.CardAdapter;
import com.finance.projeto.projetofinance.model.entities.Card;
import com.finance.projeto.projetofinance.model.services.CardBussinessService;
import com.melnykov.fab.FloatingActionButton;
import java.util.List;

public class CardActivity extends AppCompatActivity {
    public static final String DETAIL_CARD = "DETAIL_CARD";
    private ListView listViewCard;
    private Card selectedCard;
    private FloatingActionButton buttonNewCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        //binds
        bindToolbar();
        bindCardList();
        bindButtonNewCard();
    }

    public void bindToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.label_card);

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

    private void bindButtonNewCard() {
        buttonNewCard = (FloatingActionButton) findViewById(R.id.buttonNewCard);
        buttonNewCard.attachToListView(listViewCard);

        buttonNewCard.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent redirectToTaskList = new Intent(CardActivity.this, NewCardActivity.class);
                redirectToTaskList.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(redirectToTaskList);
            }
        });
    }

    private void bindCardList() {
        listViewCard = (ListView) findViewById(R.id.listViewCard);
        registerForContextMenu(listViewCard);

        listViewCard.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CardAdapter adapter = (CardAdapter) listViewCard.getAdapter();
                selectedCard = (Card) adapter.getItem(position);
                return false;
            }
        });

        listViewCard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent redirectToDetail = new Intent(CardActivity.this, CardDetailActivity.class);
                redirectToDetail.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                redirectToDetail.putExtra(DETAIL_CARD, (Card) parent.getItemAtPosition(position));
                startActivity(redirectToDetail);
            }
        });
    }

    @Override
    protected void onResume() {
        onUpdateList();
        super.onResume();

        if(listViewCard.getAdapter().getCount() > 0)
            buttonNewCard.setEnabled(false);
    }

    private void onUpdateList() {
        List<Card> values = CardBussinessService.findAll();
        listViewCard.setAdapter(new CardAdapter(this, values));

        CardAdapter adapter = (CardAdapter) listViewCard.getAdapter();
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
        Intent goToNewCard = new Intent(CardActivity.this, NewCardActivity.class);
        goToNewCard.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        goToNewCard.putExtra(NewExpenseActivity.PARAM_TASK, selectedCard);
        startActivity(goToNewCard);
    }

    private void onMenuDeleteClick() {
        new AlertDialog.Builder(CardActivity.this)
                .setTitle(R.string.lbl_confirm_delet_expense)
                .setMessage(R.string.msg_confirm_delete_expense)
                .setPositiveButton(R.string.lbl_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CardBussinessService.delete(selectedCard);
                        selectedCard = null;
                        String message = getString(R.string.msg_delete_sucessfull);
                        onUpdateList();
                        Toast.makeText(CardActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton(R.string.lbl_no, null)
                .create()
                .show();
    }
}
