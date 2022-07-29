package com.example.user_management_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmailLoginSignUpActivity extends AppCompatActivity implements ResetPassDialog.ResetPassDialogListener {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_page)
    ViewPager viewPager;
    @BindView(R.id.anonymous_login)
    TextView anonymousLoginText;
    @BindView(R.id.username_transfer)
    TextView usernameTransfer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login_sign_up);

        ButterKnife.bind(this);
        // tab layout set up

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // create and set adapter for viewpage

        final EmailLoginSignUpAdapter adapter = new EmailLoginSignUpAdapter(getSupportFragmentManager(),this,tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(onTabSelectedListener(viewPager));
    }

    // set transfer to email authentication event
    @OnClick(R.id.username_transfer)
    void userNameTransfer(){
        Intent intent = new Intent(EmailLoginSignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
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
    private void sendResetPassMail(String email){
        NCMBUser.requestPasswordResetInBackground(email, new DoneCallback() {
            @Override
            public void done(NCMBException error) {
                if(error != null ){
                    Toast.makeText(EmailLoginSignUpActivity.this,
                            "Something wrong", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EmailLoginSignUpActivity.this,
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