//package com.netmart.netmartseller.ViewHolder;
//
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
//import com.netmart.netmartseller.Model.Products;
//import com.netmart.netmartseller.R;
//import com.squareup.picasso.Picasso;
//
//import java.text.DecimalFormat;
//
//public class ProductListAdapter extends FirestoreRecyclerAdapter<Products, ProductListAdapter.ProdViewHolder> {
//    /**
//     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
//     * FirestoreRecyclerOptions} for configuration options.
//     *
//     * @param options
//     */
//    DecimalFormat df = new DecimalFormat("#.00");
//
//
//    public ProductListAdapter(@NonNull FirestoreRecyclerOptions<Products> options) {
//        super(options);
//    }
//
//    @Override
//    protected void onBindViewHolder(@NonNull ProdViewHolder holder, int position, @NonNull Products model) {
//        //retrieve data into product_pic
//        Picasso.get().load(model.getImage()).into(holder.product_pic);
//        holder.productName.setText(model.getName());
//        holder.productQty.setText(model.getQty());
//        holder.productStatus.setText(model.getStatus() ? "Available" : "Not Available");
//        holder.productPrice.setText("Price = RM " + df.format(Float.valueOf(model.getPrice())));
//
//    }
//
//    @NonNull
//    @Override
//    public ProdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.productview, parent, false);
//        return new ProdViewHolder(v);
//    }
//
//    class ProdViewHolder extends RecyclerView.ViewHolder{
//
//        ImageView product_pic;
//        TextView productName, productQty, productStatus, productPrice;
//
//        public ProdViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            product_pic = itemView.findViewById(R.id.product_pic);
//            productName = itemView.findViewById(R.id.productName);
//            productQty = itemView.findViewById(R.id.productQty);
//            productStatus = itemView.findViewById(R.id.productQty);
//            productPrice = itemView.findViewById(R.id.productPrice);
//        }
//    }
//}
