package com.hdrescuer.hdrescuer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button btn_initwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViews();
        events();

    }


    private void findViews() {
        this.btn_initwatch = findViewById(R.id.btn_initwatch);
    }


    private void events() {
        this.btn_initwatch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, ConnectionActivity.class);
        startActivity(i);
    }
}