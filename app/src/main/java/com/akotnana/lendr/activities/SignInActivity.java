package com.akotnana.lendr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.akotnana.lendr.R;

import xyz.belvi.luhn.Luhn;
import xyz.belvi.luhn.cardValidator.models.LuhnCard;
import xyz.belvi.luhn.interfaces.LuhnCallback;
import xyz.belvi.luhn.interfaces.LuhnCardVerifier;

public class SignInActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView logoTitle = findViewById(R.id.logo_title);
        logoTitle.setTypeface(fontManager.getTypeFaceRegular());

        Button login = findViewById(R.id.sign_up);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO firebase sign up, send the deets to rahul
            }
        });

        final Button showCreditDialog = findViewById(R.id.show_credit_dialog);
        showCreditDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Luhn.startLuhn(SignInActivity.this, new LuhnCallback() {
                    @Override
                    public void cardDetailsRetrieved(Context luhnContext, LuhnCard creditCard, final LuhnCardVerifier cardVerifier) {
                        cardVerifier.startProgress();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cardVerifier.requestOTP(4);
                            }
                        }, 2500);
                    }

                    @Override
                    public void otpRetrieved(Context luhnContext, final LuhnCardVerifier cardVerifier, String otp) {
                        cardVerifier.startProgress();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                cardVerifier.onCardVerified(false, getString(R.string.verification_error), getString(R.string.verification_error));
                            }
                        }, 2500);
                    }

                    @Override
                    public void onFinished(boolean isVerified) {
                        showCreditDialog.setText("CHANGE CREDIT CARD");
                    }
                }, R.style.LuhnStyle);
            }
        });

        Button switchToSignUp = findViewById(R.id.switch_sign_up);
        switchToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
