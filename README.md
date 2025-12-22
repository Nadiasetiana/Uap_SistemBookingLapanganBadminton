# 🏸 Sistem Booking Lapangan Badminton

##  Deskripsi Proyek
Aplikasi desktop untuk mengelola booking lapangan badminton dengan fitur CRUD, file handling, sorting, searching, dan laporan statistik.


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
- Desain modern dan warna menarik
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

---

## 📦 Struktur Project
```
UAP-Booking-Badminton/
├── .idea
├── src/
│   └── UAP/
        └── Booking.java
│       └── SistemBookingBadminton.java
├── booking_data.csv
├── README.md
└── pom.xml
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


