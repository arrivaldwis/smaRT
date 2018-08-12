package id.smart.model;

public class KartuKeluargaModel {
    public String nik;
    public String no_kk;
    public String nama;
    public String nama_panggilan;
    public String ttl;
    public String jenis_kelamin;
    public String alamat;
    public String agama;
    public String status;
    public String pekerjaan;
    public String keterkaitan;

    public KartuKeluargaModel(String nik, String no_kk, String nama, String nama_panggilan, String ttl, String jenis_kelamin, String alamat, String agama, String status, String pekerjaan, String keterkaitan) {
        this.nik = nik;
        this.no_kk = no_kk;
        this.nama = nama;
        this.nama_panggilan = nama_panggilan;
        this.ttl = ttl;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
        this.agama = agama;
        this.status = status;
        this.pekerjaan = pekerjaan;
        this.keterkaitan = keterkaitan;
    }

    public KartuKeluargaModel() {

    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNama_panggilan() {
        return nama_panggilan;
    }

    public void setNama_panggilan(String nama_panggilan) {
        this.nama_panggilan = nama_panggilan;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getKeterkaitan() {
        return keterkaitan;
    }

    public void setKeterkaitan(String keterkaitan) {
        this.keterkaitan = keterkaitan;
    }
}
