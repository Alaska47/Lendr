package com.akotnana.lendr.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.akotnana.lendr.R;
import com.akotnana.lendr.activities.BaseActivity;
import com.akotnana.lendr.utils.BackendUtils;
import com.akotnana.lendr.utils.VolleyCallback;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.HashMap;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import br.com.simplepass.loading_button_lib.interfaces.OnAnimationEndListener;

public class LoginActivity extends BaseActivity {

    public static String TAG = "LoginActivity";

    private FirebaseAuth mAuth;

    private CircularProgressButton btn;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        TextView logoTitle = findViewById(R.id.logo_title);
        logoTitle.setTypeface(fontManager.getTypeFaceRegular());

        email = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);

        btn = (CircularProgressButton) findViewById(R.id.sign_in);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validate()) {
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                btn.startAnimation();
                btn.setEnabled(false);
                mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                                        @Override
                                        public void onSuccess(GetTokenResult result) {
                                            Log.d("DataStorage", result.getToken());
                                            final String idToken = result.getToken();
                                            BackendUtils.doPostRequest("/api/v1/login", new HashMap<String, String>() {{
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
                                    updateUI(mAuth.getCurrentUser());
                                } else {
                                    btn.revertAnimation();
                                    btn.setEnabled(true);
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(LoginActivity.this, "Failed to login...",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });
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
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult result) {
                    Log.d("DataStorage", result.getToken());
                    final String idToken = result.getToken();
                    BackendUtils.doPostRequest("/api/v1/login", new HashMap<String, String>() {{
                        put("auth_token", idToken);
                    }}, new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            Log.d(TAG, result);
                        }

                        @Override
                        public void onError(VolleyError error) {
                        }
                    }, getApplicationContext());
                }
            });
        }
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser currUser) {
        if(currUser != null) {
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else {
            btn.revertAnimation();
            Toast.makeText(this, "Failed login...", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean validate() {
        boolean valid = true;

        String username = this.email.getText().toString();
        String password = this.password.getText().toString();

        if (username.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btn.dispose();
    }
}
