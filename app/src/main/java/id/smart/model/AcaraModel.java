package id.smart.model;

public class AcaraModel {
    public String nama;
    public String tanggal;

    public AcaraModel(String nama, String tanggal) {
        this.nama = nama;
        this.tanggal = tanggal;
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
