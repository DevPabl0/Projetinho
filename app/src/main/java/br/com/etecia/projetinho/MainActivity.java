package br.com.etecia.projetinho;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import br.com.etecia.projetinho.Bicicleta;
import br.com.etecia.projetinho.BicicletaAdapter;
import br.com.etecia.projetinho.R;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Bicicleta> bicicletas = new ArrayList<>();
        bicicletas.add(new Bicicleta(".", R.drawable.modeloum));
        bicicletas.add(new Bicicleta(".", R.drawable.modelodois));

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new BicicletaAdapter(bicicletas);
        recyclerView.setAdapter(mAdapter);
    }
}