package com.example.user_management_demo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nifcloud.mbaas.core.NCMBUser;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<NCMBUser> users;

    public UserAdapter(List<NCMBUser> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_item,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        NCMBUser user = users.get(position);
        if(user == null) return;
        String email = user.getMailAddress();
        holder.emailText.setText(email!=null?email:"null");
        holder.usernameText.setText(user.getUserName().toString());


    }

    @Override
    public int getItemCount() {
        return users != null ? users.size():0;
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.username)
        TextView usernameText;
        @BindView(R.id.email)
        TextView emailText;
        public UserViewHolder(@NonNull View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
