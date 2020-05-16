package com.example.rasuapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView assemblyLineIB = (ImageView) findViewById(R.id.assemblyline);
        ImageView barcodeIB = (ImageView) findViewById(R.id.barcode);

        assemblyLineIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, validation.class);
                startActivity(intent);
            }
        });

        barcodeIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, qrscreen.class);
                startActivity(intent);
            }
        });
    }
}