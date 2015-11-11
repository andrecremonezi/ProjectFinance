package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import com.finance.projeto.projetofinance.model.entities.Receipe;
import java.util.ArrayList;
import java.util.List;

public class ReceipeContract {
    public static final String TABLE        = "receipe";
    public static final String ID           = "id";
    public static final String DESCRIPTION  = "description";
    public static final String VALUE        = "value";
    public static final String TYPE         = "type";
    public static final String WALLATORBANK = "installments";
    public static final String MONTH        = "month";

    public static final String[] COLUNS = {ID, DESCRIPTION, VALUE, TYPE, WALLATORBANK, MONTH};

    private ReceipeContract(){
        super();
    }

    public static String getCreateTableScript(){

        return (" CREATE TABLE " + TABLE) + " ( " + ID + " INTEGER PRIMARY KEY, " + DESCRIPTION + " TEXT NOT NULL, " + VALUE + " FLOAT NOT NULL, " + TYPE + " TEXT, " + WALLATORBANK + " TEXT, " + MONTH + " TEXT " + " ); ";
    }

    public static ContentValues getContentValues(Receipe receipe) {
        ContentValues values = new ContentValues();

        values.put(ReceipeContract.ID, receipe.getId());
        values.put(ReceipeContract.DESCRIPTION, receipe.getDescription());
        values.put(ReceipeContract.VALUE, receipe.getValue());
        values.put(ReceipeContract.TYPE, receipe.getType());
        values.put(ReceipeContract.WALLATORBANK, receipe.getWalletOrBank());
        values.put(ReceipeContract.MONTH, receipe.getMonth());
        return values;
    }

    public static Receipe getReceipe(Cursor cursor) {

        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Receipe receipe = new Receipe();
            receipe.setId(cursor.getLong(cursor.getColumnIndex(ReceipeContract.ID)));
            receipe.setDescription(cursor.getString(cursor.getColumnIndex(ReceipeContract.DESCRIPTION)));
            receipe.setValue(cursor.getDouble(cursor.getColumnIndex(ReceipeContract.VALUE)));
            receipe.setType(cursor.getString(cursor.getColumnIndex(ReceipeContract.TYPE)));
            receipe.setWalletOrBank(cursor.getString(cursor.getColumnIndex(ReceipeContract.WALLATORBANK)));
            receipe.setMonth(cursor.getInt(cursor.getColumnIndex(ReceipeContract.MONTH)));

            return receipe;
        }
        return null;
    }

    public static List<Receipe> getReceipes(Cursor cursor) {
        List<Receipe> receipes = new ArrayList<>();

        while (cursor.moveToNext()) {
            receipes.add(getReceipe(cursor));
        }
        return receipes;
    }
}
