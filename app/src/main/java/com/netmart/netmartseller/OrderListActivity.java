package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class OrderListActivity extends AppCompatActivity {

    BottomNavigationView bottomtabmenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        bottomtabmenu = findViewById(R.id.bottomtabmenu);
        bottomtabmenu.setSelectedItemId(R.id.orderFragment);

        bottomtabmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.orderFragment:
                        return true;
                    case R.id.myProductFragment:
                        startActivity(new Intent(getApplicationContext(), MyProductActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.reportFragment:
                        startActivity(new Intent(getApplicationContext(), ReportActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.historyFragment:
                        startActivity(new Intent(getApplicationContext(),HistoryActivity.class));
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
