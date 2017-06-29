package info.overrideandroid.connect4.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import info.overrideandroid.connect4.R;

/**
 * Created by rahul.m on 28-06-2017.
 */

public class BottomSheet3DialogFragment extends BottomSheetDialogFragment {
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.fragment_game_mode, null);
        contentView.findViewById(R.id.online).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentView.findViewById(R.id.game_mode).animate().translationX(-contentView.findViewById(R.id.game_mode).
                                getWidth()).setDuration(500).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        contentView.findViewById(R.id.game_mode).setVisibility(View.GONE);
                        contentView.findViewById(R.id.online_mode).setVisibility(View.VISIBLE);
                    }
                }).start();
            }
        });
        dialog.setContentView(contentView);
    }

}
