package com.example.user_management_demo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EmailLoginSignUpAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;


    public EmailLoginSignUpAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;


    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                final EmailLoginFragment emailLoginFragment = new EmailLoginFragment();
                System.out.println("login");
                return emailLoginFragment;
            case 1:
                final EmailSignupFragment emailSignupFragment = new EmailSignupFragment();
                System.out.println("signup");
                return emailSignupFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
