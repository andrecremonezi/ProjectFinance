package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.finance.projeto.projetofinance.model.entities.Expense;
import java.util.List;

public class ExpenseRepository {

    private ExpenseRepository()
    {
        super();
    }

    public static void save(Expense expense) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues values = ExpenseContract.getContentValues(expense);

        if (expense.getId() == null) {

            db.insert(ExpenseContract.TABLE, null, values);

        } else {

            String where = ExpenseContract.ID + " = ? ";
            String[] params = {expense.getId().toString()};
            db.update(ExpenseContract.TABLE, values, where, params);
        }

        db.close();
        dataBaseHelper.close();
    }

    public static void delete(long id) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String where = ExpenseContract.ID + " = ? ";
        String[] params = {String.valueOf(id)};

        db.delete(ExpenseContract.TABLE, where, params);

        db.close();
        dataBaseHelper.close();
    }

    public static List<Expense> getAll() {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(ExpenseContract.TABLE, ExpenseContract.COLUNS, null, null, null, null, ExpenseContract.ID);
        List<Expense> values = ExpenseContract.getExpenses(cursor);

        db.close();
        dataBaseHelper.close();

        return values;
    }

    public static Expense getById(Long id) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String where = ExpenseContract.ID+" = ? ";
        String[] param = {id.toString()};

        Cursor cursor = db.query(ExpenseContract.TABLE, ExpenseContract.COLUNS, where, param, null, null, null);

        Expense expense = ExpenseContract.getExpense(cursor);

        dataBaseHelper.close();
        db.close();

        return  expense;

    }
}
