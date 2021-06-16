package com.mari.kemaalquiler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Form extends AppCompatActivity {

EditText etName, etApellido, etDireccion, etDocumento, etEmail, etPassword;
FirebaseFirestore db = FirebaseFirestore.getInstance();
private FirebaseAuth mAuth= FirebaseAuth.getInstance();

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_formulario2);

    etName = findViewById(R.id.etName);
    etApellido = findViewById(R.id.etApellido);
    etDireccion = findViewById(R.id.etDireccion);
    etDocumento = findViewById(R.id.etDocumento);
    etEmail = findViewById(R.id.etEmail);
    etPassword = findViewById(R.id.etPassword);



}

public void saveUser(View view) {
    Map<String, Object> user = new HashMap<>();

    String name = etName.getText().toString();
    String apellido = etApellido.getText().toString();
    String direccion = etDireccion.getText().toString();
    String documento = etDocumento.getText().toString();
    String email = etEmail.getText().toString();
    String password = etPassword.getText().toString();


    if (email.isEmpty()||password.isEmpty()||documento.isEmpty()|| name.isEmpty()||apellido.isEmpty()||direccion.isEmpty()) {
        Toast.makeText(this, "Los campos no pueden quedar vacios", Toast.LENGTH_SHORT).show();
    } else if (password.length() < 8) {
        Toast.makeText(this, "La contraseña debe tener minimo 8 caracteres", Toast.LENGTH_SHORT).show();
    } else {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Map<String, Object> user = new HashMap<>();

                            String name = etName.getText().toString();
                            String apellido = etApellido.getText().toString();
                            String direccion = etDireccion.getText().toString();
                            String documento = etDocumento.getText().toString();
                            String email = etEmail.getText().toString();
                            String password = etPassword.getText().toString();


                            user.put("name", name);
                            user.put("lastname", apellido);
                            user.put("identification", documento);
                            user.put("direction", direccion);
                            user.put("email", email);
                            user.put("password", password);


                            db.collection("users").document(email)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {


                                            etDocumento.setText("");
                                            etApellido.setText("");
                                            etDireccion.setText("");
                                            etEmail.setText("");
                                            etPassword.setText("");
                                            etName.requestFocus();
                                            Toast.makeText(Form.this, "Usuario Registrado con exito", Toast.LENGTH_SHORT).show();
                                            Log.d("Firestore", "DocumentSnapshot successfully written!");
                                            Intent intent = new Intent(Form.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Form.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                                            Log.w("Firestore", "Error writing document", e);
                                        }
                                    });


                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Form.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Form.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
}
