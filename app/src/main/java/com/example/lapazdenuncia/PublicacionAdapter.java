package com.example.lapazdenuncia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class PublicacionAdapter extends FirestoreRecyclerAdapter<Publicacion, PublicacionAdapter.PublicacionViewHolder> {

    Context contexto;

    public PublicacionAdapter(@NonNull FirestoreRecyclerOptions<Publicacion> options, Context contexto) {
        super(options);
        this.contexto = contexto;
    }

    @Override
    protected void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position, @NonNull Publicacion publicacion) {
        holder.titulo.setText(publicacion.titulo);
        holder.contenido.setText(publicacion.contenido);
        holder.fecha.setText(Utility.timestampAString(publicacion.fecha));
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_publicacion_item, parent, false);
        return new PublicacionViewHolder(view);
    }

    class PublicacionViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, contenido, fecha;

        public PublicacionViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.muroPublicacionTitulo);
            contenido = itemView.findViewById(R.id.muroPublicacionContenido);
            fecha = itemView.findViewById(R.id.muroPublicacionFecha);
        }
    }
}
