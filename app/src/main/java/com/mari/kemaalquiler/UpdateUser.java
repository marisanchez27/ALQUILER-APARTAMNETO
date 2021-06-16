package com.mari.kemaalquiler;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateUser extends AppCompatActivity {
FirebaseFirestore db = FirebaseFirestore.getInstance();
EditText jname, jlastname, jdirec;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_actualiza_user);

    jname = findViewById(R.id.etName);
    jlastname = findViewById(R.id.etApellido);
    jdirec = findViewById(R.id.etDireccion);


    String name = getIntent().getStringExtra("nombre");
    String Lastname = getIntent().getStringExtra("apellido");
    String direc = getIntent().getStringExtra("direccion");


    jname.setText(name);
    jlastname.setText(Lastname);
    jdirec.setText(direc);



}

public void UpdateUser(View view){
    String name = jname.getText().toString();
    String lastname = jlastname.getText().toString();
    String direc = jdirec.getText().toString();


    Map<String, Object> user = new HashMap<>();
    user.put("name", name);
    user.put("lastname", lastname);
    user.put("direction", direc );


    db.collection("users").document(getIntent().getStringExtra("correo")).update(user);
    Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_SHORT).show();

}
}