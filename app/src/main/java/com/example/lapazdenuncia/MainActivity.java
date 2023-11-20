package com.example.lapazdenuncia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnAniadir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAniadir = findViewById(R.id.btnAniadir);

        btnAniadir.setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, NuevaPublicacionActivity.class)));
    }
}