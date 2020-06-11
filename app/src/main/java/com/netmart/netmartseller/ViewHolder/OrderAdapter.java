//package com.netmart.netmartseller.ViewHolder;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.netmart.netmartseller.Model.Orders;
//import com.netmart.netmartseller.R;
//
//public class OrderAdapter extends FirestoreRecyclerAdapter<Orders, OrderAdapter.OrderViewHolder> {
//    /**
//     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
//     * FirestoreRecyclerOptions} for configuration options.
//     *
//     * @param options
//     */
//    public OrderAdapter(@NonNull FirestoreRecyclerOptions<Orders> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Orders model) {
//        holder.orderNameId.setText(model.getOrderId());
//        holder.orderItemList.setText(model.getItems());
//        holder.timeOrder.setText(model.getTimeorder());
//        holder.orderTimeArrival.setText(model.getTimearrival());
//    }
//
//    @NonNull
//    @Override
//    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist, parent, false);
//        return new OrderViewHolder(v);
//    }
//
//    class OrderViewHolder extends RecyclerView.ViewHolder{
//
//        TextView orderNameId, orderItemList, orderTimeArrival, timeOrder;
//        Button orderDetailBtn;
//
//        public OrderViewHolder(@NonNull View itemView) {
//            super(itemView);
//            orderNameId = itemView.findViewById(R.id.orderNameId);
//            orderItemList = itemView.findViewById(R.id.orderItemlist);
//            orderTimeArrival = itemView.findViewById(R.id.orderTimeArrival);
//            timeOrder = itemView.findViewById(R.id.timeorder);
//            orderDetailBtn = itemView.findViewById(R.id.orderDetailBtn);
//        }
//    }
//}
