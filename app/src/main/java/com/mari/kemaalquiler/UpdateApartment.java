package com.mari.kemaalquiler;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateApartment extends AppCompatActivity {
FirebaseFirestore db = FirebaseFirestore.getInstance();
EditText jtelefono, jvalor, jhabitaciones;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_actualizar_apart2);

    jtelefono = findViewById(R.id.etTelefono);
    jvalor = findViewById(R.id.etValor);
    jhabitaciones = findViewById(R.id.etHabit);

    String telefono = getIntent().getStringExtra("telefono");
    String valor = getIntent().getStringExtra("valor");
    String habitaciones = getIntent().getStringExtra("habitaciones");

    jtelefono.setText(telefono);
    jvalor.setText(valor);
    jhabitaciones.setText(habitaciones);


}

public  void UpdateApartment(View view){

    String telefono = jtelefono.getText().toString();
    String valor = jvalor.getText().toString();
    String habitaciones = jhabitaciones.getText().toString();

    Map<String, Object> user = new HashMap<>();
    user.put("telefono", telefono);
    user.put("valor", valor);
    user.put("habitaciones", habitaciones );

    db.collection("apartamentos").document(getIntent().getStringExtra("country")).update(user);
    Toast.makeText(this, "Actualizado", Toast.LENGTH_SHORT).show();



}
}