package com.lib.android.ui.dialog;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.lib.android.ui.R;
import com.lib.android.ui.databinding.DialogSureBinding;
import com.lib.android.ui.databinding.DialogSureCancelBinding;


/**
 * Created by ak on 2016/7/19.
 * Mainly used for confirmation and cancel.
 */
public class RxDialogSureCancel extends RxDialog {
    private DialogSureCancelBinding binding;
    private View.OnClickListener mListener = view -> {
        dismiss();
    };

    public RxDialogSureCancel(Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }

    public RxDialogSureCancel(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    public RxDialogSureCancel(Context context) {
        super(context);
        initView();
    }

    public RxDialogSureCancel(Context context, float alpha, int gravity) {
        super(context, alpha, gravity);
        initView();
    }

    public TextView getTitleView() {
        return binding.tvTitle;
    }

    public TextView getSureView() {
        return binding.tvSure;
    }

    public void setSureListener(View.OnClickListener listener) {
        binding.tvSure.setOnClickListener(listener);
    }

    public void setCancelListener(View.OnClickListener listener) {
        binding.tvCancel.setOnClickListener(listener);
    }

    public TextView getContentView() {
        return binding.tvContent;
    }

    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    public void setSure(String content) {
        binding.tvSure.setText(content);
    }

    public void setCancel(String content) {
        binding.tvCancel.setText(content);
    }

    public void setContent(String str) {
        binding.tvContent.setText(str);
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_sure_cancel, null);
        binding = DialogSureCancelBinding.bind(dialogView);
        binding.tvTitle.setTextIsSelectable(true);
        binding.tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        binding.tvContent.setTextIsSelectable(true);
        binding.tvSure.setOnClickListener(mListener);
        binding.tvCancel.setOnClickListener(mListener);
        setContentView(binding.getRoot());
    }

}
