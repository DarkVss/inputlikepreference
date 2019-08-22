package ru.modzi.cust_field;

import android.os.Bundle;
import android.preference.ListPreference;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final InputLikePreference ed = findViewById(R.id.xxx);

        /*HashMap<String,String> variants = new HashMap<>();
        variants.put("gender_1","Gender 1");
        variants.put("gender_2","Gender 2");
        variants.put("gender_3","Gender 3");
        variants.put("gender_4","Gender 4");
        variants.put("gender_5","Gender 5");
        variants.put("gender_6","Gender 6");
        ed.setVariants(variants);*/

        /*ed.setLabel("Заголовк поля 1");
        ed.setValue("Значение поля 1");*/

        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        getApplicationContext(),
                        ed.getValue(),
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed.setValue(ed.getValue() + "+1");
            }
        });
    }
}
