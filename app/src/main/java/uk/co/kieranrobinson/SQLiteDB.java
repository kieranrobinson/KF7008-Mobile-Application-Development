package uk.co.kieranrobinson;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "sqliteDB";
    private static final int DB_VERSION = 1;

    public SQLiteDB(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        //Forces onCreate to run
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("DATABASE CREATED");
        setupDatabase(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS memory");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS location");
        onCreate(sqLiteDatabase);
    }

    public void setupDatabase(SQLiteDatabase db){
        String createTableMemory = "CREATE TABLE IF NOT EXISTS memory (memoryID INTEGER primary key autoincrement, name TEXT NOT NULL, description TEXT, locationID INTEGER, FOREIGN KEY(locationID) REFERENCES location(locationID));";
        String createTableLocation = "CREATE TABLE IF NOT EXISTS location (locationID INTEGER primary key autoincrement, latitude TEXT NOT NULL, longitude TEXT NOT NULL);";

        db.execSQL(createTableMemory);
        db.execSQL(createTableLocation);
    }
}
