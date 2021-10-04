package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageActivity  extends RecyclerView.Adapter<MessageActivity.MessageViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public MessageActivity(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView messageText;

        public MessageViewHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.textView5);
        }
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MessageActivity.MessageViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }

        String message = mCursor.getString(mCursor.getColumnIndex("Messagelogs"));

        holder.messageText.setText(message);

    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor (Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
