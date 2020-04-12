package com.example.game_2048_db1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button buttonPlay, buttonAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectView();
        listenButton();
    }

    private void connectView(){
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonAbout = findViewById(R.id.buttonHow);
    }

    private void listenButton(){
        buttonPlay.setOnClickListener(this);
        buttonAbout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonPlay){
//            Toast.makeText(MainActivity.this, "Clicked", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, GamePlay.class);
            startActivity(intent);
        }
        if(v == buttonAbout) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        }
    }
}