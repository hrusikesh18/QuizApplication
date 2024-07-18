package quizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class AddQuiz implements ActionListener {
    JFrame frame;
    JLabel lqid, lquestion, lop1, lop2, lop3, lop4, lans, heading;
    JTextField tfqid, tfques, tfop1, tfop2, tfop3, tfop4, tfans;
    JButton addbtn, btnHome;

    AddQuiz() {
        frame = new JFrame("Add Quiz");
        frame.setBounds(0, 0, 1540, 820);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(70, 130, 180)));

        // Set background color or image
        frame.getContentPane().setBackground(Color.WHITE);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(800, 100));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));

        heading = new JLabel("Add Quiz");
        heading.setFont(new Font("Arial", Font.BOLD, 36));
        heading.setForeground(Color.WHITE);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(heading, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int number =getNumberOfQuestionsFromDatabase()+2;
        // Adding input fields
        gbc.gridy = 0;
        contentPanel.add(createPanel("Enter the Question ID:", tfqid = new JTextField(String.valueOf(number))), gbc);
        tfqid.setEditable(false);
        gbc.gridy = 1;
        contentPanel.add(createPanel("Enter the Question:", tfques = new JTextField(50)), gbc);
        gbc.gridy = 2;
        contentPanel.add(createPanel("Option 1:", tfop1 = new JTextField(20)), gbc);
        gbc.gridy = 3;
        contentPanel.add(createPanel("Option 2:", tfop2 = new JTextField(20)), gbc);
        gbc.gridy = 4;
        contentPanel.add(createPanel("Option 3:", tfop3 = new JTextField(20)), gbc);
        gbc.gridy = 5;
        contentPanel.add(createPanel("Option 4:", tfop4 = new JTextField(20)), gbc);
        gbc.gridy = 6;
        contentPanel.add(createPanel("Answer:", tfans = new JTextField(20)), gbc);

        // Buttons panel
        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(Color.WHITE);
        panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        btnHome = createButton("HOME");
        addbtn = createButton("ADD");

        panelButtons.add(btnHome);
        panelButtons.add(addbtn);

        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        contentPanel.add(panelButtons, gbc);

        frame.add(contentPanel, BorderLayout.CENTER);

        // Add action listeners
        btnHome.addActionListener(this);
        addbtn.addActionListener(this);

        frame.setVisible(true);
    }

    private JPanel createPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        panel.add(label);

        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(textField);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Set rounded corners
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(70, 130, 180), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });

        return button;
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource().equals(btnHome)) {
            frame.dispose();
            new AdminDashboard("Hrusikesh");
        } else if (ae.getSource().equals(addbtn)) {
            if (tfqid.getText().isEmpty() || tfques.getText().isEmpty() || tfop1.getText().isEmpty() || tfop2.getText().isEmpty() || tfop3.getText().isEmpty() || tfop4.getText().isEmpty() || tfans.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled out!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int qid = Integer.parseInt(tfqid.getText());
            String question = tfques.getText();
            String op1 = tfop1.getText();
            String op2 = tfop2.getText();
            String op3 = tfop3.getText();
            String op4 = tfop4.getText();
            String ans = tfans.getText();

            try {
                Class.forName("oracle.jdbc.driver.OracleDriver");
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
                if (con != null) {
                    System.out.println("Connected...");
                }
                String qry = "INSERT INTO Question values(" + qid + ",'" + question + "','" + op1 + "','" + op2 + "','" + op3 + "','" + op4 + "','" + ans + "')";
                Statement smt = con.createStatement();
                int i = smt.executeUpdate(qry);
                JOptionPane.showMessageDialog(frame, i + " Question Inserted..");

                smt.close();
                con.close();
            } catch (SQLException se) {
                System.out.println(se);
            } catch (ClassNotFoundException ce) {
                System.out.println(ce);
            } catch (Exception e) {
                System.out.println(e);
            }
            clear();
        }
    }
    private int getNumberOfQuestionsFromDatabase() {
        int numberOfQuestions = 0;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM question");

            if (resultSet.next()) {
                numberOfQuestions = resultSet.getInt(1);
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ce) {
            System.out.println(ce);
        }
        return numberOfQuestions;
    }

    public void clear() {
        tfop1.setText("");
        tfop2.setText("");
        tfop3.setText("");
        tfop4.setText("");
        tfans.setText("");
        tfques.setText("");
        tfqid.setText(String.valueOf(getNumberOfQuestionsFromDatabase()+2));
    }
}
