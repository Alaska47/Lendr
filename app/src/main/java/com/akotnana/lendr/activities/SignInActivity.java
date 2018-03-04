package com.akotnana.lendr.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akotnana.lendr.R;
import com.akotnana.lendr.utils.BackendUtils;
import com.akotnana.lendr.utils.DataStorage;
import com.akotnana.lendr.utils.VolleyCallback;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;
import xyz.belvi.luhn.Luhn;
import xyz.belvi.luhn.cardValidator.models.LuhnCard;
import xyz.belvi.luhn.interfaces.LuhnCallback;
import xyz.belvi.luhn.interfaces.LuhnCardVerifier;

public class SignInActivity extends BaseActivity {

    public static String TAG = "SignInActivity";

    private FirebaseAuth mAuth;
    private CircularProgressButton btn;

    EditText username;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        TextView logoTitle = findViewById(R.id.logo_title);
        logoTitle.setTypeface(fontManager.getTypeFaceRegular());

        username = (EditText) findViewById(R.id.input_name);
        email = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);

        btn = (CircularProgressButton) findViewById(R.id.sign_in);

        final String token = FirebaseInstanceId.getInstance().getToken();
        String realToken = "";
        if(token.equals("")) {
            realToken = new DataStorage(getApplicationContext()).getData("firebaseID");
        } else {
            realToken = token;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                btn.startAnimation();
                btn.setEnabled(false);

                mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(username.getText().toString())
                                            .build();
                                    user.updateProfile(profileUpdates);
                                    FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                                        @Override
                                        public void onSuccess(GetTokenResult result) {
                                            Log.d("DataStorage", result.getToken());
                                            final String idToken = result.getToken();
                                            BackendUtils.doPostRequest("/api/v1/register", new HashMap<String, String>() {{
                                                put("auth_token", idToken);
                                            }}, new VolleyCallback() {
                                                @Override
                                                public void onSuccess(String result) {
                                                    Log.d(TAG, result);
                                                    btn.dispose();
                                                }

                                                @Override
                                                public void onError(VolleyError error) {
                                                    btn.revertAnimation();
                                                }
                                            }, getApplicationContext());
                                        }
                                    });
                                    btn.setEnabled(true);
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    btn.revertAnimation();
                                    btn.setEnabled(true);
                                    Toast.makeText(SignInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }

                                // ...
                            }
                        });
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

    public void updateUI(FirebaseUser currUser) {
        if(currUser != null) {
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            btn.revertAnimation();
            Toast.makeText(this, "Failed to sign up...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btn.dispose();
    }

    public boolean validate() {
        boolean valid = true;

        String username = this.username.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        if (username.isEmpty()) {
            this.username.setError("enter a valid username");
            valid = false;
        } else {
            this.username.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.email.setError("enter a valid email address");
            valid = false;
        } else {
            this.email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            this.password.setError("password too short");
            valid = false;
        } else {
            this.password.setError(null);
        }

        return valid;
    }
}
