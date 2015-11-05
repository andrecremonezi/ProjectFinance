package com.finance.projeto.projetofinance.controllers.activities.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Card;

import java.text.DecimalFormat;
import java.util.List;

public class CardAdapter extends BaseAdapter{
    private List<Card> cardList;
    private Activity context;

    public CardAdapter(Activity context, List<Card> cardList) {
        this.cardList = cardList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return cardList.size();
    }

    @Override
    public Object getItem(int position) {
        return cardList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("0.00");
        Card card = (Card) getItem(position);

        View cardListView = context.getLayoutInflater().inflate(R.layout.list_item_card, parent, false);

        TextView textViewCardName = (TextView) cardListView.findViewById(R.id.textViewCardName);
        textViewCardName.setText(card.getName().toString());

        TextView textViewCardLimitValue = (TextView) cardListView.findViewById(R.id.textViewCardLimitValue);
        textViewCardLimitValue.setText(df.format(card.getLimitValue()));

        return cardListView;
    }

}
