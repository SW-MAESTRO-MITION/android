package com.mition.realty;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mition.realty.util.model.User;
import com.mition.realty.util.singleton.SingletonNetwork;
import com.mition.realty.util.singleton.SingletonUser;

import java.util.HashMap;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class SignUpActivity extends AppCompatActivity {

    EditText et_email, et_name, et_password;
    TextView tv_nav_complete, tv_nav_cancel;

    Map map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);

        tv_nav_complete = findViewById(R.id.tv_nav_complete);
        tv_nav_cancel = findViewById(R.id.tv_nav_cancel);

        tv_nav_complete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                map = new HashMap();
                map.put("email", et_email.getText().toString());
                map.put("name", et_name.getText().toString());
                map.put("password", et_password.getText().toString());

                if (et_email.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "이메일을 입력해주세요..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_name.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "이름을 입력해주세요..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (et_password.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "패스워드를 입력해주세요..", Toast.LENGTH_SHORT).show();
                    return;
                }
                connectionCreateUser(map);
            }
        });
        tv_nav_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void connectionCreateUser(Map<String, Object> filed) {
        SingletonNetwork.getInstance().getConnctionUser().createUser(filed).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("gunwoo", e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        SingletonUser.getInstance().setUser(user);
                        SharedPreferences sharedPreferences = getSharedPreferences("SHARED_PREFERENCE", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USER_ID", user._id);
                        editor.commit();

                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
