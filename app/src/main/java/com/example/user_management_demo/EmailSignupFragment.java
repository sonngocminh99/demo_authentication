package com.example.user_management_demo;

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
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBUser;

public class EmailSignupFragment extends Fragment {

    EditText emailText;
    AppCompatButton signUpBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.email_signup_fragment,container,false);

        emailText = root.findViewById(R.id.email);
        signUpBtn = root.findViewById(R.id.email_signup_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp(emailText.getText().toString());
            }
        });
        return root;
    }
    private void signUp(String email){

        NCMBUser.requestAuthenticationMailInBackground(email, new DoneCallback() {
            @Override
            public void done(NCMBException e) {
                if (e != null) {
                    Log.d("error", e.toString());
                }else{
                    Toast.makeText(getActivity(),
                            "Confirm email has been sent", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
