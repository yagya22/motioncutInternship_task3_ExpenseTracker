import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
public class ExpenseIncomeTable extends AbstractTableModel  {
    // List to store the entries (rows) in the table
    private final List<ExpenseIncomeEntry> entries;
    // Column names for the table
    private final String[] columnNames = {"Date","Category","Amount","Type"};


    //* Constructor to initialize the table model.

    public ExpenseIncomeTable(){
        entries = new ArrayList<>();
    }


    public void addEntry(ExpenseIncomeEntry entry){
        entries.add(entry);
        // Notify the table that a new row has been inserted
        fireTableRowsInserted(entries.size()-1, entries.size()-1);
    }

    public void clearEntries() {
        entries.clear();
        fireTableDataChanged();
    }


    @Override
    public int getRowCount() { return entries.size(); }

    @Override
    public int getColumnCount() { return columnNames.length;}

    @Override
    public String getColumnName(int column){ return columnNames[column]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        ExpenseIncomeEntry entry = entries.get(rowIndex);

        // Return the value for the cell based on the column index
        return switch (columnIndex) {
            case 0 -> entry.getDate();
            case 1 -> entry.getCategory();
            case 2 -> entry.getAmount();
            case 3 -> entry.getType();
            default -> null;
        };


    }


}

