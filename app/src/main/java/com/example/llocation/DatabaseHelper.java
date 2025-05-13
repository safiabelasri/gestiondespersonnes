package com.example.llocation;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "UserDB.db";
    public static final String TABLE_NAME = "users";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (email TEXT PRIMARY KEY, password TEXT)");
        db.execSQL("CREATE TABLE personnes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +
                "date_naissance TEXT, " +
                "telephone TEXT, " +
                "photo TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String email, String password) {
        if (checkUserExists(email)) return false;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("password", password);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public boolean checkUserExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE email=?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE email=? AND password=?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
    // Ajouter une personne
    public boolean insertPerson(String nom, String dateNaissance, String telephone, String photoPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("date_naissance", dateNaissance);
        values.put("telephone", telephone);
        values.put("photo", photoPath);

        long result = db.insert("personnes", null, values);
        return result != -1;
    }

    // Récupérer toutes les personnes
    public List<String> getAllPersonnes() {
        List<String> personnes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nom, telephone FROM personnes", null);

        if (cursor.moveToFirst()) {
            do {
                String nom = cursor.getString(0);
                String tel = cursor.getString(1);
                personnes.add(nom + " - " + tel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return personnes;
    }


}
