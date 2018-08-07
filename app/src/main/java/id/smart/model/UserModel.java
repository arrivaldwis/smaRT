package id.smart.model;

import java.io.Serializable;

public class UserModel implements Serializable {
    public String email;
    public String role;
    public String nama;

    public UserModel() {
    }

    public UserModel(String email, String role, String nama) {
        this.email = email;
        this.role = role;
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
