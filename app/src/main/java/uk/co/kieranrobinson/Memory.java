package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Memory extends AppCompatActivity {
    String memoryName;
    String memoryDescription;
    double memoryLongitude;
    double memoryLatitude;
    int memoryID;
    private SQLiteDB sqliteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        Intent memoryIntent = getIntent();

        sqliteDB = new SQLiteDB(Memory.this);

        memoryName = memoryIntent.getStringExtra("memoryName");
        memoryID = memoryIntent.getIntExtra("memoryID", -1);
        memoryDescription = sqliteDB.getMemoryDescription(memoryID);
        memoryLatitude = sqliteDB.getMemoryLatitude(memoryID);
        memoryLongitude = sqliteDB.getMemoryLongitude(memoryID);

        TextView textViewMemoryTitle = findViewById(R.id.textViewMemoryTitle);
        textViewMemoryTitle.setText(memoryName);

        TextView textViewSelectedMemoryDescription = findViewById(R.id.textViewSelectedMemoryDescription);
        textViewSelectedMemoryDescription.setText(String.valueOf(memoryDescription));

        TextView textViewMemoryLatitude = findViewById(R.id.textViewLatitude);
        textViewMemoryLatitude.setText(String.valueOf(memoryLatitude));

        TextView textViewMemoryLongitude = findViewById(R.id.textViewLongitude);
        textViewMemoryLongitude.setText(String.valueOf(memoryLongitude));
    }
}