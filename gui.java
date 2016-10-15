import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.Date;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;

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
    public static DefaultListModel list_model = new DefaultListModel();
    private static JList course_list;
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
        course_list = new JList(list_model);
        course_list.addListSelectionListener(lsl);
        course_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        course_list.setLayoutOrientation(JList.VERTICAL);
        course_list.setVisibleRowCount(-1);
        JScrollPane list_scroller = new JScrollPane(course_list);

        // // course info labels
        selected_course_notes.setEditable(false);
        JScrollPane notes_scroller = new JScrollPane(selected_course_notes);
        Border notes_scroller_border = BorderFactory.createTitledBorder("Notes");
        notes_scroller.setBorder(notes_scroller_border);

        // buttons
        add_course_button.addActionListener(ccbl);
        remove_course_button.addActionListener(rcbl);
        edit_course_button.addActionListener(ecbl);
        add_course_notes_button.addActionListener(acnbl);

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
        c.fill = GridBagConstraints.HORIZONTAL;
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
        c.gridwidth = 1;
        this.add(remove_course_button, c);

        c.gridx = 1;
        c.gridy = 4;
        c.weightx = 0;
        this.add(add_course_button, c);

        c.gridx = 2;
        c.gridy = 4;
        this.add(edit_course_button, c);

        c.gridx = 3;
        c.gridy = 4;
        this.add(add_course_notes_button, c);
    }

    public static void refresh_list() {
        list_model.clear();
        for (int i = 0; i < PlaceholderName_Main.course_list_size(); i++) {
            list_model.addElement(PlaceholderName_Main.get_course_at(i).get_department_and_number());
        }
    }

    public static void refresh_list(int index) {
        list_model.clear();
        for (int i = 0; i < PlaceholderName_Main.course_list_size(); i++) {
            list_model.addElement(PlaceholderName_Main.get_course_at(i).get_department_and_number());
        }
        course_list.setSelectedIndex(index);
    }

    class list_selection_listener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (! course_list.isSelectionEmpty()) {
                course selected_course = PlaceholderName_Main.get_course_at(course_list.getSelectedIndex());

                selected_course_name.setText(selected_course.get_name());
                selected_course_department.setText(selected_course.get_department());
                selected_course_number.setText(selected_course.get_number());
                selected_course_section.setText(selected_course.get_section());
                selected_course_notes.setText(selected_course.get_notes());
                selected_course_start_time.setText(selected_course.get_start_time().toString());
                selected_course_end_time.setText(selected_course.get_end_time().toString());
            }
            else {
                selected_course_name.setText("");
                selected_course_department.setText("");
                selected_course_number.setText("");
                selected_course_section.setText("");
                selected_course_notes.setText("");
                selected_course_start_time.setText("");
                selected_course_end_time.setText("");
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

    class create_course_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            add_course_frame acf = new add_course_frame();
            acf.pack();
            acf.setVisible(true);
        }
    }

    class remove_course_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (! course_list.isSelectionEmpty()) {
                PlaceholderName_Main.remove_course_from_list(course_list.getSelectedIndex());
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
    private DefaultListModel list_model;
    private JList course_list;
    private JLabel temp = new JLabel("temp placeholder for bills pane");
    public bill_panel() {
        this.setLayout(new FlowLayout());
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.add(temp);

    }
}

class add_course_frame extends JFrame {
    // ----------------------
    // input text fields
    // ----------------------
    private JTextField name_field = new JTextField(10);
    private JTextField department_field = new JTextField(10);
    private JFormattedTextField number_field = new JFormattedTextField(create_formatter("###"));
    private JFormattedTextField section_field = new JFormattedTextField(create_formatter("##"));
    private JFormattedTextField start_time_field = new JFormattedTextField(create_formatter("##:##"));
    private JFormattedTextField end_time_field = new JFormattedTextField(create_formatter("##:##"));

    // ----------------------
    // labels
    // ----------------------
    private JLabel name_label = new JLabel("Name: ");
    private JLabel department_label = new JLabel("Department: ");
    private JLabel number_label = new JLabel("Number: ");
    private JLabel section_label = new JLabel("Section: ");
    private JLabel start_time_label = new JLabel("Start Time: ");
    private JLabel end_time_label = new JLabel("End Time: ");

    // ----------------------
    // buttons
    // ----------------------
    private JButton create_button = new JButton("Create");
    private JButton cancel_button = new JButton("Cancel");
    private create_button_listener cbl = new create_button_listener();
    private cancel_button_listener cabl = new cancel_button_listener();

    public add_course_frame() {
        // overall frame panes
        JPanel label_pane = new JPanel();
        label_pane.setLayout(new GridLayout(6, 1, 6, 6));
        label_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel input_pane = new JPanel();
        input_pane.setLayout(new GridLayout(6, 1, 6, 6));
        input_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel button_pane = new JPanel();
        button_pane.setLayout(new BoxLayout(button_pane, BoxLayout.LINE_AXIS));
        button_pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // setup and add input text fields
        section_field.setFocusLostBehavior(JFormattedTextField.PERSIST);
        start_time_field.setFocusLostBehavior(JFormattedTextField.PERSIST);
        end_time_field.setFocusLostBehavior(JFormattedTextField.PERSIST);

        input_pane.add(name_field);
        input_pane.add(department_field);
        input_pane.add(number_field);
        input_pane.add(section_field);
        input_pane.add(start_time_field);
        input_pane.add(end_time_field);

        // add labels
        label_pane.add(name_label);
        label_pane.add(department_label);
        label_pane.add(number_label);
        label_pane.add(section_label);
        label_pane.add(start_time_label);
        label_pane.add(end_time_label);

        // setup and add buttons
        create_button.addActionListener(cbl);
        cancel_button.addActionListener(cabl);
        button_pane.add(Box.createHorizontalGlue());
        button_pane.add(cancel_button);
        button_pane.add(Box.createRigidArea(new Dimension(10, 0)));
        button_pane.add(create_button);

        // setup frame
        Container content_pane = getContentPane();
        content_pane.add(label_pane, BorderLayout.LINE_START);
        content_pane.add(input_pane, BorderLayout.LINE_END);
        content_pane.add(button_pane, BorderLayout.PAGE_END);
    }

