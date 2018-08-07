package id.smart.model;

public class CriticsModel {
    public String key;
    public String nama;
    public String email;
    public String pesan;
    public String tanggal;

    public CriticsModel() {
    }

    public CriticsModel(String key, String nama, String email, String pesan, String tanggal) {
        this.key = key;
        this.nama = nama;
        this.email = email;
        this.pesan = pesan;
        this.tanggal = tanggal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
