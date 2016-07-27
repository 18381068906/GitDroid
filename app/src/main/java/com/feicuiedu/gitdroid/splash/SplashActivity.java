package com.feicuiedu.gitdroid.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.feicuiedu.gitdroid.MainActivity;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.commons.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {
    private ActivityUtils activityUtils;
    @BindView(R.id.btnEnter)Button btnEnter;
    @BindView(R.id.btnLogin)Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
    }
    @OnClick({R.id.btnEnter,R.id.btnLogin})
    void login(View view){
        switch (view.getId()){
            case R.id.btnEnter:
                activityUtils.startActivity(MainActivity.class);
                finish();
                break;
            case R.id.btnLogin:
                Toast.makeText(SplashActivity.this, "登录", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
