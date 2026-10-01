package library;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Main extends JFrame {
    private final LibraryService svc = new LibraryService();
    private final DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Title", "Author", "In stock", "Issued"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable table = new JTable(model);
    private final JComboBox<Member> memberBox = new JComboBox<>(svc.getMembers().toArray(new Member[0]));
    private final JTextField search = new JTextField(16);
    private final JLabel status = new JLabel("Select a book and a member.");

    public Main() {
        super("Library Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(820, 480);
        setLocationRelativeTo(null);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Search:")); top.add(search);
        top.add(new JLabel("Member:")); top.add(memberBox);
        search.addActionListener(e -> refresh());

        JButton add = new JButton("Add book"), issue = new JButton("Issue"),
                ret = new JButton("Return"), fines = new JButton("Total fines");
        add.addActionListener(e -> addBook());
        issue.addActionListener(e -> issueSelected());
        ret.addActionListener(e -> returnSelected());
        fines.addActionListener(e -> status.setText("Fines collected: Rs " + svc.totalFines()));

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(add); bottom.add(issue); bottom.add(ret); bottom.add(fines); bottom.add(status);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
        refresh();
        setVisible(true);
    }

    private void refresh() {
        model.setRowCount(0);
        for (Book b : svc.search(search.getText()))
            model.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getCopies(), svc.issuedCount(b.getId())});
    }

    private int selectedBookId() {
        int row = table.getSelectedRow();
        if (row < 0) throw new IllegalStateException("Select a book first");
        return (int) model.getValueAt(row, 0);
    }

    private void addBook() {
        JTextField t = new JTextField(), a = new JTextField(), c = new JTextField("1");
        Object[] f = {"Title", t, "Author", a, "Copies", c};
        if (JOptionPane.showConfirmDialog(this, f, "Add book", JOptionPane.OK_CANCEL_OPTION) != JOptionPane.OK_OPTION) return;
        try {
            svc.addBook(t.getText().trim(), a.getText().trim(), Integer.parseInt(c.getText().trim()));
            refresh();
        } catch (NumberFormatException ex) { error("Copies must be a number"); }
    }

    private void issueSelected() {
        try {
            Member m = (Member) memberBox.getSelectedItem();
            Transaction t = svc.issueBook(m.getId(), selectedBookId());
            status.setText("Issued to " + m + ". Due " + t.getDueDate());
            refresh();
        } catch (RuntimeException ex) { error(ex.getMessage()); }
    }

    private void returnSelected() {
        try {
            String in = JOptionPane.showInputDialog(this, "Days kept (" + FineCalculator.LOAN_DAYS + " = on time):", "14");
            if (in == null) return;
            Member m = (Member) memberBox.getSelectedItem();
            int fine = svc.returnBook(m.getId(), selectedBookId(), Integer.parseInt(in.trim()));
            status.setText(fine > 0 ? "Returned late. Fine: Rs " + fine : "Returned on time. No fine.");
            refresh();
        } catch (NumberFormatException ex) { error("Enter a whole number of days");
        } catch (RuntimeException ex) { error(ex.getMessage()); }
    }

    private void error(String msg) { JOptionPane.showMessageDialog(this, msg, "Cannot continue", JOptionPane.WARNING_MESSAGE); }

    public static void main(String[] args) { SwingUtilities.invokeLater(Main::new); }
}
