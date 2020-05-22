package com.osman.alexander.asandic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HostelAdapter extends FirestoreRecyclerAdapter<HostelList, HostelAdapter.RoomHolder> {
    private Context mcontext;
    private HostelAdapter.onItemClickListener listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public HostelAdapter(Context context,@NonNull FirestoreRecyclerOptions<HostelList> options) {
        super(options);
        this.mcontext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull RoomHolder holder, int position, @NonNull HostelList model) {
            holder.name.setText(model.getName());
            holder.location.setText(model.getLocation());
            Picasso.with(mcontext).load(model.getImage_url()).into(holder.hostelImage);
    }

    @NonNull
    @Override
    public RoomHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.hostels_item,parent,false);
        return new RoomHolder(v);
    }

    class RoomHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView location;
        ImageView hostelImage;

        public RoomHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.hostelName);
            location = itemView.findViewById(R.id.hostelLocation);
            hostelImage = itemView.findViewById(R.id.n_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener != null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface onItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public  void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
