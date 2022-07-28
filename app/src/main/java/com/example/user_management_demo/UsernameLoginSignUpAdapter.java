package com.example.user_management_demo;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class UsernameLoginSignUpAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;


    public UsernameLoginSignUpAdapter(@NonNull FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    final UsernameLoginFragment usernameLoginFragment = new UsernameLoginFragment();
                    System.out.println("login");
                    return usernameLoginFragment;
                case 1:
                    final UsernameSignupFragment usernameSignupFragment = new UsernameSignupFragment();
                    System.out.println("signup");
                    return usernameSignupFragment;
                default:
                    return null;
            }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
