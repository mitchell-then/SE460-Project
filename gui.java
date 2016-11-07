import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import java.util.*;
import java.util.Calendar;

import org.jdatepicker.impl.*;
import org.jdatepicker.util.*;
import org.jdatepicker.*;

class gui extends JFrame {
    private JComponent course_pane = new course_panel();
    private JComponent bill_pane = new bill_panel();
    public gui() {
        JTabbedPane tabbed_pane = new JTabbedPane();
        tabbed_pane.addTab("Courses", course_pane);
        tabbed_pane.addTab("Bills", bill_pane);
        this.add(tabbed_pane);
        tabbed_pane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.pack();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                PlaceholderName_Main.end_process();
            }
        });
    }
}

class course_panel extends JPanel {
    // ----------------------
    // list
    // ----------------------
    public static DefaultListModel<String> list_model = new DefaultListModel<>();
    private static JList<String> course_list;
    private list_selection_listener lsl = new list_selection_listener();

    // ----------------------
    // course info labels
    // ----------------------
    private info_label selected_course_name = new info_label("Name");
    private info_label selected_course_department = new info_label("Department");
    private info_label selected_course_number = new info_label("Number");
    private info_label selected_course_section = new info_label("Section");
    private JTextArea selected_course_notes = new JTextArea();
    private info_label selected_course_start_time = new info_label("Start Time");
    private info_label selected_course_end_time = new info_label("End Time");
    private info_label selected_course_days_of_week = new info_label("Days of Week");

    // ----------------------
    // buttons
    // ----------------------
    private JButton add_course_button = new JButton("Create new Course");
    private JButton remove_course_button = new JButton("Remove Course");
    private JButton edit_course_button = new JButton("Edit Course details");
    private JButton add_course_notes_button = new JButton("Add Course notes");

    private create_course_button_listener ccbl = new create_course_button_listener();
    private remove_course_button_listener rcbl = new remove_course_button_listener();
    private edit_course_button_listener ecbl = new edit_course_button_listener();
    private add_course_notes_button_listener acnbl = new add_course_notes_button_listener();

