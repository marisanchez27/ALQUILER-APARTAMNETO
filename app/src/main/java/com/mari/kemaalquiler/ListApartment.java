package com.mari.kemaalquiler;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Models.ApartModels;

public class ListApartment extends AppCompatActivity {
FirebaseFirestore db = FirebaseFirestore.getInstance();
RecyclerView rvApartamento;
FirestoreRecyclerAdapter adapter;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lista_apart2);
    rvApartamento = findViewById(R.id.rvApartamento);



    Query query = db.collection("apartamentos");
    FirestoreRecyclerOptions<ApartModels> options = new FirestoreRecyclerOptions.Builder<ApartModels>()
                                                            .setQuery(query, ApartModels.class)
                                                            .build();
    //adapter = new FirestoreRecyclerAdapter() {
    adapter = new FirestoreRecyclerAdapter<ApartModels, apartViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull apartViewHolder holder, int position, @NonNull ApartModels model) {
            holder.tvPropietario.setText(model.getPropietario());
            holder.tvTelefono.setText(model.getTelefono());
            holder.tvDireccion.setText(model.getDirreccion());
            holder.tvHabitaciones.setText(model.getHabitaciones());
            holder.tvCountry.setText(model.getCountry());
            holder.tvValor.setText(model.getValor());
            String id = getSnapshots().getSnapshot(position).getId();
            holder.btnEditApart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListApartment.this, UpdateApartment.class);
                    intent.putExtra("telefono", model.getTelefono());
                    intent.putExtra("habitaciones", model.getHabitaciones());
                    intent.putExtra("valor", model.getValor());
                    intent.putExtra("country",model.getCountry());


                    startActivity(intent);
                }
            });
            holder.btnDeletApart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeletApart(id);

                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ListApartment.this, model.getTelefono(), Toast.LENGTH_SHORT).show();





                }
            });

        }

        @NonNull
        @Override
        public ListApartment.apartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.apart_list,parent,false);


            return new apartViewHolder(view);
        }
    };
    rvApartamento.setHasFixedSize(true);
    rvApartamento.setLayoutManager(new LinearLayoutManager(this));
    rvApartamento.setAdapter(adapter);

}

static class apartViewHolder extends RecyclerView.ViewHolder{
    TextView tvPropietario, tvTelefono, tvDireccion, tvHabitaciones, tvValor, tvCountry;
    Button btnDeletApart, btnEditApart;
    public apartViewHolder(@NonNull  View itemView) {
        super(itemView);
        tvPropietario = itemView.findViewById(R.id.tvPropietario);
        tvTelefono = itemView.findViewById(R.id.tvTelefono);
        tvDireccion = itemView.findViewById(R.id.tvDireccion);
        tvHabitaciones = itemView.findViewById(R.id.tvHabitaciones);
        tvValor = itemView.findViewById(R.id.tvValor);
        tvCountry = itemView.findViewById(R.id.tvCountry);
        btnDeletApart = itemView.findViewById(R.id.btnDeletApar);
        btnEditApart = itemView.findViewById(R.id.btnEditApar);



    }
}
public void DeletApart(String id){
    db.collection("apartamentos").document(id)
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ListApartment.this, "Documento eliminado", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ListApartment.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
}
@Override
protected void onStart() {

    super.onStart();
    adapter.startListening();
}

@Override
protected void onStop() {
    super.onStop();

    adapter.stopListening();
}


}