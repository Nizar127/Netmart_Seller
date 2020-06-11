package com.netmart.netmartseller.ViewHolder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.netmart.netmartseller.Model.history;
import com.netmart.netmartseller.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class historyadapter extends FirestoreRecyclerAdapter<history, historyadapter.historyViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public historyadapter(@NonNull FirestoreRecyclerOptions<history> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull historyViewHolder holder, int position, @NonNull history model) {
        Picasso.get().load(model.getImage_url()).into(holder.actions_pic);
        holder.actionHistory.setText(model.getActionhistory());
        holder.desc.setText(model.getDesc());
        holder.desc.setText(model.getDesc());
    }

    @NonNull
    @Override
    public historyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyview, parent, false);
        return new historyViewHolder(v);
    }

    class historyViewHolder extends RecyclerView.ViewHolder{

        TextView actionHistory, desc, date;
        CircleImageView actions_pic;


        public historyViewHolder(@NonNull View itemView) {
            super(itemView);

            actionHistory = itemView.findViewById(R.id.actionhistory);
            desc = itemView.findViewById(R.id.actionDescription);
            date = itemView.findViewById(R.id.dateHistory);
            actions_pic = itemView.findViewById(R.id.historyPic);

        }
    }
}
