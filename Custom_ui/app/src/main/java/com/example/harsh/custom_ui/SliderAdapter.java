package com.example.harsh.custom_ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;
    public SliderAdapter(Context context)
    {
        this.context=context;
    }
    public int[] slide_images=
            {
              R.drawable.intrologo,
              R.drawable.notes,
                    R.drawable.books
            };
    public String[] slide_heading =
            {
              "WELCOME TO PREPSTUDY",
              "SEMESTER NOTES",
              "SELL YOUR BOOKS"
            };
    public String[] slide_desc =
            {
              "College Based Education App.",
                    "Missed Your Lectures ?"+"\n\n"+"Don't worry , prepare for exams "+"\n\n"+"by notes of PrepStudy.",
                    "Want to sell your college books?"+"\n\n"+"Now sell your books using PrepStudy"+"\n\n"+"Online Payment System Provided."
            };
    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView imglyt = (ImageView) view.findViewById(R.id.imglyt);
        TextView txthead = (TextView) view.findViewById(R.id.txthead);
        TextView tstdesc = (TextView) view.findViewById(R.id.txtdesc);

        imglyt.setImageResource(slide_images[position]);
        txthead.setText(slide_heading[position]);
        tstdesc.setText(slide_desc[position]);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       View v = (View) object;
        container.removeView(v);
    }
}
