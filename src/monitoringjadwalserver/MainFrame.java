package monitoringjadwalserver;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;


public class MainFrame extends JFrame {

    DefaultTableModel model;
    List<Jadwal> data;
    JTable table;

    public MainFrame() {
        setTitle("Manajemen Jadwal Monitoring Server");
        setSize(900, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // TABLE
        model = new DefaultTableModel(
            new String[]{"ID", "Server", "IP", "Monitoring", "Tanggal", "Catatan"}, 0
        );
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // BUTTON
        JButton btnTambah = new JButton("Tambah Jadwal");
        JButton btnHapus = new JButton("Hapus Jadwal");

        JPanel panelBtn = new JPanel();
        panelBtn.add(btnTambah);
        panelBtn.add(btnHapus);
        add(panelBtn, BorderLayout.SOUTH);

        // LOAD DATA
        data = CSVHelper.load();
        for (Jadwal j : data) {
            model.addRow(j.toRow());
        }

        // EVENT TAMBAH
        btnTambah.addActionListener(e -> tambahData());

        // EVENT HAPUS
        btnHapus.addActionListener(e -> hapusData());
    }

    private void tambahData() {
        JTextField tfServer = new JTextField();
        JTextField tfIP = new JTextField();
        JTextField tfTanggal = new JTextField();
        JTextField tfCatatan = new JTextField();

        JComboBox<String> cbMonitoring = new JComboBox<>(
            new String[]{"CPU", "RAM", "Disk", "Network"}
        );

        Object[] form = {
            "Server Name:", tfServer,
            "IP Address:", tfIP,
            "Monitoring Type:", cbMonitoring,
            "Tanggal (YYYY-MM-DD):", tfTanggal,
            "Catatan:", tfCatatan
        };

        int result = JOptionPane.showConfirmDialog(
            this, form, "Tambah Jadwal Monitoring",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            // VALIDASI
            if (tfServer.getText().isEmpty() || tfIP.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Server Name dan IP wajib diisi!");
                return;
            }

            String id = String.valueOf(data.size() + 1);

            Jadwal j = new Jadwal(
                id,
                tfServer.getText(),
                tfIP.getText(),
                cbMonitoring.getSelectedItem().toString(),
                tfTanggal.getText(),
                tfCatatan.getText()
            );

            data.add(j);
            model.addRow(j.toRow());
            CSVHelper.save(data);
        }
    }

    private void hapusData() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            return;
        }

        data.remove(row);
        model.removeRow(row);
        CSVHelper.save(data);
    }
}