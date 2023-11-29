package com.example.baybay;

import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class Z_Dialogs_Animation {
    public static void applyBounceAnimation(View view) {
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f);

        float endScale = 1.0f;
        float bounceScale = 1.2f;

        view.animate()
                .scaleX(bounceScale)
                .scaleY(bounceScale)
                .alpha(1.0f)
                .setDuration(100) // Adjust the speed of the bounce
                .setInterpolator(new BounceInterpolator())
                .withEndAction(() -> {
                    // Scale back to 1.0 after the bounce animation
                    view.animate()
                            .scaleX(endScale)
                            .scaleY(endScale)
                            .setDuration(200) // Duration for the return animation
                            .setInterpolator(new AccelerateDecelerateInterpolator())
                            .start();
                })
                .start();
    }

    public static void applyZoomInAnimationMore(View view) {
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);

        float endScale = 1.0f;

        view.animate()
                .scaleX(endScale)
                .scaleY(endScale)
                .setDuration(300) // Adjust the duration of the zoom-in animation
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

}
