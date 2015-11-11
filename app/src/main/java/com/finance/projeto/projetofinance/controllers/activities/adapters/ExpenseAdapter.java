package com.finance.projeto.projetofinance.controllers.activities.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Expense;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ExpenseAdapter extends BaseAdapter {
    private List<Expense> expenseList;
    private Activity context;
    private static final String COLOR_AFTER_NEXT_MONTH = "#64B5F6";
    private final static String COLOR_NEXT_MONTH       = "#E57373";
    private final static String COLOR_THIS_MONTH       = "#009688";


    public ExpenseAdapter(Activity context, List<Expense> expenseList) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return expenseList.size();
    }

    @Override
    public Object getItem(int position) {
        return expenseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DecimalFormat df = new DecimalFormat("0.00");

        Expense expense = (Expense) getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.list_item_expense, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.description = (TextView) convertView.findViewById(R.id.textViewExpenseDescription);
            viewHolder.category = (TextView) convertView.findViewById(R.id.textViewExpenseCategory);
            viewHolder.value = (TextView) convertView.findViewById(R.id.textViewExpenseValue);
            viewHolder.form = (TextView) convertView.findViewById(R.id.textViewForm);
            viewHolder.image = (ImageView) convertView.findViewById(R.id.textViewLabel);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.category.setText(expense.getType());
        viewHolder.form.setText(expense.getForm());
        viewHolder.value.setText("R$ " + df.format(expense.getValue()));
        viewHolder.description.setText(expense.getDescription());

        //View expenseListView = context.getLayoutInflater().inflate(R.layout.list_item_expense, parent, false);

        //TextView textViewDescription = (TextView) expenseListView.findViewById(R.id.textViewExpenseDescription);
        //textViewDescription.setText(expense.getDescription());

        //TextView textViewCategory = (TextView) expenseListView.findViewById(R.id.textViewExpenseCategory);
        //textViewCategory.setText(expense.getType());

        Date date = new Date();
        GregorianCalendar dataCal = new GregorianCalendar();
        dataCal.setTime(date);

        //TextView textViewValue = (TextView) expenseListView.findViewById(R.id.textViewExpenseValue);
        //textViewValue.setText("R$ " + df.format(expense.getValue()));

        if (expense.getMonth().toString().equals(String.valueOf(dataCal.get(Calendar.MONTH) + 2))) {
            int hexColor = android.graphics.Color.parseColor(COLOR_NEXT_MONTH);
            viewHolder.value.setTextColor(hexColor);
        }else if (expense.getMonth().toString().equals(String.valueOf(dataCal.get(Calendar.MONTH) + 1))) {
            int hexColor = android.graphics.Color.parseColor(COLOR_THIS_MONTH);
            viewHolder.value.setTextColor(hexColor);
        }else{
            int hexColor = android.graphics.Color.parseColor(COLOR_AFTER_NEXT_MONTH);
            viewHolder.value.setTextColor(hexColor);
        }

        //ImageView textViewLabelLetter = (ImageView) expenseListView.findViewById(R.id.textViewLabel);
        //String first = expense.getType().toString().substring(0, 1);
        //textViewLabelLetter.setText(first);

        // TextView textViewForm = (TextView) expenseListView.findViewById(R.id.textViewForm);
        // textViewForm.setText(expense.getForm().toString());

        //int hexColor;

        switch (expense.getType()) {
            case "Saúde":
                viewHolder.image.setImageResource(R.drawable.saude);
                break;
            case "Transporte":
                viewHolder.image.setImageResource(R.drawable.transporte);
                break;
            case "Alimentação":
                viewHolder.image.setImageResource(R.drawable.alimentacao);
                break;
            case "Moradia":
                viewHolder.image.setImageResource(R.drawable.moradia);
                break;
            case "Lazer":
                viewHolder.image.setImageResource(R.drawable.lazer);
                break;
            case "Outros":
                viewHolder.image.setImageResource(R.drawable.outros);
                break;
            case "Roupas":
                viewHolder.image.setImageResource(R.drawable.roupas);
                break;
            case "Contas":
                viewHolder.image.setImageResource(R.drawable.contas);
                break;
            case "Academia":
                viewHolder.image.setImageResource(R.drawable.academia);
                break;
            case "Estudos":
                viewHolder.image.setImageResource(R.drawable.estudo);
                break;
            case "Ferramentas":
                viewHolder.image.setImageResource(R.drawable.ferramenta);
                break;
            case "Video Game":
                viewHolder.image.setImageResource(R.drawable.game);
                break;
            case "Investimento":
                viewHolder.image.setImageResource(R.drawable.investimento);
                break;
            case "Telefone":
                viewHolder.image.setImageResource(R.drawable.telefone);
                break;
            case "Viagem":
                viewHolder.image.setImageResource(R.drawable.viagem);
                break;

        }

        int colorPaid;

        if(expense.getPaid().toString().equals("1")) {
            colorPaid = android.graphics.Color.parseColor("#C8E6C9");
            //convertView.setBackgroundColor(colorPaid);
            viewHolder.imageView.setBackgroundColor(colorPaid);
        }else {
            colorPaid = android.graphics.Color.parseColor("#FFCDD2");
            //convertView.setBackgroundColor(colorPaid);
            viewHolder.imageView.setBackgroundColor(colorPaid);
        }
        return convertView;

    }

    public static class ViewHolder {

        TextView description;
        TextView value;
        TextView form;
        TextView category;
        ImageView image;
        ImageView imageView;
    }
}
