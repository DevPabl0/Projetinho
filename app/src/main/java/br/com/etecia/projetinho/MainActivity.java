package br.com.etecia.projetinho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


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
            int id = item.getItemId();
            if (id == R.id.Bottom_Home) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Home()).commit();
                return true;
            } else if (id == R.id.Bottom_Support) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Support()).commit();
                return true;
            } else if (id == R.id.Bottom_Settings) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new Settings()).commit();
                return true;
            }
            return false;
        }
    });




        List<Bicicleta> bicicletas = new ArrayList<>();
        bicicletas.add(new Bicicleta(".", R.drawable.modeloum));
        bicicletas.add(new Bicicleta(".", R.drawable.modelodois));



    }
}