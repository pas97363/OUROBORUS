package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.Viewholder> {

    private Context context;
    private ArrayList<MessageModel> courseModelArrayList;

    // Constructor
    public MessageAdapter(Context context, ArrayList<MessageModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }


    @Override
    public MessageAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        MessageModel model = courseModelArrayList.get(position);
        //holder.fromContactTV.setText(model.getFrom_contact());
        holder.toContactTV.setText(model.getTo_contact());
        holder.messageTV.setText(model.getMessage());
    }

    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return courseModelArrayList.size();
    }

    // View holder class for initializing of
    // your views such as TextView and Imageview.
    public class Viewholder extends RecyclerView.ViewHolder {
        private TextView fromContactTV,toContactTV,messageTV;

        public Viewholder(View itemView) {
            super(itemView);
            //fromContactTV = itemView.findViewById(R.id.editTextPhone);
            toContactTV = itemView.findViewById(R.id.tvAnimalChild);
            messageTV = itemView.findViewById(R.id.tvAnimalNames);
        }
    }
}
