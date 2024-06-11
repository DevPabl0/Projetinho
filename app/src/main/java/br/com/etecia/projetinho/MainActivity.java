package br.com.etecia.projetinho;

import static br.com.etecia.projetinho.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TableLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.etecia.projetinho.Bicicleta;
import br.com.etecia.projetinho.BicicletaAdapter;
import br.com.etecia.projetinho.R;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    TabLayout TabLayout;
    ViewPager2 ViewPager;
    ViewPagerAdapter ViewPagerAdapter;
    BottomNavigationView BottomNavigation;
    FrameLayout FrameLayout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout = findViewById(R.id.TabLayout);
        ViewPager = findViewById(R.id.ViewPager);
        ViewPagerAdapter = new ViewPagerAdapter(this);
        ViewPager.setAdapter(ViewPagerAdapter);
        BottomNavigation = findViewById(R.id.BottomNavigation);
        FrameLayout = findViewById(R.id.FrameLayout);
        TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(com.google.android.material.tabs.TabLayout.Tab tab) {
                ViewPager.setVisibility(View.VISIBLE);
                FrameLayout.setVisibility(View.GONE);
                ViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(com.google.android.material.tabs.TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(com.google.android.material.tabs.TabLayout.Tab tab) {
                ViewPager.setVisibility(View.GONE);
                FrameLayout.setVisibility(View.VISIBLE);
            }
        });

    ViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            switch (position){
                case 0:
                case 1:
                case 2:
                    TabLayout.getTabAt(position).select();
            }
            super.onPageSelected(position);
        }
    });
    BottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FrameLayout.setVisibility(View.VISIBLE);
            ViewPager.setVisibility(View.GONE);
            switch (item.getItemId()){
                case R.id.Bottom_Home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Home()).commit();
                    return true;
                case R.id.Bottom_Support:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Support()).commit();
                    return true;
                case R.id.Bottom_Settings:
                    getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Settings()).commit();
                    return true;
            }
            return false;
        }
    });



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