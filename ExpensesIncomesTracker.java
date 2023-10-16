import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpensesIncomesTracker extends JFrame {
    private final ExpenseIncomeTable tableModel;
    private final JDateChooser dateField;
    private final JTextField descriptionField;
    //private final JDateChooser dateField;
    private final JTextField amountField;
    private final JComboBox<String> typeCombobox;
    private final JLabel balanceLabel;
    private double balance;

    // Constructor to initialize the application and set up the form.
    public ExpensesIncomesTracker() {
        try {
            // Apply the Nimbus Look and Feel for a modern appearance.
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            e.printStackTrace();
        }

        Font customFont = new Font("SAN-SERIF", Font.BOLD, 14);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("Button.font", customFont);


        balance = 0.0;
        tableModel = new ExpenseIncomeTable();
        /*JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);*/

        dateField = new JDateChooser();
        dateField.setFont(customFont);
        descriptionField = new JTextField(20);
        amountField = new JTextField(10);
        typeCombobox = new JComboBox<>(new String[]{"Expense", "Income"});

        JButton addButton = new JButton("Click to Add");
        addButton.setBackground(Color.BLUE);
        addButton.setForeground(Color.WHITE);


        JButton clearExpensesButton = new JButton("Clear Expenses");
        clearExpensesButton.addActionListener(e -> clearExpenses());
        clearExpensesButton.setBackground(Color.RED);
        clearExpensesButton.setForeground(Color.WHITE);


        addButton.addActionListener(e -> addEntry());
        balanceLabel = new JLabel("Total Amount after your expenses : ₹" + balance);

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Date"));
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("Category"));
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Amount"));
        inputPanel.add(amountField);

        inputPanel.add(new JLabel("Type"));
        inputPanel.add(typeCombobox);

        inputPanel.setBackground(Color.CYAN.brighter());
        inputPanel.setForeground(Color.WHITE);

        inputPanel.add(addButton);

        JPanel bottomPanel = new JPanel (new BorderLayout());
        bottomPanel.add(clearExpensesButton,BorderLayout.WEST);
        bottomPanel.add(balanceLabel,BorderLayout.EAST);
        bottomPanel.setBackground(Color.PINK);
        bottomPanel.setForeground(Color.WHITE);

        setLayout(new BorderLayout());
        // Set the background color for the JFrame
        getContentPane().setBackground(Color.MAGENTA.brighter());
        add(inputPanel, BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(new JTable(tableModel));
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setTitle("Expenses And Incomes Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addEntry() {
        Date selectedDate = dateField.getDate();
        String date = formatDate(selectedDate);
        String description = descriptionField.getText();
        String amountStr = amountField.getText();
        String type = (String)typeCombobox.getSelectedItem();
        double amount;

        // Validating description to allow only letters using regular expression
        if (!description.matches("^[a-zA-Z ]+$")) {
            JOptionPane.showMessageDialog(this, "content can only have Letters Use only letters", "Exception", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validating amount to allow only numbers using regular expression
        if (!amountStr.matches("^\\d+(\\.\\d+)?$")) {
            JOptionPane.showMessageDialog(this, "Amount can have only Numbers ", "Exception", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Amount Format", "Exception", JOptionPane.ERROR_MESSAGE);
            return;
        }


        if(amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter the Necessary Fields !", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            amount = Double.parseDouble(amountStr);
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid Amount Format", "Exception", JOptionPane.ERROR_MESSAGE);
            return;
        }

        assert type != null;
        if(type.equals("Expense")) {
            amount *= -1;
        }

        ExpenseIncomeEntry entry = new ExpenseIncomeEntry(date, description, amount, type);
        tableModel.addEntry(entry);

        balance += amount;
        updateBalanceLabel();
        balanceLabel.setText("Total Amount after your expenses : ₹"+balance);

        clearInputFields();
    }

    private void clearInputFields() {
        dateField.setDate(null);
        descriptionField.setText("");
        amountField.setText("");
        typeCombobox.setSelectedIndex(0);
    }
    private String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return date != null ? sdf.format(date) : "";
    }

    private void clearExpenses() {
        // Clear all recorded expenses
        tableModel.clearEntries();
        balance = 0.0;
        updateBalanceLabel();
    }

    private void updateBalanceLabel() {
        balanceLabel.setText("Total Amount after your expenses : ₹" + balance);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ExpensesIncomesTracker::new);
    }
}



