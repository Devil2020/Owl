package com.example.mohammedmorsemorsefcis.owlchat.Adapter;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohammedmorsemorsefcis.owlchat.Models.Person;
import com.example.mohammedmorsemorsefcis.owlchat.PersonActivity.PersonInterface;
import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.CustomHolder> {
ArrayList<Person> persons;
Context context;
PersonInterface personInterface;
public UserAdapter(ArrayList<Person> people , Context context1){
    persons=people;
    context=context1;
    personInterface= (PersonInterface) context1;
}
    @NonNull
    @Override
    public CustomHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View view=LayoutInflater.from(context).inflate(R.layout.person_recyclerview_item,viewGroup , false);
        CustomHolder holder=new CustomHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomHolder customHolder,final int i) {
          customHolder.textView.setText(persons.get(i).getName());
          customHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
              @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
              @Override
              public void onClick(View v) {
                  Animator animator = ViewAnimationUtils.createCircularReveal(v,v.getWidth()/2,v.getHeight()/2,0, (float) Math.hypot(v.getWidth()/2,v.getHeight()/2));
                  animator.start();
                  personInterface.onPersonClick(persons.get(i));
              }
          });
}

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public class CustomHolder extends RecyclerView.ViewHolder{

      TextView textView;
      LinearLayout linearLayout;
        public CustomHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.PersonUserName);
            linearLayout =itemView.findViewById(R.id.PersonLayout);
        }
    }
}
