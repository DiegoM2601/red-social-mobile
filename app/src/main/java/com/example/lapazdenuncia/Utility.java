package com.example.lapazdenuncia;

import android.content.Context;
import android.widget.Toast;

public class Utility {
    static void mostrarToast(Context context, String mensaje){
        Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show();
    }
}
