package com.netmart.netmartseller.Interface;

import android.view.View;


//To move user to the details of the product
public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);
}
