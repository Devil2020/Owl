package com.example.mohammedmorsemorsefcis.owlchat.NetworkDialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage.RoomsActivity;


public class CheckNetworkDialog extends DialogFragment implements View.OnClickListener {
    Button EnableButton ,CancelButton ;
    Context context;
    Activity mainActivity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_check_network_dialog, container, false);
        EnableButton =view.findViewById(R.id.Okay);
        CancelButton =view.findViewById(R.id.Cancel);
        EnableButton.setOnClickListener(this);
        CancelButton.setOnClickListener(this);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        mainActivity= (Activity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.Okay){
            //Start Intent
            Intent intent=new Intent(Settings.ACTION_WIFI_SETTINGS);
            startActivity(intent);
        }
        else{
            mainActivity.finish();
        }
    }
}
