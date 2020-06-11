package com.netmart.netmartseller.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netmart.netmartseller.R;

import java.text.BreakIterator;

public class OrderViewHolder extends RecyclerView.ViewHolder {

   public  TextView orderNameId, orderItemList, orderTimeArrival, timeOrder, orderDeliverer;
   public Button orderDetailBtn;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        orderNameId = itemView.findViewById(R.id.orderNameId);
            orderItemList = itemView.findViewById(R.id.orderItemlist);
            orderTimeArrival = itemView.findViewById(R.id.orderTimeArrival);
            timeOrder = itemView.findViewById(R.id.timeorder);
            orderDetailBtn = itemView.findViewById(R.id.orderDetailBtn);
            orderDeliverer = itemView.findViewById(R.id.orderDelivererName);
    }
}
