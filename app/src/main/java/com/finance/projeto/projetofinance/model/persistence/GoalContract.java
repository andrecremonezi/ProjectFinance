package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import com.finance.projeto.projetofinance.model.entities.Goal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea on 11/10/2015.
 */
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
        final StringBuilder create = new StringBuilder();
        create.append(" CREATE TABLE " + TABLE);
        create.append(" ( ");
        create.append(ID + " INTEGER PRIMARY KEY, ");
        create.append(VALUE + " FLOAT NOT NULL, ");
        create.append(TYPE + " TEXT ");
        create.append(" ); ");

        return create.toString();
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
        Goal goal = new Goal();
        List<Goal> goals = new ArrayList<>();

        while (cursor.moveToNext()) {
            goals.add(getGoal(cursor));
        }
        return goals;
    }


}