    class create_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            course temp = new course(name_field.getText(), department_field.getText(), number_field.getText(), section_field.getText(), start_time_field.getText(), end_time_field.getText());
            PlaceholderName_Main.add_course_to_list(temp);
            course_panel.refresh_list();
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

class edit_course_frame extends JFrame {
    // ----------------------
    // input text fields
    // ----------------------
    private JTextField name_field = new JTextField(10);
    private JTextField department_field = new JTextField(10);
    private JFormattedTextField number_field = new JFormattedTextField(create_formatter("###"));
    private JFormattedTextField section_field = new JFormattedTextField(create_formatter("##"));
    private JFormattedTextField start_time_field = new JFormattedTextField(create_formatter("##:##"));
    private JFormattedTextField end_time_field = new JFormattedTextField(create_formatter("##:##"));

    // ----------------------
    // labels
    // ----------------------
    private JLabel name_label = new JLabel("Name: ");
    private JLabel department_label = new JLabel("Department: ");
    private JLabel number_label = new JLabel("Number: ");
    private JLabel section_label = new JLabel("Section: ");
    private JLabel start_time_label = new JLabel("Start Time: ");
    private JLabel end_time_label = new JLabel("End Time: ");

    // ----------------------
    // buttons
    // ----------------------
    private JButton done_button = new JButton("Done");
    private JButton cancel_button = new JButton("Cancel");
    private done_button_listener dbl = new done_button_listener();
    private cancel_button_listener cbl = new cancel_button_listener();

    private int index;

    public edit_course_frame(int i) {
        index = i;

        // overall frame panes
        JPanel label_pane = new JPanel();
        label_pane.setLayout(new GridLayout(6, 1, 6, 6));
        label_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel input_pane = new JPanel();
        input_pane.setLayout(new GridLayout(6, 1, 6, 6));
        input_pane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel button_pane = new JPanel();
        button_pane.setLayout(new BoxLayout(button_pane, BoxLayout.LINE_AXIS));
        button_pane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        // setup and add input text fields
        section_field.setFocusLostBehavior(JFormattedTextField.PERSIST);
        start_time_field.setFocusLostBehavior(JFormattedTextField.PERSIST);
        end_time_field.setFocusLostBehavior(JFormattedTextField.PERSIST);

        // populate fields with existing data
        name_field.setText(PlaceholderName_Main.get_course_at(index).get_name());
        department_field.setText(PlaceholderName_Main.get_course_at(index).get_department());
        number_field.setText(PlaceholderName_Main.get_course_at(index).get_number());
        section_field.setText(PlaceholderName_Main.get_course_at(index).get_section());
        start_time_field.setText(PlaceholderName_Main.get_course_at(index).get_start_time().toString());
        end_time_field.setText(PlaceholderName_Main.get_course_at(index).get_end_time().toString());

        input_pane.add(name_field);
        input_pane.add(department_field);
        input_pane.add(number_field);
        input_pane.add(section_field);
        input_pane.add(start_time_field);
        input_pane.add(end_time_field);

        // add labels
        label_pane.add(name_label);
        label_pane.add(department_label);
        label_pane.add(number_label);
        label_pane.add(section_label);
        label_pane.add(start_time_label);
        label_pane.add(end_time_label);

        // setup and add buttons
        done_button.addActionListener(dbl);
        cancel_button.addActionListener(cbl);
        button_pane.add(Box.createHorizontalGlue());
        button_pane.add(cancel_button);
        button_pane.add(Box.createRigidArea(new Dimension(10, 0)));
        button_pane.add(done_button);

        // setup frame
        Container content_pane = getContentPane();
        content_pane.add(label_pane, BorderLayout.LINE_START);
        content_pane.add(input_pane, BorderLayout.LINE_END);
        content_pane.add(button_pane, BorderLayout.PAGE_END);
    }

    class done_button_listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PlaceholderName_Main.get_course_at(index).set_name(name_field.getText());
            PlaceholderName_Main.get_course_at(index).set_department(department_field.getText());
            PlaceholderName_Main.get_course_at(index).set_number(number_field.getText());
            PlaceholderName_Main.get_course_at(index).set_section(section_field.getText());
            PlaceholderName_Main.get_course_at(index).set_start_time(start_time_field.getText());
            PlaceholderName_Main.get_course_at(index).set_end_time(end_time_field.getText());
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
        input_notes.setText(PlaceholderName_Main.get_course_at(index).get_notes());
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
            PlaceholderName_Main.get_course_at(index).set_notes(input_notes.getText());
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
