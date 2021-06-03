package com.mari.alquilerapartamento;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mari.alquilerapartamento.databinding.VerifyPasswordBinding;

import ViewModels.LoginViewModels;

public class VerifyPassword extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.verify_password);
        VerifyPasswordBinding _bindingPassword = DataBindingUtil.setContentView(this,R.layout.verify_password);
        _bindingPassword.setPasswordModel(new LoginViewModels(this,null,_bindingPassword));
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPink,null));
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(this, VerifyEmail.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
