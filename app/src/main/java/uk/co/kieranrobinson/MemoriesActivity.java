package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MemoriesActivity extends AppCompatActivity {
    private SQLiteDB sqliteDB;
    ListView list;
    EditText editTextSearch;
    private Intent selectedActivityIntent;
    ArrayList<Integer> listMemoryIDs = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memories);

        editTextSearch = findViewById(R.id.editTextSearch);
        selectedActivityIntent = new Intent(this, Memory.class);
        sqliteDB = new SQLiteDB(MemoriesActivity.this);

        setupListSearch();
        setupListview();


        getSupportActionBar().setTitle("Memories");
    }

    private void setupListview(){
        ArrayList<String> listMemoryNames = sqliteDB.getAllMemoryNames();
        list = findViewById(R.id.listMemories);

        arrayAdapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listMemoryNames);
        list.setAdapter(arrayAdapter);

        listMemoryIDs = sqliteDB.getAllMemoryID();

        list.setOnItemClickListener(entryPressed);
    }

    private void setupListSearch(){
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Unused
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //When text is changed within searchbar update the list of memories
                (MemoriesActivity.this).arrayAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Unused
            }
        });
    }

    private AdapterView.OnItemClickListener entryPressed = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //When item is selected from listview open matching memory
            String selectedItem = (String)list.getItemAtPosition(i);
            selectedActivityIntent.putExtra("memoryID",listMemoryIDs.get(i));
            startActivity(selectedActivityIntent);
        }
    };


}