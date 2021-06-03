package ViewModels;

import android.app.Activity;
import android.content.Intent;
import android.net.Network;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mari.alquilerapartamento.MainActivity;
import com.mari.alquilerapartamento.R;
import com.mari.alquilerapartamento.VerifyEmail;
import com.mari.alquilerapartamento.VerifyPassword;
import com.mari.alquilerapartamento.databinding.VerifyEmailBinding;
import com.mari.alquilerapartamento.databinding.VerifyPasswordBinding;

import Library.MemoryData;
import Library.Networks;
import Library.validate;
import Models.BindableString;
import interfaces.IonClick;

public class LoginViewModels  extends ViewModel implements IonClick {
    private Activity _activity;
    public  static String emailData = null;
    private static VerifyEmailBinding _bindingEmail;
    private static VerifyPasswordBinding _bindingPassword;
    public BindableString emailUI = new BindableString();
    public BindableString passwordUI = new BindableString();
    private FirebaseAuth mAuth;
    private MemoryData memoryData;



    public LoginViewModels (
            Activity activity,
            VerifyEmailBinding bindingEmail,
            VerifyPasswordBinding bindingPassword) {
        _activity = activity;
        _bindingEmail = bindingEmail;
        _bindingPassword = bindingPassword;
        if (emailData !=null){
            emailUI.setValue(emailData);
        }
        mAuth = FirebaseAuth.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.email_sign_in_button:
                VerifyEmail();
                break;
            case R.id.password_sign_in_button:
                Login();
                break;
        }
        //Toast.makeText(_activity,emailUI.getValue(), Toast.LENGTH_SHORT).show();
    }
    private void VerifyEmail (){
        boolean cancel = true;
        _bindingEmail.emailEditText.setError(null);
        if (TextUtils.isEmpty(emailUI.getValue())){
            _bindingEmail.emailEditText.setError(_activity.getString(R.string.error_field_required));
            _bindingEmail.emailEditText.requestFocus();
            cancel = false;

        }else if (!validate.isEmail(emailUI.getValue())){
            _bindingEmail.emailEditText.setError(_activity.getString(R.string.error_invalided_email));
            _bindingEmail.emailEditText.requestFocus();
            cancel = false;

        }
        if (cancel){
            emailData = emailUI.getValue();
            _activity.startActivity(new Intent(_activity, VerifyPassword.class));
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void Login(){
        boolean cancel = true;
        _bindingPassword.passwordEditText.setError(null);
        if (TextUtils.isEmpty(passwordUI.getValue())){
            _bindingPassword.passwordEditText.setError(_activity.getString(R.string.error_field_required));
            cancel = false;
        }else if(!isPasswordValid(passwordUI.getValue())){
            _bindingPassword.passwordEditText.setError(_activity.getString(R.string.error_invalid_password));
            cancel = false;

        }
        if (cancel){
            if (new Networks(_activity).verificaNetworks()){
                mAuth.signInWithEmailAndPassword(emailData,passwordUI.getValue()).addOnCompleteListener(_activity,(task)->{
                    if (task.isSuccessful()){
                        memoryData= MemoryData.getInstance(_activity);
                        memoryData.saveData("user",emailData);
                        _activity.startActivity(new Intent(_activity, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }else {
                        Snackbar.make(_bindingPassword.passwordEditText, R.string.invalid_credentials, Snackbar.LENGTH_LONG).show();
                    }
                });

            }else{
                Snackbar.make(_bindingPassword.passwordEditText, R.string.networks, Snackbar.LENGTH_LONG).show();
            }

        }

    }
    private boolean isPasswordValid(String password){
        return password.length() >= 6;
    }


}
