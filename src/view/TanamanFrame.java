package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TanamanFrame extends JFrame {
    private JTextField txtId, txtNama, txtJenis;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnTambah, btnEdit, btnHapus;
    
    // Konfigurasi Database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pbo_2310010041";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";
    private static final String TABLE_NAME = "tanaman";
    private static final String COL_ID = "id_tanaman";
    private static final String COL_NAMA = "nama_tanaman";
    private static final String COL_JENIS = "jenis_tanaman";
    
    public TanamanFrame() {
        setTitle("Data Tanaman");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        // Panel Form
        JPanel panelForm = createFormPanel();
        
        // Panel Tabel
        JPanel panelTable = createTablePanel();
        
        add(panelForm, BorderLayout.NORTH);
        add(panelTable, BorderLayout.CENTER);
        
        // Load data awal
        loadData();
        
        // Action Listeners
        setupListeners();
    }
    
    private JPanel createFormPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(168, 213, 186));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(168, 213, 186));
        
        txtId = new JTextField();
        txtNama = new JTextField();
        txtJenis = new JTextField();
        
        panel.add(new JLabel("ID Tanaman:"));
        panel.add(txtId);
        panel.add(new JLabel("Nama Tanaman:"));
        panel.add(txtNama);
        panel.add(new JLabel("Jenis Tanaman:"));
        panel.add(txtJenis);
        
        // Panel Buttons
        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBtn.setBackground(new Color(168, 213, 186));
        
        btnTambah = new JButton("Tambah");
        btnEdit = new JButton("Edit");
        btnHapus = new JButton("Hapus");
        
        // Styling buttons
        btnTambah.setBackground(new Color(76, 175, 80));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);
        
        btnEdit.setBackground(new Color(33, 150, 243));
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        
        btnHapus.setBackground(new Color(244, 67, 54));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setFocusPainted(false);
        
        panelBtn.add(btnTambah);
        panelBtn.add(btnEdit);
        panelBtn.add(btnHapus);
        
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(panelBtn, BorderLayout.SOUTH);
        
        return mainPanel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        String[] columns = {"ID", "Nama Tanaman", "Jenis Tanaman"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setBackground(new Color(100, 180, 150));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 150), 2));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void setupListeners() {
        // Tombol Tambah
        btnTambah.addActionListener(e -> tambahData());
        
        // Tombol Edit
        btnEdit.addActionListener(e -> editData());
        
        // Tombol Hapus
        btnHapus.addActionListener(e -> hapusData());
        
        // Table Selection - klik baris untuk isi form
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                int row = table.getSelectedRow();
                txtId.setText(tableModel.getValueAt(row, 0).toString());
                txtNama.setText(tableModel.getValueAt(row, 1).toString());
                txtJenis.setText(tableModel.getValueAt(row, 2).toString());
            }
        });
    }
    
    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL tidak ditemukan: " + e.getMessage());
        }
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_ID;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Object[] row = {
                    rs.getObject(COL_ID),
                    rs.getString(COL_NAMA),
                    rs.getString(COL_JENIS)
                };
                tableModel.addRow(row);
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error loading data:\n" + e.getMessage() +
                "\n\nPastikan:\n1. Database sudah berjalan\n2. Nama database, tabel, dan kolom sudah benar\n3. Username dan password benar",
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void tambahData() {
        if (!validateInput()) return;
        
        String sql = "INSERT INTO " + TABLE_NAME + " (" + COL_ID + ", " + COL_NAMA + ", " + COL_JENIS + ") VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, txtId.getText().trim());
            pstmt.setString(2, txtNama.getText().trim());
            pstmt.setString(3, txtJenis.getText().trim());
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Data berhasil ditambahkan!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                clearForm();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error menambah data:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void editData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Pilih data dari tabel yang akan diedit!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateInput()) return;
        
        String sql = "UPDATE " + TABLE_NAME + " SET " + COL_NAMA + " = ?, " + COL_JENIS + " = ? WHERE " + COL_ID + " = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, txtNama.getText().trim());
            pstmt.setString(2, txtJenis.getText().trim());
            pstmt.setString(3, txtId.getText());
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, 
                    "Data berhasil diupdate!",
                    "Sukses",
                    JOptionPane.INFORMATION_MESSAGE);
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Data tidak ditemukan!",
                    "Info",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error mengupdate data:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void hapusData() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Pilih data dari tabel yang akan dihapus!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus data:\n" +
            "ID: " + txtId.getText() + "\n" +
            "Nama: " + txtNama.getText() + "\n" +
            "Jenis: " + txtJenis.getText() + "?",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";
            
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, txtId.getText());
                
                int result = pstmt.executeUpdate();
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "Data berhasil dihapus!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadData();
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Data tidak ditemukan!",
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                    "Error menghapus data:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    private boolean validateInput() {
        if (txtId.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "ID tanaman harus diisi!",
                "Validasi",
                JOptionPane.WARNING_MESSAGE);
            txtId.requestFocus();
            return false;
        }
        
        if (txtNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nama tanaman harus diisi!",
                "Validasi",
                JOptionPane.WARNING_MESSAGE);
            txtNama.requestFocus();
            return false;
        }
        
        if (txtJenis.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Jenis tanaman harus diisi!",
                "Validasi",
                JOptionPane.WARNING_MESSAGE);
            txtJenis.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        txtId.setText("");
        txtNama.setText("");
        txtJenis.setText("");
        table.clearSelection();
        txtNama.requestFocus();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TanamanFrame frame = new TanamanFrame();
            frame.setVisible(true);
        });
    }
}