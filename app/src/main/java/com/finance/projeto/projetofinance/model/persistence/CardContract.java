package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import com.finance.projeto.projetofinance.model.entities.Card;
import java.util.ArrayList;
import java.util.List;


public class CardContract {
    public static final String TABLE      = "card";
    public static final String ID         = "id";
    public static final String NAME       = "name";
    public static final String LIMITVALUE = "limitvalue";

    public static final String[] COLUNS = {ID, NAME, LIMITVALUE};

    private CardContract(){
        super();
    }

    public static String getCreateTableScript(){

        return (" CREATE TABLE " + TABLE) + " ( " + ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + LIMITVALUE + " FLOAT NOT NULL " + " ); ";
    }

    public static ContentValues getContentValues(Card card) {
        ContentValues values = new ContentValues();

        values.put(CardContract.ID, card.getId());
        values.put(CardContract.NAME, card.getName());
        values.put(CardContract.LIMITVALUE, card.getLimitValue());

        return values;
    }

    public static Card getCard(Cursor cursor) {

        if (!cursor.isBeforeFirst() || cursor.moveToNext()) {
            Card card = new Card();

            card.setId(cursor.getLong(cursor.getColumnIndex(CardContract.ID)));
            card.setName(cursor.getString(cursor.getColumnIndex(CardContract.NAME)));
            card.setLimitValue(cursor.getDouble(cursor.getColumnIndex(CardContract.LIMITVALUE)));

            return card;
        }
        return null;
    }

    public static List<Card> getCards(Cursor cursor) {
        List<Card> cards = new ArrayList<>();

        while (cursor.moveToNext()) {
            cards.add(getCard(cursor));
        }
        return cards;
    }
}
