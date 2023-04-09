package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Memory extends AppCompatActivity {
    String memoryName;
    int memoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        Intent memoryIntent = getIntent();

        memoryName = memoryIntent.getStringExtra("memoryName");
        memoryId = memoryIntent.getIntExtra("memoryID", -1);

        TextView textViewMemoryTitle = findViewById(R.id.textViewMemoryTitle);
        textViewMemoryTitle.setText(memoryName);

        TextView textViewSelectedMemoryId = findViewById(R.id.textViewSelectedMemoryId);
        textViewSelectedMemoryId.setText(String.valueOf(memoryId));
    }
}