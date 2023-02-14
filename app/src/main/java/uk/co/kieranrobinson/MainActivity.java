package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SQLiteDB sqliteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqliteDB = new SQLiteDB(MainActivity.this);
    }

    public void openAddMemoryActivity(View view){
        Context context = getApplicationContext();
        int toastDuration = Toast.LENGTH_SHORT;
        CharSequence toastText = "Feature not implemented yet";
        Toast toast = Toast.makeText(context, toastText, toastDuration);
        toast.show();
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