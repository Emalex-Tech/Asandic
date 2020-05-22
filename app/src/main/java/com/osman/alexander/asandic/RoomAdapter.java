package com.osman.alexander.asandic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RoomAdapter extends FirestoreRecyclerAdapter<RoomList, RoomAdapter.RoomHolder> {
    private Context mcontext;
    private Rooms room;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public RoomAdapter(Context context,@NonNull FirestoreRecyclerOptions<RoomList> options,Rooms room) {
        super(options);
        this.mcontext = context;
        this.room = room;
    }

    @Override
    protected void onBindViewHolder(@NonNull RoomHolder holder, int position, @NonNull RoomList model) {
        holder.price.setText(model.getPrice());
        holder.roomies.setText(model.getNumberOfRoomies());
        Picasso.with(mcontext).load(model.getImage_url()).into(holder.roomImage);
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_item,parent
                ,false);
        return new RoomHolder(v);
    }

    class RoomHolder extends RecyclerView.ViewHolder{
        TextView price;
        TextView roomies;
        ImageView roomImage;
        LinearLayout book;

        public RoomHolder(@NonNull View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.roomPrice);
            roomies = itemView.findViewById(R.id.roomyNumber);
            roomImage = itemView.findViewById(R.id.Roomimage);
        }
    }

}