    public course_panel() {
        // initially populate list
        refresh_list();

        // list
        course_list = new JList<>(list_model);
        course_list.addListSelectionListener(lsl);
        course_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        course_list.setLayoutOrientation(JList.VERTICAL);
        course_list.setVisibleRowCount(-1);
        JScrollPane list_scroller = new JScrollPane(course_list);

        // notes
        selected_course_notes.setEditable(false);
        selected_course_notes.setBackground(new Color(238, 238, 238));
        JScrollPane notes_scroller = new JScrollPane(selected_course_notes);
        Border notes_scroller_border = BorderFactory.createTitledBorder("Notes");
        notes_scroller.setBorder(notes_scroller_border);

        // buttons
        add_course_button.addActionListener(ccbl);
        remove_course_button.addActionListener(rcbl);
        edit_course_button.addActionListener(ecbl);
        add_course_notes_button.addActionListener(acnbl);
        remove_course_button.setEnabled(false);
        edit_course_button.setEnabled(false);
        add_course_notes_button.setEnabled(false);

        // setup panel
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // list
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 4;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(list_scroller, c);

        // labels
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.weightx = 0.5;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(selected_course_name, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        c.gridwidth = 1;
        this.add(selected_course_department, c);

        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.5;
        this.add(selected_course_number, c);

        c.gridx = 3;
        c.gridy = 1;
        c.weightx = 0.5;
        this.add(selected_course_section, c);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.5;
        this.add(selected_course_start_time, c);

        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 0.5;
        this.add(selected_course_end_time, c);

        c.gridx = 3;
        c.gridy = 2;
        c.weightx = 0.5;
        this.add(selected_course_days_of_week, c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 3;
        c.ipady = 80;
        c.weightx = 0;
        this.add(notes_scroller, c);

        // buttons
        c.gridx = 0;
        c.gridy = 4;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 0.1;
        c.gridwidth = 1;
        this.add(remove_course_button, c);

        c.gridx = 1;
        c.gridy = 4;
        this.add(add_course_button, c);

        c.gridx = 2;
        c.gridy = 4;
        this.add(edit_course_button, c);

        c.gridx = 3;
        c.gridy = 4;
        this.add(add_course_notes_button, c);

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    public static void refresh_list() {
        list_model.clear();
        for (int i = 0; i < PlaceholderName_Main.course_list_size(); i++) {
            list_model.addElement(PlaceholderName_Main.course_list_get_at(i).get_department_and_number());
        }
    }

    public static void refresh_list(int index) {
        list_model.clear();
        for (int i = 0; i < PlaceholderName_Main.course_list_size(); i++) {
            list_model.addElement(PlaceholderName_Main.course_list_get_at(i).get_department_and_number());
        }
        course_list.setSelectedIndex(index);
    }

    class list_selection_listener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (! course_list.isSelectionEmpty()) {
                course selected_course = PlaceholderName_Main.course_list_get_at(course_list.getSelectedIndex());

                selected_course_name.setText(selected_course.get_name());
                selected_course_department.setText(selected_course.get_department());
                selected_course_number.setText(selected_course.get_number());
                selected_course_section.setText(selected_course.get_section());
                selected_course_notes.setText(selected_course.get_notes());
                selected_course_start_time.setText(selected_course.get_start_time().toString());
                selected_course_end_time.setText(selected_course.get_end_time().toString());
                selected_course_days_of_week.setText(selected_course.get_days_of_week());

                remove_course_button.setEnabled(true);
                edit_course_button.setEnabled(true);
                add_course_notes_button.setEnabled(true);

                selected_course_notes.setCaretPosition(0);
            }
            else {
                selected_course_name.setText("");
                selected_course_department.setText("");
                selected_course_number.setText("");
                selected_course_section.setText("");
                selected_course_notes.setText("");
                selected_course_start_time.setText("");
                selected_course_end_time.setText("");
                selected_course_days_of_week.setText("");

                remove_course_button.setEnabled(false);
                edit_course_button.setEnabled(false);
                add_course_notes_button.setEnabled(false);

            }
        }
    }

    class create_course_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            course_frame acf = new course_frame();
            acf.pack();
            acf.setVisible(true);
        }
    }

    class remove_course_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (! course_list.isSelectionEmpty()) {
                PlaceholderName_Main.course_list_remove(course_list.getSelectedIndex());
                refresh_list();
            }
        }
    }

    class edit_course_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (! course_list.isSelectionEmpty()) {
                edit_course_frame ecf = new edit_course_frame(course_list.getSelectedIndex());
                ecf.pack();
                ecf.setVisible(true);
            }
        }
    }

    class add_course_notes_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (! course_list.isSelectionEmpty()) {
                add_course_notes_frame acnf = new add_course_notes_frame(course_list.getSelectedIndex());
                acnf.pack();
                acnf.setVisible(true);
            }
        }
    }
}

class bill_panel extends JPanel {
    // ----------------------
    // list
    // ----------------------
    private static DefaultListModel<String> list_model = new DefaultListModel<>();
    private static JList<String> bill_list;
    private list_selection_listener lsl = new list_selection_listener();

    // ----------------------
    // bill info labels
    // ----------------------
    private info_label selected_bill_name = new info_label("Name");
    private info_label selected_bill_amount = new info_label("Amount");
    private info_label selected_bill_due_date = new info_label("Due Date");
    private JTextArea selected_bill_notes = new JTextArea();

    // ----------------------
    // buttons
    // ----------------------
    private JButton create_bill_button = new JButton("Create new Bill");
    private JButton remove_bill_button = new JButton("Remove Bill");
    private JButton edit_bill_button = new JButton("Edit Bill details");
    private JButton add_bill_notes_button = new JButton("Add Bill notes");

    private create_bill_button_listener cbbl = new create_bill_button_listener();
    private remove_bill_button_listener rbbl = new remove_bill_button_listener();
    private edit_bill_button_listener ebbl = new edit_bill_button_listener();
    private add_bill_notes_button_listener abnbl = new add_bill_notes_button_listener();

