package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyProductActivity extends AppCompatActivity {

    BottomNavigationView bottomtabmenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);

        bottomtabmenu = findViewById(R.id.bottomtabmenu);
        bottomtabmenu.setSelectedItemId(R.id.myProductFragment);

        bottomtabmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.orderFragment:
                        startActivity(new Intent(getApplicationContext(), OrderListActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.myProductFragment:
                        return true;
                    case R.id.reportFragment:
                        startActivity(new Intent(getApplicationContext(), ReportActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.historyFragment:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profileFragment:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}
