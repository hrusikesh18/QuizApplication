package quizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage implements ActionListener {

    JFrame frame;
    JLabel lHome, lLoginPrompt, lUsername, lPassword, ltailmsg;
    JTextField tfUsername;
    JPasswordField pfPassword;
    JButton btnLogin, btnSignUp;

    HomePage() {
        frame = new JFrame("Home Page");
        frame.setBounds(0, 0, 1540, 820);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the background color or image
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new BorderLayout());
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(70, 130, 180)));

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(800, 100));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));

        lHome = new JLabel("QUIZ APP");
        lHome.setFont(new Font("Arial", Font.BOLD, 50));
        lHome.setForeground(Color.WHITE);
        lHome.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lHome, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lLoginPrompt = new JLabel("Please enter Login details.");
        lLoginPrompt.setFont(new Font("Arial", Font.PLAIN, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        lLoginPrompt.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(lLoginPrompt, gbc);

        lUsername = new JLabel("Username: ");
        lUsername.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(lUsername, gbc);

        tfUsername = new JTextField(20);
        tfUsername.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        contentPanel.add(tfUsername, gbc);

        lPassword = new JLabel("Password: ");
        lPassword.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        contentPanel.add(lPassword, gbc);

        pfPassword = new JPasswordField(20);
        pfPassword.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        contentPanel.add(pfPassword, gbc);

        btnLogin = createButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        contentPanel.add(btnLogin, gbc);

        btnSignUp = createButton("Sign Up");
        gbc.gridx = 1;
        gbc.gridy = 3;
        contentPanel.add(btnSignUp, gbc);

        frame.add(contentPanel, BorderLayout.CENTER);

        // Tail message
        ltailmsg = new JLabel("@project for TA Silicon University");
        ltailmsg.setFont(new Font("Arial", Font.PLAIN, 18));
        ltailmsg.setForeground(Color.BLACK);
        ltailmsg.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(ltailmsg, BorderLayout.SOUTH);

        // Add action listeners
        btnLogin.addActionListener(this);
        btnSignUp.addActionListener(this);

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
        if (ae.getSource().equals(btnLogin)) {
            String username = tfUsername.getText();
            String password = new String(pfPassword.getPassword());
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Username and Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (authenticateAdmin(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose();
                    new AdminDashboard(username); // Replace with actual user details
                } else if (authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Login successful!");
                    frame.dispose();
                    new Dashboard(username);
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else if (ae.getSource().equals(btnSignUp)) {
            new QuizStart();
        }
    }

    private boolean authenticateAdmin(String username, String password) {
        return username.equals("Hrusikesh") && password.equals("12345");
    }
    private boolean authenticateUser(String username, String password) {
        CandidateInfo valid=CandidateInfo.fetchCandidateDetails(CandidateInfo.getCid(username));
        return username.equals(valid.getName()) && password.equals(valid.getPassword());
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
