package Models.Pojo;

public class User {
    private String LastName;
    private String Name;
    private String Email;
    private String Role;
    private String Image;

public User(String lastName, String name, String email, String role, String image) {
    LastName = lastName;
    Name = name;
    Email = email;
    Role = role;
    Image = image;
}

public String getLastName() {
    return LastName;
}

public String getName() {
    return Name;
}

public String getEmail() {
    return Email;
}

public String getRole() {
    return Role;
}

public String getImage() {
    return Image;
}
}
