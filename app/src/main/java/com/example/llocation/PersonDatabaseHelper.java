package com.example.llocation;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import com.example.llocation.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PersonDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PERSON = "person";

    public PersonDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PERSON + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, birthDate TEXT, phone TEXT, photoPath TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSON);
        onCreate(db);
    }

    public void insertPerson(Person person) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", person.getName());
        values.put("birthDate", person.getBirthDate());
        values.put("phone", person.getPhone());
        values.put("photoPath", person.getPhotoPath());
        db.insert(TABLE_PERSON, null, values);
    }

    public List<Person> getAllPersons() {
        List<Person> personList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PERSON, null);

        if (cursor.moveToFirst()) {
            do {
                Person person = new Person(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                personList.add(person);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return personList;
    }
}
