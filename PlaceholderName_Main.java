import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;
import javax.swing.*;
import java.text.*;


class PlaceholderName_Main {
    private static String xml_file_name = "saved_data.xml";
    public static Document xml_doc = null;

    private static List<course> course_list = new ArrayList<>();
    private static List<assignment> assignment_list = new ArrayList<>();
    private static List<bill> bill_list = new ArrayList<>();

    public static void main(String[] args) {
        // --------------------------------------------------------------------
        // initial setup
        // --------------------------------------------------------------------
        File xml_file = new File(xml_file_name);
        if (!xml_file.exists()) {
            generate_empty_xml();
        }

        load_from_xml();
        // --------------------------------------------------------------------


        JFrame main_gui = new gui();
        swing_console.run(main_gui);

        // gui.refresh();
        // end_process();
    }

    public static void end_process() {
        System.out.println("saving");
        save_to_xml();
        System.exit(0);
    }

    private static void write_to_xml() {
        File xml_file = new File(xml_file_name);

        XMLOutputter xml_out = new XMLOutputter();
        xml_out.setFormat(Format.getPrettyFormat());
        try {
            xml_out.output(xml_doc, new FileOutputStream(xml_file));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void save_to_xml() {
        File xml_file = new File(xml_file_name);

        xml_file.delete();

        generate_empty_xml();

        // courses
        Element course_root = xml_doc.getRootElement().getChild("courses");
        for (int i = 0; i < course_list.size(); i++) {
            course_root.addContent(course_list.get(i).get_xml());
        }

        // assignments
        Element assignment_root = xml_doc.getRootElement().getChild("assignments");
        for (int i = 0; i < assignment_list.size(); i++) {
            assignment_root.addContent(assignment_list.get(i).get_xml());
        }

        // bills
        Element bill_root = xml_doc.getRootElement().getChild("bills");
        for (int i = 0; i < bill_list.size(); i++) {
            bill_root.addContent(bill_list.get(i).get_xml());
        }

        // TODO: add saving for other objects

        write_to_xml();
    }

    private static void load_from_xml() {
        File xml_file = new File(xml_file_name);
        SAXBuilder sax_builder = new SAXBuilder();

        try {
            xml_doc = sax_builder.build(xml_file);

            List<Element> xml_course_list = xml_doc.getRootElement().getChild("courses").getChildren();
            List<Element> xml_assignment_list = xml_doc.getRootElement().getChild("assignments").getChildren();

            for (int i = 0; i < xml_course_list.size(); i++) {
                course_list.add(xml_to_course(xml_course_list.get(i)));
            }

            for (int i = 0; i < xml_assignment_list.size(); i++) {
                assignment_list.add(xml_to_assignment(xml_assignment_list.get(i)));
            }

            List<Element> xml_bill_list = xml_doc.getRootElement().getChild("bills").getChildren();
            for (int i = 0; i < xml_bill_list.size(); i++) {
                bill_list.add(xml_to_bill(xml_bill_list.get(i)));
            }

            // TODO: add loading for other objects
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    //TODO: This method and the one up above can be merged into one
    private static void load_assignment_from_xml(List<assignment> assignment_list) {
        File xml_file = new File(xml_file_name);
        SAXBuilder sax_builder = new SAXBuilder();

        try {
            xml_doc = sax_builder.build(xml_file);

            List<Element> xml_assignment_list = xml_doc.getRootElement().getChild("assignments").getChildren();

            for (int i = 0; i < xml_assignment_list.size(); i++) {
                assignment_list.add(xml_to_assignment(xml_assignment_list.get(i)));
            }
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

    private static void generate_empty_xml() {
        Element xml_root = new Element("PlaceholderName");
        xml_doc = new Document(xml_root);

        Element courses = new Element("courses");
        xml_doc.getRootElement().addContent(courses);

        Element bills = new Element("bills");
        xml_doc.getRootElement().addContent(bills);

        Element assignments = new Element("assignments");
        xml_doc.getRootElement().addContent(assignments);

        // TODO: add generation for other objects

        write_to_xml();
    }

    // ------------------------------------
    // xml_to
    // ------------------------------------
    private static course xml_to_course(Element course_element) {
        String temp_name = course_element.getChild("name").getText();
        String temp_department = course_element.getChild("department").getText();
        String temp_number = course_element.getChild("number").getText();
        String temp_section = course_element.getChild("section").getText();
        String temp_grade = course_element.getChild("grade").getText();
        String temp_start_time = course_element.getChild("start_time").getText();
        String temp_end_time = course_element.getChild("end_time").getText();
        String temp_notes = course_element.getChild("notes").getText();
        boolean temp_monday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("monday").getText());
        boolean temp_tuesday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("tuesday").getText());
        boolean temp_wednesday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("wednesday").getText());
        boolean temp_thursday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("thursday").getText());
        boolean temp_friday = Boolean.parseBoolean(course_element.getChild("days_of_week").getChild("friday").getText());

        course temp = new course(temp_name, temp_department, temp_number, temp_section, temp_grade, temp_start_time, temp_end_time, temp_monday, temp_tuesday, temp_wednesday, temp_thursday, temp_friday);
        temp.set_notes(temp_notes);

        return temp;
    }

    private static assignment xml_to_assignment(Element assignment_element) {
        Date temp_due_date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        String temp_name = assignment_element.getChild("name").getText();
        String temp_description = assignment_element.getChild("description").getText();
        String temp_course = assignment_element.getChild("course").getText();
        String temp_grade_percent = assignment_element.getChild("grade_percent").getText();
        String temp_status = assignment_element.getChild("status").getText();
        String temp_actual_grade = assignment_element.getChild("actual_grade").getText();
        try {
            temp_due_date = df.parse(assignment_element.getChild("due_date").getText());
        }
        catch(ParseException e) {
            e.printStackTrace();
        }

        assignment temp = new assignment(temp_name, temp_description, temp_course, temp_grade_percent, temp_status, temp_actual_grade);
        temp.set_due_date(df.format(temp_due_date).toString());

        return temp;
    }

    private static bill xml_to_bill(Element bill_element) {
        Date temp_due_date = null;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        String temp_name = bill_element.getChild("name").getText();
        String temp_notes = bill_element.getChild("notes").getText();
        String temp_amount = bill_element.getChild("amount").getText();
        try {
            temp_due_date = df.parse(bill_element.getChild("due_date").getText());
        }
        catch(ParseException e) {
            e.printStackTrace();
        }

        bill temp = new bill(temp_name, temp_amount, df.format(temp_due_date).toString());
        temp.set_notes(temp_notes);

        return temp;
    }

    // ------------------------------------
    // course_list
    // ------------------------------------
    public static void course_list_add(course c) {
        course_list.add(c);
    }

    public static void course_list_remove(int index) {
        course_list.remove(index);
    }

    public static int course_list_size() {
        return course_list.size();
    }

    public static boolean course_list_is_empty() {
        return course_list.isEmpty();
    }

    public static course course_list_get_at(int index) {
        return course_list.get(index);
    }

    //////
    public static void add_assignment_to_list(assignment a) {
        assignment_list.add(a);
    }

    public static void remove_assignment_from_list(int index) {
        assignment_list.remove(index);
    }

    public static int assignment_list_size() {
        return assignment_list.size();
    }

    public static assignment get_assignment_at(int index) {
        return assignment_list.get(index);
    }

    // ------------------------------------
    // bill_list
    // ------------------------------------
    public static void bill_list_add(bill b) {
        bill_list.add(b);
    }

    public static void bill_list_remove(int index) {
        bill_list.remove(index);
    }

    public static int bill_list_size() {
        return bill_list.size();
    }

    public static boolean bill_list_is_empty() {
        return bill_list.isEmpty();
    }

    public static bill bill_list_get_at(int index) {
        return bill_list.get(index);
    }
}
