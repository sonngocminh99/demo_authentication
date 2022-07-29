package com.example.user_management_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.FindCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBQuery;
import com.nifcloud.mbaas.core.NCMBUser;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UsernameSignupFragment extends Fragment {

    @BindView(R.id.username)
    EditText usernameText;
    @BindView(R.id.pass)
    EditText passwordText;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.username_sign_up_fragment,container,false);
        ButterKnife.bind(this,root);

        return root;
    }

    @OnClick(R.id.username_signup_button)
    void signUpClick(){
        SignUp(usernameText.getText().toString(),passwordText.getText().toString());
    }
    private void SignUp(String username,String pass){
        //　User instance creation
        NCMBUser user = new NCMBUser();

        // Set user name and password
        user.setUserName(username);
        user.setPassword(pass);


        //New user registration process
        user.signUpInBackground(new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    Toast.makeText(getActivity(),
                            "Register failed", Toast.LENGTH_LONG).show();
                    Log.d("error",e.toString());
                }
                else{
                    Toast.makeText(getActivity(),
                            "Register success. Please login", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
