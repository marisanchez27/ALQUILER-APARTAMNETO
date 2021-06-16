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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Models.UsersModel;

public class ListUser extends AppCompatActivity {
FirebaseFirestore db = FirebaseFirestore.getInstance();
RecyclerView rvUser;
FirestoreRecyclerAdapter adapter;
Button registrarApart, listaApt;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_user2);
    rvUser = findViewById(R.id.rvUser);
    registrarApart = findViewById(R.id.btnApartament);
    listaApt = findViewById(R.id.btnguardado);

    listaApt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListUser.this, ListApartment.class);
            startActivity(intent);
        }
    });
    registrarApart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ListUser.this, Apartment.class);
            startActivity(intent);
        }
    });

    Query query = db.collection("users");
    FirestoreRecyclerOptions<UsersModel> options = new FirestoreRecyclerOptions.Builder<UsersModel>()
                                                           .setQuery(query, UsersModel.class)
                                                           .build();
    //adapter = new FirestoreRecyclerAdapter() {
    adapter = new FirestoreRecyclerAdapter<UsersModel, UserViewHolder>(options) {
        @Override
        protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull UsersModel model) {
            holder.tvApellido.setText(model.getLastname());
            holder.tvName.setText(model.getName());
            holder.tvDireecion.setText(model.getDirection());
            holder.tvIdenti.setText(model.getIdentification());
            holder.tvPassword.setText(model.getPassword());
            holder.tvEmail.setText(model.getEmail());
            String id = getSnapshots().getSnapshot(position).getId();

            holder.btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //EditUser(id);
                    Intent intent =  new Intent(ListUser.this, UpdateUser.class);

                    intent.putExtra("nombre", model.getName());
                    intent.putExtra("apellido", model.getLastname());
                    intent.putExtra("direccion", model.getDirection());
                    intent.putExtra("correo",model.getEmail());


                    startActivity(intent);
                }
            });
            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DeletUser(id);
                }
            });


        }

        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_iten_single,parent,false);


            return new  UserViewHolder (view);
        }
    };
    rvUser.setHasFixedSize(true);
    rvUser.setLayoutManager(new LinearLayoutManager(this));
    rvUser.setAdapter(adapter);

}


static class UserViewHolder extends RecyclerView.ViewHolder{
    TextView tvApellido, tvName, tvIdenti, tvDireecion, tvEmail, tvPassword;
    Button btnEliminar, btnEditar;
    public UserViewHolder (@NonNull View itemView){
        super(itemView);

        tvApellido = itemView.findViewById(R.id.tvApellido);
        tvName = itemView.findViewById(R.id.tvName);
        tvIdenti = itemView.findViewById(R.id.tvIdenti);
        tvDireecion = itemView.findViewById(R.id.tvDireecion);
        tvEmail = itemView.findViewById(R.id.tvEmail);
        tvPassword = itemView.findViewById(R.id.tvPassword);
        btnEliminar = itemView.findViewById(R.id.btnEliminar);
        btnEditar = itemView.findViewById(R.id.btnEditar);

    }
}

public void DeletUser(String id){
    db.collection("users").document(id)
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ListUser.this, "Documento eliminado", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ListUser.this, "error", Toast.LENGTH_SHORT).show();
                }
            });
}
public void EditUser( String id){
    DocumentReference washingtonRef = db.collection("users").document(id);

// Set the "isCapital" field of the city 'DC'
    washingtonRef
            .update("valor", true)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(ListUser.this,  "doc editado", Toast.LENGTH_SHORT).show();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ListUser.this, "error", Toast.LENGTH_SHORT).show();
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