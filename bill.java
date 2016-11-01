import java.time.*;
import org.jdom2.*;
import java.util.Date;
import java.text.*;


public class bill {
    private String name;
    private String notes;
    private String amount;

    private Date due_date;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");



    bill(String n, String a, String t) {
        name = n;
        amount = a;
        try {
            due_date = df.parse(t);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // setters
    // ------------------------------------
    public void set_name(String n) {
        name = n;
    }

    public void set_notes(String n) {
        notes = n;
    }

    public void set_amount(String a) {
        amount = a;
    }

    public void set_due_date(String t) {
        try {
            due_date = df.parse(t);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------
    // getters
    // ------------------------------------
    public String get_name() { return name; }
    public String get_notes() { return notes; }
    public String get_amount() { return amount; }
    public Date get_due_date() { return due_date; }

    // ------------------------------------
    // debug
    // ------------------------------------
    public void debug_print() {
        System.out.println("Bill");
        System.out.println("  name:     [" + name + "]");
        System.out.println("  notes:    [" + notes + "]");
        System.out.println("  amount:   [" + amount + "]");
        System.out.println("  due date: [" + df.format(due_date).toString() + "]");
    }

    // ------------------------------------
    // xml
    // ------------------------------------
    public Element get_xml() {
        Element bill_element = new Element("bill");

        Element name_element = new Element("name");
        name_element.setText(name);
        bill_element.addContent(name_element);

        Element notes_element = new Element("notes");
        notes_element.setText(notes);
        bill_element.addContent(notes_element);

        Element amount_element = new Element("amount");
        amount_element.setText(amount);
        bill_element.addContent(amount_element);

        Element due_date_element = new Element("due_date");
        due_date_element.setText(df.format(due_date).toString());
        bill_element.addContent(due_date_element);

        return bill_element;
    }
}
