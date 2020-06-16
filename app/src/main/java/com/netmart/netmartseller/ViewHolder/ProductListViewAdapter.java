//package com.netmart.netmartseller.ViewHolder;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.netmart.netmartseller.Model.Products;
//import com.netmart.netmartseller.R;
//import com.squareup.picasso.Picasso;
//
//import java.text.DecimalFormat;
//
//public class ProductListViewAdapter extends FirestoreRecyclerAdapter<Products, ProductListViewAdapter.ProductListViewHolder> {
//    DecimalFormat df = new DecimalFormat("#.00");
//    private static final String Onbindview =" OnBind Testing:";
//
//
//    public ProductListViewAdapter(@NonNull FirestoreRecyclerOptions<Products> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull ProductListViewHolder holder, int position, @NonNull Products model) {
//        //Picasso.get().load(model.getProductImage()).into(holder.product_pic);
//                holder.productName.setText(model.getName());
//                holder.productCategory.setText(model.getCategory());
//                holder.productQty.setText(String.valueOf(model.getQty()));
//                //holder.productQty.setText(model.getQty());
//                //holder.productStatus.setText(model.getStatus() ? "Available" : "Not Available");
//                holder.productPrice.setText("Price = RM " + df.format(Float.valueOf(model.getPrice())));
//                Log.v(Onbindview, "OnBind finished");
//
//    }
//
//    @NonNull
//    @Override
//    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.productview, parent, false);
//        return new ProductListViewHolder(v);
//    }
//
//    class ProductListViewHolder extends RecyclerView.ViewHolder{
//
//        public ImageView product_pic;
//        public TextView productName, productCategory, productQty, productPrice;
//
//        public ProductListViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            product_pic = itemView.findViewById(R.id.product_pic);
//            productName = itemView.findViewById(R.id.productName);
//            //productQty = itemView.findViewById(R.id.productQty);
//            productCategory = itemView.findViewById(R.id.productCategory);
//            productQty = itemView.findViewById(R.id.productQty);
//            productPrice = itemView.findViewById(R.id.productPrice);
//
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return super.getItemCount();
//    }
//
//    @Override
//    public void onError(@NonNull FirebaseFirestoreException e) {
//        Log.e("error", e.getMessage());
//    }
//}
//
//
