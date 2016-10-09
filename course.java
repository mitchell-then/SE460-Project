import java.time.*;
import org.jdom2.*;

class course {
    private String name;
    private String department;
    private String number;
    private String section;

    private LocalTime start_time;
    private LocalTime end_time;

    private String notes;

    course(String n, String d, String nu, String s, String st, String et) {
        name = n;
        department = d;
        number = nu;
        section = s;
        start_time = LocalTime.parse(st);
        end_time = LocalTime.parse(et);
    }

    // ------------------------------------
    // setters
    // ------------------------------------
    public void set_name(String new_name) {
        name = new_name;
    }

    public void set_department(String new_department) {
        department = new_department;
    }

    public void set_number(String new_number) {
        number = new_number;
    }

    public void set_section(String new_section) {
        section = new_section;
    }

    public void set_start_time(String new_start_time) {
        start_time = LocalTime.parse(new_start_time);
    }

    public void set_end_time(String new_end_time) {
        end_time = LocalTime.parse(new_end_time);
    }

    public void set_notes(String new_notes) {
        notes = new_notes;
    }

    // ------------------------------------
    // getters
    // ------------------------------------
    public String get_name() { return name; }

    public String get_department() { return department; }

    public String get_number() { return number; }

    public String get_section() { return section; }

    public LocalTime get_start_time() { return start_time; }

    public LocalTime get_end_time() { return end_time; }

    public String get_notes() { return notes; }

    // ------------------------------------
    // debug
    // ------------------------------------
    public void debug_print() {
        System.out.println("Name: " + name);
        System.out.println("  Department: " + department);
        System.out.println("  Number:     " + number);
        System.out.println("  Section:    " + section);
        System.out.println("  Start Time: " + start_time.toString());
        System.out.println("  End Time:   " + end_time.toString());
        System.out.println("  Notes:      " + notes);
    }

    // ------------------------------------
    // xml
    // ------------------------------------
    public Element get_xml() {
        Element course_element = new Element("course");

        Element name_element = new Element("name");
        name_element.setText(name);
        course_element.addContent(name_element);

        Element department_element = new Element("department");
        department_element.setText(department);
        course_element.addContent(department_element);

        Element number_element = new Element("number");
        number_element.setText(number);
        course_element.addContent(number_element);

        Element section_element = new Element("section");
        section_element.setText(section);
        course_element.addContent(section_element);

        Element start_time_element = new Element("start_time");
        start_time_element.setText(start_time.toString());
        course_element.addContent(start_time_element);

        Element end_time_element = new Element("end_time");
        end_time_element.setText(end_time.toString());
        course_element.addContent(end_time_element);

        Element notes_element = new Element("notes");
        notes_element.setText(notes);
        course_element.addContent(notes_element);

        return course_element;
    }


}
