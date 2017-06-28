package info.overrideandroid.connect4.activity;

import android.content.Intent;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import info.overrideandroid.connect4.R;
import info.overrideandroid.connect4.controller.GameMenuController;
import info.overrideandroid.connect4.fragments.BottomSheet3DialogFragment;
import info.overrideandroid.connect4.rules.GameRules;
import info.overrideandroid.connect4.view.MenuView;

public class GameMenuActivity extends AppCompatActivity implements GameMenuController.MenuControllerListener {

    float STIFFNESS = SpringForce.STIFFNESS_MEDIUM;
    float DAMPING_RATIO = SpringForce.DAMPING_RATIO_HIGH_BOUNCY;
    private SpringAnimation xAnimation;
    private SpringAnimation yAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheet3DialogFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

            }
        });
//        MenuView menuView = (MenuView) findViewById(R.id.menuView);
//        GameMenuController gameMenuController =new GameMenuController(this, menuView);
//        menuView.setListeners(gameMenuController);
//        final Button b = (Button)findViewById(R.id.play);
//        b.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                xAnimation = createSpringAnimation(
//                        b, SpringAnimation.SCALE_X, b.getScaleX(), STIFFNESS, DAMPING_RATIO);
//                yAnimation = createSpringAnimation(
//                        b, SpringAnimation.SCALE_Y, b.getScaleY(), STIFFNESS, DAMPING_RATIO);
//
//                b.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });
//
//        final float dX = 0f;
//        final float dY = 0f;
//
//        b.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                switch (event.getActionMasked()) {
//                    case MotionEvent.ACTION_DOWN:
//                        b.animate().scaleX(b.getScaleX()/2)
//                                .scaleY(b.getScaleY()/2)
//                                .setDuration(0)
//                                .start();
//                        xAnimation.cancel();
//                        yAnimation.cancel();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        xAnimation.start();
//                        yAnimation.start();
//                        break;
//                }
//                return true;
//            }
//        });
    }
    SpringAnimation createSpringAnimation(View view,
                                          DynamicAnimation.ViewProperty property,
                                          float finalPosition,
                                          @FloatRange(from = 0.0) Float stiffness,
                                          @FloatRange(from = 0.0) Float dampingRatio) {
        SpringAnimation animation = new SpringAnimation(view, property);
        SpringForce spring = new SpringForce(finalPosition);
        spring.setStiffness(stiffness);
        spring.setDampingRatio(dampingRatio);
        animation.setSpring(spring);
        return animation;
    }
    @Override
    public void onPlay(@NonNull GameRules gameRules) {
        Intent gamePlayIntent = new Intent(this,GamePlayActivity.class);
        gamePlayIntent.putExtras(gameRules.exportTo(new Bundle()));
        startActivity(gamePlayIntent);
    }
}
