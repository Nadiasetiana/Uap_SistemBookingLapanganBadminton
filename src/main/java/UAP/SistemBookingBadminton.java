package UAP;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Sistem Booking Lapangan Badminton
 * UAP Pemrograman Lanjut 2025
 *
 * Fitur:
 * - CRUD Operations (Create, Read, Update, Delete)
 * - File Handling (CSV format)
 * - GUI dengan Java Swing
 * - Sorting & Searching
 * - Exception Handling
 * - Data Validation
 * - Laporan & Statistik
 */
public class SistemBookingBadminton extends JFrame {

    private static final String DATA_FILE = "booking_data.csv";
    private static ArrayList<Booking> bookingList = new ArrayList<>();
    private static int idCounter = 1;

    private JPanel cardPanel;
    private CardLayout cardLayout;

    public SistemBookingBadminton() {
        setTitle("Sistem Booking Badminton - UAP 2025");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadDataFromFile();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(createDashboard(), "MENU");
        cardPanel.add(createLihatLapangan(), "LAPANGAN");
        cardPanel.add(new BookingPanel(), "BOOKING");
        cardPanel.add(new KelolaPanel(), "KELOLA");
        cardPanel.add(new LaporanPanel(), "LAPORAN");

        add(cardPanel);
        cardLayout.show(cardPanel, "MENU");

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                saveDataToFile();
            }
        });
    }

    private void saveDataToFile() { // FILE HANDLING - WRITE
        try (BufferedWriter w = new BufferedWriter(new FileWriter(DATA_FILE))) {
            w.write("ID,Nama,Telepon,Lapangan,Tanggal,JamMulai,JamSelesai,Total\n");
            for (Booking b : bookingList) {
                w.write(b.toCSV() + "\n");
            }
            System.out.println("✓ Data disimpan: " + bookingList.size() + " booking");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error save: " + e.getMessage());
        }
    }

    private void loadDataFromFile() { // FILE HANDLING - READ
        File f = new File(DATA_FILE);
        if (!f.exists()) return;

        try (BufferedReader r = new BufferedReader(new FileReader(f))) {
            r.readLine(); // skip header
            String line;
            bookingList.clear();
            int maxId = 0;

            while ((line = r.readLine()) != null) {
                try {
                    Booking b = Booking.fromCSV(line);
                    bookingList.add(b);
                    int id = Integer.parseInt(b.getId());
                    if (id > maxId) maxId = id;
                } catch (Exception e) {
                    System.err.println("Parse error: " + line);
                }
            }
            idCounter = maxId + 1;
            System.out.println("✓ Data loaded: " + bookingList.size() + " booking");
        } catch (IOException e) {
            System.err.println("Load error: " + e.getMessage());
        }
    }

    private JPanel createDashboard() {
        JPanel p = new JPanel(new BorderLayout());

        JPanel header = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(41, 98, 255),
                        0, getHeight(), new Color(0, 51, 153));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        }; header.setPreferredSize(new Dimension(950, 120));

        JLabel title = new JLabel("SISTEM BOOKING BADMINTON");
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        header.setLayout(new GridBagLayout());
        header.add(title);

        JPanel menu = new JPanel(new GridLayout(3, 2, 25, 25));
        menu.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        menu.setBackground(new Color(245, 247, 250));

        JButton b1 = createMenuBtn("Lihat Lapangan", new Color(128, 200, 239));
        JButton b2 = createMenuBtn("Booking Baru", new Color(151, 234, 153));
        JButton b3 = createMenuBtn("Kelola Booking", new Color(228, 176, 99));
        JButton b4 = createMenuBtn("Laporan", new Color(248, 145, 234));
        JButton b5 = createMenuBtn("Simpan Data", new Color(0, 150, 136));
        JButton b6 = createMenuBtn("Keluar", new Color(244, 67, 54));

        b1.addActionListener(e -> cardLayout.show(cardPanel, "LAPANGAN"));
        b2.addActionListener(e -> {
            cardPanel.remove(2);
            cardPanel.add(new BookingPanel(), "BOOKING", 2);
            cardLayout.show(cardPanel, "BOOKING"); });
        b3.addActionListener(e -> {
            cardPanel.remove(3);
            cardPanel.add(new KelolaPanel(), "KELOLA", 3);
            cardLayout.show(cardPanel, "KELOLA"); });
        b4.addActionListener(e -> {
            cardPanel.remove(4);
            cardPanel.add(new LaporanPanel(), "LAPORAN", 4);
            cardLayout.show(cardPanel, "LAPORAN"); });
        b5.addActionListener(e -> {
            saveDataToFile();
            JOptionPane.showMessageDialog(this, "✓ Data berhasil disimpan!");
        });
        b6.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Keluar?") == 0) {
                saveDataToFile();
                System.exit(0);
            }
        });

        menu.add(b1); menu.add(b2); menu.add(b3);
        menu.add(b4); menu.add(b5); menu.add(b6);
        p.add(header, BorderLayout.NORTH);
        p.add(menu, BorderLayout.CENTER);
        return p;
    }

    private JButton createMenuBtn(String txt, Color col) {
        JButton b = new JButton(txt);
        b.setFont(new Font("Segoe UI", Font.BOLD, 18));
        b.setForeground(Color.WHITE);
        b.setBackground(col);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(col.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent e) { b.setBackground(col); }
        }); return b;
    }

    private JPanel createLihatLapangan() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(245, 247, 250));
        p.add(createHeader("DAFTAR LAPANGAN"), BorderLayout.NORTH);

        String[] cols = {"No", "Lapangan", "Tipe", "Harga/Jam", "Fasilitas"};
        String[][] data = {
                {"1", "Court A", "Vinyl Premium", "Rp 50.000", "AC, Lighting"},
                {"2", "Court B", "Karpet Standar", "Rp 40.000", "Lighting"},
                {"3", "Court C", "Vinyl Premium", "Rp 50.000", "AC, Lighting"},
                {"4", "Court D", "Karpet Standar", "Rp 40.000", "Lighting"}
        };

        JTable tbl = new JTable(data, cols);
        tbl.setRowHeight(40);
        tbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tbl.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tbl.getTableHeader().setBackground(new Color(76, 175, 80));
        tbl.getTableHeader().setForeground(Color.BLACK);
        tbl.setEnabled(false);

        JPanel bottom = new JPanel();
        bottom.setBackground(new Color(245, 247, 250));
        JButton back = createBtn("Kembali", new Color(96, 125, 139));
        back.addActionListener(e -> cardLayout.show(cardPanel, "MENU"));
        bottom.add(back);

        p.add(new JScrollPane(tbl), BorderLayout.CENTER);
        p.add(bottom, BorderLayout.SOUTH);
        return p;
    }

    private JPanel createHeader(String txt) {
        JPanel h = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(128, 200, 239),
                        0, getHeight(), new Color(49, 81, 147));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        h.setPreferredSize(new Dimension(950, 80));
        JLabel l = new JLabel(txt);
        l.setFont(new Font("Segoe UI", Font.BOLD, 26));
        l.setForeground(Color.WHITE);
        h.setLayout(new GridBagLayout());
        h.add(l);
        return h;
    }

    private JButton createBtn(String txt, Color col) {
        JButton b = new JButton(txt);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setForeground(Color.WHITE);
        b.setBackground(col);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(160, 42));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) { b.setBackground(col.brighter()); }
            public void mouseExited(java.awt.event.MouseEvent e) { b.setBackground(col); }
        }); return b;
    }

    class BookingPanel extends JPanel { // PANEL BOOKING (CREATE)
        JTextField txtNama, txtTelp;
        JComboBox<String> comboLap;
        JSpinner spinMulai, spinSelesai, spinTanggal;

        BookingPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(245, 247, 250));
            add(createHeader("BOOKING BARU"), BorderLayout.NORTH);

            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(Color.WHITE);
            form.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(12, 15, 12, 15);

            comboLap = new JComboBox<>(new String[]{
                    "Court A (Vinyl) - Rp 50.000", "Court B (Karpet) - Rp 40.000",
                    "Court C (Vinyl) - Rp 50.000", "Court D (Karpet) - Rp 40.000"
            });
            txtNama = new JTextField(25);
            txtTelp = new JTextField(25);
            spinTanggal = new JSpinner(new SpinnerDateModel());
            spinTanggal.setEditor(new JSpinner.DateEditor(spinTanggal, "dd-MM-yyyy"));
            spinMulai = new JSpinner(new SpinnerNumberModel(8, 8, 22, 1));
            spinSelesai = new JSpinner(new SpinnerNumberModel(10, 8, 23, 1));

            addRow(form, gbc, 0, "Lapangan:", comboLap);
            addRow(form, gbc, 1, "Nama:", txtNama);
            addRow(form, gbc, 2, "Telepon:", txtTelp);
            addRow(form, gbc, 3, "Tanggal:", spinTanggal);
            addRow(form, gbc, 4, "Jam Mulai:", spinMulai);
            addRow(form, gbc, 5, "Jam Selesai:", spinSelesai);

            JButton save = createBtn("Booking", new Color(76, 175, 80));
            JButton cancel = createBtn("Batal", new Color(158, 158, 158));
            save.addActionListener(e -> saveBooking());
            cancel.addActionListener(e -> cardLayout.show(cardPanel, "MENU"));

            JPanel btnP = new JPanel();
            btnP.setBackground(new Color(245, 247, 250));
            btnP.add(save);
            btnP.add(cancel);

            add(form, BorderLayout.CENTER);
            add(btnP, BorderLayout.SOUTH);
        }

        void addRow(JPanel p, GridBagConstraints g, int row, String lbl, JComponent comp) {
            g.gridx = 0; g.gridy = row; g.weightx = 0.35;
            JLabel l = new JLabel(lbl);
            l.setFont(new Font("Segoe UI", Font.BOLD, 15));
            p.add(l, g);
            g.gridx = 1; g.weightx = 0.65;
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            p.add(comp, g);
        }

        void saveBooking() {
            try {
                String nama = txtNama.getText().trim();
                String telp = txtTelp.getText().trim();

                if (nama.isEmpty()) throw new IllegalArgumentException("Nama harus diisi!");
                if (nama.length() < 3) throw new IllegalArgumentException("Nama min 3 karakter!");
                if (telp.isEmpty()) throw new IllegalArgumentException("Telepon harus diisi!");
                if (!telp.matches("\\d{10,13}"))
                    throw new IllegalArgumentException("Telepon 10-13 digit!");

                int mulai = (Integer) spinMulai.getValue();
                int selesai = (Integer) spinSelesai.getValue();
                if (mulai >= selesai)
                    throw new IllegalArgumentException("Jam selesai > jam mulai!");

                java.util.Date d = (java.util.Date) spinTanggal.getValue();
                LocalDate tgl = LocalDate.ofInstant(d.toInstant(), java.time.ZoneId.systemDefault());
                if (tgl.isBefore(LocalDate.now()))
                    throw new IllegalArgumentException("Tanggal tidak boleh masa lalu!");

                String lap = comboLap.getSelectedItem().toString().split(" \\(")[0];
                int harga = (comboLap.getSelectedIndex() % 2 == 0) ? 50000 : 40000;
                int durasi = selesai - mulai;
                double total = harga * durasi;
                if (durasi >= 3) total *= 0.9;

                Booking b = new Booking(String.valueOf(idCounter++), nama, telp, lap,
                        tgl, mulai, selesai, total);
                bookingList.add(b);
                saveDataToFile();

                JOptionPane.showMessageDialog(this, String.format(
                        "✓ Booking Berhasil!\n\nID: #%s\nNama: %s\nLapangan: %s\n" +
                                "Tanggal: %s\nJam: %s\nTotal: %s%s",
                        b.getId(), b.getNama(), b.getLapangan(), b.getTanggalFormat(),
                        b.getJamFormat(), b.getTotalFormat(),
                        (durasi >= 3 ? "\n(Diskon 10%)" : "")));

                cardLayout.show(cardPanel, "MENU");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage(),
                        "Validasi Error", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        }
    }

    class KelolaPanel extends JPanel { // PANEL KELOLA (READ, UPDATE, DELETE)
        JTable table;
        DefaultTableModel model;
        TableRowSorter<DefaultTableModel> sorter;
        JTextField txtNama, txtTelp, txtSearch;
        JComboBox<String> comboLap, comboSort;
        JSpinner spinMulai, spinSelesai, spinTanggal;
        int selectedRow = -1;

        KelolaPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(245, 247, 250));
            add(createHeader("KELOLA BOOKING"), BorderLayout.NORTH);

            JPanel topP = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
            topP.setBackground(Color.WHITE);

            txtSearch = new JTextField(20);
            txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            JButton btnSearch = createBtn("Cari", new Color(33, 150, 243));
            btnSearch.addActionListener(e -> searchData());

            comboSort = new JComboBox<>(new String[]{
                    "ID", "Nama A-Z", "Nama Z-A", "Tanggal ↑", "Tanggal ↓", "Total ↑", "Total ↓"
            });
            comboSort.addActionListener(e -> sortData());

            topP.add(new JLabel("Cari:"));
            topP.add(txtSearch);
            topP.add(btnSearch);
            topP.add(Box.createHorizontalStrut(20));
            topP.add(new JLabel("Sort:"));
            topP.add(comboSort);

            String[] cols = {"ID", "Nama", "Telepon", "Lapangan", "Tanggal", "Jam", "Total"};
            model = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            table = new JTable(model);
            table.setRowHeight(30);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            table.getTableHeader().setBackground(new Color(76, 175, 80));
            table.getTableHeader().setForeground(Color.BLACK);

            sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            table.getSelectionModel().addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) loadToForm();
            });

            loadData();

            JScrollPane scroll = new JScrollPane(table);
            scroll.setPreferredSize(new Dimension(900, 200));

            JPanel form = new JPanel(new GridBagLayout());
            form.setBackground(Color.WHITE);
            form.setBorder(BorderFactory.createTitledBorder("Edit Booking"));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(8, 10, 8, 10);

            comboLap = new JComboBox<>(new String[]{"Court A", "Court B", "Court C", "Court D"});
            txtNama = new JTextField(15);
            txtTelp = new JTextField(15);
            spinTanggal = new JSpinner(new SpinnerDateModel());
            spinTanggal.setEditor(new JSpinner.DateEditor(spinTanggal, "dd-MM-yyyy"));
            spinMulai = new JSpinner(new SpinnerNumberModel(8, 8, 22, 1));
            spinSelesai = new JSpinner(new SpinnerNumberModel(10, 8, 23, 1));

            addRow(form, gbc, 0, "Lapangan:", comboLap);
            addRow(form, gbc, 1, "Nama:", txtNama);
            addRow(form, gbc, 2, "Telepon:", txtTelp);
            addRow(form, gbc, 3, "Tanggal:", spinTanggal);
            addRow(form, gbc, 4, "Jam Mulai:", spinMulai);
            addRow(form, gbc, 5, "Jam Selesai:", spinSelesai);

            JPanel centerP = new JPanel(new BorderLayout());
            centerP.add(scroll, BorderLayout.NORTH);
            centerP.add(form, BorderLayout.CENTER);

            JButton update = createBtn("Update", new Color(255, 152, 0));
            JButton delete = createBtn("Hapus", new Color(244, 67, 54));
            JButton clear = createBtn("Clear", new Color(96, 125, 139));
            JButton back = createBtn("Kembali", new Color(117, 117, 117));

            update.addActionListener(e -> updateBooking());
            delete.addActionListener(e -> deleteBooking());
            clear.addActionListener(e -> { clearForm(); table.clearSelection(); });
            back.addActionListener(e -> cardLayout.show(cardPanel, "MENU"));

            JPanel btnP = new JPanel();
            btnP.setBackground(new Color(245, 247, 250));
            btnP.add(update); btnP.add(delete); btnP.add(clear); btnP.add(back);

            JPanel mainP = new JPanel(new BorderLayout());
            mainP.add(topP, BorderLayout.NORTH);
            mainP.add(centerP, BorderLayout.CENTER);

            add(mainP, BorderLayout.CENTER);
            add(btnP, BorderLayout.SOUTH);
        }

        void addRow(JPanel p, GridBagConstraints g, int row, String lbl, JComponent comp) {
            g.gridx = 0; g.gridy = row; g.weightx = 0.3;
            JLabel l = new JLabel(lbl);
            l.setFont(new Font("Segoe UI", Font.BOLD, 13));
            p.add(l, g);
            g.gridx = 1; g.weightx = 0.7;
            comp.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            p.add(comp, g);
        }

        void loadData() {
            model.setRowCount(0);
            for (Booking b : bookingList) {
                model.addRow(new Object[]{b.getId(), b.getNama(), b.getTelepon(),
                        b.getLapangan(), b.getTanggalFormat(), b.getJamFormat(), b.getTotalFormat()});
            }
        }

        void sortData() {
            int idx = comboSort.getSelectedIndex();
            switch(idx) {
                case 1: bookingList.sort(Comparator.comparing(Booking::getNama)); break;
                case 2: bookingList.sort(Comparator.comparing(Booking::getNama).reversed()); break;
                case 3: bookingList.sort(Comparator.comparing(Booking::getTanggal)); break;
                case 4: bookingList.sort(Comparator.comparing(Booking::getTanggal).reversed()); break;
                case 5: bookingList.sort(Comparator.comparing(Booking::getTotal)); break;
                case 6: bookingList.sort(Comparator.comparing(Booking::getTotal).reversed()); break;
                default: bookingList.sort(Comparator.comparing(b -> Integer.parseInt(b.getId())));
            }
            loadData();
        }

        void searchData() {
            String txt = txtSearch.getText().trim().toLowerCase();
            if (txt.isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + txt));
            }
        }

        void loadToForm() {
            selectedRow = table.getSelectedRow();
            if (selectedRow == -1) return;
            int modelRow = table.convertRowIndexToModel(selectedRow);

            Booking b = bookingList.get(modelRow);
            txtNama.setText(b.getNama());
            txtTelp.setText(b.getTelepon());

            java.util.Date d = java.util.Date.from(
                    b.getTanggal().atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
            spinTanggal.setValue(d);

            String[] courts = {"Court A", "Court B", "Court C", "Court D"};
            for (int i = 0; i < courts.length; i++) {
                if (b.getLapangan().equals(courts[i])) {
                    comboLap.setSelectedIndex(i);
                    break;
                }
            }
            spinMulai.setValue(b.getJamMulai());
            spinSelesai.setValue(b.getJamSelesai());
        }

        void updateBooking() {
            try {
                if (selectedRow == -1) throw new IllegalArgumentException("Pilih booking!");

                int modelRow = table.convertRowIndexToModel(selectedRow);
                Booking b = bookingList.get(modelRow);

                String nama = txtNama.getText().trim();
                String telp = txtTelp.getText().trim();
                if (nama.isEmpty() || telp.isEmpty())
                    throw new IllegalArgumentException("Data tidak boleh kosong!");
                if (!telp.matches("\\d{10,13}"))
                    throw new IllegalArgumentException("Telepon tidak valid!");

                int mulai = (Integer) spinMulai.getValue();
                int selesai = (Integer) spinSelesai.getValue();
                if (mulai >= selesai)
                    throw new IllegalArgumentException("Jam selesai > jam mulai!");

                java.util.Date d = (java.util.Date) spinTanggal.getValue();
                LocalDate tgl = LocalDate.ofInstant(d.toInstant(), java.time.ZoneId.systemDefault());

                String lap = comboLap.getSelectedItem().toString();
                int harga = (lap.contains("A") || lap.contains("C")) ? 50000 : 40000;
                int durasi = selesai - mulai;
                double total = harga * durasi;
                if (durasi >= 3) total *= 0.9;

                b.setNama(nama);
                b.setTelepon(telp);
                b.setLapangan(lap);
                b.setTanggal(tgl);
                b.setJamMulai(mulai);
                b.setJamSelesai(selesai);
                b.setTotal(total);

                saveDataToFile();
                loadData();
                JOptionPane.showMessageDialog(this, "✓ Booking berhasil diupdate!");
                clearForm();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + ex.getMessage());
            }
        }

        void deleteBooking() {
            try {
                if (selectedRow == -1) throw new IllegalArgumentException("Pilih booking!");

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Hapus booking ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    bookingList.remove(modelRow);
                    saveDataToFile();
                    loadData();
                    clearForm();
                    JOptionPane.showMessageDialog(this, "✓ Booking berhasil dihapus!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ " + ex.getMessage());
            }
        }

        void clearForm() {
            txtNama.setText("");
            txtTelp.setText("");
            comboLap.setSelectedIndex(0);
            spinTanggal.setValue(new java.util.Date());
            spinMulai.setValue(8);
            spinSelesai.setValue(10);
            selectedRow = -1;
        }
    }

    class LaporanPanel extends JPanel { // PANEL LAPORAN
        LaporanPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(245, 247, 250));
            add(createHeader("📈 LAPORAN & STATISTIK"), BorderLayout.NORTH);

            JPanel stats = new JPanel(new GridLayout(2, 2, 20, 20));
            stats.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 50));
            stats.setBackground(Color.WHITE);

            int totalBooking = bookingList.size(); // Statistik
            double totalPendapatan = bookingList.stream()
                    .mapToDouble(Booking::getTotal).sum();

            String lapFav = "Court A";
            if (!bookingList.isEmpty()) {
                java.util.Map<String, Long> lapCount = new java.util.HashMap<>();
                for (Booking b : bookingList) {
                    lapCount.put(b.getLapangan(),
                            lapCount.getOrDefault(b.getLapangan(), 0L) + 1);
                }
                lapFav = java.util.Collections.max(lapCount.entrySet(),
                        java.util.Map.Entry.comparingByValue()).getKey();
            }

            stats.add(createStatCard("Total Booking", String.valueOf(totalBooking),
                    new Color(128, 200, 239)));
            stats.add(createStatCard("Total Pendapatan",
                    String.format("Rp %,.0f", totalPendapatan), new Color(151, 234, 153)));
            stats.add(createStatCard("Lapangan Favorit", lapFav,
                    new Color(239, 151, 147)));
            stats.add(createStatCard("Booking Hari Ini",
                    String.valueOf(bookingList.stream()
                            .filter(b -> b.getTanggal().equals(LocalDate.now())).count()),
                    new Color(248, 145, 234)));

            String[] cols = {"ID", "Nama", "Lapangan", "Tanggal", "Total"}; // Tabel booking terakhir
            DefaultTableModel model = new DefaultTableModel(cols, 0);

            bookingList.stream()
                    .sorted(Comparator.comparing(b -> Integer.parseInt(b.getId()),
                            Comparator.reverseOrder()))
                    .limit(10)
                    .forEach(b -> model.addRow(new Object[]{
                            b.getId(), b.getNama(), b.getLapangan(),
                            b.getTanggalFormat(), b.getTotalFormat()
                    }));

            JTable table = new JTable(model);
            table.setRowHeight(30);
            table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
            table.getTableHeader().setBackground(new Color(76, 175, 80));
            table.getTableHeader().setForeground(Color.BLACK);
            table.setEnabled(false);

            JScrollPane scroll = new JScrollPane(table);
            scroll.setBorder(BorderFactory.createTitledBorder("10 Booking Terakhir"));

            JPanel centerP = new JPanel(new BorderLayout(0, 20));
            centerP.setBackground(new Color(245, 247, 250));
            centerP.add(stats, BorderLayout.NORTH);
            centerP.add(scroll, BorderLayout.CENTER);

            JButton back = createBtn("Kembali", new Color(96, 125, 139));
            back.addActionListener(e -> cardLayout.show(cardPanel, "MENU"));

            JPanel btnP = new JPanel();
            btnP.setBackground(new Color(245, 247, 250));
            btnP.add(back);

            add(centerP, BorderLayout.CENTER);
            add(btnP, BorderLayout.SOUTH);
        }

        JPanel createStatCard(String label, String value, Color col) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(col);
            card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel lbl = new JLabel(label);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
            lbl.setForeground(Color.WHITE);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);

            JLabel val = new JLabel(value);
            val.setFont(new Font("Segoe UI", Font.BOLD, 28));
            val.setForeground(Color.WHITE);
            val.setHorizontalAlignment(SwingConstants.CENTER);

            card.add(lbl, BorderLayout.NORTH);
            card.add(val, BorderLayout.CENTER);
            return card;
        }
    }

    /**
     * Main method - entry point aplikasi
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            SistemBookingBadminton app = new SistemBookingBadminton();
            app.setVisible(true);
        });
    }
}