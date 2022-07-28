package com.example.user_management_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.nifcloud.mbaas.core.NCMB;

public class MainActivity extends AppCompatActivity {

    public static String APP_KEY = "2bfb444423219ff54256bbe41ff270c5d8c3e81eaa3121c18603363e99b0b673";
    public static String CLIENT_KEY = "2e0167555ae06b73a73a8b2ef1ea9614d566b17cb7c0d191da80797221088bf2";
    TabLayout tabLayout;
    ViewPager viewPager;
    TextView anonymousLoginText, emailTransfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NCMB.initialize(this.getApplicationContext(),APP_KEY,CLIENT_KEY);
        // Link object to view
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_page);

        anonymousLoginText = findViewById(R.id.anonymous_login);
        emailTransfer = findViewById(R.id.email_transfer);

        // tab layout set up

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // create and set adapter for viewpage

        final UsernameLoginSignUpAdapter adapter = new UsernameLoginSignUpAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager));



        // set transfer to email authentication event

        emailTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EmailLoginSignUpActivity.class);
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
                            emailTransfer.setText("Login with Email");
                            break;
                        case 1:
                            emailTransfer.setText("Sign up with Email");
                            break;
                        default:
                            emailTransfer.setText("Login with Email");
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