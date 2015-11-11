package com.finance.projeto.projetofinance.model.persistence;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.finance.projeto.projetofinance.model.entities.Receipe;
import java.util.List;

public class ReceipeRepository {

        private ReceipeRepository()
        {
            super();
        }

        public static void save(Receipe receipe) {

            DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();

            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
            ContentValues values = ReceipeContract.getContentValues(receipe);

            if (receipe.getId() == null) {

                db.insert(ReceipeContract.TABLE, null, values);

            } else {

                String where = ReceipeContract.ID + " = ? ";
                String[] params = {receipe.getId().toString()};
                db.update(ReceipeContract.TABLE, values, where, params);
            }

            db.close();
            dataBaseHelper.close();
        }

        public static void delete(long id) {

            DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
            SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

            String where = ReceipeContract.ID + " = ? ";
            String[] params = {String.valueOf(id)};

            db.delete(ReceipeContract.TABLE, where, params);

            db.close();
            dataBaseHelper.close();
        }




        public static List<Receipe> getAll() {

            DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
            SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
            Cursor cursor = db.query(ReceipeContract.TABLE, ReceipeContract.COLUNS, null, null, null, null,ReceipeContract.ID);
            List<Receipe> values = ReceipeContract.getReceipes(cursor);

            db.close();
            dataBaseHelper.close();

            return values;
        }

    public static Receipe getById(Long id) {

        DataBaseHelper dataBaseHelper = DataBaseHelper.getIstance();
        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();

        String where = ReceipeContract.ID + " = ? ";
        String[] param = {id.toString()};

        Cursor cursor = db.query(ReceipeContract.TABLE, ReceipeContract.COLUNS, where, param, null, null, null);

        Receipe receipe = ReceipeContract.getReceipe(cursor);

        dataBaseHelper.close();
        db.close();

        return  receipe;

    }

}
