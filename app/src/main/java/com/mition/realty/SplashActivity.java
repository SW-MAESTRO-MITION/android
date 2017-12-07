package com.mition.realty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mition.realty.util.model.User;
import com.mition.realty.util.singleton.SingletonNetwork;
import com.mition.realty.util.singleton.SingletonUser;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", MODE_PRIVATE);
        final String id = sharedPreferences.getString("USER_ID", "");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (id != null && !id.equals("")) {
                    connectionGetUser(id);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LandingActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2500);
    }

    public void connectionGetUser(String id) {
        SingletonNetwork.getInstance().getConnctionUser().getUser(id).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(User user) {
                        SingletonUser.getInstance().setUser(user);
                        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USER_ID", user._id);
                        editor.commit();

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
