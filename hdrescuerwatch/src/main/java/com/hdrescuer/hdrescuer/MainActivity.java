package com.hdrescuer.hdrescuer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

/**
 * Actividad principal del reloj
 * @author Domingo Lopez
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Button btn_initwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViews();
        events();

    }

    /**
     * Método que inicializa los elementos de la vista
     * @author Domingo Lopez
     */
    private void findViews() {
        this.btn_initwatch = findViewById(R.id.btn_initwatch);
    }

    /**
     * Método que inicia los eventos de click
     * @author Domingo Lopez
     */
    private void events() {
        this.btn_initwatch.setOnClickListener(this);
    }

    /**
     * Método que gestiona las acciones de click
     * @author Domingo Lopez
     * @param v
     */
    @Override
    public void onClick(View v) {
        Intent i = new Intent(MainActivity.this, ConnectionActivity.class);
        startActivity(i);

        finish();
    }
}