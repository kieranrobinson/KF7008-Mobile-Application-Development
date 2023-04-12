package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;

public class AddMemoryActivity extends AppCompatActivity {
    private SQLiteDB sqliteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);

        sqliteDB = new SQLiteDB(AddMemoryActivity.this);
    }

    public void addMemory(View view){
        EditText memoryName = (EditText) findViewById(R.id.editTextMemoryName);
        EditText memoryDescription = (EditText) findViewById(R.id.editTextMemoryDescription);

        sqliteDB.addNewMemory(memoryName.getText().toString(), memoryDescription.getText().toString());

        //TODO: Add current location to memory with highest ID using addNewLocation()
    }
}