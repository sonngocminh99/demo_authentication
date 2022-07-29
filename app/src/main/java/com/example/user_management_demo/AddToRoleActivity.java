package com.example.user_management_demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.FindCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBQuery;
import com.nifcloud.mbaas.core.NCMBRole;
import com.nifcloud.mbaas.core.NCMBUser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddToRoleActivity extends AppCompatActivity {
    @BindView(R.id.spinner)
    Spinner roleSpinner;
    @BindView(R.id.userID)
    EditText userIDText;
    List<String> roleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_role);
        ButterKnife.bind(this);
        roleList = new ArrayList<>();
        roleList.add("Admin");
        roleList.add("user");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddToRoleActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,roleList);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

    }
    @OnClick(R.id.back)
    void backClick(){
        finish();
    }
    @OnClick(R.id.add)
    void addClick() {
        if(userIDText.getText().toString().isEmpty()){

        }else{
            final NCMBUser user = new NCMBUser();
            try {
                user.setObjectId(userIDText.getText().toString());
            } catch (NCMBException e) {
                e.printStackTrace();
            }
            NCMBQuery<NCMBRole> query = NCMBRole.getQuery();
            ((NCMBQuery<?>) query).whereEqualTo("roleName", roleSpinner.getSelectedItem().toString());
            query.findInBackground(new FindCallback<NCMBRole>() {
                @Override
                public void done(List<NCMBRole> results, NCMBException e) {
                    if (results.size() > 0) {
                        NCMBRole role = results.get(0);
                        role.addUserInBackground(Arrays.asList(user), new DoneCallback() {
                            @Override
                            public void done(NCMBException e) {
                                if (e != null) {
                                    Toast.makeText(AddToRoleActivity.this,
                                            "Invalid user", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    Toast.makeText(AddToRoleActivity.this,
                                            "Add user to role success", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
        }
    }
}