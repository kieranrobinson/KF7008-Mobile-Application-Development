package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MemoriesActivity extends AppCompatActivity {
    private SQLiteDB sqliteDB;
    ListView list;
    private Intent selectedActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);


        selectedActivityIntent = new Intent(this, Memory.class);
        sqliteDB = new SQLiteDB(MemoriesActivity.this);

        setupListview();
    }

    private void setupListview(){
        ArrayList<String> listMemoryNames = sqliteDB.getAllMemoryNames();
        list = findViewById(R.id.listMemories);

        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listMemoryNames);
        list.setAdapter(arrayAdapter);

        list.setOnItemClickListener(entryPressed);
    }

    private AdapterView.OnItemClickListener entryPressed = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedItem = (String)list.getItemAtPosition(i);
            startActivity(selectedActivityIntent);
        }
    };


}