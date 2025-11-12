package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import config.Koneksi;
import java.util.Vector;

public class PeriodeTanamFrame extends JFrame {
    private JTextField txtIdPeriode, txtTanggalMulai;
    private JTextField txtJumlahTanam, txtStatus, txtLatitude, txtLongitude;
    private JComboBox<String> cmbIdTanaman, cmbIdPetani;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public PeriodeTanamFrame() {
        setTitle("Data Periode Tanam");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form Input
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(168, 213, 186));
        
        formPanel.add(new JLabel("ID Periode:"));
        txtIdPeriode = new JTextField();
        formPanel.add(txtIdPeriode);
        
        // COMBOBOX UNTUK ID TANAMAN
        formPanel.add(new JLabel("ID Tanaman:"));
        cmbIdTanaman = new JComboBox<>();
        formPanel.add(cmbIdTanaman);
        
        // COMBOBOX UNTUK ID PETANI
        formPanel.add(new JLabel("ID Petani:"));
        cmbIdPetani = new JComboBox<>();
        formPanel.add(cmbIdPetani);
        
        formPanel.add(new JLabel("Tanggal Mulai (YYYY-MM-DD):"));
        txtTanggalMulai = new JTextField();
        formPanel.add(txtTanggalMulai);
        
        formPanel.add(new JLabel("Jumlah Tanam:"));
        txtJumlahTanam = new JTextField();
        formPanel.add(txtJumlahTanam);
        
        formPanel.add(new JLabel("Status:"));
        txtStatus = new JTextField("Belum Panen");
        formPanel.add(txtStatus);
        
        formPanel.add(new JLabel("Latitude:"));
        txtLatitude = new JTextField();
        formPanel.add(txtLatitude);
        
