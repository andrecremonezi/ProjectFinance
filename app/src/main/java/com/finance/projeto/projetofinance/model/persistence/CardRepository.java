package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.finance.projeto.projetofinance.model.entities.Card;
import com.finance.projeto.projetofinance.model.entities.Receipe;

import java.util.List;

/**
 * Created by Andrea on 13/10/2015.
 */
public class CardRepository {

    private CardRepository()
    {
        super();
    }

    public static void save(Card card) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        ContentValues values = CardContract.getContentValues(card);

        if (card.getId() == null) {

            db.insert(CardContract.TABLE, null, values);

        } else {

            String where = CardContract.ID + " = ? ";
            String[] params = {card.getId().toString()};
            db.update(CardContract.TABLE, values, where, params);
        }

        db.close();
        dataBaseHelper.close();
    }

    public static void delete(long id) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String where = CardContract.ID + " = ? ";
        String[] params = {String.valueOf(id)};

        db.delete(CardContract.TABLE, where, params);

        db.close();
        dataBaseHelper.close();
    }




    public static List<Card> getAll() {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.query(CardContract.TABLE, CardContract.COLUNS, null, null, null, null,CardContract.ID);
        List<Card> values = CardContract.getCards(cursor);

        db.close();
        dataBaseHelper.close();

        return values;
    }


    public static Card getById(Long id) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String where = ReceipeContract.ID + " = ? ";
        String[] param = {id.toString()};

        Cursor cursor = db.query(CardContract.TABLE, CardContract.COLUNS, where, param, null, null, null);

        Card card = CardContract.getCard(cursor);

        dataBaseHelper.close();
        db.close();

        return card;

    }

}
