package com.mari.alquilerapartamento;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mari.alquilerapartamento.databinding.VerifyEmailBinding;

import Library.MemoryData;
import ViewModels.LoginViewModels;

public class VerifyEmail  extends AppCompatActivity {
    private MemoryData memoryData;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        memoryData = MemoryData.getInstance(this);
        if (memoryData.getData("user").equals("")){
            //setContentView(R.layout.verify_email);
            VerifyEmailBinding _bindingEmail = DataBindingUtil.setContentView(this,R.layout.verify_email);
            _bindingEmail.setEmailModel(new LoginViewModels(this,_bindingEmail,null));
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colorPink,null));
        }else{
            startActivity(new Intent(this, MainActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }



    }

}
