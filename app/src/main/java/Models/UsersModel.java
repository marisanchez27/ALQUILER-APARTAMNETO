package Models;

public class UsersModel {
private String name;
private String lastname;
private String identification;
private String direction;
private String email;
private String password;

private UsersModel (){}

public UsersModel(String lastname, String identification, String direction, String email, String password, String name) {
    this.lastname = lastname;
    this.identification = identification;
    this.direction = direction;
    this.email = email;
    this.password = password;
    this.name = name;
}





public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

public String getLastname() {
    return lastname;
}

public void setLastname(String lastname) {
    this.lastname = lastname;
}

public String getIdentification() {
    return identification;
}

public void setIdentification(String identification) {
    this.identification = identification;
}

public String getDirection() {
    return direction;
}

public void setDirection(String direction) {
    this.direction = direction;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}


}
