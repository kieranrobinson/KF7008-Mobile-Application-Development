package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Memory extends AppCompatActivity {
    String memoryName;
    String memoryDescription;
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

        TextView textViewMemoryTitle = findViewById(R.id.textViewMemoryTitle);
        textViewMemoryTitle.setText(memoryName);

        TextView textViewSelectedMemoryDescription = findViewById(R.id.textViewSelectedMemoryDescription);
        textViewSelectedMemoryDescription.setText(String.valueOf(memoryDescription));
    }
}