package view;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import config.Koneksi;

public class PetaniFrame extends JFrame {
    private JTextField txtIdPetani, txtNamaPetani, txtJenisKelamin;
    private JTextField txtAlamat, txtNoHp, txtJumlahLahan;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public PetaniFrame() {
        setTitle("Data Petani");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form Input
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(new Color(168, 213, 186));
        
        formPanel.add(new JLabel("ID Petani:"));
        txtIdPetani = new JTextField();
        formPanel.add(txtIdPetani);
        
        formPanel.add(new JLabel("Nama Petani:"));
        txtNamaPetani = new JTextField();
        formPanel.add(txtNamaPetani);
        
        formPanel.add(new JLabel("Jenis Kelamin:"));
        txtJenisKelamin = new JTextField();
        formPanel.add(txtJenisKelamin);
        
        formPanel.add(new JLabel("Alamat:"));
        txtAlamat = new JTextField();
        formPanel.add(txtAlamat);
        
        formPanel.add(new JLabel("No HP:"));
        txtNoHp = new JTextField();
        formPanel.add(txtNoHp);
        
        formPanel.add(new JLabel("Jumlah Lahan:"));
        txtJumlahLahan = new JTextField();
        formPanel.add(txtJumlahLahan);
        
        // Panel Tombol
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(168, 213, 186));
        
        JButton btnTambah = new JButton("Tambah");
        JButton btnEdit = new JButton("Edit");
        JButton btnHapus = new JButton("Hapus");
        
        btnTambah.setPreferredSize(new Dimension(100, 30));
        btnEdit.setPreferredSize(new Dimension(100, 30));
        btnHapus.setPreferredSize(new Dimension(100, 30));
        
        buttonPanel.add(btnTambah);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnHapus);
        
        // Panel Kiri (Form + Tombol)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.CENTER);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Tabel
        String[] columnNames = {"ID Petani", "Nama Petani", "Jenis Kelamin", 
                                "Alamat", "No HP", "Jumlah Lahan"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Data Petani"));
        
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
                    txtIdPetani.setText(tableModel.getValueAt(row, 0).toString());
                    txtNamaPetani.setText(tableModel.getValueAt(row, 1).toString());
                    txtJenisKelamin.setText(tableModel.getValueAt(row, 2).toString());
                    txtAlamat.setText(tableModel.getValueAt(row, 3).toString());
                    txtNoHp.setText(tableModel.getValueAt(row, 4).toString());
                    txtJumlahLahan.setText(tableModel.getValueAt(row, 5).toString());
                }
            }
        });
        
        // Load data dari database saat frame dibuka
        loadData();
    }
    
    private void loadData() {
        tableModel.setRowCount(0); // Kosongkan tabel
        try {
            String sql = "SELECT * FROM petani";
            Connection conn = Koneksi.getKoneksi();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("id_petani"),
                    rs.getString("nama_petani"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("alamat"),
                    rs.getString("no_hp"),
                    rs.getDouble("jumlah_lahan")
                };
                tableModel.addRow(row);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage());
        }
    }
    
    private void tambahData() {
        if (validateInput()) {
            try {
                String sql = "INSERT INTO petani (id_petani, nama_petani, jenis_kelamin, alamat, no_hp, jumlah_lahan) VALUES (?, ?, ?, ?, ?, ?)";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, txtIdPetani.getText());
                pstmt.setString(2, txtNamaPetani.getText());
                pstmt.setString(3, txtJenisKelamin.getText());
                pstmt.setString(4, txtAlamat.getText());
                pstmt.setString(5, txtNoHp.getText());
                pstmt.setDouble(6, Double.parseDouble(txtJumlahLahan.getText()));
                
                pstmt.executeUpdate();
                pstmt.close();
                
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                loadData();
                clearForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error tambah data: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Jumlah lahan harus berupa angka!");
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
                String sql = "UPDATE petani SET nama_petani=?, jenis_kelamin=?, alamat=?, no_hp=?, jumlah_lahan=? WHERE id_petani=?";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, txtNamaPetani.getText());
                pstmt.setString(2, txtJenisKelamin.getText());
                pstmt.setString(3, txtAlamat.getText());
                pstmt.setString(4, txtNoHp.getText());
                pstmt.setDouble(5, Double.parseDouble(txtJumlahLahan.getText()));
                pstmt.setString(6, txtIdPetani.getText());
                
                pstmt.executeUpdate();
                pstmt.close();
                
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                loadData();
                clearForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error update data: " + e.getMessage());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Jumlah lahan harus berupa angka!");
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
                String sql = "DELETE FROM petani WHERE id_petani=?";
                Connection conn = Koneksi.getKoneksi();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                
                pstmt.setString(1, txtIdPetani.getText());
                pstmt.executeUpdate();
                pstmt.close();
                
                JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                loadData();
                clearForm();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error hapus data: " + e.getMessage());
            }
        }
    }
    
    private boolean validateInput() {
        if (txtIdPetani.getText().trim().isEmpty() ||
            txtNamaPetani.getText().trim().isEmpty() ||
            txtJenisKelamin.getText().trim().isEmpty() ||
            txtAlamat.getText().trim().isEmpty() ||
            txtNoHp.getText().trim().isEmpty() ||
            txtJumlahLahan.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        txtIdPetani.setText("");
        txtNamaPetani.setText(""); 
        txtJenisKelamin.setText("");
        txtAlamat.setText("");
        txtNoHp.setText("");
        txtJumlahLahan.setText("");
        table.clearSelection();
    }
}