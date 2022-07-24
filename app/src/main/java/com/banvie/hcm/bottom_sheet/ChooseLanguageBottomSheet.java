package com.banvie.hcm.bottom_sheet;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.banvie.hcm.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Locale;

public class ChooseLanguageBottomSheet extends BottomSheetDialogFragment {

    Context context;
    boolean en;

    RadioGroup rdg_language;

    public ChooseLanguageBottomSheet(Context context) {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
        en = preferences.getBoolean("en", true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_language, container, false);
        initUI(view);
        initListener();
        return view;
    }

    private void initUI(View view) {
        rdg_language = view.findViewById(R.id.rdg_language);
        rdg_language.check(en ? R.id.rd_en : R.id.rd_vn);
    }

    private void initListener() {
        rdg_language.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                SharedPreferences preferences = context.getSharedPreferences("language", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                switch (i) {
//                    case R.id.rd_vn:
//                        editor.putBoolean("en", false);
//                        Resources resources = context.getResources();
//                        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
//                        Configuration configuration = resources.getConfiguration();
//                        configuration.setLocale(new Locale(""));
////                        resources.updateConfiguration();
//                        context.createConfigurationContext(configuration);
//                        break;
//                    case R.id.rd_en:
//                        editor.putBoolean("en", true);
//                        break;
//                }
                dismiss();
            }
        });
    }
}
