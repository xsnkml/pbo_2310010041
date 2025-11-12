package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import config.Koneksi;

public class PanenFrame extends JFrame {
    private JTextField txtIdPanen, txtTanggalPanen, txtJumlahPanen;
    private JComboBox<String> cmbIdPeriode;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public PanenFrame() {
        setTitle("Data Hasil Panen");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form Input
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(168, 213, 186));
        
        formPanel.add(new JLabel("ID Panen:"));
        txtIdPanen = new JTextField();
        formPanel.add(txtIdPanen);
        
        // COMBOBOX UNTUK PERIODE TANAM
        formPanel.add(new JLabel("Periode Tanam:"));
        cmbIdPeriode = new JComboBox<>();
        formPanel.add(cmbIdPeriode);
        
        formPanel.add(new JLabel("Tanggal Panen (YYYY-MM-DD):"));
        txtTanggalPanen = new JTextField();
        formPanel.add(txtTanggalPanen);
        
        formPanel.add(new JLabel("Jumlah Panen (kg):"));
        txtJumlahPanen = new JTextField();
        formPanel.add(txtJumlahPanen);
        
        // Tombol
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
        
        // Panel Kiri
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Tabel
        String[] columnNames = {"ID Panen", "ID Periode", "Petani", "Tanaman", 
                                "Tanggal Panen", "Jumlah Panen (kg)"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Hasil Panen"));
        
        add(leftPanel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        
        // Event Listeners
        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        
        // Klik tabel untuk edit
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    txtIdPanen.setText(tableModel.getValueAt(row, 0).toString());
                    cmbIdPeriode.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                    txtTanggalPanen.setText(tableModel.getValueAt(row, 4).toString());
                    txtJumlahPanen.setText(tableModel.getValueAt(row, 5).toString());
                }
            }
        });
        
        loadComboBoxPeriode();
        loadData();
    }
    
    // LOAD PERIODE TANAM YANG BELUM PANEN
    private void loadComboBoxPeriode() {
        cmbIdPeriode.removeAllItems();
        cmbIdPeriode.addItem("-- Pilih Periode Tanam --");
        try {
            // HANYA AMBIL PERIODE YANG BELUM PANEN
            String sql = "SELECT pt.id_periode, p.nama_petani, t.nama_tanaman, pt.tgl_mulai_tanam " +
                        "FROM periode_tanam pt " +
                        "JOIN petani p ON pt.id_petani = p.id_petani " +
                        "JOIN tanaman t ON pt.id_tanaman = t.id_tanaman " +
                        "WHERE pt.status = 'Belum Panen' " +
                        "ORDER BY pt.tgl_mulai_tanam DESC";
            
            Connection conn = Koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                String display = rs.getString("id_periode") + " - " + 
                               rs.getString("nama_petani") + " - " + 
                               rs.getString("nama_tanaman") + " (" + 
                               rs.getString("tgl_mulai_tanam") + ")";
                cmbIdPeriode.addItem(display);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load periode tanam: " + e.getMessage());
        }
    }
    
    // LOAD DATA PANEN
    private void loadData() {
        tableModel.setRowCount(0);
        try {
            String sql = "SELECT pa.id_panen, pa.id_periode, p.nama_petani, " +
                        "t.nama_tanaman, pa.tgl_panen, pa.jumlah_panen " +
                        "FROM panen pa " +
                        "JOIN periode_tanam pt ON pa.id_periode = pt.id_periode " +
                        "JOIN petani p ON pt.id_petani = p.id_petani " +
                        "JOIN tanaman t ON pt.id_tanaman = t.id_tanaman " +
                        "ORDER BY pa.tgl_panen DESC";
            
            Connection conn = Koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("id_panen"),
                    rs.getString("id_periode"),
                    rs.getString("nama_petani"),
                    rs.getString("nama_tanaman"),
                    rs.getString("tgl_panen"),
                    rs.getDouble("jumlah_panen")
                };
                tableModel.addRow(row);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
        }
    }
    
    private String getIdFromComboBox(JComboBox<String> combo) {
        String selected = (String) combo.getSelectedItem();
        if (selected == null || selected.startsWith("--")) {
            return null;
        }
        return selected.split(" - ")[0];
    }
    
    // TAMBAH DATA PANEN + UPDATE STATUS PERIODE
    private void tambahData() {
        if (validateInput()) {
            try {
                Connection conn = Koneksi.getKoneksi();
                conn.setAutoCommit(false); // MULAI TRANSAKSI
                
                // 1. INSERT DATA PANEN
                String sqlInsert = "INSERT INTO panen (id_panen, id_periode, tgl_panen, jumlah_panen) " +
                                  "VALUES (?, ?, ?, ?)";
                PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
                pstmtInsert.setString(1, txtIdPanen.getText().trim());
                pstmtInsert.setString(2, getIdFromComboBox(cmbIdPeriode));
                pstmtInsert.setString(3, txtTanggalPanen.getText().trim());
                pstmtInsert.setDouble(4, Double.parseDouble(txtJumlahPanen.getText().trim()));
                pstmtInsert.executeUpdate();
                
                // 2. UPDATE STATUS PERIODE TANAM JADI "Sudah Panen"
                String sqlUpdate = "UPDATE periode_tanam SET status = 'Sudah Panen' WHERE id_periode = ?";
                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setString(1, getIdFromComboBox(cmbIdPeriode));
                pstmtUpdate.executeUpdate();
                
                conn.commit(); // COMMIT TRANSAKSI
                
                JOptionPane.showMessageDialog(this, 
                    "Data panen berhasil ditambahkan!\nStatus periode tanam diupdate menjadi 'Sudah Panen'");
                
                pstmtInsert.close();
                pstmtUpdate.close();
                
                loadComboBoxPeriode(); // REFRESH COMBOBOX
                loadData();
                clearForm();
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error tambah data: " + e.getMessage());
                e.printStackTrace();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Jumlah panen harus berupa angka!");
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
                String sql = "UPDATE panen SET id_periode=?, tgl_panen=?, jumlah_panen=? " +
                           "WHERE id_panen=?";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, getIdFromComboBox(cmbIdPeriode));
                pstmt.setString(2, txtTanggalPanen.getText().trim());
                pstmt.setDouble(3, Double.parseDouble(txtJumlahPanen.getText().trim()));
                pstmt.setString(4, txtIdPanen.getText().trim());
                
                pstmt.executeUpdate();
                pstmt.close();
                
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                loadData();
                clearForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error update data: " + e.getMessage());
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
            "Apakah Anda yakin ingin menghapus data ini?\nStatus periode tanam akan kembali ke 'Belum Panen'", 
            "Konfirmasi Hapus", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = Koneksi.getKoneksi();
                conn.setAutoCommit(false);
                
                // Ambil id_periode sebelum hapus
                String idPeriode = tableModel.getValueAt(selectedRow, 1).toString();
                
                // 1. HAPUS DATA PANEN
                String sqlDelete = "DELETE FROM panen WHERE id_panen=?";
                PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete);
                pstmtDelete.setString(1, txtIdPanen.getText().trim());
                pstmtDelete.executeUpdate();
                
                // 2. KEMBALIKAN STATUS PERIODE KE "Belum Panen"
                String sqlUpdate = "UPDATE periode_tanam SET status = 'Belum Panen' WHERE id_periode = ?";
                PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
                pstmtUpdate.setString(1, idPeriode);
                pstmtUpdate.executeUpdate();
                
                conn.commit();
                
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                
                pstmtDelete.close();
                pstmtUpdate.close();
                
                loadComboBoxPeriode();
                loadData();
                clearForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error hapus data: " + e.getMessage());
            }
        }
    }
    
    private boolean validateInput() {
        if (txtIdPanen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID Panen harus diisi!");
            return false;
        }
        
        if (getIdFromComboBox(cmbIdPeriode) == null) {
            JOptionPane.showMessageDialog(this, "Pilih Periode Tanam!");
            return false;
        }
        
        if (txtTanggalPanen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tanggal Panen harus diisi!");
            return false;
        }
        
        if (!txtTanggalPanen.getText().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Format tanggal harus YYYY-MM-DD!");
            return false;
        }
        
        if (txtJumlahPanen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah Panen harus diisi!");
            return false;
        }
        
        try {
            Double.parseDouble(txtJumlahPanen.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah panen harus berupa angka!");
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        txtIdPanen.setText("");
        cmbIdPeriode.setSelectedIndex(0);
        txtTanggalPanen.setText("");
        txtJumlahPanen.setText("");
        table.clearSelection();
    }
}