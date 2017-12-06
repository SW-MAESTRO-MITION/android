package com.mition.realty;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class LandingActivity extends AppCompatActivity {

    TextView tv_nav_login, tv_nav_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        tv_nav_login = (TextView) findViewById(R.id.tv_nav_login);
        tv_nav_signup = (TextView) findViewById(R.id.tv_nav_signup);

        tv_nav_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tv_nav_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LandingActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
