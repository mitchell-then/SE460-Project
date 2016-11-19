import java.time.*;
import org.jdom2.*;

public class course implements Comparable<course> {
    private String name;
    private String department;
    private String number;
    private String section;
    private String notes;
    private String grade;
    private boolean[] days_of_week = new boolean[5];

    private LocalTime start_time;
    private LocalTime end_time;

    course(String n, String d, String nu, String s, String grade, String st, String et, boolean m, boolean tu, boolean w, boolean th, boolean f) {
        name = n;
        department = d;
        number = nu;
        section = s;
        this.grade = grade;
        start_time = LocalTime.parse(st);
        end_time = LocalTime.parse(et);

        days_of_week[0] = m;
        days_of_week[1] = tu;
        days_of_week[2] = w;
        days_of_week[3] = th;
        days_of_week[4] = f;
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

    public void set_grade (String grade) {
        this.grade = grade;
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

    public void set_days_of_week(boolean m, boolean tu, boolean w, boolean th, boolean f) {
        days_of_week[0] = m;
        days_of_week[1] = tu;
        days_of_week[2] = w;
        days_of_week[3] = th;
        days_of_week[4] = f;
    }

    // ------------------------------------
    // getters
    // ------------------------------------
    public String get_name() { return name; }

    public String get_department() { return department; }

    public String get_number() { return number; }

    public String get_section() { return section; }

    public String get_grade() { return grade; }

    public LocalTime get_start_time() { return start_time; }

    public LocalTime get_end_time() { return end_time; }

    public String get_notes() { return notes; }

    public String get_department_and_number() { return department + " " + number; }

    public String get_days_of_week() {
        String out = "";

        if (days_of_week[0]) {
            out = out + "M ";
        }
        if (days_of_week[1]) {
            out = out + "Tu ";
        }
        if (days_of_week[2]) {
            out = out + "W ";
        }
        if (days_of_week[3]) {
            out = out + "Th ";
        }
        if (days_of_week[4]) {
            out = out + "F ";
        }

        return out;
    }

    public boolean get_specific_day(int i) { return days_of_week[i]; }

    public boolean get_monday() { return days_of_week[0]; }
    public boolean get_tuesday() { return days_of_week[1]; }
    public boolean get_wednesday() { return days_of_week[2]; }
    public boolean get_thursday() { return days_of_week[3]; }
    public boolean get_friday() { return days_of_week[4]; }

    public void set_monday(boolean a) { days_of_week[0] = a; }
    public void set_tuesday(boolean a) { days_of_week[1] = a; }
    public void set_wednesday(boolean a) { days_of_week[2] = a; }
    public void set_thursday(boolean a) { days_of_week[3] = a; }
    public void set_friday(boolean a) { days_of_week[4] = a; }

    // ------------------------------------
    // debug
    // ------------------------------------
    public void debug_print() {
        System.out.println("Name: " + name);
        System.out.println("  Department:   [" + department + "]");
        System.out.println("  Number:       [" + number + "]");
        System.out.println("  Section:      [" + section + "]");
        System.out.println("  Final grade:  [" + grade + "]");
        System.out.println("  Start Time:   [" + start_time.toString() + "]");
        System.out.println("  End Time:     [" + end_time.toString() + "]");
        System.out.println("  Notes:        [" + notes + "]");
        System.out.println("  Days of week: [" + get_days_of_week() + "]");
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

        Element grade_element = new Element("grade");
        grade_element.setText(grade);
        course_element.addContent(grade_element);

        Element start_time_element = new Element("start_time");
        start_time_element.setText(start_time.toString());
        course_element.addContent(start_time_element);

        Element end_time_element = new Element("end_time");
        end_time_element.setText(end_time.toString());
        course_element.addContent(end_time_element);

        Element notes_element = new Element("notes");
        notes_element.setText(notes);
        course_element.addContent(notes_element);

        Element days_of_week_element = new Element("days_of_week");

        Element monday_element = new Element("monday");
        monday_element.setText(Boolean.toString(days_of_week[0]));
        days_of_week_element.addContent(monday_element);

        Element tuesday_element = new Element("tuesday");
        tuesday_element.setText(Boolean.toString(days_of_week[1]));
        days_of_week_element.addContent(tuesday_element);

        Element wednesday_element = new Element("wednesday");
        wednesday_element.setText(Boolean.toString(days_of_week[2]));
        days_of_week_element.addContent(wednesday_element);

        Element thursday_element = new Element("thursday");
        thursday_element.setText(Boolean.toString(days_of_week[3]));
        days_of_week_element.addContent(thursday_element);

        Element friday_element = new Element("friday");
        friday_element.setText(Boolean.toString(days_of_week[4]));
        days_of_week_element.addContent(friday_element);

        course_element.addContent(days_of_week_element);

        return course_element;
    }

    public int compareTo(course c) {
        return start_time.compareTo(c.start_time);
    }
}
