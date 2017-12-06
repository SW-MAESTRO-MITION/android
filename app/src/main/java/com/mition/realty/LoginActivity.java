package com.mition.realty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mition.realty.util.model.User;
import com.mition.realty.util.singleton.SingletonNetwork;
import com.mition.realty.util.singleton.SingletonUser;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class LoginActivity extends AppCompatActivity {

    EditText et_email, et_password;
    TextView tv_nav_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        tv_nav_login = findViewById(R.id.tv_nav_login);

        tv_nav_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map field = new HashMap();
                field.put("email", et_email.getText().toString());
                field.put("password", et_email.getText().toString());

                connectionLogin(field);
            }
        });
    }

    public void connectionLogin(Map<String, Object> filed) {
        SingletonNetwork.getInstance().getConnctionUser().login(filed).observeOn(AndroidSchedulers.mainThread())
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

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}