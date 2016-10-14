import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import javax.swing.text.*;


class add_course_frame extends JFrame {
    // private Format short_time = DateFormat.getTimeInstance(DateFormat.SHORT);
    private JButton create_button = new JButton("Create");
    private JButton cancel_button = new JButton("Cancel");
    private JTextField temp_name = new JTextField(10);
    private JTextField temp_department = new JTextField(10);
    private JFormattedTextField temp_number = new JFormattedTextField(create_formatter("###"));
    private JFormattedTextField temp_section = new JFormattedTextField(create_formatter("##"));
    private JFormattedTextField temp_start_time = new JFormattedTextField(create_formatter("##:##"));
    private JFormattedTextField temp_end_time = new JFormattedTextField(create_formatter("##:##"));
    private JLabel temp_name_label = new JLabel("Name: ");
    private JLabel temp_department_label = new JLabel("Department: ");
    private JLabel temp_number_label = new JLabel("Number: ");
    private JLabel temp_section_label = new JLabel("Section: ");
    private JLabel temp_start_time_label = new JLabel("Start Time: ");
    private JLabel temp_end_time_label = new JLabel("End Time: ");

    class create_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            course temp = new course(temp_name.getText(), temp_department.getText(), temp_number.getText(), temp_section.getText(), temp_start_time.getText(), temp_end_time.getText());
            PlaceholderName_Main.add_course_to_list(temp);
        }
    }
    private create_button_listener cbl = new create_button_listener();
    public add_course_frame() {
        create_button.addActionListener(cbl);
        // b2.addActionListener(bl);

        temp_section.setFocusLostBehavior(JFormattedTextField.PERSIST);
        temp_start_time.setFocusLostBehavior(JFormattedTextField.PERSIST);
        temp_end_time.setFocusLostBehavior(JFormattedTextField.PERSIST);

        JPanel label_pane = new JPanel();
        label_pane.setLayout(new GridLayout(6, 1, 6, 6));
        label_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel input_pane = new JPanel();
        input_pane.setLayout(new GridLayout(6, 1, 6, 6));
        input_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel button_pane = new JPanel();
        button_pane.setLayout(new BoxLayout(button_pane, BoxLayout.LINE_AXIS));
        button_pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        label_pane.add(temp_name_label);
        input_pane.add(temp_name);

        label_pane.add(temp_department_label);
        input_pane.add(temp_department);

        label_pane.add(temp_number_label);
        input_pane.add(temp_number);

        label_pane.add(temp_section_label);
        input_pane.add(temp_section);

        label_pane.add(temp_start_time_label);
        input_pane.add(temp_start_time);

        label_pane.add(temp_end_time_label);
        input_pane.add(temp_end_time);

        button_pane.add(Box.createHorizontalGlue());
        button_pane.add(cancel_button);
        button_pane.add(Box.createRigidArea(new Dimension(10, 0)));
        button_pane.add(create_button);

        Container content_pane = getContentPane();
        content_pane.add(label_pane, BorderLayout.LINE_START);
        content_pane.add(input_pane, BorderLayout.LINE_END);
        content_pane.add(button_pane, BorderLayout.PAGE_END);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                PlaceholderName_Main.end_process();
            }
        });

    }

    protected MaskFormatter create_formatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (java.text.ParseException exc) {
            System.err.println("formatter is bad: " + exc.getMessage());
            System.exit(-1);
        }
        return formatter;
    }
}
