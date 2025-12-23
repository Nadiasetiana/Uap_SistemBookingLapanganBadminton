# 🏸 Sistem Booking Lapangan Badminton

## 📋 Deskripsi Proyek
Aplikasi desktop untuk mengelola booking lapangan badminton dengan fitur lengkap CRUD, file handling, sorting, searching, dan laporan statistik.

**UAP Pemrograman Lanjut 2025**  
**Universitas Muhammadiyah Malang**

---

## 👥 Anggota Kelompok
- Nadia Setiana Paris (202410370110403)
- Shinta Alya Nachasyi (202410370110405)

---

## 🎯 Fitur Utama

### 1. **CRUD Operations**
- ✅ **Create**: Tambah booking baru dengan validasi lengkap
- ✅ **Read**: Tampilkan semua data booking dalam tabel
- ✅ **Update**: Edit data booking yang sudah ada
- ✅ **Delete**: Hapus booking dengan konfirmasi

### 2. **File Handling (Persistent Data)**
- Format: CSV (`booking_data.csv`)
- Auto-save saat aplikasi ditutup
- Auto-load saat aplikasi dibuka
- Data tetap tersimpan permanen

### 3. **GUI dengan Java Swing**
- 4 Halaman utama:
    - Dashboard/Menu Utama
    - Daftar Lapangan
    - Form Booking
    - Kelola Booking (dengan tabel)
    - Laporan & Statistik
- Desain modern dengan gradient dan warna menarik
- User-friendly interface

### 4. **Sorting & Searching**
- Sort berdasar: ID, Nama, Tanggal, Total
- Search real-time pada semua kolom
- Filter data menggunakan `TableRowSorter`

### 5. **Exception Handling**
- Try-catch untuk file operations
- Validasi input (nama, telepon, tanggal, jam)
- Error handling untuk parse CSV
- User-friendly error messages

### 6. **Java API yang Digunakan**
- `LocalDate` - pengelolaan tanggal
- `ArrayList` - penyimpanan data sementara
- `Comparator` - sorting data
- `DateTimeFormatter` - format tanggal
- `BufferedReader/BufferedWriter` - file I/O
- `TableRowSorter` - filter tabel
- `SpinnerModel` - input jam dan tanggal

---

## 🛠️ Teknologi & Tools

- **Bahasa**: Java 8+
- **GUI**: Java Swing
- **IDE**: IntelliJ IDEA / NetBeans / VS Code
- **Version Control**: Git & GitHub
- **File Format**: CSV

---

## 📦 Struktur Project
```
UAP-Booking-Badminton/
├── src/
│   └── UAP/
│       └── SistemBookingBadminton.java
├── booking_data.csv (auto-generated)
├── README.md
├── TESTING.md
└── CODE_REVIEW.md
```

---

## 🚀 Cara Menjalankan Program

### Prasyarat
- Java Development Kit (JDK) 8 atau lebih tinggi
- IDE Java (IntelliJ IDEA/NetBeans/Eclipse) atau text editor + terminal

### Langkah-langkah:

#### **Opsi 1: Menggunakan IDE**
1. Clone repository:
```bash
   git clone https://github.com/username/uap-booking-badminton.git
```
2. Buka project di IDE
3. Buka file `SistemBookingBadminton.java`
4. Jalankan method `main()`

#### **Opsi 2: Menggunakan Terminal**
1. Clone repository
2. Navigate ke folder project:
```bash
   cd uap-booking-badminton/src
```
3. Compile:
```bash
   javac UAP/SistemBookingBadminton.java
```
4. Run:
```bash
   java UAP.SistemBookingBadminton
```

---

## 📖 Panduan Penggunaan

### 1. **Lihat Lapangan**
- Klik "📋 Lihat Lapangan"
- Tampil daftar 4 lapangan dengan harga dan fasilitas

### 2. **Booking Baru**
- Klik "➕ Booking Baru"
- Isi form: Nama, Telepon, Lapangan, Tanggal, Jam
- Validasi otomatis:
    - Nama minimal 3 karakter
    - Telepon 10-13 digit (hanya angka)
    - Tanggal tidak boleh masa lalu
    - Jam selesai > jam mulai