    public bill_panel() {
        // initially populate list
        refresh_list();

        // list
        bill_list = new JList<>(list_model);
        bill_list.addListSelectionListener(lsl);
        bill_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bill_list.setLayoutOrientation(JList.VERTICAL);
        bill_list.setVisibleRowCount(-1);
        JScrollPane list_scroller = new JScrollPane(bill_list);

        // notes
        selected_bill_notes.setEditable(false);
        selected_bill_notes.setBackground(new Color(238, 238, 238));
        JScrollPane notes_scroller = new JScrollPane(selected_bill_notes);
        Border notes_scroller_border = BorderFactory.createTitledBorder("Notes");
        notes_scroller.setBorder(notes_scroller_border);

        // buttons
        create_bill_button.addActionListener(cbbl);
        remove_bill_button.addActionListener(rbbl);
        edit_bill_button.addActionListener(ebbl);
        add_bill_notes_button.addActionListener(abnbl);
        remove_bill_button.setEnabled(false);
        edit_bill_button.setEnabled(false);
        add_bill_notes_button.setEnabled(false);

        // setup panel
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;

        // list
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        c.weighty = 1;
        c.weightx = 0.5;
        this.add(list_scroller, c);

        // labels
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        this.add(selected_bill_name, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        this.add(selected_bill_amount, c);

        c.gridx = 2;
        c.gridy = 1;
        this.add(selected_bill_due_date, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.ipady = 160;
        c.weightx = 0;
        c.weighty = 1;
        this.add(notes_scroller, c);

        // buttons
        JPanel button_pane = new JPanel();
        button_pane.setLayout(new GridLayout(1, 4, 6, 6));

        button_pane.add(remove_bill_button);
        button_pane.add(create_bill_button);
        button_pane.add(edit_bill_button);
        button_pane.add(add_bill_notes_button);

        c.gridx = 0;
        c.gridy = 3;
        c.ipady = 0;
        c.weightx = 0;
        c.weighty = 0.1;
        c.gridwidth = 3;
        this.add(button_pane, c);

        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


    }

    public static void refresh_list() {
        list_model.clear();
        for (int i = 0; i < PlaceholderName_Main.bill_list_size(); i++) {
            list_model.addElement(PlaceholderName_Main.bill_list_get_at(i).get_name());
        }
    }

    public static void refresh_list(int index) {
        list_model.clear();
        for (int i = 0; i < PlaceholderName_Main.bill_list_size(); i++) {
            list_model.addElement(PlaceholderName_Main.bill_list_get_at(i).get_name());
        }
        bill_list.setSelectedIndex(index);
    }

    class list_selection_listener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (! bill_list.isSelectionEmpty()) {
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");


                bill selected_bill = PlaceholderName_Main.bill_list_get_at(bill_list.getSelectedIndex());

                selected_bill_name.setText(selected_bill.get_name());
                selected_bill_amount.setText(selected_bill.get_amount());
                selected_bill_due_date.setText(df.format(selected_bill.get_due_date()).toString());
                selected_bill_notes.setText(selected_bill.get_notes());

                remove_bill_button.setEnabled(true);
                edit_bill_button.setEnabled(true);
                add_bill_notes_button.setEnabled(true);

                selected_bill_notes.setCaretPosition(0);
            }
            else {
                selected_bill_name.setText("");
                selected_bill_amount.setText("");
                selected_bill_due_date.setText("");
                selected_bill_notes.setText("");

                remove_bill_button.setEnabled(false);
                edit_bill_button.setEnabled(false);
                add_bill_notes_button.setEnabled(false);
            }
        }
    }

    class create_bill_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            bill_frame abf = new bill_frame();
            abf.pack();
            abf.setVisible(true);
        }
    }

    class remove_bill_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (! bill_list.isSelectionEmpty()) {
                PlaceholderName_Main.bill_list_remove(bill_list.getSelectedIndex());
                refresh_list();
            }
        }
    }

    class edit_bill_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (! bill_list.isSelectionEmpty()) {
                edit_bill_frame ebf = new edit_bill_frame(bill_list.getSelectedIndex());
                ebf.pack();
                ebf.setVisible(true);
            }
        }
    }

    class add_bill_notes_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (! bill_list.isSelectionEmpty()) {
                add_bill_notes_frame abnf = new add_bill_notes_frame(bill_list.getSelectedIndex());
                abnf.pack();
                abnf.setVisible(true);
            }
        }
    }
}

