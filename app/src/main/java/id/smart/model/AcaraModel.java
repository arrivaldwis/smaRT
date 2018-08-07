package id.smart.model;

public class AcaraModel {
    public String key;
    public String nama;
    public String tanggal;

    public AcaraModel(String key, String nama, String tanggal) {
        this.key = key;
        this.nama = nama;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public AcaraModel() {

    }
}
