package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.util.LogTime;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.LogDescriptor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.netmart.netmartseller.Model.Products;
import com.netmart.netmartseller.Model.Users;
import com.netmart.netmartseller.ViewHolder.ProductListViewHolder;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class MyProductActivity extends AppCompatActivity {

    BottomNavigationView bottomtabmenu;
    FloatingActionButton fab;
    //FirebaseFirestore firestoreDB;
    Users users;
    RecyclerView recyclerView;
    //ProductListAdapter productList;
    RecyclerView.LayoutManager layoutManager;
    //CollectionReference my_productRef;
    FirebaseFirestore my_productRef;
    DecimalFormat df = new DecimalFormat("#.00");
    private static final String TAG = "MyProductActivity";
    String adapterStart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_product);

        bottomtabmenu = findViewById(R.id.bottomtabmenu);
        bottomtabmenu.setSelectedItemId(R.id.myProductFragment);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyProductActivity.this, AddProductActivity.class);
                startActivity(intent);
            }
        });

        my_productRef = FirebaseFirestore.getInstance();
        setupRecyclerView();

        //my_productRef = FirebaseFirestore.getInstance().collection("Products");



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

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //Query query = my_productRef.orderBy("name", Query.Direction.ASCENDING);
//        Query query = my_productRef.collection("Products").orderBy("pid", Query.Direction.ASCENDING);
//
//        FirestoreRecyclerOptions<Products> options =
//                new FirestoreRecyclerOptions.Builder<Products>()
//                .setQuery(query, Products.class)
//                .build();
//
//        FirestoreRecyclerAdapter<Products, ProductListViewHolder> adapter = new FirestoreRecyclerAdapter<Products, ProductListViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull ProductListViewHolder holder, int position, @NonNull final Products model) {
//                Picasso.get().load(model.getImage()).into(holder.product_pic);
//                holder.productName.setText(model.getName());
//                holder.productCategory.setText(model.getCategory());
//                holder.productQty.setText(String.valueOf(model.getQty()));
//                //holder.productQty.setText(model.getQty());
//                //holder.productStatus.setText(model.getStatus() ? "Available" : "Not Available");
//                holder.productPrice.setText("Price = RM " + df.format(Float.valueOf(model.getPrice())));
//                Log.d(TAG, "MyproductActivity: " );
//                //Log
//
//                //go to details
//
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MyProductActivity.this, ProductDetailsActivity.class);
//                        intent.putExtra("pid", model.getPid());
//                        startActivity(intent);
//                    }
//                });
//
//
//            }
//
//
//
//            @NonNull
//            @Override
//            public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.productview, parent, false);
//                return new ProductListViewHolder(v);
//            }
//
//
//        };
//
//        recyclerView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        adapter.startListening();
//
//
//        //productList.startListening();
//    }



    private void setupRecyclerView() {

        Query query = my_productRef.collection("Products").orderBy("pid", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Products> options =
                new FirestoreRecyclerOptions.Builder<Products>()
               //.setLifecycleOwner(this)
                .setQuery(query, Products.class)
                .build();

        FirestoreRecyclerAdapter<Products, ProductListViewHolder> adapter = new FirestoreRecyclerAdapter<Products, ProductListViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductListViewHolder holder, int position, @NonNull Products model) {
                Picasso.get().load(model.getImage()).into(holder.product_pic);
                holder.productName.setText(model.getName());
                holder.productCategory.setText(model.getCategory());
                holder.productQty.setText(String.valueOf(model.getQty()));
                //holder.productQty.setText(model.getQty());
                //holder.productStatus.setText(model.getStatus() ? "Available" : "Not Available");
                holder.productPrice.setText("Price = RM " + df.format(Float.valueOf(model.getPrice())));
                Log.d(TAG, "MyproductActivity: " );

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MyProductActivity.this, ProductDetailsActivity.class);
                        intent.putExtra("pid", model.getPid());
                        startActivity(intent);
                    }
                });

            }

            @NonNull
            @Override
            public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.productview, parent, false);
                return new ProductListViewHolder(v);
            }
        };



        //define recyclerview
        recyclerView = findViewById(R.id.productList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        //setting the recyclerview with the layout manager
        recyclerView.setLayoutManager(layoutManager);

//        adapter.startListening();
        //recyclerView.setAdapter(productList);

//        if(adapter != null)
//        {
//            adapter.startListening();
//        } else{
//            adapter.stopListening();
//        }


    }


}
