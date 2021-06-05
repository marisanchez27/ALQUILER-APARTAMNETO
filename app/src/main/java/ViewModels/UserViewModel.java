package ViewModels;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mari.alquilerapartamento.AddUser;
import com.mari.alquilerapartamento.R;
import com.mari.alquilerapartamento.databinding.AddUserBinding;

import java.util.HashMap;
import java.util.Map;

import Library.MemoryData;
import Library.Multimedia;
import Library.Networks;
import Library.Permissions;
import Library.validate;
import Models.BindableString;
import Models.Collections;
import Models.Item;
import interfaces.IonClick;

import static android.app.Activity.RESULT_OK;

public class UserViewModel extends ViewModel implements IonClick {
    private Activity _activity;
    private AddUserBinding _binding;
    private Permissions _permissions;
    private Multimedia _multimedia;
    private static final int RESQUEST_CODE_CROP_IMAGE = 1;
    public static final int REQUEST_CODE_TAKE_PHOTO = 0 ;
    private static final String TEMP_PHOYO_FILE = "temporary_img.png";
    private MemoryData memoryData;
    private FirebaseAuth mAuth;
    private FirebaseFirestore _db;
    private DocumentReference _documentRef;
    private FirebaseStorage _storage;
    private StorageReference _storageRef;

    public BindableString nameUI = new BindableString();
    public BindableString lastnameUI = new BindableString();
    public BindableString emailUI = new BindableString();
    public BindableString passwordUI = new BindableString();
    public Item item = new Item();

    public UserViewModel(Activity activity, AddUserBinding binding){
        _activity = activity;
        _binding = binding;
        mAuth = FirebaseAuth.getInstance();
        _storage = FirebaseStorage.getInstance();
        _storageRef = _storage.getReference();
        memoryData = MemoryData.getInstance(_activity);
        _permissions = new Permissions(activity);
        _multimedia = new Multimedia(activity);
        _binding.progressBar.setVisibility(ProgressBar.INVISIBLE);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonCamera:
                if (_permissions.CAMERA() && _permissions.STORAGE()){
                    _multimedia.dispatchTakePictureIntent();
                }
                break;
            case R.id.buttonGallery:
                if (_permissions.STORAGE()){
                    _multimedia.cropCapturedImage(1);
                }
                break;
            case R.id.buttonAddUser:
                AddUser();
                break;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_CODE_TAKE_PHOTO:
                    _multimedia.cropCapturedImage(0);
                    break;
                case RESQUEST_CODE_CROP_IMAGE:
                    //Este seria el bitmap de nuestra imagen cortada.
                    Bitmap imagenCortada = (Bitmap) data.getExtras().get("data");
                    if ( imagenCortada == null){
                        String filePath = Environment.getExternalStorageDirectory()+"/"+TEMP_PHOYO_FILE;
                        imagenCortada  = BitmapFactory.decodeFile(filePath);
                    }
                    _binding.imageViewUser.setImageBitmap(imagenCortada);
                    _binding.imageViewUser.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    break;
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void AddUser(){
        if (TextUtils.isEmpty(nameUI.getValue())) {
            _binding.nameEditText.setError(_activity.getString(R.string.error_field_required));
            _binding.nameEditText.requestFocus();
        }else {
            if (TextUtils.isEmpty(lastnameUI.getValue())) {
                _binding.lastnameEditText.setError(_activity.getString(R.string.error_field_required));
                _binding.lastnameEditText.requestFocus();
            }else {
                if (TextUtils.isEmpty(emailUI.getValue())) {
                    _binding.emailEditText.setError(_activity.getString(R.string.error_field_required));
                    _binding.emailEditText.requestFocus();

                }else {
                    if (!validate.isEmail(emailUI.getValue())) {
                        _binding.emailEditText.setError(_activity.getString(R.string.error_invalided_email));
                        _binding.emailEditText.requestFocus();
                    }else {
                        if (TextUtils.isEmpty(passwordUI.getValue())) {
                            _binding.passwordEditText.setError(_activity.getString(R.string.error_field_required));
                            _binding.passwordEditText.requestFocus();
                        }else {
                            if (!isPasswordValid(passwordUI.getValue())) {
                                _binding.passwordEditText.setError(_activity.getString(R.string.error_invalid_password));
                                _binding.passwordEditText.requestFocus();
                            }else {
                                if (new Networks(_activity).verificaNetworks()){
                                    insertUser();
                                }else {
                                    Snackbar.make(_binding.passwordEditText, R.string.networks, Snackbar.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private boolean isPasswordValid(String password) {

        return password.length() >= 6;
    }
    private void insertUser(){
        _binding.progressBar.setVisibility(ProgressBar.VISIBLE);
        mAuth.createUserWithEmailAndPassword(emailUI.getValue(), passwordUI.getValue())
                .addOnCompleteListener(_activity, (task) ->{
                    if (task.isSuccessful()){
                        StorageReference imagesRef = _storageRef.
                                child(Collections.User.USERS + "/"
                                        + emailUI.getValue());
                        byte[] data = _multimedia.ImageByte(_binding.imageViewUser);
                        UploadTask uploadTask = imagesRef.putBytes(data);
                        uploadTask.addOnFailureListener((exception) ->{

                        }).addOnSuccessListener((taskSnapshot )->{
                            String image = taskSnapshot.getMetadata().getPath();
                            String role = _activity.getResources().getStringArray(R.array.item_roles)[
                                    item.getSelectedItemPosition()];
                            _db = FirebaseFirestore.getInstance();
                            _documentRef = _db.collection(Collections.User.USERS).document(emailUI.getValue());
                            Map<String, Object> user = new HashMap<>();
                            user.put(Collections.User.LASTNAME, lastnameUI.getValue());
                            user.put(Collections.User.EMAIL, emailUI.getValue());
                            user.put(Collections.User.NAME, nameUI.getValue());
                            user.put(Collections.User.ROLE, role);
                            user.put(Collections.User.IMAGE, image);
                            _documentRef.set(user).addOnCompleteListener((task2)->{
                                if (task2.isSuccessful()){
                                    _activity.finish();
                                }

                            });
                        });
                    }else {
                        _binding.progressBar.setVisibility(ProgressBar.INVISIBLE);
                        Snackbar.make(_binding.passwordEditText, R.string.fail_register, Snackbar.LENGTH_LONG).show();
                    }
                });
    }
}

