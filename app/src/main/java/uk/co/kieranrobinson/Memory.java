package uk.co.kieranrobinson;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Memory extends AppCompatActivity {
    String memoryName;
    String memoryDescription;
    double memoryLongitude;
    double memoryLatitude;
    int memoryID;
    String memoryDate;
    private SQLiteDB sqliteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        Intent memoryIntent = getIntent();

        sqliteDB = new SQLiteDB(Memory.this);

        memoryID = memoryIntent.getIntExtra("memoryID", -1);
        memoryName = sqliteDB.getMemoryName(memoryID);
        memoryDescription = sqliteDB.getMemoryDescription(memoryID);
        memoryLatitude = sqliteDB.getMemoryLatitude(memoryID);
        memoryLongitude = sqliteDB.getMemoryLongitude(memoryID);
        memoryDate = sqliteDB.getMemoryDate(memoryID);


        TextView textViewMemoryTitle = findViewById(R.id.textViewMemoryTitle);
        textViewMemoryTitle.setText(memoryName);

        TextView textViewSelectedMemoryDescription = findViewById(R.id.textViewSelectedMemoryDescription);
        textViewSelectedMemoryDescription.setText(String.valueOf(memoryDescription));

        TextView textViewMemoryDate = findViewById(R.id.textViewDate);
        textViewMemoryDate.setText(String.valueOf(memoryDate));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}