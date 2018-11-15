package com.example.mohammedmorsemorsefcis.owlchat.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohammedmorsemorsefcis.owlchat.Models.Message;
import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MessagesAdapter  extends RecyclerView.Adapter<MessagesAdapter.CustomHolder> {
    ArrayList<Message> Messages;
    Context context ;
   String  OwnerName;
    public MessagesAdapter(ArrayList<Message> messages , Context context , String Name){
        Messages =messages;
        this.context=context;
        OwnerName=Name;
    }
    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.chat_recyclerview_item,viewGroup ,false);
        CustomHolder holder=new CustomHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder, int i) {
       if(Messages.get(i).getSender().equals(OwnerName)){
           Picasso.with(context).load(Messages.get(i).getImgUrl()).into(customHolder.UserImageRec);
       }
       else{
           Picasso.with(context).load("https://pbs.twimg.com/profile_images/834093730244079616/0um-zqxI_400x400.jpg").into(customHolder.UserImageRec);
       }
       customHolder.MessageRec.setText(Messages.get(i).getTextMessage());
    }
    @Override
    public int getItemCount() {
        return Messages.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder
    {
        ImageView UserImageRec;
        TextView MessageRec;
        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            UserImageRec=itemView.findViewById(R.id.UserImageChat);
            MessageRec =itemView.findViewById(R.id.MessageChat);

        }
    }
}
