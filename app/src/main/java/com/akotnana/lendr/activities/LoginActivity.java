package com.akotnana.lendr.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akotnana.lendr.R;
import com.akotnana.lendr.activities.BaseActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

public class LoginActivity extends BaseActivity {

    private CircularProgressButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView logoTitle = findViewById(R.id.logo_title);
        logoTitle.setTypeface(fontManager.getTypeFaceRegular());

        btn = (CircularProgressButton) findViewById(R.id.sign_in);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.startAnimation();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(LoginActivity.this, NavigationActivity.class);
                        startActivity(i);
                        btn.dispose();
                        finish();
                    }
                }, 2000);

                //TODO firebase login, send the deets to rahul
            }
        });



        Button switchToSignUp = findViewById(R.id.switch_sign_in);
        switchToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btn.dispose();
    }
}
