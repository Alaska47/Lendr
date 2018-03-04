package com.akotnana.lendr.activities;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.akotnana.lendr.R;
import com.akotnana.lendr.fragments.BrowseFragment;
import com.akotnana.lendr.fragments.DashboardFragment;
import com.akotnana.lendr.fragments.LoansFragment;
import com.akotnana.lendr.fragments.ProfileFragment;
import com.akotnana.lendr.utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;

public class NavigationActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationView);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title1);

        ImageButton newLoanRequest = (ImageButton) findViewById(R.id.new_loan_request);
        newLoanRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        /*
        ImageButton powerOff = (ImageButton) findViewById(R.id.log_out);
        powerOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return;
            }
        });
        */

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.navigation_loans:
                                toolbarTitle.setText(item.getTitle());
                                selectedFragment = LoansFragment.newInstance();
                                break;
                            case R.id.navigation_dashboard:
                                toolbarTitle.setText(item.getTitle());
                                selectedFragment = DashboardFragment.newInstance();
                                break;
                            case R.id.navigation_browse:
                                toolbarTitle.setText(item.getTitle());
                                selectedFragment = BrowseFragment.newInstance();
                                break;
                            case R.id.navigation_profile:
                                toolbarTitle.setText(item.getTitle());
                                selectedFragment = ProfileFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.container, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, DashboardFragment.newInstance());
        transaction.commit();

        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        toolbarTitle.setText("Dashboard");
    }
}
