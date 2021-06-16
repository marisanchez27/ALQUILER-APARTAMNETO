package com.mari.kemaalquiler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Apartment extends AppCompatActivity {

EditText etPropietario, etTelefono, etDirec, etHabit, etValor, etCountry;
FirebaseFirestore db = FirebaseFirestore.getInstance();

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_apartamento2);

    etPropietario = findViewById(R.id.etPropietario);
    etTelefono = findViewById(R.id.etTelefono);
    etDirec = findViewById(R.id.etDirec);
    etHabit = findViewById(R.id.etHabit);
    etValor = findViewById(R.id.etValor);
    etCountry = findViewById(R.id.etCountry);
}

public void saveApart (View View){
    Map<String, Object> user = new HashMap<>();
    String propietario = etPropietario.getText().toString();
    String telefono = etTelefono.getText().toString();
    String dirreccion = etDirec.getText().toString();
    String habitaciones = etHabit.getText().toString();
    String valor = etValor.getText().toString();
    String country = etCountry.getText().toString();


    user.put("propietario", propietario);
    user.put("telefono", telefono);
    user.put("dirreccion", dirreccion);
    user.put("habitaciones", habitaciones);
    user.put("valor", valor);
    user.put("country", country);

    db.collection("apartamentos")
            .add(user)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Toast.makeText(Apartment.this, "apartamento registrado", Toast.LENGTH_SHORT).show();
                    Log.d("firebase", "DocumentSnapshot added with ID: " + documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Apartment.this, "error", Toast.LENGTH_SHORT).show();
                    Log.w("firebase", "Error adding document", e);
                }
            });

}
}

