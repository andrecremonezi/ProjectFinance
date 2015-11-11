package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.finance.projeto.projetofinance.model.entities.Goal;
import java.util.List;


public class GoalRepository {

    private GoalRepository()
    {
        super();
    }

    public static void save(Goal goal) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues values = GoalContract.getContentValues(goal);

        if (goal.getId() == null) {

            db.insert(GoalContract.TABLE, null, values);

        } else {

            String where = GoalContract.ID + " = ? ";
            String[] params = {goal.getId().toString()};
            db.update(GoalContract.TABLE, values, where, params);
        }

        db.close();
        dataBaseHelper.close();
    }

    public static void delete(long id) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String where = GoalContract.ID + " = ? ";
        String[] params = {String.valueOf(id)};

        db.delete(GoalContract.TABLE, where, params);

        db.close();
        dataBaseHelper.close();
    }




    public static List<Goal> getAll() {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(GoalContract.TABLE, GoalContract.COLUNS, null, null, null, null,GoalContract.ID);
        List<Goal> values = GoalContract.getGoals(cursor);

        db.close();
        dataBaseHelper.close();

        return values;
    }


    public static Goal getById(Long id) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String where = GoalContract.ID + " = ? ";
        String[] param = {id.toString()};

        Cursor cursor = db.query(GoalContract.TABLE, GoalContract.COLUNS, where, param, null, null, null);

        Goal goal = GoalContract.getGoal(cursor);

        dataBaseHelper.close();
        db.close();

        return  goal;

    }

}