class info_label extends JLabel {
    public info_label(String title) {
        Border border = BorderFactory.createTitledBorder(title);
        this.setBorder(border);
        this.setMinimumSize(new Dimension(144, 36));
        this.setPreferredSize(new Dimension(144, 36));
        this.setMaximumSize(new Dimension(144, 36));
    }
}

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

    private String datePattern = "MM/dd/yyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            // System.out.println(dateFormatter.format(cal.getTime()));
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

}

class bill_frame extends JFrame {
    // ----------------------
    // input text fields
    // ----------------------
    protected JTextField name_field = new JTextField();
    protected JTextField amount_field = new JTextField();



    // ----------------------
    // labels
    // ----------------------
    protected JLabel name_label = new JLabel("Name: ");
    protected JLabel amount_label = new JLabel("Amount: ");

    // ----------------------
    // buttons
    // ----------------------
    protected JButton done_button = new JButton("Done");
    protected JButton cancel_button = new JButton("Cancel");
    protected done_button_listener dbl = new done_button_listener();
    protected cancel_button_listener cbl = new cancel_button_listener();

    protected UtilDateModel model;
    protected Properties p;
    protected JDatePanelImpl date_panel;
    protected JDatePickerImpl date_picker;


    public bill_frame() {
        JPanel frame_pane = new JPanel();
        frame_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        done_button.addActionListener(dbl);
        cancel_button.addActionListener(cbl);


        model = new UtilDateModel();
        p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        // JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        date_panel = new JDatePanelImpl(model, p);
        date_picker = new JDatePickerImpl(date_panel, new DateLabelFormatter());

        frame_pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // name
        c.gridx = 0;
        c.gridy = 0;
        frame_pane.add(name_label, c);

        c.gridx = 1;
        c.gridy = 0;
        frame_pane.add(name_field, c);

        // amount
        c.gridx = 0;
        c.gridy = 1;
        frame_pane.add(amount_label, c);

        c.gridx = 1;
        c.gridy = 1;
        frame_pane.add(amount_field, c);

        // date
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        frame_pane.add(date_picker, c);

        // buttons
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        frame_pane.add(cancel_button, c);

        c.gridx = 1;
        c.gridy = 3;
        frame_pane.add(done_button, c);

        this.add(frame_pane);
    }

    class done_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

                // System.out.println(df.format((Date) date_picker.getModel().getValue()).toString());
                // Calendar temp_value =
                bill temp = new bill(name_field.getText(), amount_field.getText(), df.format((Date) date_picker.getModel().getValue()).toString());
                PlaceholderName_Main.bill_list_add(temp);
                bill_panel.refresh_list();
            }
            catch (Exception ex) {
                System.err.println("Invalid bill data: " + ex);
            }
            finally {
                setVisible(false);
                dispose();
            }
        }
    }

    class cancel_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
    }
}

class edit_bill_frame extends bill_frame {
    private int index;
    public edit_bill_frame(int i) {
        index = i;

        done_button.removeActionListener(dbl);
        done_button.addActionListener(new done_button_listener());

        // populate fields with existing data
        name_field.setText(PlaceholderName_Main.bill_list_get_at(index).get_name());
        amount_field.setText(PlaceholderName_Main.bill_list_get_at(index).get_amount());
        // model.setDate(PlaceholderName_Main.bill_list_get_at(index).get_due_date());
    }

    class done_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PlaceholderName_Main.bill_list_get_at(index).set_name(name_field.getText());
            PlaceholderName_Main.bill_list_get_at(index).set_amount(amount_field.getText());
            PlaceholderName_Main.bill_list_get_at(index).set_due_date(date_picker.getModel().getValue().toString());

            bill_panel.refresh_list(index);
            setVisible(false);
            dispose();
        }
    }
}

