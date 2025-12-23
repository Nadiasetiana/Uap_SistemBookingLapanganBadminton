package UAP;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Class untuk merepresentasikan data booking
class Booking {
    String id;
    String nama;
    String telepon;
    String lapangan;
    LocalDate tanggal;
    int jamMulai;
    int jamSelesai;
    double total;

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

    public String getJamFormat() {
        return String.format("%02d:00-%02d:00", jamMulai, jamSelesai);
    }

    public String getTanggalFormat() {
        return tanggal.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public String getTotalFormat() {
        return String.format("Rp %,.0f", total);
    }
}
