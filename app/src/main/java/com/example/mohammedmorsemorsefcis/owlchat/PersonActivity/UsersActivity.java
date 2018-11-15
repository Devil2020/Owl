package com.example.mohammedmorsemorsefcis.owlchat.PersonActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.mohammedmorsemorsefcis.owlchat.Adapter.UserAdapter;
import com.example.mohammedmorsemorsefcis.owlchat.Models.Person;
import com.example.mohammedmorsemorsefcis.owlchat.Models.UserData;
import com.example.mohammedmorsemorsefcis.owlchat.PersonActivity.PersonInterface;
import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage.RoomsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity implements PersonInterface {
RecyclerView recyclerView;
ArrayList<Person> people;
FirebaseDatabase database;
DatabaseReference referencea;
    DatabaseReference referenceaData;
    UserAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        setTitle("Users");
        database=FirebaseDatabase.getInstance();
        String Name=FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        referencea=database.getReference();
        recyclerView=findViewById(R.id.PersonRecyclerView);
        people=new ArrayList<>();
       userAdapter=new UserAdapter(people ,this);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        people.clear();
        referencea.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                          String  Name=dataSnapshot.getKey();
                         // Toast.makeText(UsersActivity.this, ""+person.getName(), Toast.LENGTH_SHORT).show();
                         Person person=new Person();
                         person.setName(Name);
                          people.add(person);
                          userAdapter.notifyDataSetChanged();
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
    public void onPersonClick(Person person) {
        Bundle bundle=new Bundle();
        bundle.putBoolean("DispRoomsOfUser",true);
        bundle.putSerializable("Person",person);
        Intent intent=new Intent(this,RoomsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(this, person.getName()+" Clicked", Toast.LENGTH_SHORT).show();
    }
}