class course_frame extends JFrame {
    // ----------------------
    // input text fields
    // ----------------------
    protected JTextField name_field = new JTextField();
    protected JTextField department_field = new JTextField(4);
    protected JFormattedTextField number_field = new JFormattedTextField(create_formatter("###"));
    protected JFormattedTextField section_field = new JFormattedTextField(create_formatter("##"));
    protected JFormattedTextField start_time_field = new JFormattedTextField(create_formatter("##:##"));
    protected JFormattedTextField end_time_field = new JFormattedTextField(create_formatter("##:##"));

    // ----------------------
    // labels
    // ----------------------
    protected JLabel name_label = new JLabel("Name: ");
    protected JLabel department_label = new JLabel("Department: ");
    protected JLabel number_label = new JLabel("Number: ");
    protected JLabel section_label = new JLabel("Section: ");
    protected JLabel start_time_label = new JLabel("Start Time: ");
    protected JLabel end_time_label = new JLabel("End Time: ");

    // ----------------------
    // checkboxes
    // ----------------------
    protected JCheckBox monday_checkbox = new JCheckBox("Monday");
    protected JCheckBox tuesday_checkbox = new JCheckBox("Tuesday");
    protected JCheckBox wednesday_checkbox = new JCheckBox("Wednesday");
    protected JCheckBox thursday_checkbox = new JCheckBox("Thursday");
    protected JCheckBox friday_checkbox = new JCheckBox("Friday");

    protected boolean temp_course_monday = false;
    protected boolean temp_course_tuesday = false;
    protected boolean temp_course_wednesday = false;
    protected boolean temp_course_thursday = false;
    protected boolean temp_course_friday = false;

    protected checkbox_listener all_checkbox_listener = new checkbox_listener();

    // ----------------------
    // buttons
    // ----------------------
    protected JButton done_button = new JButton("Done");
    protected JButton cancel_button = new JButton("Cancel");
    protected done_button_listener dbl = new done_button_listener();
    protected cancel_button_listener cbl = new cancel_button_listener();

    public course_frame() {
        JPanel frame_pane = new JPanel();
        frame_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel checkbox_pane = new JPanel();
        checkbox_pane.setLayout(new GridLayout(1, 5, 6, 6));
        checkbox_pane.setBorder(BorderFactory.createTitledBorder("Days of Class"));

        // setup and add input text fields
        section_field.setFocusLostBehavior(JFormattedTextField.PERSIST);
        start_time_field.setFocusLostBehavior(JFormattedTextField.PERSIST);
        end_time_field.setFocusLostBehavior(JFormattedTextField.PERSIST);

        // setup and add checkboxes
        monday_checkbox.addItemListener(all_checkbox_listener);
        tuesday_checkbox.addItemListener(all_checkbox_listener);
        wednesday_checkbox.addItemListener(all_checkbox_listener);
        thursday_checkbox.addItemListener(all_checkbox_listener);
        friday_checkbox.addItemListener(all_checkbox_listener);

        checkbox_pane.add(monday_checkbox);
        checkbox_pane.add(tuesday_checkbox);
        checkbox_pane.add(wednesday_checkbox);
        checkbox_pane.add(thursday_checkbox);
        checkbox_pane.add(friday_checkbox);

        // setup and add buttons
        done_button.addActionListener(dbl);
        cancel_button.addActionListener(cbl);

        // setup panel
        frame_pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        // add name
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.5;
        frame_pane.add(name_label, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 5;
        c.weightx = 0.5;
        frame_pane.add(name_field, c);

        // add department
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        frame_pane.add(department_label, c);

        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 0.5;
        frame_pane.add(department_field, c);

        // add number
        c.gridx = 2;
        c.gridy = 1;
        c.weightx = 0.5;
        frame_pane.add(number_label, c);

        c.gridx = 3;
        c.gridy = 1;
        c.weightx = 0.5;
        frame_pane.add(number_field, c);

        // add section
        c.gridx = 4;
        c.gridy = 1;
        c.weightx = 0.5;
        frame_pane.add(section_label, c);

        c.gridx = 5;
        c.gridy = 1;
        c.weightx = 0.5;
        frame_pane.add(section_field, c);

        // add start_time
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        frame_pane.add(start_time_label, c);

        c.gridx = 1;
        c.gridy = 2;
        c.weightx = 0.5;
        frame_pane.add(start_time_field, c);

        // add end_time
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 0.5;
        frame_pane.add(end_time_label, c);

        c.gridx = 3;
        c.gridy = 2;
        c.weightx = 0.5;
        frame_pane.add(end_time_field, c);

        // add days_of_week
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 6;
        c.weightx = 0.5;
        frame_pane.add(checkbox_pane, c);

        // add buttons
        c.gridx = 4;
        c.gridy = 4;
        c.gridwidth = 1;
        c.weightx = 0.5;
        frame_pane.add(cancel_button, c);

        c.gridx = 5;
        c.gridy = 4;
        c.weightx = 0.5;
        frame_pane.add(done_button, c);
        this.add(frame_pane);
    }

    class checkbox_listener implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            Object source = e.getItemSelectable();

            if (source == monday_checkbox) {
                temp_course_monday = true ? monday_checkbox.isSelected() : false;
            }
            else if (source == tuesday_checkbox) {
                temp_course_tuesday = true ? tuesday_checkbox.isSelected() : false;
            }
            else if (source == wednesday_checkbox) {
                temp_course_wednesday = true ? wednesday_checkbox.isSelected() : false;
            }
            else if (source == thursday_checkbox) {
                temp_course_thursday = true ? thursday_checkbox.isSelected() : false;
            }
            else if (source == friday_checkbox) {
                temp_course_friday = true ? friday_checkbox.isSelected() : false;
            }
        }
    }

    class done_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                course temp = new course(name_field.getText(), department_field.getText(), number_field.getText(), section_field.getText(), start_time_field.getText(), end_time_field.getText(), temp_course_monday, temp_course_tuesday, temp_course_wednesday, temp_course_thursday, temp_course_friday);
                PlaceholderName_Main.course_list_add(temp);
                course_panel.refresh_list();
            }
            catch (Exception ex) {
                System.err.println("Invalid course data: " + ex);
            }
            finally {
                setVisible(false);
                dispose();
            }
        }
    }

    class cancel_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
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

