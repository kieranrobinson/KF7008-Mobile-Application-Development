package uk.co.kieranrobinson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonViewMap = (Button) findViewById(R.id.buttonViewMap);
        buttonViewMap.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    public void openAddMemoryActivity(View view){
        Context context = getApplicationContext();
        int toastDuration = Toast.LENGTH_SHORT;
        CharSequence toastText = "Feature not implemented yet";
        Toast toast = Toast.makeText(context, toastText, toastDuration);
        toast.show();
    }

    public void openViewMemoriesActivity(View view){
        Context context = getApplicationContext();
        int toastDuration = Toast.LENGTH_SHORT;
        CharSequence toastText = "Feature not implemented yet";
        Toast toast = Toast.makeText(context, toastText, toastDuration);
        toast.show();
    }

    public void openViewMapActivity(View view){
        Context context = getApplicationContext();
        int toastDuration = Toast.LENGTH_SHORT;
        CharSequence toastText = "Feature not implemented yet";
        Toast toast = Toast.makeText(context, toastText, toastDuration);
        toast.show();
    }
}