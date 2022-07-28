package com.example.user_management_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.nifcloud.mbaas.core.LoginCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBUser;

public class EmailLoginFragment extends Fragment {
    EditText emailText,passwordText;
    AppCompatButton loginBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.email_login_fragment,container,false);

        emailText = root.findViewById(R.id.email);
        passwordText = root.findViewById(R.id.pass);
        loginBtn = root.findViewById(R.id.email_login_btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginBtn(emailText.getText().toString(),passwordText.getText().toString());
            }
        });
        return root;
    }

    private void loginBtn(String email, String pass) {
        NCMBUser.loginWithMailAddressInBackground(email, pass, new LoginCallback() {
            @Override
            public void done(NCMBUser user, NCMBException e) {
                if (e != null) {
                    Toast.makeText(getActivity(),
                            "Incorrect login information", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(),
                            "Login...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }


}
