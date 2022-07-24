package com.banvie.hcm.bottom_sheet;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;

import com.banvie.hcm.R;
import com.banvie.hcm.listener.OnLoadNotificationsListener;
import com.banvie.hcm.model.notification.Notification;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class OptionNotificationBottomSheet extends BottomSheetDialogFragment {

//    private static final String NOTIFICATION = "option_notification";
    Notification notification;

    ImageView iv_image;
    TextView tv_mark, tv_remove, tv_title;
    OnLoadNotificationsListener listener;

    public OptionNotificationBottomSheet(Notification notification, OnLoadNotificationsListener listener) {
        this.listener = listener;
        this.notification = notification;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.option_notification, null);

        dialog.setContentView(view);

        initUI(view);

        initListener();

        return dialog;
    }

    void initUI(View view) {
        tv_mark = view.findViewById(R.id.tv_mark);
        tv_title = view.findViewById(R.id.tv_title);
        tv_remove = view.findViewById(R.id.tv_remove);
        iv_image = view.findViewById(R.id.iv_image);

        tv_title.setText(HtmlCompat.fromHtml(notification.shortContent, HtmlCompat.FROM_HTML_MODE_COMPACT));
        if (notification.image_bytes == null) {
            iv_image.setImageResource(R.drawable.logo);

        } else {
            iv_image.setImageBitmap(BitmapFactory.decodeByteArray(
                    notification.image_bytes, 0, notification.image_bytes.length));
        }
    }

    private void initListener() {
        tv_mark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!notification.read) {
                    listener.setOnReadNotificationListener(notification);
                }
                dismiss();
            }
        });

        tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.setOnRemoveNotificationListener(notification);
                dismiss();
            }
        });
    }
}
