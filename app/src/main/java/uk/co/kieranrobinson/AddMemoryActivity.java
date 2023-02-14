package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AddMemoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);
    }

    public void addMemory(View view){
        Context context = getApplicationContext();
        int toastDuration = Toast.LENGTH_SHORT;
        CharSequence toastText = "Feature not implemented yet";
        Toast toast = Toast.makeText(context, toastText, toastDuration);
        toast.show();
    }
}