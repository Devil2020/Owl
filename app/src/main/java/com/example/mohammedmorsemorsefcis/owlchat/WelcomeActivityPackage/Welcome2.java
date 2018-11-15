package com.example.mohammedmorsemorsefcis.owlchat.WelcomeActivityPackage;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage.RoomsActivity;


public class Welcome2 extends Fragment {
Button StartAct;
Context context;
    public Welcome2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view=inflater.inflate(R.layout.fragment_welcome2, container, false);
        StartAct=view.findViewById(R.id.StartLoginActivity);
        StartAct.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putBoolean("IsNotFirstTime",true);
                editor.apply();
                Animator animator = ViewAnimationUtils.createCircularReveal(v,v.getWidth()/2,v.getHeight()/2,0, (float) Math.hypot(v.getWidth()/2,v.getHeight()/2));
                animator.start();
                startActivity(new Intent(context , RoomsActivity.class));
            }
        });
        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    this.context=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
