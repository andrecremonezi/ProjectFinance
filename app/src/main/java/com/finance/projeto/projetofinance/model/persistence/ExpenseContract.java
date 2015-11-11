package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import com.finance.projeto.projetofinance.model.entities.Expense;
import java.util.ArrayList;
import java.util.List;

public class ExpenseContract {

    public static final String TABLE        = "expense";
    public static final String ID           = "id";
    public static final String DESCRIPTION  = "description";
    public static final String VALUE        = "value";
    public static final String TYPE         = "type";
    public static final String FORM         = "form";
    public static final String MONTH        = "month";
    public static final String DATE         = "date";
    public static final String PAID         = "paid";


    public static final String[] COLUNS = {ID, DESCRIPTION, VALUE, TYPE, FORM, MONTH, DATE, PAID};

    private ExpenseContract(){
        super();
    }

    public static String getCreateTableScript(){

        return (" CREATE TABLE " + TABLE) + " ( " + ID + " INTEGER PRIMARY KEY, " + DESCRIPTION + " TEXT NOT NULL, " + VALUE + " FLOAT NOT NULL, " + TYPE + " TEXT, " + FORM + " TEXT, " + MONTH + " TEXT, " + DATE + " DATETIME DEFAULT CURRENT_DATE, " + PAID + " INTEGER DEFAULT 0 " + " ); ";
    }

    public static ContentValues getContentValues(Expense expense) {
        ContentValues values = new ContentValues();

        values.put(ExpenseContract.ID, expense.getId());
        values.put(ExpenseContract.DESCRIPTION, expense.getDescription());
        values.put(ExpenseContract.VALUE, expense.getValue());
        values.put(ExpenseContract.TYPE, expense.getType());
        values.put(ExpenseContract.FORM, expense.getForm());
        values.put(ExpenseContract.MONTH, expense.getForm());
        values.put(ExpenseContract.DATE, expense.getMonth());
        values.put(ExpenseContract.PAID, expense.getPaid());
        return values;
    }

    public static Expense getExpense(Cursor cursor) {

        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Expense expense = new Expense();

            expense.setId(cursor.getLong(cursor.getColumnIndex(ExpenseContract.ID)));
            expense.setDescription(cursor.getString(cursor.getColumnIndex(ExpenseContract.DESCRIPTION)));
            expense.setValue(cursor.getDouble(cursor.getColumnIndex(ExpenseContract.VALUE)));
            expense.setType(cursor.getString(cursor.getColumnIndex(ExpenseContract.TYPE)));
            expense.setForm(cursor.getString(cursor.getColumnIndex(ExpenseContract.FORM)));
            expense.setForm(cursor.getString(cursor.getColumnIndex(ExpenseContract.MONTH)));
            expense.setMonth(cursor.getInt(cursor.getColumnIndex(ExpenseContract.DATE)));
            expense.setPaid(cursor.getInt(cursor.getColumnIndex(ExpenseContract.PAID)));

            return expense;
        }
        return null;
    }

    public static List<Expense> getExpenses(Cursor cursor) {
        List<Expense> expenses = new ArrayList<>();

        while(cursor.moveToNext()) {
            expenses.add(getExpense(cursor));
        }
        return expenses;
    }

}
