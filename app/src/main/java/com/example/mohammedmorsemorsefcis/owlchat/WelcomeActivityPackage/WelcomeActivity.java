package com.example.mohammedmorsemorsefcis.owlchat.WelcomeActivityPackage;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.mohammedmorsemorsefcis.owlchat.R;
import com.example.mohammedmorsemorsefcis.owlchat.RoomActivityPackage.RoomsActivity;
import com.example.mohammedmorsemorsefcis.owlchat.WelcomeActivityPackage.PageAdapter.CustomFragmentAdapter;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener , View.OnClickListener{
    ViewPager viewPager ;
    CustomFragmentAdapter adapter;
    LinearLayout linearLayout;
    ArrayList<ImageView> dots;
    ImageView Next , Previous ;
    boolean NextTwice=false;
    boolean IsNotFirstOpen;
    SharedPreferences preferences;
    public int PositionOfFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences=PreferenceManager.getDefaultSharedPreferences(this);
        IsNotFirstOpen=preferences.getBoolean("IsNotFirstTime",false);
        setContentView(R.layout.activity_welcome);
        PositionOfFragment=0;
        viewPager=findViewById(R.id.my_view_Pager);
        linearLayout=findViewById(R.id.CustomDots);
        Next=findViewById(R.id.Next);
        Previous=findViewById(R.id.Before);
        Next.setOnClickListener(this);
        Previous.setOnClickListener(this);
        Previous.setVisibility(View.INVISIBLE);
        dots=new ArrayList<>();
        adapter =new CustomFragmentAdapter(getSupportFragmentManager() , getFragments());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        if(IsNotFirstOpen==true){
            startActivity(new Intent(this,RoomsActivity.class));
        }
        CreateDots();
    }

    public ArrayList<Fragment> getFragments(){
        ArrayList<Fragment > fragments=new ArrayList<>();
        fragments.add(new Welcome1());
        fragments.add(new Welcome2());
        return fragments;
    }
    public void CreateDots(){
        for(int i=0;i<2;i++) {
            ImageView view = new ImageView(this);
            if(i==0) {
                view.setBackground(getResources().getDrawable(R.drawable.activate_dots));
            }
            else{
                view.setBackground(getResources().getDrawable(R.drawable.non_activate_dots));

            }
            dots.add(view);
            linearLayout.addView(view);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        PositionOfFragment=i;
        if(i==0){
            dots.get(0).setBackground(getResources().getDrawable(R.drawable.activate_dots));
            dots.get(1).setBackground(getResources().getDrawable(R.drawable.non_activate_dots));
            Next.setBackgroundColor(getResources().getColor(R.color.BUTT));
            Previous.setVisibility(View.INVISIBLE);
            Next.setVisibility(View.VISIBLE);
            Previous.setBackgroundColor(getResources().getColor(R.color.BUTT));
        }
        else{
            dots.get(1).setBackground(getResources().getDrawable(R.drawable.activate_dots));
            dots.get(0).setBackground(getResources().getDrawable(R.drawable.non_activate_dots));
            Next.setBackgroundColor(getResources().getColor(R.color.owlColor1));
            Next.setVisibility(View.INVISIBLE);
            Previous.setVisibility(View.VISIBLE);
            Previous.setBackgroundColor(getResources().getColor(R.color.owlColor1));
        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        int id =v.getId();
        if(id==R.id.Next){
            PositionOfFragment=1;
            if(NextTwice==false){
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
            NextTwice=true;
                }
                else{
                //
                startActivity(new Intent(this , RoomsActivity.class));
            }

        }
        else{
            PositionOfFragment=0;
            viewPager.setCurrentItem(viewPager.getCurrentItem()-1,true);
            NextTwice=false;
            Next.setVisibility(View.VISIBLE);
        }
        Animator animator = ViewAnimationUtils.createCircularReveal(v,v.getWidth()/2,v.getHeight()/2,0, (float) Math.hypot(v.getWidth()/2,v.getHeight()/2));
        animator.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("IndexofFragment",PositionOfFragment);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        PositionOfFragment=savedInstanceState.getInt("IndexofFragment");
        viewPager.setCurrentItem(PositionOfFragment,true);
    }
}
