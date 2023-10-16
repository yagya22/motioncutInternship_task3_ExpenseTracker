

public class ExpenseIncomeEntry {
    private final String date;
    private String Category;
    private final double amount;
    private final String type; // The type of the entry (expense or income).


    public ExpenseIncomeEntry(String date, String Category, double amount, String type)
    {
        this.date = date;
        this.amount = amount;
        this.Category= Category;
        this.type = type;
    }

    public String getDate(){ return date;}
    public String getCategory(){ return Category;}
    public double getAmount(){ return amount;}
    public String getType(){ return type;}
}

