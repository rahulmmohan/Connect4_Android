package info.overrideandroid.connect4.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.overrideandroid.connect4.R;

/**
 * Created by rahul.m on 28-06-2017.
 */

public class BottomSheet3DialogFragment extends BottomSheetDialogFragment {
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.fragment_bottomsheet3, null);
        contentView.findViewById(R.id.online).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentView.findViewById(R.id.game_mode).setVisibility(View.GONE);
                contentView.findViewById(R.id.online_mode).setVisibility(View.VISIBLE);
            }
        });
        dialog.setContentView(contentView);
    }

}
