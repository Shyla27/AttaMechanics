package com.example.attamechanics.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Font {
    public Typeface saralaRegular;
    public Typeface saralaBold;


    public Font(Context context) {
//        saralaRegular = Typeface.createFromAsset(context.getAssets(), "font/sarala-regular.ttf");
//        saralaBold = Typeface.createFromAsset(context.getAssets(), "font/sarala-bold.ttf");

           // saralaRegular = Typeface.createFromAsset(context.getAssets(), "font/sarala_regular.ttf");


        //Typeface saralaRegular = Typeface.createFromAsset(context.getAssets(), "fonts/sarala_regular.ttf");

    }

    public void applyFont(View view, Typeface typeface) {
        if (view instanceof TextView) {
            ((TextView) view).setTypeface(typeface);
        } else if (view instanceof TextInputLayout) {
            ((TextInputLayout) view).setTypeface(typeface);
        }
    }
}
