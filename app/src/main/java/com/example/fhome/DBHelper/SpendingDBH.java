package com.example.fhome.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.fhome.Model.IncomeItem;
import com.example.fhome.Model.SpendingItem;
import com.example.fhome.Model.SpendingPortFolioItem;

import java.util.ArrayList;
import java.util.List;

public class SpendingDBH extends SQLiteOpenHelper {

    public static final String DBNAME = "Spending.db";

    public static final String TABLE_SPENDINGPORTFOLIO= "tblSpendPortfolio";
    public static final String TABLE_SPENDING= "tblSpending";


    public SpendingDBH(Context context) {
        super(context, DBNAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SPENDINGPORTFOLIO + "(id INTEGER PRIMARY KEY AUTOINCREMENT, spendingName TEXT)");
        MyDB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_SPENDING + "(id INTEGER PRIMARY KEY AUTOINCREMENT, spendName TEXT, money INTEGER, dateSpend TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SPENDINGPORTFOLIO);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SPENDING);
        onCreate(MyDB);
    }

    public long insertSpendingPortFolio(String spendingName) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("spendingName", spendingName);
        return MyDB.insert(TABLE_SPENDINGPORTFOLIO, null, contentValues);
    }

    public int deleteSpendingPortFolio(int spendingPortFolioID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete(TABLE_SPENDINGPORTFOLIO, "id=?", new String[]{String.valueOf(spendingPortFolioID)});
    }

    public List<SpendingPortFolioItem> getAllSpendingPortFolios() {
        List<SpendingPortFolioItem> spendingPortFolioList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SPENDINGPORTFOLIO + " ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("spendingName"));

                SpendingPortFolioItem spendingPortFolioItem = new SpendingPortFolioItem(id, name);
                spendingPortFolioList.add(spendingPortFolioItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return spendingPortFolioList;
    }


    public long insertSpending(String spendingName, String money, String date) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("spendName", spendingName);
        contentValues.put("money", money);
        contentValues.put("dateSpend", date);
        return MyDB.insert(TABLE_SPENDING, null, contentValues);
    }

    public List<SpendingItem> getAllSpendingItems() {
        List<SpendingItem> spendingItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SPENDING, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("spendName"));
                String money = cursor.getString(cursor.getColumnIndexOrThrow("money"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("dateSpend"));

                SpendingItem spendingItem = new SpendingItem(id, name, money, date);
                spendingItemList.add(spendingItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return spendingItemList;
    }

    public List<SpendingItem> getSpendItemsBetweenDates(String startDate, String endDate) {
        List<SpendingItem> spendingItemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SPENDING +
                " WHERE " + "dateSpend" + " BETWEEN '" + startDate + "' AND '" + endDate + "'";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("spendName"));
                String money = cursor.getString(cursor.getColumnIndexOrThrow("money"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("dateSpend"));

                SpendingItem spendingItem = new SpendingItem(id, name, money, date);
                spendingItemList.add(spendingItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return spendingItemList;
    }

    public int deleteSpending(int spendingID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        return MyDB.delete(TABLE_SPENDING, "id=?", new String[]{String.valueOf(spendingID)});
    }

}
