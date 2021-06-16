package com.mari.kemaalquiler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
FirebaseFirestore db = FirebaseFirestore.getInstance();
Button registrar, ingresar;
EditText Usuario, Password;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    registrar = findViewById(R.id.btnRegistrar);
    ingresar = findViewById(R.id.btnIngresar);
    Usuario = (EditText) findViewById(R.id.etUsuario);
    Password = (EditText) findViewById(R.id.etPass);
    ingresar = findViewById(R.id.btnIngresar);

}


public void registrarUsuario (View view){
    Intent intent = new Intent(MainActivity.this, Form.class);
    startActivity(intent);

}

public boolean validarUser() {
    String etUsuario = Usuario.getText().toString();
    if (!etUsuario.isEmpty())return false;
    return true;
}
public boolean validarPass(){
    String etPass = Password.getText().toString();
    if (!etPass.isEmpty())return  false;
    return true;
}

public void validar(View view){
    String usuario = Usuario.getText().toString();
    if (validarUser())Usuario.setError("usuario vacio");
    if (validarPass())Password.setError("contraseña vacia");

    if (validarPass() && validarUser()) Toast.makeText(getApplicationContext(), "datos validados", Toast.LENGTH_SHORT).show();



    DocumentReference docRef = db.collection("users").document(usuario);
    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
        @Override
        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d("Mensaje1", "DocumentSnapshot data: " + document.getData());



                    Intent intent = new Intent(MainActivity.this, ListUser.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Usuario no extiste", Toast.LENGTH_LONG).show();
                }
            } else {

                Log.d("Mensaje3", "get failed with ", task.getException());
                Toast.makeText(getApplicationContext(), "Usuario no extiste, por favor confirmar", Toast.LENGTH_LONG).show();


            }

        }


    });

}



}