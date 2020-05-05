package com.example.asecingorderapplication;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetFragment extends BottomSheetDialogFragment {
    AlphabetOrder alphabetOrder;
    //Bottom Sheet Callback
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        //Get the content View
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_sheet, null);
        dialog.setContentView(contentView);

        TextView mTvAsecding = contentView.findViewById(R.id.tv_acending);
        TextView mTvDeAsecding = contentView.findViewById(R.id.tv_deacending);
        LinearLayout mlAsecending = contentView.findViewById(R.id.ll_asecending);
        LinearLayout mldesecending = contentView.findViewById(R.id.ll_decending);

        //Set the coordinator layout behavior
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        //Set callback
        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(mBottomSheetBehaviorCallback);
        }

        mldesecending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alphabetOrder.desecndingOrder();
                dismiss();
            }
        });

        mlAsecending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alphabetOrder.asecndingOrder();
                dismiss();
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public void setAlphabetOrder(AlphabetOrder alphabetOrder) {
        this.alphabetOrder = alphabetOrder;
    }

    public interface AlphabetOrder{
        void asecndingOrder();
        void desecndingOrder();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            alphabetOrder = (AlphabetOrder)context;
        }catch (ClassCastException ex){
            throw  new ClassCastException(context.toString());
        }

    }
}
