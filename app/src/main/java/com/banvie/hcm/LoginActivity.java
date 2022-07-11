package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {

    Button btn_signin;
    EditText edt_username, edt_password;
    CheckBox cbx_remember;
    ImageButton ibt_eye;
    static boolean eye_off = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
        initListener();
    }

    private void initUI() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        btn_signin = findViewById(R.id.btn_sigin);
        edt_password = findViewById(R.id.edt_password);
        edt_username = findViewById(R.id.edt_username);
        cbx_remember = findViewById(R.id.cbx_remeber);
        ibt_eye = findViewById(R.id.ibt_eye);
    }

    private void initListener() {
        ibt_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface cache = edt_password.getTypeface();

                eye_off = !eye_off;
                int type = eye_off ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;
                edt_password.setInputType(type);
                if (eye_off) {
                    ibt_eye.setImageResource(R.drawable.ic_eye_off);
                } else {
                    ibt_eye.setImageResource(R.drawable.ic_eye_on);
                }

                edt_password.setTypeface(cache);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkLogin()) return;
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private boolean checkLogin() {
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        return true;
    }
}