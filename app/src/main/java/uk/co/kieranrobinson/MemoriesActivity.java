package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MemoriesActivity extends AppCompatActivity {
    private SQLiteDB sqliteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);

        sqliteDB = new SQLiteDB(MemoriesActivity.this);

        setupListview();
    }

    private void setupListview(){
        ArrayList<String> listMemoryNames = sqliteDB.getAllMemoryNames();
        ListView list = findViewById(R.id.listMemories);

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listMemoryNames);
        list.setAdapter(arrayAdapter);
    }
}