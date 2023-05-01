package uk.co.kieranrobinson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SQLiteDB sqliteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqliteDB = new SQLiteDB(MainActivity.this);

        getSupportActionBar().hide();
    }

    public void openAddMemoryActivity(View view){
        Intent intent = new Intent(view.getContext(), AddMemoryActivity.class);
        view.getContext().startActivity(intent);

    }

    public void openViewMemoriesActivity(View view){
        Intent intent = new Intent(view.getContext(), MemoriesActivity.class);
        view.getContext().startActivity(intent);
    }

    public void openViewMapActivity(View view){
        Intent intent = new Intent(view.getContext(), MapsActivity.class);
        view.getContext().startActivity(intent);
    }
}