- Diskon 10% untuk booking ≥ 3 jam
- Klik "💾 Booking"

### 3. **Kelola Booking**
- Klik "📊 Kelola Booking"
- **Cari**: Ketik nama/ID/lapangan, klik "🔍 Cari"
- **Sort**: Pilih opsi dari dropdown
- **Edit**:
    - Klik baris di tabel
    - Edit di form bawah
    - Klik "✏️ Update"
- **Hapus**: Pilih baris → "🗑️ Hapus"

### 4. **Laporan**
- Klik "📈 Laporan"
- Lihat statistik:
    - Total booking
    - Total pendapatan
    - Lapangan terfavorit
    - Daftar booking terakhir

### 5. **Simpan Data**
- Otomatis saat tutup aplikasi
- Manual: Klik "💾 Simpan Data"

---

## 🧪 Testing

Lihat file `TESTING.md` untuk detail hasil testing:
- Unit testing manual setiap fitur CRUD
- Test validasi input
- Test file handling
- Test sorting & searching

---

## 🔍 Code Review

Lihat file `CODE_REVIEW.md` untuk:
- Evaluasi kualitas kode
- Refactoring yang dilakukan
- Best practices yang diterapkan

---

## 📊 Implementasi Materi (Modul 1-6)

| Materi | Implementasi |
|--------|--------------|
| **OOP** | Class `Booking`, inner classes, encapsulation |
| **Exception Handling** | Try-catch pada file I/O dan validasi |
| **File Handling** | CSV read/write dengan BufferedReader/Writer |
| **Java API** | LocalDate, ArrayList, Comparator, DateTimeFormatter |
| **GUI (Swing)** | JFrame, JPanel, JTable, CardLayout, dll |
| **CRUD** | Create, Read, Update, Delete lengkap |
| **Sorting** | Comparator untuk multiple criteria |
| **Searching** | TableRowSorter dengan RowFilter |
| **Data Validation** | Defensive programming dengan validasi input |
| **JavaDoc** | Dokumentasi pada class dan method |

---

## 🎨 Fitur Tambahan

1. **Diskon Otomatis**: 10% untuk booking ≥ 3 jam
2. **Auto-increment ID**: ID booking otomatis
3. **Format Currency**: Tampilan Rupiah
4. **Color Coding**: Warna berbeda per menu
5. **Gradient Header**: Tampilan modern
6. **Hover Effect**: Button interaktif

---

## 📝 Catatan Penting

### File Data (`booking_data.csv`)
- Dibuat otomatis saat pertama kali save
- Format: `ID,Nama,Telepon,Lapangan,Tanggal,JamMulai,JamSelesai,Total`
- **JANGAN dihapus** saat aplikasi berjalan
- Backup berkala direkomendasikan

### Validasi Input
- Nama: minimal 3 karakter
- Telepon: 10-13 digit, hanya angka
- Tanggal: tidak boleh masa lalu
- Jam: selesai harus > mulai (8:00 - 23:00)

---

## 🐛 Troubleshooting

**Q: Data hilang setelah restart?**  
A: Pastikan file `booking_data.csv` ada di root folder project

**Q: Error saat compile?**  
A: Pastikan JDK sudah terinstall dan PATH sudah diset

**Q: GUI tidak muncul?**  
A: Cek apakah Java Swing sudah ter-support di system

---

## 📞 Kontak & Support

Jika ada pertanyaan atau issue:
1. Buat issue di GitHub repository
2. Hubungi anggota kelompok
3. Konsultasi dengan asisten lab

---

## 📄 Lisensi

Project ini dibuat untuk keperluan UAP Pemrograman Lanjut 2025.  
© 2025 - Universitas Muhammadiyah Malang

---

## 🙏 Terima Kasih

Terima kasih kepada:
- Dosen Pengampu: Ir. Wildan Suharso, M.Kom.
- Asisten Praktikum
- Tim Laboratorium Informatika UMM

---

**Last Updated**: Desember 2025  
**Version**: 1.0
