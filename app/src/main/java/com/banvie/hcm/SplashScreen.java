package com.banvie.hcm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        getSharedPreferences("token", MODE_PRIVATE).edit().putString("access_token", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJqeTlxNWxUMjU1VVktc3o1M3JIRFFIOUF5dlB2cGV6Z25XQXJfNGNCVHhVIn0.eyJleHAiOjE2NTg4MDEwMDUsImlhdCI6MTY1ODcxNDYwNSwianRpIjoiOWE4ZjI4MTItMmMwZS00YWNiLTkzMGEtNGU2NmQwZTI4NWZjIiwiaXNzIjoiaHR0cDovLzE5Mi4xNjguMjAwLjQxOjMyMDAwL2F1dGgvcmVhbG1zL2hjbS1wcm9kIiwiYXVkIjoiaGNtLWFjY291bnQiLCJzdWIiOiI4OWNmNDc0Mi1jYTdiLTQwY2QtODc2MS1hZmVkNjdmNTQ1MzUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJoY20tYWNjb3VudCIsInNlc3Npb25fc3RhdGUiOiJkMGYxMDBiYi01NTY5LTRkM2YtOWJmMS0wYzhkOGM4YmZiMTQiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly8xOTIuMTY4LjIwMC40MTo4MDgwIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwiYXV0aG9yaXphdGlvbiI6eyJwZXJtaXNzaW9ucyI6W3sicnNpZCI6IjA0Y2FiYjE5LTRjNjEtNDFiOS1iMjMzLTAxNGQ1YTUxMWE1MSIsInJzbmFtZSI6IkRlZmF1bHQgUmVzb3VyY2UifV19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJvcmdUeXBlIjoiTDIiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJOaGF0IFRpbmggUGhhbSIsInRlbmFudElkIjoiMmE2YjNjNDYtYTE1ZS00NGNkLWIxOGUtMWFlOWZlZTNmY2NhIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidGluaC5waGFtLW5oYXQiLCJnaXZlbl9uYW1lIjoiTmhhdCBUaW5oIiwidXNlcklkIjoiODljZjQ3NDItY2E3Yi00MGNkLTg3NjEtYWZlZDY3ZjU0NTM1IiwiZmFtaWx5X25hbWUiOiJQaGFtIiwib3JnSWQiOiIwODQ5M2Q3NC04NDFlLTRkMzgtOTkyNC04MDFmN2UzYTllMGIiLCJlbWFpbCI6InRpbmgucGhhbS1uaGF0QGJhbnZpZW4uY29tLnZuIn0.gTg1_jiYFVt4NSQFsnXKnCMweKLNK-2aAfuWS6k5AjicsSXxe2gcd8M7zbkUl8ZrXYEDVlaL3ACJ8fTbyqI7QJcJXWi2bZMAWvWHLy6-sFPnKLKieMIqDzydyVl0NZctN23fAmDzfu8ZuP4udFPSGv7jRlxPlW8vKHtiVXn2rQA-LX50vhfEOIAX3daSe2qRDEtx-jvDRwVZt5ZWjS4s6i1qK9kFk3O277Q9yeptMwy4D2eo37V-UI63Z1lwgaGIhXPc1rRxdFhwSovB9dXGmly_lTmL1EQaItMIufG8MbqH_JW05V52l8PMJNe3VG3Hih01nhBHovhBKGF7MKIkFg").commit();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        

        ((ImageView) findViewById(R.id.loading)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate));

        new Thread(() -> {
            try {
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }).start();
    }
}