class edit_course_frame extends course_frame {
    private int index;
    public edit_course_frame(int i) {
        index = i;

        done_button.removeActionListener(dbl);
        done_button.addActionListener(new done_button_listener());

        // populate fields with existing data
        name_field.setText(PlaceholderName_Main.course_list_get_at(index).get_name());
        department_field.setText(PlaceholderName_Main.course_list_get_at(index).get_department());
        number_field.setText(PlaceholderName_Main.course_list_get_at(index).get_number());
        section_field.setText(PlaceholderName_Main.course_list_get_at(index).get_section());
        start_time_field.setText(PlaceholderName_Main.course_list_get_at(index).get_start_time().toString());
        end_time_field.setText(PlaceholderName_Main.course_list_get_at(index).get_end_time().toString());

        monday_checkbox.setSelected(PlaceholderName_Main.course_list_get_at(index).get_monday());
        tuesday_checkbox.setSelected(PlaceholderName_Main.course_list_get_at(index).get_tuesday());
        wednesday_checkbox.setSelected(PlaceholderName_Main.course_list_get_at(index).get_wednesday());
        thursday_checkbox.setSelected(PlaceholderName_Main.course_list_get_at(index).get_thursday());
        friday_checkbox.setSelected(PlaceholderName_Main.course_list_get_at(index).get_friday());
    }

    class done_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PlaceholderName_Main.course_list_get_at(index).set_name(name_field.getText());
            PlaceholderName_Main.course_list_get_at(index).set_department(department_field.getText());
            PlaceholderName_Main.course_list_get_at(index).set_number(number_field.getText());
            PlaceholderName_Main.course_list_get_at(index).set_section(section_field.getText());
            PlaceholderName_Main.course_list_get_at(index).set_start_time(start_time_field.getText());
            PlaceholderName_Main.course_list_get_at(index).set_end_time(end_time_field.getText());
            PlaceholderName_Main.course_list_get_at(index).set_monday(true ? monday_checkbox.isSelected() : false);
            PlaceholderName_Main.course_list_get_at(index).set_tuesday(true ? tuesday_checkbox.isSelected() : false);
            PlaceholderName_Main.course_list_get_at(index).set_wednesday(true ? wednesday_checkbox.isSelected() : false);
            PlaceholderName_Main.course_list_get_at(index).set_thursday(true ? thursday_checkbox.isSelected() : false);
            PlaceholderName_Main.course_list_get_at(index).set_friday(true ? friday_checkbox.isSelected() : false);
            course_panel.refresh_list(index);
            setVisible(false);
            dispose();
        }
    }
}

