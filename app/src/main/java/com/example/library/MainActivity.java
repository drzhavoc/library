package com.example.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button CV1,CV2;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CV1= findViewById(R.id.signin);
        CV2= findViewById(R.id.signup);
        CV1.setOnClickListener(this);
        CV2.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.signin)
        {
            Intent intent= new Intent(MainActivity.this, signin.class);
            startActivity(intent);

        }


        else if(view.getId()==R.id.signup)
        {
            Intent intent= new Intent(MainActivity.this, signup.class);
            startActivity(intent);


        }

    }
}