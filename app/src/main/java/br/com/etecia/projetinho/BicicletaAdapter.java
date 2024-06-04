package br.com.etecia.projetinho;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BicicletaAdapter extends RecyclerView.Adapter<BicicletaAdapter.BicicletaViewHolder> {

    private final List<Bicicleta> bicicletas;

    public static class BicicletaViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagem;
        public TextView modelo;

        public BicicletaViewHolder(View v) {
            super(v);
            imagem = v.findViewById(R.id.imagem);
            modelo = v.findViewById((R.id.modelo));

        }
    }

    public BicicletaAdapter(List<Bicicleta> bicicletas) {
        this.bicicletas = bicicletas;
    }

    @Override
    public BicicletaAdapter.BicicletaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bicicleta_item, parent, false);
        BicicletaViewHolder bvh = new BicicletaViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(BicicletaViewHolder holder, int position) {
        holder.modelo.setText(bicicletas.get(position).getModelo());
        holder.imagem.setImageResource(bicicletas.get(position).getImagem());
    }

    @Override
    public int getItemCount() {
        return bicicletas.size();
    }
}

