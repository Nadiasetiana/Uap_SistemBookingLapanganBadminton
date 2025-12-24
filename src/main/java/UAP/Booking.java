package UAP;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Inner class Booking - Model data
 */
class Booking {
    private String id, nama, telepon, lapangan;
    private LocalDate tanggal;
    private int jamMulai, jamSelesai;
    private double total;

    public Booking(String id, String nama, String telepon, String lapangan,
                   LocalDate tanggal, int jamMulai, int jamSelesai, double total) {
        this.id = id;
        this.nama = nama;
        this.telepon = telepon;
        this.lapangan = lapangan;
        this.tanggal = tanggal;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.total = total;
    }

    // Getters & Setters
    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getTelepon() {
        return telepon;
    }

    public String getLapangan() {
        return lapangan;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public int getJamMulai() {
        return jamMulai;
    }

    public int getJamSelesai() {
        return jamSelesai;
    }

    public double getTotal() {
        return total;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public void setLapangan(String lapangan) {
        this.lapangan = lapangan;
    }

    public void setTanggal(LocalDate tanggal) {
        this.tanggal = tanggal;
    }

    public void setJamMulai(int jamMulai) {
        this.jamMulai = jamMulai;
    }

    public void setJamSelesai(int jamSelesai) {
        this.jamSelesai = jamSelesai;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getJamFormat() {
        return String.format("%02d:00-%02d:00", jamMulai, jamSelesai);
    }

    public String getTanggalFormat() {
        return tanggal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getTotalFormat() {
        return String.format("Rp %,.0f", total);
    }

    public int getDurasi() {
        return jamSelesai - jamMulai;
    }

    public String toCSV() {
        return String.join(",", id, nama, telepon, lapangan,
                tanggal.toString(), String.valueOf(jamMulai),
                String.valueOf(jamSelesai), String.valueOf(total));
    }

    public static Booking fromCSV(String csv) throws Exception {// perubahan
        String[] p = csv.split(",");
        if (p.length != 8) throw new Exception("Format CSV tidak valid");
        return new Booking(p[0], p[1], p[2], p[3], LocalDate.parse(p[4]),
                Integer.parseInt(p[5]), Integer.parseInt(p[6]), Double.parseDouble(p[7]));
    }
}