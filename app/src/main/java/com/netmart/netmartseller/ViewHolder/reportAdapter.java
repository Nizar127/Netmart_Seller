package com.netmart.netmartseller.ViewHolder;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.netmart.netmartseller.Model.Report;
import com.netmart.netmartseller.R;
import com.netmart.netmartseller.ReportActivity;
import com.netmart.netmartseller.ReportDetailActivity;

public class reportAdapter extends FirestoreRecyclerAdapter<Report, reportAdapter.ReportViewHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public reportAdapter(@NonNull FirestoreRecyclerOptions<Report> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReportViewHolder holder, final int position, @NonNull Report model) {
        holder.dateorderId.setText(model.getDate_order());
        holder.orderId.setText(model.getOrderId());
        holder.item_price.setText(model.getItem_price());
        holder.orderItemList.setText(model.getItem_num());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getItemViewType(holder), ReportDetailActivity.class);
               // intent.putExtra("")
            }
        });

    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reportlist, parent, false);
        return new ReportViewHolder(v);
    }

    class ReportViewHolder extends RecyclerView.ViewHolder {

        TextView dateorderId, orderId, orderItemList, timeorder, item_price;
        //ImageView orderDetailBtn;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            dateorderId = itemView.findViewById(R.id.dateOrderId);
            orderId = itemView.findViewById(R.id.orderNameId);
            orderItemList = itemView.findViewById(R.id.orderItemlist);
            timeorder = itemView.findViewById(R.id.timeorder);
            item_price = itemView.findViewById(R.id.priceoforder);
            //orderDetailBtn = itemView.findViewById(R.id.orderDetailBtn);


        }
    }
}
