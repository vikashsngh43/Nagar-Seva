package com.example.googleapiimplimentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        //holder.mobile.setText(model.getMobile());
        holder.address.setText(model.getAddress());
        String s1;
       // holder.status.setText(model.getStatus());
        s1 = model.getStatus();
        Log.e("status-text", "Status: "+s1);
        if(s1.equalsIgnoreCase("Unresolved"))
        {
            holder.status.setText(s1);
            holder.status.setTextColor(Color.RED);
        }
        else
        {
            holder.status.setText(s1);
            holder.status.setTextColor(Color.GREEN);
        }

        holder.pincode.setText(model.getPincode());
        holder.date.setText(model.getDate());

        Glide.with(holder.image.getContext())
                .load(model.getImage())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.image);
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_up);
        holder.itemView.startAnimation(animation);
        //opening full image
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent= new Intent(context,Full_image.class);
                intent.putExtra("image_url",model.getImage());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView name,address,/*mobile*/status,date,pincode;

        public myViewHolder(@NonNull View itemView){
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.name);
            address = (TextView)itemView.findViewById(R.id.address);
            //mobile = (TextView)itemView.findViewById(R.id.mobile);
            status = (TextView)itemView.findViewById((R.id.status));
            date = (TextView) itemView.findViewById(R.id.date);
            pincode = (TextView) itemView.findViewById(R.id.pincode);
        }
    }
}