class add_course_notes_frame extends JFrame {
    private int index;

    private JTextArea input_notes = new JTextArea(8, 80);

    // ----------------------
    // buttons
    // ----------------------
    private JButton done_button = new JButton("Done");
    private JButton cancel_button = new JButton("Cancel");
    private done_button_listener dbl = new done_button_listener();
    private cancel_button_listener cbl = new cancel_button_listener();

    public add_course_notes_frame(int i) {
        index = i;

        // overall frame panes
        JPanel text_pane = new JPanel();
        text_pane.setLayout(new BoxLayout(text_pane, BoxLayout.LINE_AXIS));
        text_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel button_pane = new JPanel();
        button_pane.setLayout(new BoxLayout(button_pane, BoxLayout.LINE_AXIS));
        button_pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // input text
        input_notes.setLineWrap(true);
        input_notes.setWrapStyleWord(true);
        input_notes.setText(PlaceholderName_Main.course_list_get_at(index).get_notes());
        input_notes.setCaretPosition(0);
        JScrollPane notes_scroller = new JScrollPane(input_notes);
        text_pane.add(notes_scroller);

        // setup and add buttons
        done_button.addActionListener(dbl);
        cancel_button.addActionListener(cbl);
        button_pane.add(Box.createHorizontalGlue());
        button_pane.add(cancel_button);
        button_pane.add(Box.createRigidArea(new Dimension(10, 0)));
        button_pane.add(done_button);

        // setup frame
        Container content_pane = getContentPane();
        content_pane.add(text_pane, BorderLayout.CENTER);
        content_pane.add(button_pane, BorderLayout.PAGE_END);
    }

    class done_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PlaceholderName_Main.course_list_get_at(index).set_notes(input_notes.getText());
            course_panel.refresh_list(index);
            setVisible(false);
            dispose();
        }
    }

    class cancel_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
    }
}

class add_bill_notes_frame extends JFrame {
    private int index;

    private JTextArea input_notes = new JTextArea(8, 80);

    // ----------------------
    // buttons
    // ----------------------
    private JButton done_button = new JButton("Done");
    private JButton cancel_button = new JButton("Cancel");
    private done_button_listener dbl = new done_button_listener();
    private cancel_button_listener cbl = new cancel_button_listener();

    public add_bill_notes_frame(int i) {
        index = i;

        // overall frame panes
        JPanel text_pane = new JPanel();
        text_pane.setLayout(new BoxLayout(text_pane, BoxLayout.LINE_AXIS));
        text_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel button_pane = new JPanel();
        button_pane.setLayout(new BoxLayout(button_pane, BoxLayout.LINE_AXIS));
        button_pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // input text
        input_notes.setLineWrap(true);
        input_notes.setWrapStyleWord(true);
        input_notes.setText(PlaceholderName_Main.bill_list_get_at(index).get_notes());
        input_notes.setCaretPosition(0);
        JScrollPane notes_scroller = new JScrollPane(input_notes);
        text_pane.add(notes_scroller);

        // setup and add buttons
        done_button.addActionListener(dbl);
        cancel_button.addActionListener(cbl);
        button_pane.add(Box.createHorizontalGlue());
        button_pane.add(cancel_button);
        button_pane.add(Box.createRigidArea(new Dimension(10, 0)));
        button_pane.add(done_button);

        // setup frame
        Container content_pane = getContentPane();
        content_pane.add(text_pane, BorderLayout.CENTER);
        content_pane.add(button_pane, BorderLayout.PAGE_END);
    }

    class done_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PlaceholderName_Main.bill_list_get_at(index).set_notes(input_notes.getText());
            bill_panel.refresh_list(index);
            setVisible(false);
            dispose();
        }
    }

    class cancel_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
        }
    }
}

