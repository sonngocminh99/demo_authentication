package com.example.user_management_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.FetchCallback;
import com.nifcloud.mbaas.core.FindCallback;
import com.nifcloud.mbaas.core.LoginCallback;
import com.nifcloud.mbaas.core.NCMB;
import com.nifcloud.mbaas.core.NCMBAcl;
import com.nifcloud.mbaas.core.NCMBBase;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;
import com.nifcloud.mbaas.core.NCMBQuery;
import com.nifcloud.mbaas.core.NCMBUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements ResetPassDialog.ResetPassDialogListener {

    public static String APP_KEY = "2bfb444423219ff54256bbe41ff270c5d8c3e81eaa3121c18603363e99b0b673";
    public static String CLIENT_KEY = "2e0167555ae06b73a73a8b2ef1ea9614d566b17cb7c0d191da80797221088bf2";
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPager;
    @BindView(R.id.email_transfer)
    TextView emailTransfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NCMB.initialize(this.getApplicationContext(),APP_KEY,CLIENT_KEY);
        // tab layout set up
        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // create and set adapter for viewpage
        final UsernameLoginSignUpAdapter adapter = new UsernameLoginSignUpAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager));



    }
    // set transfer to email authentication event
    @OnClick(R.id.email_transfer)
    void emailTransfer(){
        Intent intent = new Intent(MainActivity.this, EmailLoginSignUpActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }
    //anonymous login
    @OnClick(R.id.anonymous_login)
    void anonymousLogin(){
        NCMBUser.loginWithAnonymousInBackground(new LoginCallback() {
            @Override
            public void done(NCMBUser user, NCMBException e) {
                if (e != null) {
                    Toast.makeText(MainActivity.this,
                            "Login Failed", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            "Login...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
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

    private void sendResetPassMail(String email){
        NCMBUser.requestPasswordResetInBackground(email, new DoneCallback() {
            @Override
            public void done(NCMBException error) {
                if(error != null ){
                    Toast.makeText(MainActivity.this,
                            "Something wrong", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Reset email has been sent ", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    public void applyTexts(String email) {
        sendResetPassMail(email);
    }
}