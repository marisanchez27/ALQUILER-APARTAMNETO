package ViewModels.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.mari.alquilerapartamento.R;
import com.mari.alquilerapartamento.databinding.ItemUserBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import Models.Pojo.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
private List<User> _userList;
private LayoutInflater _layoutInflater;
private AdapterListener _listener;

public UserAdapter(List<User> userList, AdapterListener listener) {
    _userList = userList;
    _listener = listener;
}

public class MyViewHolder extends RecyclerView.ViewHolder {
    private final ItemUserBinding _binding;

    public MyViewHolder(final ItemUserBinding binding) {
        super(binding.getRoot());
        _binding = binding;
    }
}

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    if (_layoutInflater == null) {
        _layoutInflater = LayoutInflater.from(parent.getContext());
    }
    ItemUserBinding binding=
            DataBindingUtil.inflate(_layoutInflater, R.layout.item_user,parent,false);
    return new MyViewHolder(binding);
}

@Override
public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
    holder._binding.setUser(_userList.get(position));
    holder._binding.cardViewUser.setOnClickListener((v) -> {
        if (_listener != null){
            _listener.onUserClicked(_userList.get(position));
        }
    });

}

@Override
public int getItemCount() {
    return _userList.size();
}
public interface AdapterListener{
    void onUserClicked(User user);
}


}
