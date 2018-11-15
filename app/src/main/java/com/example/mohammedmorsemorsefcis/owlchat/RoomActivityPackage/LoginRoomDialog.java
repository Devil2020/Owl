package com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage;

import android.animation.Animator;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mohammedmorsemorsefcis.owlchat.Models.Room;
import com.example.mohammedmorsemorsefcis.owlchat.R;

public class LoginRoomDialog extends DialogFragment {
Room room;
EditText Password;
FloatingActionButton Check;
RoomInterface roomInterface;
Context context;
    public LoginRoomDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        room=new Room();
        room= (Room) getArguments().getSerializable("Room");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_login_room_dialog, container, false);
        Password=view.findViewById(R.id.LoginRoomPasswordEdit);
        Check =view.findViewById(R.id.Check);
        Check.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Animator animator = ViewAnimationUtils.createCircularReveal(v,v.getWidth()/2,v.getHeight()/2,0, (float) Math.hypot(v.getWidth()/2,v.getHeight()/2));
                animator.start();
                String Passw=Password.getText().toString();
            Password.setText("");
             //   Toast.makeText(context, "My Password is = "+Passw +" , tHE Room Password is "+room.getPassword(), Toast.LENGTH_SHORT).show();
                if(Passw.equals(room.getPassword())){
                    roomInterface.onRoomLoginResult(true);
                }
                else{
                    roomInterface.onRoomLoginResult(false);
                }
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    roomInterface= (RoomInterface) context;
    this.context=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
