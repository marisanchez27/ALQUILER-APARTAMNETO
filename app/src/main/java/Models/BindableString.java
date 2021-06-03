package Models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.mari.alquilerapartamento.BR;

import java.util.Objects;

public class BindableString extends BaseObservable {
    private String value;
    /* @Bindable este oyente se invoca en cada actualizacion y actualiza las vistas correspondientes*/
    @Bindable
    public String getValue(){
        return value != null ? value : "";
    }

    public void setValue(String value) {
        if(!Objects.equals(this.value,value)){
            this.value = value;
            notifyPropertyChanged(BR.value);
        }
    }
}
