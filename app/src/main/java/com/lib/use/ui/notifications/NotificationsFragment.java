package com.lib.use.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lib.android.ui.dialog.RxDialogEditSureCancel;
import com.lib.android.ui.dialog.RxDialogSure;
import com.lib.android.ui.dialog.RxDialogSureCancel;
import com.lib.use.R;
import com.lib.use.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                binding.textNotifications.setText(s);
            }
        });

        binding.btnShowDialog.setOnClickListener(view -> showDialogSure());
        binding.button.setOnClickListener(view -> showDialogSureCancel());
        binding.button2.setOnClickListener(view -> showDialogEdit());
        return binding.getRoot();
    }

    RxDialogEditSureCancel rxDialogEditSureCancel;
    private void showDialogEdit(){
        if (rxDialogEditSureCancel == null)
            rxDialogEditSureCancel = new RxDialogEditSureCancel(getActivity());
        rxDialogEditSureCancel.setTitle("Edit");
        rxDialogEditSureCancel.setSureListener(view -> {
            String msg = rxDialogEditSureCancel.getContentView().getText().toString();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            rxDialogEditSureCancel.dismiss();
        });
        rxDialogEditSureCancel.show();

    }

    RxDialogSureCancel rxDialogSureCancel;
    private void showDialogSureCancel() {
        if (rxDialogSureCancel == null) rxDialogSureCancel = new RxDialogSureCancel(getActivity());
        rxDialogSureCancel.setContent("我爱中国天安门");
        rxDialogSureCancel.setCanceledOnTouchOutside(false);
        rxDialogSureCancel.setSureListener(view -> {
            String msg = rxDialogSureCancel.getContentView().getText().toString();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            rxDialogSureCancel.dismiss();
        });
        rxDialogSureCancel.setSure("是");
        rxDialogSureCancel.setCancel("否");
        rxDialogSureCancel.show();
    }

    RxDialogSure rxDialogSure;
    private void showDialogSure() {
        if (rxDialogSure == null){
            rxDialogSure = new RxDialogSure(getActivity());
        }
        rxDialogSure.setTitle("Title");
        rxDialogSure.setContent("测试Dialog测试Dialog测试Dialog测试Dialog测试Dialog测试Dialog测试Dialog测试Dialog");

        rxDialogSure.setSureListener(view -> {
            String msg = rxDialogSure.getContentView().getText().toString();
            Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            rxDialogSure.dismiss();
        });

        rxDialogSure.show();
    }
}