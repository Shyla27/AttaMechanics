package com.example.attamechanics.Utils;

import android.view.View;

public class Animate {
    public Animate() {
    }

    public void toggleAnimationView(boolean showAnimation, int visibility, View view, View anim) {
        int viewVis = showAnimation ? visibility : View.VISIBLE;
        int animVis = showAnimation ? View.VISIBLE : visibility;

        view.setVisibility(viewVis);
        anim.setVisibility(animVis);
    }
}
