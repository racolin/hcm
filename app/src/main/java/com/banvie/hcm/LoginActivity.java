package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.banvie.hcm.api.ApiService;
import com.banvie.hcm.api.Constant;
import com.banvie.hcm.api.RetrofitApi;
import com.banvie.hcm.listener.OnLoginListener;
import com.banvie.hcm.model.Token;
import com.banvie.hcm.param.UserParam;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity
//        implements OnLoginListener
{

    private Button btn_signin;
    private EditText edt_username, edt_password;
    private CheckBox cbx_remember;

    private ImageButton ibt_eye, ibt_clear_username;

    private TextView tv_check;
    private boolean eye_off = true;
    private ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("token", MODE_PRIVATE);

        Constant.ACCESS_TOKEN = preferences.getString("access_token", "");
        Constant.TOKEN_TYPE = preferences.getString("token_type", "");
        Constant.REFRESH_TOKEN = preferences.getString("refresh_token", "");

        Log.d("debug:" + "Token", Constant.ACCESS_TOKEN);
// Thêm điều kiện để đăng nhập thành công vì chưa chắc access token đã chính xác
        if (!Constant.ACCESS_TOKEN.equals("")) {
            toHomeActivity();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();

        initListener();
    }

    private void initUI() {

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        
        layout = findViewById(R.id.layout);
        tv_check = findViewById(R.id.tv_check);
        btn_signin = findViewById(R.id.btn_sigin);
        edt_password = findViewById(R.id.edt_password);
        edt_username = findViewById(R.id.edt_username);
        cbx_remember = findViewById(R.id.cbx_remember);
        ibt_eye = findViewById(R.id.ibt_eye);
        ibt_clear_username = findViewById(R.id.ibt_clear_username);

        SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
        if (preferences != null) {
            cbx_remember.setChecked(preferences.getBoolean("remember", false));
//            edt_username.setText(preferences.getString("username", ""));
            edt_username.setText(preferences.getString("username", getString(R.string.username_hint)));
            edt_password.setText(preferences.getString("password", ""));
        }
    }

    private void initListener() {

        layout.setOnClickListener((view) -> {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            });

        ibt_eye.setOnClickListener((view) -> {
                Typeface cache = edt_password.getTypeface();
                eye_off = !eye_off;
                int type = eye_off ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                        : InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

                edt_password.setInputType(type);

                ibt_eye.setImageResource(eye_off ? R.drawable.ic_eye_off : R.drawable.ic_eye_on);

                edt_password.setTypeface(cache);
        });

        ibt_clear_username.setOnClickListener((view) -> {
                edt_username.setText("");
        });

        edt_username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                if (edt_username.getText().toString().equals("")) {
//                    edt_username.setText(getString(R.string.username_hint));
//                }
                int visible = b ? View.VISIBLE : View.INVISIBLE;
                ibt_clear_username.setVisibility(visible);
            }
        });

        btn_signin.setOnClickListener((view) -> {
                checkLogin();
        });

        cbx_remember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                if (b) {
                    preferences.edit()
                            .putBoolean("remember", true)
                            .putString("username", edt_username.getText().toString())
                            .putString("password", edt_password.getText().toString())
                            .commit();
                } else {
                    deleteSharedPreferences("login");
                }
            }
        });
    }

    private void checkLogin() {
        String username = edt_username.getText().toString();
        String password = edt_password.getText().toString();
        UserParam userParam = new UserParam(username, password);
//        RetrofitApi.login(userParam, this);
        ApiService.apiService.login(userParam).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Constant.ACCESS_TOKEN = response.body().access_token;
                    Constant.REFRESH_TOKEN = response.body().refresh_token;
                    Constant.EXPIRE_IN = response.body().expire_in;
                    Constant.TOKEN_TYPE = response.body().token_type;
                    toHomeActivity();
                } else {
                    tv_check.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {

            }
        });
    }

    private void toHomeActivity() {
        getSharedPreferences("token", MODE_PRIVATE).edit()
                .putString("access_token", Constant.ACCESS_TOKEN)
                .putString("refresh_token", Constant.REFRESH_TOKEN)
                .putString("token_type", Constant.TOKEN_TYPE)
                .putLong("expired", (new Date()).getTime() + Constant.EXPIRE_IN)
                .commit();
        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        finish();
    }

//    @Override
//    public void setOnLoginListener(boolean success) {
//        if (success) {
//                toHomeActivity();
//        } else {
//            tv_check.setVisibility(View.VISIBLE);
//        }
//    }
}