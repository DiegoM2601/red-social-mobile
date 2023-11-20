package com.example.lapazdenuncia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CrearCuentaActivity extends AppCompatActivity {

    EditText txtEmail, txtContrasenia, txtContraseniaConfirmar;
    Button btnCrearCuenta;
    ProgressBar barraProgreso;
    TextView irLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);


        txtEmail = findViewById(R.id.txtEmail);
        txtContrasenia = findViewById(R.id.txtContrasenia);
        txtContraseniaConfirmar = findViewById(R.id.txtContraseniaConfirmar);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);
        barraProgreso = findViewById(R.id.barra_progreso);
        irLogin = findViewById(R.id.btnLogin);

        btnCrearCuenta.setOnClickListener(v -> crearCuenta());
        irLogin.setOnClickListener(v -> finish());
    }

    void crearCuenta() {
        String email = txtEmail.getText().toString();
        String contrasenia = txtContrasenia.getText().toString();
        String contraseniaConfirmar = txtContraseniaConfirmar.getText().toString();

        if (validarData(email, contrasenia, contraseniaConfirmar)) {
            crearCuentaFirebase(email, contrasenia);
        }

    }

    void crearCuentaFirebase(String email, String contrasenia) {
        cambioEnProgreso(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, contrasenia).addOnCompleteListener(CrearCuentaActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    cambioEnProgreso(false);
                    Utility.mostrarToast(CrearCuentaActivity.this, "Se ha creado la cuenta exitosamente.");
                    firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();
                    finish();
                }
                else{
                    Utility.mostrarToast(CrearCuentaActivity.this, task.getException().getLocalizedMessage());
                }
            }
        });
    }

    void cambioEnProgreso(boolean enProgreso) {
        if (enProgreso) {
            barraProgreso.setVisibility(View.VISIBLE);
            btnCrearCuenta.setVisibility(View.GONE);
        } else {
            barraProgreso.setVisibility(View.GONE);
            btnCrearCuenta.setVisibility(View.VISIBLE);
        }
    }

    boolean validarData(String email, String contrasenia, String confirmarContrasenia) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txtEmail.setError("Email no es válido.");
            return false;
        }

        if (contrasenia.length() < 6) {
            txtContrasenia.setError("Contraseña débil.");
            return false;
        }

        if (!contrasenia.equals(confirmarContrasenia)) {
            txtContraseniaConfirmar.setError("Las contraseñas no coinciden.");
            return false;
        }

        return true;
    }
}