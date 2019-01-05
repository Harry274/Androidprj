package com.example.harsh.custom_ui;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Intro extends AppCompatActivity  {

    private LinearLayout mdotlyt;
    private ViewPager pagerlyt;
    private TextView mDots[];
    private Button btnBck ,btnNxt;
    private int mCurrpage;
     @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introdemo);
        mdotlyt = (LinearLayout) findViewById(R.id.dot);
        pagerlyt = (ViewPager) findViewById(R.id.pager);
        btnBck = (Button) findViewById(R.id.btnBck);
        btnNxt = (Button) findViewById(R.id.btnNxt);
        pagerlyt.setAdapter(new SliderAdapter(this));
        addDots(0);
        pagerlyt.addOnPageChangeListener(viewListener);

        //OnClickListenerButton
         btnNxt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(mCurrpage==mDots.length-1)
                 {
                     Intent i = new Intent(Intro.this,MainActivity2.class);
                     startActivity(i);
                     finish();
                 }
                 else
                 {
                     pagerlyt.setCurrentItem(mCurrpage+1);
                 }

             }
         });

         btnBck.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 pagerlyt.setCurrentItem(mCurrpage-1);
             }
         });
    }

    public void addDots(int position)
    {
        mDots = new TextView[3];
        mdotlyt.removeAllViews();
        for(int i=0;i<mDots.length;i++)
        {
            mDots[i] =new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparentWhite));
            mdotlyt.addView(mDots[i]);
        }

        if(mDots.length>0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }
    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDots(i);
            mCurrpage = i;
            if(i==0)
            {
                btnNxt.setEnabled(true);
                btnBck.setEnabled(false);
                btnBck.setVisibility(View.INVISIBLE);

                btnNxt.setText("Next");
                btnBck.setText("");

            }
            else if(i==mDots.length-1)
            {
                btnNxt.setEnabled(true);
                btnBck.setEnabled(true);
                btnBck.setVisibility(View.VISIBLE);

                btnNxt.setText("Finish");
                btnBck.setText("Back");
            }
            else
            {
                btnNxt.setEnabled(true);
                btnBck.setEnabled(true);
                btnBck.setVisibility(View.VISIBLE);

                btnNxt.setText("Next");
                btnBck.setText("Back");

            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}


