package quizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class QuizStart implements ActionListener {
    JFrame frame;
    JLabel lQuiz, lUserName, lRollNo, lEmail, lPassword;
    JTextField tfUserName, tfRollNo, tfEmail;
    JPasswordField pfPassword;
    JButton btStart, btnHome;

    int countcandidate;

    QuizStart() {
        countcandidate = setCountcandidate();
        System.out.println(countcandidate);
        frame = new JFrame("Quiz Start");
        frame.setBounds(0, 0, 1540, 820);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set background color or image
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(70, 130, 180)));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(800, 100));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));

        lQuiz = new JLabel("User Details");
        lQuiz.setFont(new Font("Arial", Font.BOLD, 36));
        lQuiz.setForeground(Color.WHITE);
        lQuiz.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lQuiz, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lUserName = new JLabel("Username:");
        lUserName.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(lUserName, gbc);

        tfUserName = new JTextField();
        tfUserName.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across 2 columns
        tfUserName.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        contentPanel.add(tfUserName, gbc);

        lRollNo = new JLabel("Roll No:");
        lRollNo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // Reset grid width
        contentPanel.add(lRollNo, gbc);

        tfRollNo = new JTextField();
        tfRollNo.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 1;
        tfRollNo.setPreferredSize(new Dimension(150, 30)); // Set preferred size
        contentPanel.add(tfRollNo, gbc);

        lEmail = new JLabel("Email:");
        lEmail.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        contentPanel.add(lEmail, gbc);

        tfEmail = new JTextField();
        tfEmail.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across 2 columns
        tfEmail.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        contentPanel.add(tfEmail, gbc);

        lPassword = new JLabel("Password:");
        lPassword.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1; // Reset grid width
        contentPanel.add(lPassword, gbc);

        pfPassword = new JPasswordField();
        pfPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Span across 2 columns
        pfPassword.setPreferredSize(new Dimension(300, 30)); // Set preferred size
        contentPanel.add(pfPassword, gbc);

        btStart = createButton("Create");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1; // Reset grid width
        contentPanel.add(btStart, gbc);

        btnHome = createButton("Home");
        gbc.gridx = 1;
        gbc.gridy = 4;
        contentPanel.add(btnHome, gbc);

        frame.add(contentPanel, BorderLayout.CENTER);

        // Add action listeners
        btStart.addActionListener(this);
        btnHome.addActionListener(this);

        frame.setVisible(true);
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
            new HomePage();
        } else if (ae.getSource().equals(btStart)) {
            if (validateFields()) {
                createUser();
                frame.dispose();
                new Dashboard(tfUserName.getText());
            }
        }
    }

    private boolean validateFields() {
        if (tfUserName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter username");
            tfUserName.setText("");
            return false;
        }
        if (tfRollNo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter roll no");
            tfRollNo.setText("");
            return false;
        }
        if (tfEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter email");
            tfEmail.setText("");
            return false;
        }
        if (new String(pfPassword.getPassword()).isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter password");
            pfPassword.setText("");
            return false;
        }
        return true;
    }

    private void createUser() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO candidate (cid, name, roll, email, correct, password) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, setCountcandidate() + 1);
            stmt.setString(2, tfUserName.getText());
            stmt.setInt(3, Integer.parseInt(tfRollNo.getText()));
            stmt.setString(4, tfEmail.getText());
            stmt.setInt(5, 0);
            stmt.setString(6, new String(pfPassword.getPassword()));
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ce) {
            JOptionPane.showMessageDialog(frame, "Error: " + ce.getMessage());
            System.out.println(ce);
        }
    }

    public int setCountcandidate() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM candidate");

            if (resultSet.next()) {
                countcandidate = resultSet.getInt(1);
            }
            resultSet.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException ce) {
            System.out.println(ce);
        }
        return countcandidate;
    }

//    public static void main(String[] args) {
//        new QuizStart();
//    }
}
