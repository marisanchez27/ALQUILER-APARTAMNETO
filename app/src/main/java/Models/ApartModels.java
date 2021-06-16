package Models;

public class ApartModels {
private String propietario;

public ApartModels(String propietario, String telefono, String dirreccion, String habitaciones, String valor, String country) {
    this.propietario = propietario;
    this.telefono = telefono;
    this.dirreccion = dirreccion;
    this.habitaciones = habitaciones;
    this.valor = valor;
    this.country = country;
}

private String telefono;
private String dirreccion;
private String habitaciones;
private String valor;
private String country;

public ApartModels() {
}
public String getPropietario() {
    return propietario;
}

public void setPropietario(String propietario) {
    this.propietario = propietario;
}

public String getTelefono() {
    return telefono;
}

public void setTelefono(String telefono) {
    this.telefono = telefono;
}

public String getDirreccion() {
    return dirreccion;
}

public void setDirreccion(String dirreccion) {
    this.dirreccion = dirreccion;
}

public String getHabitaciones() {
    return habitaciones;
}

public void setHabitaciones(String habitaciones) {
    this.habitaciones = habitaciones;
}

public String getValor() {
    return valor;
}

public void setValor(String valor) {
    this.valor = valor;
}

public String getCountry() {
    return country;
}

public void setCountry(String country) {
    this.country = country;
}


}

