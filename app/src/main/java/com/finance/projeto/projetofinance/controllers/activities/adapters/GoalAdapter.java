package com.finance.projeto.projetofinance.controllers.activities.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.daimajia.numberprogressbar.NumberProgressBar;
import com.finance.projeto.projetofinance.R;
import com.finance.projeto.projetofinance.model.entities.Goal;
import com.finance.projeto.projetofinance.model.services.ExpenseBussinessService;

import java.text.DecimalFormat;
import java.util.List;

public class GoalAdapter extends BaseAdapter {
    private List<Goal> goalList;
    private Activity context;

    public GoalAdapter(Activity context, List<Goal> goalList) {
        this.goalList = goalList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return goalList.size();
    }

    @Override
    public Object getItem(int position) {
        return goalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DecimalFormat df = new DecimalFormat("0.00");

        Goal goal = (Goal) getItem(position);

        View goalListView = context.getLayoutInflater().inflate(R.layout.list_item_goal, parent, false);

        TextView textViewCategory = (TextView) goalListView.findViewById(R.id.textViewGoalCategory);
        textViewCategory.setText(goal.getType().toString());

        TextView textViewValueTotal = (TextView) goalListView.findViewById(R.id.textViewGoalTotalValue);
        textViewValueTotal.setText(df.format(goal.getValue()));

        Double amountEspent = ExpenseBussinessService.amountSpentThisMonth(goal.getType().toString());
        TextView textViewValueCurrent = (TextView) goalListView.findViewById(R.id.textViewGoalCurrentValue);
        textViewValueCurrent.setText(df.format(amountEspent));

        TextView textViewLog = (TextView) goalListView.findViewById(R.id.textViewLog);

        Double percentage = (amountEspent / goal.getValue()) * 100;

        Integer setPercentage = 0;

        if(percentage <= 100){
            setPercentage = percentage.intValue();
        }
        else if(percentage > 100){
            setPercentage = 100;

            textViewLog.setText("O valor gasto ultrapassou valor meta!");
        }

        NumberProgressBar numberProgressBar = (NumberProgressBar) goalListView.findViewById(R.id.number_progress_bar);
        numberProgressBar.setProgress(setPercentage);

        return goalListView;
    }
}
