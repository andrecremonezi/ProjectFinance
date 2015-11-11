package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import com.finance.projeto.projetofinance.model.entities.Goal;
import java.util.ArrayList;
import java.util.List;

public class GoalContract {

    public static final String TABLE        = "goal";
    public static final String ID           = "id";
    public static final String VALUE        = "value";
    public static final String TYPE         = "type";

    public static final String[] COLUNS = {ID, VALUE, TYPE};

    private GoalContract(){
        super();
    }

    public static String getCreateTableScript(){

        return (" CREATE TABLE " + TABLE) + " ( " + ID + " INTEGER PRIMARY KEY, " + VALUE + " FLOAT NOT NULL, " + TYPE + " TEXT " + " ); ";
    }

    public static ContentValues getContentValues(Goal goal) {
        ContentValues values = new ContentValues();

        values.put(GoalContract.ID, goal.getId());
        values.put(GoalContract.VALUE, goal.getValue());
        values.put(GoalContract.TYPE, goal.getType());

        return values;
    }

    public static Goal getGoal(Cursor cursor) {

        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Goal goal = new Goal();

            goal.setId(cursor.getLong(cursor.getColumnIndex(GoalContract.ID)));
            goal.setValue(cursor.getDouble(cursor.getColumnIndex(GoalContract.VALUE)));
            goal.setType(cursor.getString(cursor.getColumnIndex(GoalContract.TYPE)));

            return goal;
        }
        return null;
    }

    public static List<Goal> getGoals(Cursor cursor) {
        List<Goal> goals = new ArrayList<>();

        while (cursor.moveToNext()) {
            goals.add(getGoal(cursor));
        }
        return goals;
    }


}
