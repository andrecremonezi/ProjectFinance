package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;

import com.finance.projeto.projetofinance.model.entities.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrea on 13/10/2015.
 */
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
        final StringBuilder create = new StringBuilder();
        create.append(" CREATE TABLE " + TABLE);
        create.append(" ( ");
        create.append(ID + " INTEGER PRIMARY KEY, ");
        create.append(NAME + " TEXT, ");
        create.append(LIMITVALUE + " FLOAT NOT NULL ");
        create.append(" ); ");

        return create.toString();
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
        Card card = new Card();
        List<Card> cards = new ArrayList<>();

        while (cursor.moveToNext()) {
            cards.add(getCard(cursor));
        }
        return cards;
    }
}
