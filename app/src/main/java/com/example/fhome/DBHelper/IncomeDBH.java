package com.example.fhome.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fhome.Model.IncomeItem;
import com.example.fhome.Model.IncomePortFolioItem;
import com.example.fhome.Model.SpendingItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class IncomeDBH extends SQLiteOpenHelper {

    public static final String DBNAME = "Income.db";

    public static final String TABLE_INCOMEPORTFOLIO= "tblIncomePortfolio";

    public static final String TABLE_INCOME= "tblIncome";

    public IncomeDBH(Context context) {
        super(context, DBNAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_INCOMEPORTFOLIO + "(id INTEGER PRIMARY KEY AUTOINCREMENT, incomeName TEXT)");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_INCOME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, incomeName TEXT, money INTEGER, dateSpend TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOMEPORTFOLIO);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        onCreate(MyDB);
    }

    public long insertincomePortFolio(String incomeName) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("incomeName", incomeName);
        return MyDB.insert(TABLE_INCOMEPORTFOLIO, null, contentValues);
    }

    public int deleteincomePortFolio(int incomePortFolioID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete(TABLE_INCOMEPORTFOLIO, "id=?", new String[]{String.valueOf(incomePortFolioID)});
    }

    public List<IncomePortFolioItem> getAllincomePortFolios() {
        List<IncomePortFolioItem> incomePortFolioList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INCOMEPORTFOLIO + " ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("incomeName"));

                IncomePortFolioItem incomePortFolioItem = new IncomePortFolioItem(id, name);
                incomePortFolioList.add(incomePortFolioItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return incomePortFolioList;
    }

    public long insertIncome(String incomeName, String money, String date) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("incomeName", incomeName);
        contentValues.put("money", money);
        contentValues.put("dateSpend", date);
        return MyDB.insert(TABLE_INCOME, null, contentValues);
    }

    public List<IncomeItem> getAllIncomeItems() {
        List<IncomeItem> incomeItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INCOME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("incomeName"));
                String money = cursor.getString(cursor.getColumnIndexOrThrow("money"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("dateSpend"));

                IncomeItem incomeItem = new IncomeItem(id, name, money, date);
                incomeItemList.add(incomeItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return incomeItemList;
    }

    public List<IncomeItem> getIncomeItemsBetweenDates(String startDate, String endDate) {
        List<IncomeItem> spendItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_INCOME +
                " WHERE " + "dateSpend" + " BETWEEN '" + startDate + "' AND '" + endDate + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("incomeName"));
                String money = cursor.getString(cursor.getColumnIndexOrThrow("money"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("dateSpend"));

                IncomeItem incomeItem = new IncomeItem(id, name, money, date);
                spendItemList.add(incomeItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return spendItemList;
    }

    public int deleteIncome(int incomeID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete(TABLE_INCOME, "id=?", new String[]{String.valueOf(incomeID)});
    }
}
