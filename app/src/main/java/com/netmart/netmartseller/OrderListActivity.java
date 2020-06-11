package com.netmart.netmartseller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

//import com.firebase.ui.database.
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.netmart.netmartseller.Model.Orders;
import com.netmart.netmartseller.ViewHolder.OrderViewHolder;
//import com.netmart.netmartseller.ViewHolder.OrderAdapter;

import java.text.DecimalFormat;

public class OrderListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BottomNavigationView bottomtabmenu;
    CollectionReference userOrderDetailRef;
    private String userNameAsKey;
   // OrderAdapter orderadapter;
   private float totalPrice;
    DecimalFormat df = new DecimalFormat("#.00");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        bottomtabmenu = findViewById(R.id.bottomtabmenu);
        bottomtabmenu.setSelectedItemId(R.id.orderFragment);

        setupRecyclerView();

        userOrderDetailRef = FirebaseFirestore.getInstance().collection("Order");

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



//        FirestoreRecyclerOptions<Orders> options =
//                new FirestoreRecyclerOptions.Builder<Orders>()
//                .setQuery(userOrderDetailRef, Orders.class).build();
//
//        FirestoreRecyclerAdapter<Orders, OrderViewHolder> adapter = new FirestoreRecyclerAdapter<Orders, OrderViewHolder>(options) {
//
//
//            @NonNull
//            @Override
//            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderlist, viewGroup, false);
//                return new OrderViewHolder(view);
//                //OrderViewHolder holder = new OrderViewHolder(view);
//                //return holder;
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Orders model) {
//                //holder.itemView.findViewById(R.id.orderNameId).setText(model.getName());
//                holder
//
//            }
//        };


    //put onstart to grab the order list if available
    @Override
    protected void onStart() {
        super.onStart();

        Query query = userOrderDetailRef.orderBy("timeorder", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Orders> options =
                new FirestoreRecyclerOptions.Builder<Orders>()
                .setQuery(query, Orders.class)
                .build();

        FirestoreRecyclerAdapter<Orders, OrderViewHolder> adapter = new FirestoreRecyclerAdapter<Orders, OrderViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Orders model) {
                totalPrice = Float.valueOf(model.getTotalAmount());
                userNameAsKey = model.getUsername();

                holder.orderNameId.setText(model.getOrderId());
                holder.orderDeliverer.setText(model.getDelivererName());
                holder.orderItemList.setText(model.getItem_num());
                holder.timeOrder.setText(model.getTimeorder());
                holder.orderTimeArrival.setText(model.getTimearrival());

            }

            @NonNull
            @Override
            public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist, parent, false);
                return new OrderViewHolder(v);
            }
        };

        recyclerView.setAdapter(adapter);
        adapter.startListening();

        //sort by time of order comes
       //orderadapter.startListening();

    }

    private void setupRecyclerView() {


        recyclerView = findViewById(R.id.recyclerOrder);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //recyclerView.setAdapter(orderadapter);


    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//    }
}
