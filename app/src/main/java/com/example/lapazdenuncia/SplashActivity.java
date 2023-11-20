package com.example.lapazdenuncia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.InetSocketAddress;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //verificar si el usuario ya se encuentra logeado
                FirebaseUser usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
                if(usuarioActual == null){
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                else{
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }

//                impedir que el usuario vuelva a esta activity pulsando el botón para ir hacia atrás
//                ni bien se abra la activity main, la presente activity cesará su ejecución y, por ende, su disponibilidad
                finish();
            }
        }, 1500);
    }
}