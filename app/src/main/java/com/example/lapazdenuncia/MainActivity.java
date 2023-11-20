package com.example.lapazdenuncia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnAniadir;
    RecyclerView muro;
    ImageButton btnMenu;

    PublicacionAdapter publicacionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAniadir = findViewById(R.id.btnAniadir);
        muro = findViewById(R.id.muro);
        btnMenu = findViewById(R.id.btnMenu);

        btnAniadir.setOnClickListener((v) -> startActivity(new Intent(MainActivity.this, NuevaPublicacionActivity.class)));
        btnMenu.setOnClickListener((v) -> mostrarMenu());
        configurarRecyclerView();
    }

    void mostrarMenu() {

    }

    void configurarRecyclerView() {
        //analizar la clase Query que se esta importando, hay 2 opciones
//        Query query = Utility.getCollectionReferenceForPublicaciones().orderBy("fecha", Query.Direction.DESCENDING);
        Query query= Utility.getCollectionReferenceForPublicaciones().orderBy("fecha", Query.Direction.DESCENDING);
//        FirestoreRecyclerOptions<Publicacion> options = new FirestoreRecyclerOptions.Builder<Publicacion>().setQuery(query, Publicacion.class).build();
        FirestoreRecyclerOptions<Publicacion> options =
                new FirestoreRecyclerOptions.Builder<Publicacion>()
                        .setQuery(query, Publicacion.class)
                        .build();

        muro.setLayoutManager(new LinearLayoutManager(this));
        publicacionAdapter = new PublicacionAdapter(options, this);
        muro.setAdapter(publicacionAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        publicacionAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        publicacionAdapter.stopListening();
    }

}