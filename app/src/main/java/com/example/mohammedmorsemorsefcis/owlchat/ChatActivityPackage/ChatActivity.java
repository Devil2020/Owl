package com.example.mohammedmorsemorsefcis.owlchat.ChatActivityPackage;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Toast;

import com.example.mohammedmorsemorsefcis.owlchat.Adapter.MessagesAdapter;
import com.example.mohammedmorsemorsefcis.owlchat.Models.Message;
import com.example.mohammedmorsemorsefcis.owlchat.Models.Room;
import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.example.mohammedmorsemorsefcis.owlchat.databinding.ActivityChatBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
   FirebaseDatabase database;
   DatabaseReference reference;
   ActivityChatBinding binding;
    String User ;
    String UserImage ;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<Message> messages;
    MessagesAdapter adapter;
    FirebaseAuth Auth;
    Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        binding=DataBindingUtil.setContentView(this,R.layout.activity_chat);
        messages=new ArrayList<>();
        User = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        room = (Room) getIntent().getExtras().getSerializable("Room");
        Toast.makeText(this, ""+room.getRoomOwner(), Toast.LENGTH_SHORT).show();
        setTitle(room.getRoomName());
        adapter=new MessagesAdapter(messages , this ,room.getRoomOwner());
        binding.ChatRecyclerview.setAdapter(adapter);
        binding.ChatRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference().child(room.getRoomOwner()).child("Chat"+room.getRoomName());
        Auth=FirebaseAuth.getInstance();
       Uri Image=Auth.getCurrentUser().getPhotoUrl();
        if(Image==null){
            UserImage="https://pbs.twimg.com/profile_images/834093730244079616/0um-zqxI_400x400.jpg";
        }
        else{
            UserImage=FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString();
        }
        database=FirebaseDatabase.getInstance();
        reference=database.getReference().child(room.getRoomOwner()).child("Chat"+room.getRoomName());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_logout){
            //Logout
            Auth.signOut();
            finish();
            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        messages.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                      Message message=dataSnapshot.getValue(Message.class);
                      messages.add(message);
                      adapter.notifyDataSetChanged();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SendMessage(View view) {
        Animator animator = ViewAnimationUtils.createCircularReveal(view,view.getWidth()/2,view.getHeight()/2,0, (float) Math.hypot(view.getWidth()/2,view.getHeight()/2));
        animator.start();
        Message message=new Message();
        String User = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        message.setTextMessage(binding.Message.getText().toString());
        message.setSender(User);
        message.setImgUrl(UserImage);
        reference.push().setValue(message);
        binding.Message.setText(null);
    }

}
