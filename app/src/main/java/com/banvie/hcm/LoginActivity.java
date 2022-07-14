package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.banvie.hcm.api.Header;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnLoginListener;
import com.banvie.hcm.param.UserParam;

public class LoginActivity extends AppCompatActivity implements OnLoginListener {

    Button btn_signin;
    EditText edt_username, edt_password;
    CheckBox cbx_remember;
    ImageButton ibt_eye, ibt_clear_username;
    TextView tv_check;
    static boolean eye_off = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("token", MODE_PRIVATE);

        Log.d("rrrtoken", preferences.getString("access_token", ""));

        if (preferences != null) {
            Header.ACCESS_TOKEN = preferences.getString("access_token", "");
            Header.TOKEN_TYPE = preferences.getString("token_type", "");
        }

        if (!Header.ACCESS_TOKEN.equals("")) {
            toHomeActivity();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();

        preferences = getSharedPreferences("login", MODE_PRIVATE);
        if (preferences != null) {
            edt_username.setText(preferences.getString("username", getString(R.string.username_hint)));
            edt_password.setText(preferences.getString("password", ""));
        }

        initListener();
    }

    private void initUI() {
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        tv_check = findViewById(R.id.tv_check);
        btn_signin = findViewById(R.id.btn_sigin);
        edt_password = findViewById(R.id.edt_password);
        edt_username = findViewById(R.id.edt_username);
        cbx_remember = findViewById(R.id.cbx_remember);
        ibt_eye = findViewById(R.id.ibt_eye);
        ibt_clear_username = findViewById(R.id.ibt_clear_username);
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

        ibt_clear_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt_username.setText("");
            }
        });

        edt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (edt_username.getText().toString().equals("")) {
                    edt_username.setText(getString(R.string.username_hint));
                }
                int visible = b ? View.VISIBLE : View.INVISIBLE;
                ibt_clear_username.setVisibility(visible);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        cbx_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                if (b) {
                    edt_username.setText(preferences.getString("username", getString(R.string.username_hint)));
                    edt_password.setText(preferences.getString("password", ""));
                    preferences.edit()
                            .putString("username", edt_username.getText().toString())
                            .putString("password", edt_password.getText().toString())
                            .commit();
                } else {
                    preferences.edit().remove("username").remove("password").commit();
                }
            }
        });
    }

    private void checkLogin() {
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        UserParam userParam = new UserParam(username, password);
        RetrofitApi.login(userParam, this);
    }

    private void toHomeActivity() {
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void setOnLoginListener(boolean success) {
        if (success) {
            getSharedPreferences("token", MODE_PRIVATE).edit()
                    .putString("access_token", Header.ACCESS_TOKEN)
                    .putString("refresh_token", Header.REFRESH_TOKEN)
                    .putString("token_type", Header.TOKEN_TYPE)
                    .commit();
                toHomeActivity();
        } else {
            tv_check.setVisibility(View.VISIBLE);
        }
    }
}