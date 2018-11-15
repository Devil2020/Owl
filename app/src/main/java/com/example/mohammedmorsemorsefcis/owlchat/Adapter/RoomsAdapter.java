package com.example.mohammedmorsemorsefcis.owlchat.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.example.mohammedmorsemorsefcis.owlchat.Models.Room;
import com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage.RoomInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.CustHolder> {
Context context;
ArrayList<Room> rooms;
RoomInterface roomInterface;
List<Drawable> Colors;
    public RoomsAdapter(ArrayList<Room> rooms , Context context ){
    this.rooms=rooms;
    this.context=context;
    this.roomInterface= (RoomInterface) context;
    Colors=Arrays.asList(context.getResources().getDrawable(R.drawable.room_image1),context.getResources().getDrawable(R.drawable.room_image2),context.getResources().getDrawable(R.drawable.room_image3),context.getResources().getDrawable(R.drawable.room_image4));
}
    @NonNull
    @Override
    public CustHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =LayoutInflater.from(context).inflate(R.layout.room_recycler_view_item,viewGroup,false);
        CustHolder holder=new CustHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustHolder custHolder, final int i) {
        //Generate Random Index
        Random random=new Random();
        int position = random.nextInt(4);
       // RoomClicked Listener
        Log.i("Morse","Position is "+position);
    custHolder.RoomLayout.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            Animator animator = ViewAnimationUtils.createCircularReveal(custHolder.RoomLayout,custHolder.RoomLayout.getWidth()/2,custHolder.RoomLayout.getHeight()/2,0, (float) Math.hypot(custHolder.RoomLayout.getWidth()/2,custHolder.RoomLayout.getHeight()/2));
            animator.start();
            roomInterface.onRoomClicked(rooms.get(i));
        }
    });
    // Make a Circular Image
            custHolder.ImageBackgroundColor.setImageDrawable(Colors.get(position));

    //Make a First Char
        char[] chars = rooms.get(i).getRoomName().toCharArray();
        Log.i("Morse",String .valueOf(chars[0]));
        custHolder.zx.setText(String.valueOf(chars[0]));
    //Make Room Name
        custHolder.RoomName.setText(rooms.get(i).getRoomName());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class CustHolder extends RecyclerView.ViewHolder{
    LinearLayout RoomLayout;
    ImageView ImageBackgroundColor;
    TextView RoomName  ;
    TextView zx;
        public CustHolder(@NonNull View itemView) {
            super(itemView);
            RoomLayout = itemView.findViewById(R.id.Room);
            ImageBackgroundColor =itemView.findViewById(R.id.RoomImage );
            RoomName=itemView.findViewById(R.id.RoomName);
           zx=itemView.findViewById(R.id.X);
        }
    }
}
