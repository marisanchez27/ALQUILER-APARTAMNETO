package Models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class Item extends BaseObservable {
    private int selectedItemPosition;

    /*la anotacion enlazable debe aplicarse a cualquier metodo de acceso getter de una ObservableClase*/
     @Bindable
    public int getSelectedItemPosition(){
         return selectedItemPosition;
     }
     public void setSelectedItemPosition(int selectedItemPosition){
         this.selectedItemPosition = selectedItemPosition;
         notifyPropertyChanged(BR.selectedItemPosition);
     }
}
