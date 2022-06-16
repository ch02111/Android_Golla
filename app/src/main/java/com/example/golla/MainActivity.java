package com.example.golla;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FrameLayout homeLayout;
    BottomNavigationView bottomMenu;
    randomFragment randomFragment;
    MapFragment mapFragment;
    HistoryFragment historyFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        randomFragment = new randomFragment();
        mapFragment = new MapFragment();
        historyFragment = new HistoryFragment();
        homeLayout = findViewById(R.id.homeLayout);
        bottomMenu = findViewById(R.id.bottomMenu);
        getSupportFragmentManager().beginTransaction().replace(R.id.homeLayout, randomFragment).commit();
        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.games:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homeLayout, randomFragment).commit();
                        return true;

                    case R.id.map:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homeLayout, mapFragment).commit();
                        return true;

                    case R.id.history:
                        getSupportFragmentManager().beginTransaction().replace(R.id.homeLayout, historyFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }
}