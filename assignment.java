import java.time.*;
import org.jdom2.*;
import java.text.*;
import java.util.Date;


public class assignment {
    private String name;
    private String description;
    private String course;
    private String grade_percent;
    private String status;
    private String actual_grade;

    private Date due_date;
    private DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    // -----------
    // Constructor
    // -----------
    assignment(String n, String d, String c, String grade_p, String status, String actual_grade) {
        name              = n;
        description       = d;
        course            = c;
        grade_percent     = grade_p;
        this.status       = status;
        this.actual_grade = actual_grade;
    }


    // -----------
    // Set methods
    // -----------

    public void set_name(String name) {
        this.name = name;
    }

    public void set_description(String description) {
        this.description = description;
    }

    public void set_course(String course) {
        this.course = course;
    }

    public void set_grade_percent(String grade_percent) {
        this.grade_percent = grade_percent;
    }

    public void set_status(String status) {
        this.status = status;
    }

    public void set_actual_grade (String actual_grade) {
        this.actual_grade = actual_grade;
    }

    public void set_due_date(String t) {
        try {
            due_date = df.parse(t);
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
    }

    // -----------
    // Get methods
    // -----------

    public String get_name() {
        return name;
    }

    public String get_description() {
        return description;
    }

    public String get_course() {
        return course;
    }

    public String get_grade_percent() {
        return grade_percent;
    }

    public String get_status() {
        return status;
    }

    public String get_actual_grade() {
        return actual_grade;
    }

    public Date get_due_date() {
        return due_date;
    }

    public String get_due_date_string() {
        return df.format(due_date).toString();
    }

    // -----------------
    // Debugging methods
    // -----------------
    public void debug_print() {
        System.out.println("Assignment name: " + name);
        System.out.println("  Description:             [" + description + "]");
        System.out.println("  Course:                  [" + course + "]");
        System.out.println("  Percent of final grade:  [" + grade_percent + "]");
        System.out.println("  Status of assignment:    [" + status + "]");
        System.out.println("  Actual grade:            [" + actual_grade + "]");
        System.out.println("  Due date grade:            [" + get_due_date_string() + "]");
    }


    // -------------------
    // Convert data to XML
    // -------------------
    public Element get_xml() {
        Element assignment_element = new Element("assignment");

        Element name_element = new Element("name");
        name_element.setText(name);
        assignment_element.addContent(name_element);

        Element description_element = new Element("description");
        description_element.setText(description);
        assignment_element.addContent(description_element);

        Element course_element = new Element("course");
        course_element.setText(course);
        assignment_element.addContent(course_element);

        Element grade_percent_element = new Element("grade_percent");
        grade_percent_element.setText(grade_percent);
        assignment_element.addContent(grade_percent_element);

        Element status_element = new Element("status");
        status_element.setText(status);
        assignment_element.addContent(status_element);

        Element actual_grade_element = new Element("actual_grade");
        actual_grade_element.setText(actual_grade);
        assignment_element.addContent(actual_grade_element);

        Element due_date_element = new Element("due_date");
        due_date_element.setText(get_due_date_string());
        assignment_element.addContent(due_date_element);

        return assignment_element;
    }
}
