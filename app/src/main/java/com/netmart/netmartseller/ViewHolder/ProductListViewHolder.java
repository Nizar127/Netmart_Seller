package com.netmart.netmartseller.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.netmart.netmartseller.R;

public class ProductListViewHolder extends RecyclerView.ViewHolder {

    public ImageView product_pic;
    public TextView productName, productCategory, productQty, productStatus, productPrice;


    public ProductListViewHolder(@NonNull View itemView) {
        super(itemView);

            product_pic = itemView.findViewById(R.id.product_pic);
            productName = itemView.findViewById(R.id.productName);
            //productQty = itemView.findViewById(R.id.productQty);
            productCategory = itemView.findViewById(R.id.productCategory);
            productQty = itemView.findViewById(R.id.productQty);
            productPrice = itemView.findViewById(R.id.productPrice);
    }
}
