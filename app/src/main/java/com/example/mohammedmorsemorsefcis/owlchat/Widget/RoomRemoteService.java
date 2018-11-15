package com.example.mohammedmorsemorsefcis.owlchat.Widget;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.mohammedmorsemorsefcis.owlchat.Models.Room;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomRemoteService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RoomRemoteServiceFactory();
    }
    public class RoomRemoteServiceFactory implements RemoteViewsFactory {
        ArrayList<Room> rooms;
        FirebaseDatabase database;
        DatabaseReference reference;
        @Override
        public void onCreate() {
             rooms=new ArrayList<>();
             database=FirebaseDatabase.getInstance();
             reference=database.getReference().child(FirebaseAuth.getInstance().getCurrentUser().getDisplayName()).child("Rooms");

        }

        @Override
        public void onDataSetChanged() {
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                               rooms.add(dataSnapshot.getValue(Room.class));
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return rooms.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName()
                    , android.R.layout.simple_list_item_1);
            remoteViews.setTextViewText(android.R.id.text1,rooms.get(position).getRoomName());
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
