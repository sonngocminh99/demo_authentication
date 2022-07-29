package com.example.user_management_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.FetchCallback;
import com.nifcloud.mbaas.core.FindCallback;
import com.nifcloud.mbaas.core.NCMB;
import com.nifcloud.mbaas.core.NCMBAcl;
import com.nifcloud.mbaas.core.NCMBBase;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;
import com.nifcloud.mbaas.core.NCMBQuery;
import com.nifcloud.mbaas.core.NCMBUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class HomeActivity extends AppCompatActivity {
    @BindView(R.id.greet_Text)
    TextView greetText;
    @BindView(R.id.rv_user)
    RecyclerView userRecyclerView;
    @BindView(R.id.search_text)
    EditText searchText;
    List<NCMBUser> users;
    UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        NCMB.initialize(this.getApplicationContext(),MainActivity.APP_KEY,MainActivity.CLIENT_KEY);
        NCMBUser user = NCMBUser.getCurrentUser();

        greetText.setText("Welcome user: "+ user.getUserName());

        users = new ArrayList<>();
        adapter = new UserAdapter(users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeActivity.this);
        userRecyclerView.setLayoutManager(linearLayoutManager);
        userRecyclerView.setAdapter(adapter);

        //load user list

        try {
            loadUsers();
        } catch (NCMBException e) {
            e.printStackTrace();
        }


    }

    @OnClick(R.id.logout_btn)
    void logout(){
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
    @OnClick(R.id.add_to_role_btn)
    void addToRoleClick(){
        Intent intent = new Intent(HomeActivity.this, AddToRoleActivity.class);
        startActivity(intent);
    }
    @OnTextChanged(R.id.search_text)
    void searchTextChange(){

        NCMBQuery query = new NCMBQuery("user");
        String keyword = searchText.getText().toString();
        query.whereEqualTo("userName", keyword);
        query.findInBackground(new FindCallback<NCMBUser>() {
            @Override
            public void done(List<NCMBUser> results, NCMBException e) {
                if (e != null) {
                    //検索失敗時の処理
                }
                else{
                    users.clear();
                    users.addAll(results);
                    adapter.notifyDataSetChanged();
                    userRecyclerView.refreshDrawableState();
                }
            }
        });

    }
    private void loadUsers() throws NCMBException {
        NCMBQuery<NCMBUser> query = new NCMBQuery<>("user");
        query.findInBackground(new FindCallback<NCMBUser>() {
            @Override
            public void done(List<NCMBUser> results, NCMBException e) {
                if (e != null) {
                    //検索失敗時の処理
                    Log.d("error",e.getMessage());

                }
                else{
                    users.addAll(results);
                    adapter.notifyDataSetChanged();
                    userRecyclerView.refreshDrawableState();

                }
            }
        });


    }
}