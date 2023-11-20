package com.example.lapazdenuncia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText txtEmail, txtContrasenia;
    Button btnIniciarSesion;
    ProgressBar barraProgreso;
    TextView irRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtEmail);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        barraProgreso = findViewById(R.id.barra_progreso);
        irRegistro = findViewById(R.id.btnRegistro);

        btnIniciarSesion.setOnClickListener((v) -> loginUsuario());
        irRegistro.setOnClickListener((v) -> startActivity(new Intent(LoginActivity.this, CrearCuentaActivity.class)));
    }

    void loginUsuario(){
        String email = txtEmail.getText().toString();
        String contrasenia = txtContrasenia.getText().toString();
        cambioEnProgreso(true);
        if (validarData(email, contrasenia)) {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signInWithEmailAndPassword(email, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    cambioEnProgreso(false);
                    if(task.isSuccessful()){

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();

                        //comprobar si el correo fue verificado
//                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
//                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                        finish();
//                        }
//                        else{
//                            Utility.mostrarToast(LoginActivity.this, "Correo electrónico no verificado. Por favor, revisa tu bandeja de entrada.");
//                        }
                    }
                    else{
                        Utility.mostrarToast(LoginActivity.this, task.getException().getLocalizedMessage());
                    }
                }
            });
        }
    }


    void cambioEnProgreso(boolean enProgreso) {
        if (enProgreso) {
            barraProgreso.setVisibility(View.VISIBLE);
            btnIniciarSesion.setVisibility(View.GONE);
        } else {
            barraProgreso.setVisibility(View.GONE);
            btnIniciarSesion.setVisibility(View.VISIBLE);
        }
    }

    boolean validarData(String email, String contrasenia) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Email no es válido.");
            return false;
        }

        if (contrasenia.length() < 6) {
            txtContrasenia.setError("Contraseña no válida.");
            return false;
        }

        return true;
    }
}