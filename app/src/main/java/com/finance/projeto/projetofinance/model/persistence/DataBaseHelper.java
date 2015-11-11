package com.finance.projeto.projetofinance.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.finance.projeto.projetofinance.util.ApplicationUtil;


public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskmanagerdb";
    private static final int DATABASE_VERSION = 1;


    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseHelper getIstance(){
        return new DataBaseHelper(ApplicationUtil.getApplicationContext());
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("Finance Manager", "Create Table User");
        db.execSQL(UserContract.getCreateTableScript());

        Log.e("Finance Manager", "Create Table Expense");
        db.execSQL(ExpenseContract.getCreateTableScript());

        Log.e("Finance Manager", "Create Table Receipe");
        db.execSQL(ReceipeContract.getCreateTableScript());

        Log.e("Finance Manager", "Create Table Goal");
        db.execSQL(GoalContract.getCreateTableScript());

        Log.e("Finance Manager", "Create Table Card");
        db.execSQL(CardContract.getCreateTableScript());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }


}
