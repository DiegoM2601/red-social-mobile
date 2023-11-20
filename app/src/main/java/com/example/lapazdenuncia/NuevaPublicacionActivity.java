package com.example.lapazdenuncia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

public class NuevaPublicacionActivity extends AppCompatActivity {

    EditText tituloPublicacion, contenidoPublicacion;
    ImageButton btnPublicar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_publicacion);

        tituloPublicacion = findViewById(R.id.txtTituloPublicacion);
        contenidoPublicacion = findViewById(R.id.txtPublicacionContenido);
        btnPublicar = findViewById(R.id.btnPublicar);

        btnPublicar.setOnClickListener((v) -> publicar());
    }

    void publicar(){
        String titulo = tituloPublicacion.getText().toString();
        String contenido = contenidoPublicacion.getText().toString();

        if(titulo == null || titulo.isEmpty()){
            tituloPublicacion.setError("Se requiere un título.");
            return;
        }

        Publicacion publicacion = new Publicacion();
        publicacion.setTitulo(titulo);
        publicacion.setContenido(contenido);
        publicacion.setFecha(Timestamp.now());

        subirFirebase(publicacion);
    }

    void subirFirebase(Publicacion publicacion){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForPublicaciones().document();

        documentReference.set(publicacion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //publicacion subida a firebase
                    Utility.mostrarToast(NuevaPublicacionActivity.this, "La denuncia se ha publicado exitosamente.");
//                    finish();
                    startActivity(new Intent(NuevaPublicacionActivity.this, MainActivity.class));
                }
                else{
                    //fallo en la subida
                    Utility.mostrarToast(NuevaPublicacionActivity.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }
}