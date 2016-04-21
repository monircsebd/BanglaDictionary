package com.bangladictionary.secapps.bangladictionary;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monir_mondal on 2/2/2016.
 */
public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new MyDatabase(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getWords(String word) {

        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT langFullWord FROM wordbook where langFullWord like '" + word + "%'", null);
        Log.e("Cursor", cursor.toString());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public String getMeaning(String selectedWord) {
        String meaningWord = null;
        Log.e("Meaning", selectedWord);
        Cursor cursor = database.rawQuery("SELECT entry FROM wordbook where langFullWord = " + "'" + selectedWord + "'", null);

        Log.e("Meaning", cursor.toString());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            meaningWord = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return meaningWord;
    }

    public String getSynonimId(String selectedWord) {
        String meaningWord = null;
        Log.e("Meaning", selectedWord);
        Cursor cursor = database.rawQuery("SELECT synonyms FROM wordbook where langFullWord = " + "'" + selectedWord + "'", null);

        Log.e("Synonyms", cursor.toString());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            meaningWord = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return meaningWord;
    }

    public String getAntonymsID(String selectedWord) {
        String meaningWord = null;
        Log.e("Meaning", selectedWord);
        Cursor cursor = database.rawQuery("SELECT antonyms FROM wordbook where langFullWord = " + "'" + selectedWord + "'", null);

        Log.e("Antonyms", cursor.toString());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            meaningWord = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return meaningWord;
    }

    public String getSimilarWordId(String selectedWord) {
        String meaningWord = null;
        Log.e("Meaning", selectedWord);
        Cursor cursor = database.rawQuery("SELECT similar FROM wordbook where langFullWord = " + "'" + selectedWord + "'", null);

        Log.e("Similar_Word", cursor.toString());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            meaningWord = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return meaningWord;
    }


    public String getSynonymWord(String selectedWord) {
        int id = Integer.parseInt(selectedWord);
        Log.e("id", "" + id);
        String meaningWord = null;
        Log.e("Meaning", selectedWord);
        Cursor cursor = database.rawQuery("SELECT langFullWord FROM wordbook where _id = " + id + "", null);
        Log.e("Synonyms", cursor.toString());
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            meaningWord = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return meaningWord;
    }
}
