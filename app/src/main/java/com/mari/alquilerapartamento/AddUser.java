package com.mari.alquilerapartamento;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mari.alquilerapartamento.databinding.AddUserBinding;

import ViewModels.UserViewModel;

public class AddUser extends AppCompatActivity {
    private UserViewModel user;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.add_user);
        AddUserBinding _binding = DataBindingUtil.setContentView(this,R.layout.add_user);
        user = new UserViewModel(this,_binding);
        _binding.setUserModel(user);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPink, null));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        user.onActivityResult(requestCode,resultCode,data);
    }
}

