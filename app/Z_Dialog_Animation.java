import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class Z_Dialog_Animation {
    public static void applyBounceAnimation(View view) {
        view.setScaleX(0.1f);
        view.setScaleY(0.1f);
        view.setAlpha(0.0f);

        float endScale = 1.0f;
        float bounceScale = 1.2f;

        view.animate()
                .scaleX(bounceScale)
                .scaleY(bounceScale)
                .alpha(1.0f)
                .setDuration(100) // Adjust the speed of the bounce
                .setInterpolator(new BounceInterpolator())
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        // Scale back to 1.0 after the bounce animation
                        view.animate()
                                .scaleX(endScale)
                                .scaleY(endScale)
                                .setDuration(300) // Duration for the return animation
                                .setInterpolator(new AccelerateDecelerateInterpolator())
                                .start();
                    }
                })
                .start();
    }
}
