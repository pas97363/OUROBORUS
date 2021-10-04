package com.example.myapplication;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class MessageAdapter0 extends RecyclerView.Adapter<MessageAdapter0.Viewholder> {

    private Context context;
    private ArrayList<MessageModel> courseModelArrayList;

    // Constructor
    public MessageAdapter0(Context context, ArrayList<MessageModel> courseModelArrayList) {
        this.context = context;
        this.courseModelArrayList = courseModelArrayList;
    }


    @Override
    public MessageAdapter0.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_message, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(MessageAdapter0.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        MessageModel model = courseModelArrayList.get(position);
        holder.fromContactTV.setText(model.getFrom_contact());
        //holder.toContactTV.setText(model.getTo_contact());
        holder.messageTV.setText(AES.decrypt(model.getMessage(),model.getTo_contact()));
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
            fromContactTV = itemView.findViewById(R.id.tvAnimalChild);
            messageTV = itemView.findViewById(R.id.tvAnimalNames);
        }
    }
    public static class AES {

        private static SecretKeySpec secretKey;
        private static byte[] key;

        public static void setKey(String myKey) {
            MessageDigest sha = null;
            try {
                key = myKey.getBytes("UTF-8");
                sha = MessageDigest.getInstance("SHA-1");
                key = sha.digest(key);
                key = Arrays.copyOf(key, 16);
                secretKey = new SecretKeySpec(key, "AES");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        public static String encrypt(String strToEncrypt, String secret) {
            try {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
                }
            } catch (Exception e) {
                System.out.println("Error while encrypting: " + e.toString());
            }
            return null;
        }

        public static String decrypt(String strToDecrypt, String secret) {
            try {
                setKey(secret);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
                }
                else
                    return strToDecrypt;
            } catch (Exception e) {
                System.out.println("Error while decrypting: " + e.toString());
            }
            return null;
        }
    }
}
