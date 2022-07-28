package com.example.user_management_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBUser;

public class HomeActivity extends AppCompatActivity {
    TextView greetText;
    AppCompatButton logoutBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        greetText = findViewById(R.id.greet_Text);
        logoutBtn = findViewById(R.id.logout_btn);
        NCMBUser user = NCMBUser.getCurrentUser();
        greetText.setText("Welcome user: "+ user.getUserName());
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NCMBUser.logoutInBackground(new DoneCallback() {
                    @Override
                    public void done(NCMBException e) {
                        if (e != null) {
                            Log.d("logout error",e.toString());
                        }else{
                            Toast.makeText(HomeActivity.this,
                                    "Log out...", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                });
            }
        });
    }
}