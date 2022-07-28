package com.example.user_management_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class EmailLoginSignUpActivity extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    TextView anonymousLoginText, usernameTransfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login_sign_up);
        // Link object to view
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_page);

        anonymousLoginText = findViewById(R.id.anonymous_login);
        usernameTransfer = findViewById(R.id.username_transfer);

        // tab layout set up

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // create and set adapter for viewpage

        final EmailLoginSignUpAdapter adapter = new EmailLoginSignUpAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager));



        // set transfer to email authentication event

        usernameTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmailLoginSignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }
    private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                pager.setCurrentItem(tab.getPosition());

                switch (position) {
                    case 0:
                        usernameTransfer.setText("Login with Username");
                        break;
                    case 1:
                        usernameTransfer.setText("Sign up with Username");
                        break;
                    default:
                        usernameTransfer.setText("Login with Username");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }
}