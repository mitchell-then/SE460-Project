import java.time.*;
import org.jdom2.*;

public class assignment {
    private String name;
    private String description;
    private String course;
    private String grade_percent;
    private String status;

    assignment(String n, String d, String c, String grade_p, String status) {
        name          = n;
        description   = d;
        course        = c;
        grade_percent = grade_p;
        this.status   = status;
    }

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

    public void debug_print() {
        System.out.println("Assignment name: " + name);
        System.out.println("  Description:             [" + description + "]");
        System.out.println("  Course:                  [" + course + "]");
        System.out.println("  Percent of final grade:  [" + grade_percent + "]");
        System.out.println("  Status of assignment     [" + status + "]");
    }

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

        return assignment_element;
    }
}
