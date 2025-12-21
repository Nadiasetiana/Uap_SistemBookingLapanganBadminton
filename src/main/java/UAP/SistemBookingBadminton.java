package UAP;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;

public class SistemBookingBadminton extends JFrame {

    private static ArrayList<Booking> bookingList = new ArrayList<>();
    private static int idCounter = 1;

    private JPanel cardPanel;
    private CardLayout cardLayout;
    //jj

    // Class untuk merepresentasikan data booking
    static class Booking {
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

    public SistemBookingBadminton() {
        setTitle("Sistem Booking Badminton");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createDashboard(), "MENU");
        cardPanel.add(createLihatLapangan(), "LAPANGAN");
        cardPanel.add(new BookingPanel(), "BOOKING");
        cardPanel.add(new KelolaPanel(), "KELOLA");

        add(cardPanel);
        cardLayout.show(cardPanel, "MENU");
    }

    private JPanel createDashboard() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(new Color(41, 98, 255));
        header.setPreferredSize(new Dimension(900, 100));
        JLabel title = new JLabel("🏸 SISTEM BOOKING BADMINTON 🏸");
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(Color.WHITE);
        header.add(title);

        JPanel menu = new JPanel(new GridLayout(2, 2, 20, 20));
        menu.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));

        JButton btn1 = createButton("📋 Lihat Lapangan", new Color(33, 150, 243));
        JButton btn2 = createButton("➕ Booking Baru", new Color(76, 175, 80));
        JButton btn3 = createButton("📊 Kelola Booking", new Color(255, 152, 0));
        JButton btn4 = createButton("❌ Keluar", new Color(244, 67, 54));

        btn1.addActionListener(e -> cardLayout.show(cardPanel, "LAPANGAN"));
        btn2.addActionListener(e -> {
            cardPanel.remove(2);
            cardPanel.add(new BookingPanel(), "BOOKING", 2);
            cardLayout.show(cardPanel, "BOOKING");
        });
        btn3.addActionListener(e -> {
            cardPanel.remove(3);
            cardPanel.add(new KelolaPanel(), "KELOLA", 3);
            cardLayout.show(cardPanel, "KELOLA");
        });
        btn4.addActionListener(e -> System.exit(0));

        menu.add(btn1);
        menu.add(btn2);
        menu.add(btn3);
        menu.add(btn4);

        panel.add(header, BorderLayout.NORTH);
        panel.add(menu, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createLihatLapangan() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel header = new JPanel();
        header.setBackground(new Color(41, 98, 255));
        header.setPreferredSize(new Dimension(900, 70));
        JLabel title = new JLabel("📋 DAFTAR LAPANGAN BADMINTON");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title);

        String[] cols = {"No", "Nama Lapangan", "Tipe Lantai", "Harga/Jam"};
        String[][] data = {
                {"1", "Court A", "Vinyl Premium", "Rp 50.000"},
                {"2", "Court B", "Karpet Standar", "Rp 40.000"},
                {"3", "Court C", "Vinyl Premium", "Rp 50.000"},
                {"4", "Court D", "Karpet Standar", "Rp 40.000"}
        };

        JTable table = new JTable(data, cols);
        table.setRowHeight(35);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(76, 175, 80));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setEnabled(false);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnBack = createButton("Kembali", new Color(96, 125, 139));
        btnBack.addActionListener(e -> cardLayout.show(cardPanel, "MENU"));

        JPanel bottom = new JPanel();
        bottom.add(btnBack);

        panel.add(header, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);

        return panel;
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(180, 45));
        return btn;
    }

    class BookingPanel extends JPanel {
        JTextField txtNama, txtTelp;
        JComboBox<String> comboLap;
        JSpinner spinMulai, spinSelesai;
        JSpinner spinTanggal;

        BookingPanel() {
            setLayout(new BorderLayout());

            JPanel header = new JPanel();
            header.setBackground(new Color(41, 98, 255));
            header.setPreferredSize(new Dimension(900, 70));
            JLabel title = new JLabel("➕ BOOKING LAPANGAN");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setForeground(Color.WHITE);
            header.add(title);

            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(10, 10, 10, 10);

            comboLap = new JComboBox<>(new String[]{
                    "Court A (Vinyl Premium) - Rp 50.000",
                    "Court B (Karpet Standar) - Rp 40.000",
                    "Court C (Vinyl Premium) - Rp 50.000",
                    "Court D (Karpet Standar) - Rp 40.000"
            });
            txtNama = new JTextField(20);
            txtTelp = new JTextField(20);

            // Menggunakan JSpinner dengan LocalDate untuk tanggal
            SpinnerDateModel dateModel = new SpinnerDateModel();
            spinTanggal = new JSpinner(dateModel);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinTanggal, "dd-MM-yyyy");
            spinTanggal.setEditor(dateEditor);

            spinMulai = new JSpinner(new SpinnerNumberModel(8, 8, 22, 1));
            spinSelesai = new JSpinner(new SpinnerNumberModel(10, 8, 22, 1));

            int row = 0;
            addRow(form, gbc, row++, "Lapangan:", comboLap);
            addRow(form, gbc, row++, "Nama:", txtNama);
            addRow(form, gbc, row++, "Telepon:", txtTelp);
            addRow(form, gbc, row++, "Tanggal:", spinTanggal);
            addRow(form, gbc, row++, "Jam Mulai:", spinMulai);
            addRow(form, gbc, row++, "Jam Selesai:", spinSelesai);

            JButton btnSave = createButton("Booking", new Color(76, 175, 80));
            JButton btnCancel = createButton("Batal", new Color(158, 158, 158));

            btnSave.addActionListener(e -> saveBooking());
            btnCancel.addActionListener(e -> cardLayout.show(cardPanel, "MENU"));

            JPanel btnPanel = new JPanel();
            btnPanel.add(btnSave);
            btnPanel.add(btnCancel);

            add(header, BorderLayout.NORTH);
            add(form, BorderLayout.CENTER);
            add(btnPanel, BorderLayout.SOUTH);
        }

        void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent comp) {
            g.gridx = 0;
            g.gridy = row;
            g.weightx = 0.3;
            JLabel lbl = new JLabel(label);
            lbl.setFont(new Font("Arial", Font.BOLD, 14));
            p.add(lbl, g);

            g.gridx = 1;
            g.weightx = 0.7;
            comp.setFont(new Font("Arial", Font.PLAIN, 14));
            p.add(comp, g);
        }

        void saveBooking() {
            String nama = txtNama.getText().trim();
            String telp = txtTelp.getText().trim();

            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama harus diisi!");
                return;
            }
            if (telp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Telepon harus diisi!");
                return;
            }

            int mulai = (Integer) spinMulai.getValue();
            int selesai = (Integer) spinSelesai.getValue();

            if (mulai >= selesai) {
                JOptionPane.showMessageDialog(this, "Jam selesai harus lebih besar dari jam mulai!");
                return;
            }

            // Konversi tanggal dari JSpinner ke LocalDate
            java.util.Date date = (java.util.Date) spinTanggal.getValue();
            LocalDate tanggal = LocalDate.ofInstant(date.toInstant(),
                    java.time.ZoneId.systemDefault());

            // Validasi tanggal tidak boleh di masa lalu
            if (tanggal.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(this, "Tanggal booking tidak boleh di masa lalu!");
                return;
            }

            String lap = comboLap.getSelectedItem().toString().split(" \\(")[0];
            int harga = (comboLap.getSelectedIndex() == 0 || comboLap.getSelectedIndex() == 2)
                    ? 50000 : 40000;

            int durasi = selesai - mulai;
            double total = harga * durasi;

            // Diskon 10% untuk booking 3 jam atau lebih
            if (durasi >= 3) {
                total *= 0.9;
            }

            Booking booking = new Booking(
                    String.valueOf(idCounter++),
                    nama, telp, lap, tanggal,
                    mulai, selesai, total
            );

            bookingList.add(booking);

            JOptionPane.showMessageDialog(this,
                    "✓ Booking Berhasil!\n" +
                            "ID: " + booking.id + "\n" +
                            "Lapangan: " + booking.lapangan + "\n" +
                            "Tanggal: " + booking.getTanggalFormat() + "\n" +
                            "Jam: " + booking.getJamFormat() + "\n" +
                            "Total: " + booking.getTotalFormat() +
                            (durasi >= 3 ? "\n(Diskon 10% sudah diterapkan)" : ""));

            cardLayout.show(cardPanel, "MENU");
        }
    }

    class KelolaPanel extends JPanel {
        JTable table;
        DefaultTableModel model;
        JTextField txtNama, txtTelp;
        JComboBox<String> comboLap;
        JSpinner spinMulai, spinSelesai, spinTanggal;
        JComboBox<String> comboSort;
        int selectedRow = -1;

        KelolaPanel() {
            setLayout(new BorderLayout());

            JPanel header = new JPanel();
            header.setBackground(new Color(41, 98, 255));
            header.setPreferredSize(new Dimension(900, 70));
            JLabel title = new JLabel("📊 KELOLA BOOKING");
            title.setFont(new Font("Arial", Font.BOLD, 24));
            title.setForeground(Color.WHITE);
            header.add(title);

            String[] cols = {"ID", "Nama", "Telepon", "Lapangan", "Tanggal", "Jam", "Total"};
            model = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };

            table = new JTable(model);
            table.setRowHeight(30);
            table.setFont(new Font("Arial", Font.PLAIN, 13));
            table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
            table.getTableHeader().setBackground(new Color(76, 175, 80));
            table.getTableHeader().setForeground(Color.WHITE);

            loadData();

            table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    loadToForm();
                }
            });

            JScrollPane scroll = new JScrollPane(table);
            scroll.setPreferredSize(new Dimension(880, 180));
            scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Panel untuk sorting
            JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            sortPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            JLabel lblSort = new JLabel("Urutkan berdasarkan:");
            lblSort.setFont(new Font("Arial", Font.BOLD, 12));
            comboSort = new JComboBox<>(new String[]{
                    "ID (Default)", "Nama (A-Z)", "Nama (Z-A)",
                    "Tanggal (Terlama)", "Tanggal (Terbaru)", "Total (Terendah)", "Total (Tertinggi)"
            });
            comboSort.addActionListener(e -> sortBookings());

            sortPanel.add(lblSort);
            sortPanel.add(comboSort);

            JPanel form = new JPanel(new GridBagLayout());
            form.setBorder(BorderFactory.createTitledBorder("Edit Booking"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(8, 10, 8, 10);

            comboLap = new JComboBox<>(new String[]{
                    "Court A", "Court B", "Court C", "Court D"
            });
            txtNama = new JTextField(15);
            txtTelp = new JTextField(15);

            SpinnerDateModel dateModel = new SpinnerDateModel();
            spinTanggal = new JSpinner(dateModel);
            JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinTanggal, "dd-MM-yyyy");
            spinTanggal.setEditor(dateEditor);

            spinMulai = new JSpinner(new SpinnerNumberModel(8, 8, 22, 1));
            spinSelesai = new JSpinner(new SpinnerNumberModel(10, 8, 22, 1));

            int row = 0;
            addRow(form, gbc, row++, "Lapangan:", comboLap);
            addRow(form, gbc, row++, "Nama:", txtNama);
            addRow(form, gbc, row++, "Telepon:", txtTelp);
            addRow(form, gbc, row++, "Tanggal:", spinTanggal);
            addRow(form, gbc, row++, "Jam Mulai:", spinMulai);
            addRow(form, gbc, row++, "Jam Selesai:", spinSelesai);

            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.add(scroll, BorderLayout.CENTER);
            topPanel.add(sortPanel, BorderLayout.SOUTH);

            JPanel center = new JPanel(new BorderLayout());
            center.add(topPanel, BorderLayout.NORTH);
            center.add(form, BorderLayout.CENTER);

            JButton btnUpdate = createButton("Update", new Color(255, 152, 0));
            JButton btnDelete = createButton("Hapus", new Color(244, 67, 54));
            JButton btnClear = createButton("Clear", new Color(96, 125, 139));
            JButton btnBack = createButton("Kembali", new Color(117, 117, 117));

            btnUpdate.addActionListener(e -> updateBooking());
            btnDelete.addActionListener(e -> deleteBooking());
            btnClear.addActionListener(e -> {
                clearForm();
                table.clearSelection();
            });
            btnBack.addActionListener(e -> cardLayout.show(cardPanel, "MENU"));

            JPanel btnPanel = new JPanel();
            btnPanel.add(btnUpdate);
            btnPanel.add(btnDelete);
            btnPanel.add(btnClear);
            btnPanel.add(btnBack);

            add(header, BorderLayout.NORTH);
            add(center, BorderLayout.CENTER);
            add(btnPanel, BorderLayout.SOUTH);
        }

        void addRow(JPanel p, GridBagConstraints g, int row, String label, JComponent comp) {
            g.gridx = 0;
            g.gridy = row;
            g.weightx = 0.3;
            JLabel lbl = new JLabel(label);
            lbl.setFont(new Font("Arial", Font.BOLD, 13));
            p.add(lbl, g);

            g.gridx = 1;
            g.weightx = 0.7;
            comp.setFont(new Font("Arial", Font.PLAIN, 13));
            p.add(comp, g);
        }

        void sortBookings() {
            int index = comboSort.getSelectedIndex();

            switch(index) {
                case 1: // Nama A-Z
                    bookingList.sort(Comparator.comparing(b -> b.nama));
                    break;
                case 2: // Nama Z-A
                    bookingList.sort(Comparator.comparing((Booking b) -> b.nama).reversed());
                    break;
                case 3: // Tanggal Terlama
                    bookingList.sort(Comparator.comparing(b -> b.tanggal));
                    break;
                case 4: // Tanggal Terbaru
                    bookingList.sort(Comparator.comparing((Booking b) -> b.tanggal).reversed());
                    break;
                case 5: // Total Terendah
                    bookingList.sort(Comparator.comparing(b -> b.total));
                    break;
                case 6: // Total Tertinggi
                    bookingList.sort(Comparator.comparing((Booking b) -> b.total).reversed());
                    break;
                default: // ID Default
                    bookingList.sort(Comparator.comparing(b -> Integer.parseInt(b.id)));
                    break;
            }

            loadData();
            clearForm();
            table.clearSelection();
        }

        void loadData() {
            model.setRowCount(0);
            for (Booking b : bookingList) {
                model.addRow(new Object[]{
                        b.id, b.nama, b.telepon, b.lapangan,
                        b.getTanggalFormat(), b.getJamFormat(), b.getTotalFormat()
                });
            }
        }

        void loadToForm() {
            selectedRow = table.getSelectedRow();
            if (selectedRow == -1) return;

            Booking b = bookingList.get(selectedRow);
            txtNama.setText(b.nama);
            txtTelp.setText(b.telepon);

            // Set tanggal ke spinner
            java.util.Date date = java.util.Date.from(
                    b.tanggal.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
            spinTanggal.setValue(date);

            if (b.lapangan.equals("Court A")) comboLap.setSelectedIndex(0);
            else if (b.lapangan.equals("Court B")) comboLap.setSelectedIndex(1);
            else if (b.lapangan.equals("Court C")) comboLap.setSelectedIndex(2);
            else comboLap.setSelectedIndex(3);

            spinMulai.setValue(b.jamMulai);
            spinSelesai.setValue(b.jamSelesai);
        }

        void updateBooking() {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih booking dari tabel!");
                return;
            }

            String nama = txtNama.getText().trim();
            String telp = txtTelp.getText().trim();

            if (nama.isEmpty() || telp.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            int mulai = (Integer) spinMulai.getValue();
            int selesai = (Integer) spinSelesai.getValue();

            if (mulai >= selesai) {
                JOptionPane.showMessageDialog(this, "Jam selesai harus lebih besar dari jam mulai!");
                return;
            }

            java.util.Date date = (java.util.Date) spinTanggal.getValue();
            LocalDate tanggal = LocalDate.ofInstant(date.toInstant(),
                    java.time.ZoneId.systemDefault());

            String lap = comboLap.getSelectedItem().toString();
            int harga = (comboLap.getSelectedIndex() == 0 || comboLap.getSelectedIndex() == 2)
                    ? 50000 : 40000;

            int durasi = selesai - mulai;
            double total = harga * durasi;
            if (durasi >= 3) total *= 0.9;

            Booking b = bookingList.get(selectedRow);
            b.nama = nama;
            b.telepon = telp;
            b.lapangan = lap;
            b.tanggal = tanggal;
            b.jamMulai = mulai;
            b.jamSelesai = selesai;
            b.total = total;

            loadData();
            clearForm();
            JOptionPane.showMessageDialog(this, "✓ Booking berhasil diupdate!");
        }

        void deleteBooking() {
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih booking dari tabel!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Hapus booking ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bookingList.remove(selectedRow);
                loadData();
                clearForm();
                JOptionPane.showMessageDialog(this, "✓ Booking berhasil dihapus!");
            }
        }

        void clearForm() {
            txtNama.setText("");
            txtTelp.setText("");
            spinTanggal.setValue(new java.util.Date());
            comboLap.setSelectedIndex(0);
            spinMulai.setValue(8);
            spinSelesai.setValue(10);
            selectedRow = -1;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SistemBookingBadminton().setVisible(true));
    }
}