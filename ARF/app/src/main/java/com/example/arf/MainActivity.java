package com.example.arf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {
    public ZXingScannerView sxscannerview;
    private TextView TxtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                new HomeFragment()).commit();
    }

    /*
        Le bouton lance l'activité qui scan les codes barres

    */

    public void scanner(View view)
    {
        Button button =(Button) view;
        Intent intent = new Intent(MainActivity.this, Scanner.class);
        startActivity(intent);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFrag = null;

                    switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFrag = new HomeFragment();
                            break;
                        case R.id.nav_scan:
                            selectedFrag = new ScannerFragment();
                            break;
                        case R.id.nav_profil:
                            selectedFrag = new ProfilFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,
                            selectedFrag).commit();
                    return true;
                }
            };
}