        formPanel.add(new JLabel("Longitude:"));
        txtLongitude = new JTextField();
        formPanel.add(txtLongitude);
        
        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(168, 213, 186));
        
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        
        btnTambah.setPreferredSize(new Dimension(90, 30));
        btnEdit.setPreferredSize(new Dimension(90, 30));
        btnHapus.setPreferredSize(new Dimension(90, 30));
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        
        // Panel Kiri (Form + Tombol)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Tabel
        String[] columnNames = {"ID Periode", "ID Tanaman", "ID Petani", "Tanggal Mulai", 
                                "Jumlah Tanam", "Status", "Latitude", "Longitude"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Periode Tanam"));
        
        // Menambahkan komponen ke frame
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        
        // Event Listeners
        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        
        // Event untuk mengisi form saat baris tabel diklik
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtIdPeriode.setText(tableModel.getValueAt(row, 0).toString());
                    cmbIdTanaman.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                    cmbIdPetani.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                    txtTanggalMulai.setText(tableModel.getValueAt(row, 3).toString());
                    txtJumlahTanam.setText(tableModel.getValueAt(row, 4).toString());
                    txtStatus.setText(tableModel.getValueAt(row, 5).toString());
                    
                    Object lat = tableModel.getValueAt(row, 6);
                    Object longi = tableModel.getValueAt(row, 7);
                    txtLatitude.setText(lat != null ? lat.toString() : "");
                    txtLongitude.setText(longi != null ? longi.toString() : "");
                }
            }
        });
        
        // Load data dari database saat frame dibuka
        loadComboBoxData();
        loadData();
    }
    
    // METHOD BARU: LOAD DATA KE COMBOBOX
    private void loadComboBoxData() {
        // Load ID Tanaman
        cmbIdTanaman.removeAllItems();
        cmbIdTanaman.addItem("-- Pilih Tanaman --");
        try {
            String sql = "SELECT id_tanaman, nama_tanaman FROM tanaman ORDER BY id_tanaman";
            Connection conn = Koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String display = rs.getString("id_tanaman") + " - " + rs.getString("nama_tanaman");
                cmbIdTanaman.addItem(display);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load data tanaman: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Load ID Petani
        cmbIdPetani.removeAllItems();
        cmbIdPetani.addItem("-- Pilih Petani --");
        try {
            String sql = "SELECT id_petani, nama_petani FROM petani ORDER BY id_petani";
            Connection conn = Koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String display = rs.getString("id_petani") + " - " + rs.getString("nama_petani");
                cmbIdPetani.addItem(display);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load data petani: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        try {
            String sql = "SELECT * FROM periode_tanam ORDER BY id_periode";
            Connection conn = Koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("id_periode"),
                    rs.getString("id_tanaman"),
                    rs.getString("id_petani"),
                    rs.getString("tgl_mulai_tanam"),
                    rs.getDouble("jumlah_tanam"),
                    rs.getString("status"),
                    rs.getString("lat"),
                    rs.getString("longi")
                };
                tableModel.addRow(row);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // METHOD UNTUK EXTRACT ID DARI COMBOBOX
    private String getIdFromComboBox(JComboBox<String> combo) {
        String selected = (String) combo.getSelectedItem();
        if (selected == null || selected.startsWith("--")) {
            return null;
        }
        // Extract ID dari format "ID - Nama"
        return selected.split(" - ")[0];
    }
    
    private void tambahData() {
        if (validateInput()) {
            try {
                String sql = "INSERT INTO periode_tanam (id_periode, id_tanaman, id_petani, " +
                           "tgl_mulai_tanam, jumlah_tanam, status, lat, longi) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, txtIdPeriode.getText().trim());
                pstmt.setString(2, getIdFromComboBox(cmbIdTanaman));
                pstmt.setString(3, getIdFromComboBox(cmbIdPetani));
                pstmt.setString(4, txtTanggalMulai.getText().trim());
                pstmt.setDouble(5, Double.parseDouble(txtJumlahTanam.getText().trim()));
                pstmt.setString(6, txtStatus.getText().trim());
                
                // Handle null untuk lat dan longi
                String lat = txtLatitude.getText().trim();
                String longi = txtLongitude.getText().trim();
                pstmt.setString(7, lat.isEmpty() ? null : lat);
                pstmt.setString(8, longi.isEmpty() ? null : longi);
                
                pstmt.executeUpdate();
                pstmt.close();
                
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                loadData();
                clearForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error tambah data: " + e.getMessage() + 
                    "\n\nPastikan ID Tanaman dan ID Petani sudah ada di database!", 
                    "Error Database", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Jumlah tanam harus berupa angka!");
            }
        }
    }
    
    private void editData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diedit!");
            return;
        }
        
        if (validateInput()) {
            try {
                String sql = "UPDATE periode_tanam SET id_tanaman=?, id_petani=?, " +
                           "tgl_mulai_tanam=?, jumlah_tanam=?, status=?, lat=?, longi=? " +
                           "WHERE id_periode=?";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, getIdFromComboBox(cmbIdTanaman));
                pstmt.setString(2, getIdFromComboBox(cmbIdPetani));
                pstmt.setString(3, txtTanggalMulai.getText().trim());
                pstmt.setDouble(4, Double.parseDouble(txtJumlahTanam.getText().trim()));
                pstmt.setString(5, txtStatus.getText().trim());
                
                String lat = txtLatitude.getText().trim();
                String longi = txtLongitude.getText().trim();
                pstmt.setString(6, lat.isEmpty() ? null : lat);
                pstmt.setString(7, longi.isEmpty() ? null : longi);
                pstmt.setString(8, txtIdPeriode.getText().trim());
                
                pstmt.executeUpdate();
                pstmt.close();
                
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                loadData();
                clearForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error update data: " + e.getMessage());
                e.printStackTrace();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Jumlah tanam harus berupa angka!");
            }
        }
    }
    
    private void hapusData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus data ini?", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM periode_tanam WHERE id_periode=?";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, txtIdPeriode.getText().trim());
                pstmt.executeUpdate();
                pstmt.close();
                
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                loadData();
                clearForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error hapus data: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private boolean validateInput() {
        if (txtIdPeriode.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Periode harus diisi!");
            return false;
        }
        
        if (getIdFromComboBox(cmbIdTanaman) == null) {
            JOptionPane.showMessageDialog(this, "Pilih ID Tanaman!");
            return false;
        }
        
        if (getIdFromComboBox(cmbIdPetani) == null) {
            JOptionPane.showMessageDialog(this, "Pilih ID Petani!");
            return false;
        }
        
        if (txtTanggalMulai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal Mulai harus diisi!");
            return false;
        }
        
        if (!txtTanggalMulai.getText().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Format tanggal harus YYYY-MM-DD!\nContoh: 2025-01-15");
            return false;
        }
        
        if (txtJumlahTanam.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah Tanam harus diisi!");
            return false;
        }
        
        try {
            Double.parseDouble(txtJumlahTanam.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah tanam harus berupa angka!");
            return false;
        }
        
        if (txtStatus.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Status harus diisi!");
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        txtIdPeriode.setText("");
        cmbIdTanaman.setSelectedIndex(0);
        cmbIdPetani.setSelectedIndex(0);
        txtTanggalMulai.setText("");
        txtJumlahTanam.setText("");
        txtStatus.setText("Belum Panen");
        txtLatitude.setText("");
        txtLongitude.setText("");
        table.clearSelection();
    }
}