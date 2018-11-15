package com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage;

import android.animation.Animator;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.example.mohammedmorsemorsefcis.owlchat.Models.Room;
import com.example.mohammedmorsemorsefcis.owlchat.databinding.FragmentAddRoomDialogBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddRoomDialog extends DialogFragment {
    FragmentAddRoomDialogBinding binding;
    LinearLayout layout;
    Context context;
    RoomInterface roomInterface;
    FirebaseDatabase database;
    DatabaseReference reference , go;
    String UserName;
    public AddRoomDialog() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        Bundle bundle=getArguments();
        UserName = bundle.getString("UserName");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_room_dialog, container, false);
        binding =DataBindingUtil.bind(view);
        binding.Create.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
//                database.setPersistenceEnabled(true);
                Animator animator = ViewAnimationUtils.createCircularReveal(v,v.getWidth()/2,v.getHeight()/2,0, (float) Math.hypot(v.getWidth()/2,v.getHeight()/2));
                animator.start();
                reference=database.getReference().child(UserName).child("Rooms").child(binding.RoomNameEdit.getText().toString());
                Room room=new Room();
                room.setRoomName(binding.RoomNameEdit.getText().toString());
                room.setPassword(binding.RoomPasswordEdit.getText().toString());
                room.setRoomOwner(UserName);
                room.setRemeberMe(binding.RemebermeCheck.isChecked());
                reference.setValue(room);
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
                roomInterface.onCreateRoomClicked();
            }
        });
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
this.context=(RoomsActivity)context;
this.roomInterface=(RoomInterface) context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
