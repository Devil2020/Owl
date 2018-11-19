package com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage;

import android.animation.Animator;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohammedmorsemorsefcis.owlchat.Adapter.RoomsAdapter;
import com.example.mohammedmorsemorsefcis.owlchat.ChatActivityPackage.ChatActivity;
import com.example.mohammedmorsemorsefcis.owlchat.Models.Person;
import com.example.mohammedmorsemorsefcis.owlchat.NetworkDialogs.CheckNetworkDialog;
import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.example.mohammedmorsemorsefcis.owlchat.Models.Room;
import com.example.mohammedmorsemorsefcis.owlchat.PersonActivity.UsersActivity;
import com.example.mohammedmorsemorsefcis.owlchat.Widget.RoomsWidget;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomsActivity extends AppCompatActivity implements RoomInterface {
    AddRoomDialog dialog;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;
    RecyclerView recyclerView;
    RoomsAdapter roomsAdapter;
    LinearLayoutManager manager;
    ArrayList<Room> rooms;
    FirebaseDatabase database;
    DatabaseReference reference;
    ChildEventListener listener;
    Bundle bundle;
    String  UserImage;
    AppWidgetManager appWidgetManager;
    TextView textView;
    Room roomClicked;
    LoginRoomDialog loginRoomDialog;
    int appWidgetIds[];
    FrameLayout frameLayout;
    boolean isConn;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);
        isConn=CheckNetwork();
        setTitle(getResources().getString(R.string.room));
        frameLayout=findViewById(R.id.frame);
        bundle=getIntent().getExtras();
        rooms=new ArrayList<>();
        textView=findViewById(R.id.creatroomtext);
        recyclerView=findViewById(R.id.RoomRecyclerview);
        roomsAdapter=new RoomsAdapter(rooms,this);
        manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(roomsAdapter);
        UserImage=new String ();
        appWidgetManager=AppWidgetManager.getInstance(this);
        appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, RoomsWidget.class));
        hideTextAfterTime(5000,textView);
    }
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public void hideTextAfterTime(int Time , final View view){
   final Slide slide=new Slide();
    slide.setSlideEdge(Gravity.LEFT);
slide.setDuration(3000);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(frameLayout,slide);
                view.setVisibility(View.INVISIBLE);
            }
        };
    Handler handler=new Handler();
    handler.postDelayed(runnable,Time);
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
            firebaseAuth.signOut();
            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
        }
        else {
            startActivity(new Intent(this , UsersActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                Person person=new Person();
                person.setName(user.getDisplayName());
                if(user.getPhotoUrl()==null) {
                    person.setImageUrl("https://photos.google.com/u/0/photo/AF1QipPyWZmA9OcS_zR666tDvgNdf7_wo2BA2sdZra4k");
                    UserImage="https://photos.google.com/u/0/photo/AF1QipPyWZmA9OcS_zR666tDvgNdf7_wo2BA2sdZra4k";
                }
                else{
                    person.setImageUrl(user.getPhotoUrl().toString());
                    UserImage=user.getPhotoUrl().toString();
                }
               reference=database.getReference().child(user.getDisplayName()).child("Data");
               reference.child(getResources().getString(R.string.userinfo)).setValue(person);
                Log.i("Morse", "onActivityResult: ");

            }
            else {
                finish();
                Toast.makeText(this, getResources().getString(R.string.network_problems), Toast.LENGTH_SHORT).show();
            }

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void AddRoom(View view) {
        Animator animator = ViewAnimationUtils.createCircularReveal(view,view.getWidth()/2,view.getHeight()/2,0, (float) Math.hypot(view.getWidth()/2,view.getHeight()/2));
        animator.start();
        dialog=new AddRoomDialog();
        Bundle bundle=new Bundle();
        bundle.putString("UserName",user.getDisplayName());
        dialog.setArguments(bundle);
        dialog.show(getSupportFragmentManager(),"Open Room");
    }
    @Override
    protected void onResume() {
        super.onResume();

           authStateListener = new FirebaseAuth.AuthStateListener() {
               @Override
               public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                   user = firebaseAuth.getCurrentUser();
                   if (user == null) {
                       //Login
                       startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setIsSmartLockEnabled(false).setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build())).build(), 10);
                   } else {
                       // Toast.makeText(RoomsActivity.this, "Welcome " + user.getDisplayName(), Toast.LENGTH_SHORT).show();
                       rooms.clear();
                       roomsAdapter.notifyDataSetChanged();
                   }
               }
           };

           user = FirebaseAuth.getInstance().getCurrentUser();
           firebaseAuth.addAuthStateListener(authStateListener);

        if (user != null) {
            if(bundle==null) {
                reference = database.getReference().child(user.getDisplayName()).child("Rooms");
            }
            else{
              Person person= (Person) bundle.getSerializable("Person");
                reference = database.getReference().child(person.getName()).child("Rooms");
            }
            listener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Room room = dataSnapshot.getValue(Room.class);
                    rooms.add(room);
                    roomsAdapter.notifyDataSetChanged();
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
            };
            reference.addChildEventListener(listener);
        }
       }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreateRoomClicked() {
       dialog.dismiss();
       if(textView.getVisibility()==View.INVISIBLE){
           Slide slide=new Slide();
           slide.setSlideEdge(Gravity.RIGHT);
           TransitionManager.beginDelayedTransition(frameLayout,slide);
           textView.setVisibility(View.VISIBLE);
           hideTextAfterTime(5000,textView);
       }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.list_view_widget);
    }

    @Override
    public void onRoomClicked(Room room) {
     //This Work Good
       /*   Bundle bundle=new Bundle();
        bundle.putSerializable("Room",room);
        //bundle.putSerializable("User",PersonData);
        Intent intent=new Intent(this , ChatActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);*/
      //  Toast.makeText(this, "Room "+room.getRoomName()+ " is Clicked ", Toast.LENGTH_SHORT).show();
        roomClicked=room;
        Bundle bundle=new Bundle();
        bundle.putSerializable("Room",roomClicked);
       if(user.getDisplayName().equals(room.getRoomOwner())){
         Intent intent=new Intent(this , ChatActivity.class);
         intent.putExtras(bundle);
         startActivity(intent);
       }
       else {
           loginRoomDialog = new LoginRoomDialog();
           loginRoomDialog.setArguments(bundle);
           loginRoomDialog.show(getSupportFragmentManager(), "Login in Room");
       }
    }

    @Override
    public void onRoomLoginResult(boolean b) {
          loginRoomDialog.dismiss();
        if(b==true){
            Bundle bundle=new Bundle();
            bundle.putSerializable("Room",roomClicked);
            //bundle.putSerializable("User",PersonData);
            Intent intent=new Intent(this , ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            final AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.errpr_login));
            builder.setIcon(R.drawable.error);
            builder.setPositiveButton(getResources().getText(R.string.login), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("Room",roomClicked);
                    loginRoomDialog.setArguments(bundle);
                    loginRoomDialog.show(getSupportFragmentManager(),"Login in Room");
                }
            });
            builder.setNegativeButton(getResources().getText(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
    }
    public boolean CheckNetwork(){
        boolean isConn =false;
        ConnectivityManager manager= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info=manager.getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
         isConn=true;
        }
        else{
            CheckNetworkDialog networkDialog=new CheckNetworkDialog();
            networkDialog.show(getSupportFragmentManager(),"Open Network Fragment");
       isConn=false;
        }
        return isConn;
    }
}
