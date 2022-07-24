package com.banvie.hcm.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.banvie.hcm.NotificationAndSoundActivity;
import com.banvie.hcm.ProfileActivity;
import com.banvie.hcm.R;
import com.banvie.hcm.adapter.ProfileAdapter;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.bottom_sheet.ChooseLanguageBottomSheet;
import com.banvie.hcm.dialog.SignOutDialog;
import com.banvie.hcm.listener.OnLoadProfileListener;
import com.banvie.hcm.model.education.Education;
import com.banvie.hcm.model.employee_duration.EmployeeDuration;
import com.banvie.hcm.model.shui.Shui;
import com.banvie.hcm.model.summary.Summary;

public class SettingFragment extends Fragment {

    RelativeLayout personal, notify_sounds, language,
            message, help, about, sign_out;

    public SettingFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        initUI(view);

        initListener();

        return view;
    }

    void initUI(View view) {

        sign_out = view.findViewById(R.id.sign_out);
        personal = view.findViewById(R.id.personal);
        notify_sounds = view.findViewById(R.id.notify_sounds);
        language = view.findViewById(R.id.language);
        message = view.findViewById(R.id.message);
        help = view.findViewById(R.id.help);
        about = view.findViewById(R.id.about);

    }

    void initListener() {
        sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new SignOutDialog(getContext());
                dialog.show();
            }
        });

        notify_sounds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NotificationAndSoundActivity.class);
                startActivity(intent);
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseLanguageBottomSheet sheet = new ChooseLanguageBottomSheet(getContext());
                sheet.show(getChildFragmentManager(), sheet.getTag());
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NotificationAndSoundActivity.class);
                startActivity(intent);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NotificationAndSoundActivity.class);
                startActivity(intent);
            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NotificationAndSoundActivity.class);
                startActivity(intent);
            }
        });

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
