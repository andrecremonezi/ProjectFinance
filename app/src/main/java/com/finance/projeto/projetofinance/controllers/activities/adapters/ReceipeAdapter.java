package com.finance.projeto.projetofinance.controllers.activities.adapters;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Receipe;

import java.text.DecimalFormat;
import java.util.List;

public class ReceipeAdapter extends BaseAdapter {
    private List<Receipe> receipeList;
    private Activity context;

    public ReceipeAdapter(Activity context, List<Receipe> receipeList) {
        this.receipeList = receipeList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return receipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return receipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("0.00");

        Receipe receipe = (Receipe) getItem(position);

        View receipeListView = context.getLayoutInflater().inflate(R.layout.list_item_receipe, parent,false);

        TextView textViewDescription = (TextView) receipeListView.findViewById(R.id.textViewReceipeDescription);
        textViewDescription.setText(receipe.getDescription());

        TextView textViewCategory = (TextView) receipeListView.findViewById(R.id.textViewReceipeCategory);
        textViewCategory.setText(receipe.getType());

        TextView textViewValue = (TextView) receipeListView.findViewById(R.id.textViewReceipeValue);
        textViewValue.setText("R$ " + df.format(receipe.getValue()));

        ImageView textViewLabelLetter = (ImageView) receipeListView.findViewById(R.id.textViewLabel);
        //String first = receipe.getType().toString().substring(0,1);
        //textViewLabelLetter.setText(first);

        TextView textViewForm = (TextView) receipeListView.findViewById(R.id.textViewReceipeForm);
        textViewForm.setText(receipe.getWalletOrBank().toString());

        //int hexColor;

        switch (receipe.getType().toString()) {
            case "Salário":
                textViewLabelLetter.setImageResource(R.drawable.salario);
                break;
            case "Aluguel":
                textViewLabelLetter.setImageResource(R.drawable.moradia);
                break;
            case "Investimentos":
                textViewLabelLetter.setImageResource(R.drawable.investimento);
                break;
            case "Outros":
                textViewLabelLetter.setImageResource(R.drawable.outros);
                break;
        }
            return receipeListView;
    }

    public void setDataValues(List<Receipe> values) {
        receipeList.clear();
        receipeList.addAll(values);
    }
}
