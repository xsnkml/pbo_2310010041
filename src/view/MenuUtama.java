package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuUtama extends JFrame {
    public MenuUtama() {
        setTitle("Menu Utama");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(168, 213, 186));

        JPanel panel = new JPanel();
        panel.setBackground(new Color(168, 213, 186));
        panel.setLayout(new GridLayout(1, 4, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton btnPetani = new JButton("👨‍🌾\nPetani");
        JButton btnTanaman = new JButton("🌿\nTanaman");
        JButton btnPeriode = new JButton("🌱\nPeriode Tanam");
        JButton btnPanen = new JButton("🌾\nPanen");

        JButton[] buttons = { btnPetani, btnTanaman, btnPeriode, btnPanen };
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(new Color(144, 197, 166));
            btn.setFocusPainted(false);
            panel.add(btn);
        }

        btnPetani.addActionListener(e -> new PetaniFrame().setVisible(true));
        btnTanaman.addActionListener(e -> new TanamanFrame().setVisible(true));
        btnPeriode.addActionListener(e -> new PeriodeTanamFrame().setVisible(true));
        btnPanen.addActionListener(e -> new PanenFrame().setVisible(true));

        add(panel);
    }
}
