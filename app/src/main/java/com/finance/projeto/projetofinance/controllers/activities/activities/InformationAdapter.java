package com.finance.projeto.projetofinance.controllers.activities.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.finance.projeto.projetofinance.R;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by c1284528 on 06/11/2015.
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.MyViewHolder>  {

    private LayoutInflater inflater;
    private List<Information> data = Collections.emptyList();
    private Activity activity;
    private static final String DESPESAS = "Despesas";
    private static final String RECEITAS = "Receitas";
    private static final String METAS    = "Metas";
    private static final String CARTOES  = "Cartão";

    public InformationAdapter(Activity context, List<Information> data) {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.activity = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listText);
            icon  = (ImageView) itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View v) {

            String title = data.get(getPosition()).title;

            switch (title){
                case DESPESAS:
                        activity.startActivity(new Intent(activity,ExpenseActivity.class));
                    break;
                case RECEITAS:
                    activity.startActivity(new Intent(activity,ReceipeActivity.class));
                    break;
                case METAS:
                    activity.startActivity(new Intent(activity,GoalActivity.class));
                    break;
                case CARTOES:
                    activity.startActivity(new Intent(activity,CardActivity.class));
                    break;
            }
        }
    }
}
