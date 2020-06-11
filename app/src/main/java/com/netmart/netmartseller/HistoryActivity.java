package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.netmart.netmartseller.Model.Products;
import com.netmart.netmartseller.Model.history;
import com.netmart.netmartseller.ViewHolder.historyadapter;

import java.text.DecimalFormat;

public class HistoryActivity extends AppCompatActivity {

    BottomNavigationView bottomtabmenu;

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CollectionReference userhistoyDetailRef;
    historyadapter histAdapter;
    DecimalFormat df = new DecimalFormat("#.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        bottomtabmenu = findViewById(R.id.bottomtabmenu);
        bottomtabmenu.setSelectedItemId(R.id.historyFragment);

        setupRecyclerView();

        bottomtabmenu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.orderFragment:
                        startActivity(new Intent(getApplicationContext(), OrderListActivity.class));
                        overridePendingTransition(0,0);
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

    private void setupRecyclerView() {

        Query query = userhistoyDetailRef.orderBy("timestamp", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<history> options = new FirestoreRecyclerOptions.Builder<history>()
                .setQuery(query, history.class)
                .build();

        histAdapter = new historyadapter(options);

        recyclerView = findViewById(R.id.productList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        //setting the recyclerview with the layout manager
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(histAdapter);
    }
}
