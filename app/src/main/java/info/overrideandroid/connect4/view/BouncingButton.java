package info.overrideandroid.connect4.view;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.FloatRange;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by rahul.m on 27-06-2017.
 */

public class BouncingButton extends android.support.v7.widget.AppCompatImageButton implements View.OnTouchListener {
    float STIFFNESS = SpringForce.STIFFNESS_MEDIUM;
    float DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
    private SpringAnimation xAnimation;
    private SpringAnimation yAnimation;

    public BouncingButton(Context context) {
        super(context);
        initAnimation();
    }


    public BouncingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAnimation();
        setOnTouchListener(this);
    }

    private void initAnimation() {
        xAnimation = createSpringAnimation(
                SpringAnimation.SCALE_X, getScaleX(), STIFFNESS, DAMPING_RATIO);
        yAnimation = createSpringAnimation(
                SpringAnimation.SCALE_Y, getScaleY(), STIFFNESS, DAMPING_RATIO);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                animate().scaleX(getScaleX() *2/ 3)
                        .scaleY(getScaleY() *2/ 3)
                        .setDuration(0)
                        .start();
                xAnimation.cancel();
                yAnimation.cancel();
                break;
            case MotionEvent.ACTION_UP:
                xAnimation.start();
                yAnimation.start();
                break;
        }
        return false;
    }


    SpringAnimation createSpringAnimation(
            DynamicAnimation.ViewProperty property,
            float finalPosition,
            @FloatRange(from = 0.0) Float stiffness,
            @FloatRange(from = 0.0) Float dampingRatio) {
        SpringAnimation animation = new SpringAnimation(this, property);
        SpringForce spring = new SpringForce(finalPosition);
        spring.setStiffness(stiffness);
        spring.setDampingRatio(dampingRatio);
        animation.setSpring(spring);
        return animation;
    }
}
