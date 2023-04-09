package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Memory extends AppCompatActivity {
    String surveyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        Intent memoryIntent = getIntent();

        surveyName = memoryIntent.getStringExtra("memoryName");
        TextView textViewMemoryTitle = findViewById(R.id.textViewMemoryTitle);
        textViewMemoryTitle.setText(surveyName);
    }
}