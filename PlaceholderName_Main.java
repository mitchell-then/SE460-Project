import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;


class PlaceholderName_Main {
    private static String xml_file_name = "saved_data.xml";

    public static void main(String[] args) {
        List<course> course_list = new ArrayList<>();
        File xml_file = new File(xml_file_name);
        if (!xml_file.exists()) {
            generate_empty_xml();
        }

        Document xml_doc = load_from_xml(course_list);

        course test_course = new course("Some Fake Course Name", "SE", "999", "01", "10:00", "10:50");
        course test_course_a = new course("Some Fake Course Name", "SE", "999", "01", "10:00", "10:50");

        course_list.add(test_course);
        course_list.add(test_course_a);

        save_to_xml(xml_doc, course_list);
    }

    private static void write_to_xml(Document xml_doc) {
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

    private static void save_to_xml(Document xml_doc, List<course> course_list) {
        // courses
        Element course_root = xml_doc.getRootElement().getChild("courses");
        for (int i = 0; i < course_list.size(); i++) {
            course_root.addContent(course_list.get(i).get_xml());
        }

        // TODO: add saving for other objects

        write_to_xml(xml_doc);
    }

    private static Document load_from_xml(List<course> course_list) {
        File xml_file = new File(xml_file_name);
        SAXBuilder sax_builder = new SAXBuilder();
        Document xml_doc = null;

        try {
            xml_doc = sax_builder.build(xml_file);

            List<Element> xml_course_list = xml_doc.getRootElement().getChild("courses").getChildren();

            for (int i = 0; i < xml_course_list.size(); i++) {
                course_list.add(xml_to_course(xml_course_list.get(i)));
            }

            // TODO: add loading for other objects
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return xml_doc;

    }

    private static void generate_empty_xml() {
        Element xml_root = new Element("PlaceholderName");
        Document xml_doc = new Document(xml_root);

        Element courses = new Element("courses");
        xml_doc.getRootElement().addContent(courses);

        Element bills = new Element("bills");
        xml_doc.getRootElement().addContent(bills);

        // TODO: add generation for other objects

        write_to_xml(xml_doc);
    }

    private static course xml_to_course(Element course_element) {
        String temp_name = course_element.getChild("name").getText();
        String temp_department = course_element.getChild("department").getText();
        String temp_number = course_element.getChild("number").getText();
        String temp_section = course_element.getChild("section").getText();
        String temp_start_time = course_element.getChild("start_time").getText();
        String temp_end_time = course_element.getChild("end_time").getText();
        String temp_notes = course_element.getChild("notes").getText();

        course temp = new course(temp_name, temp_department, temp_number, temp_section, temp_start_time, temp_end_time);
        temp.set_notes(temp_notes);

        return temp;
    }
}
