package quizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class Dashboard implements ActionListener {
    JFrame frame;
    JLabel lTitle, lUserName, lRollNo, lEmail, lWelcome;
    JButton btnStartQuiz, btnShowResults, btnHome;
    String userName, rollNo, email;
    CandidateInfo candi;

    Dashboard(String userName) {
        this.userName = userName;
        candi=CandidateInfo.fetchCandidateDetails(CandidateInfo.getCid(userName));

        frame = new JFrame("Dashboard");
        frame.setBounds(0, 0, 1540, 820);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(70, 130, 180)));

        // Set background color or image
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(800, 100));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));

        lTitle = new JLabel("User Dashboard");
        lTitle.setFont(new Font("Arial", Font.BOLD, 36));
        lTitle.setForeground(Color.WHITE);
        lTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lTitle, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        lWelcome = new JLabel("Welcome, " + userName + "!");
        lWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(lWelcome, gbc);

        lUserName = new JLabel("Username: " + userName);
        lUserName.setFont(new Font("Arial", Font.PLAIN, 20));
        lUserName.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        contentPanel.add(lUserName, gbc);

        lRollNo = new JLabel("Roll No: " + candi.rollNo);
        lRollNo.setFont(new Font("Arial", Font.PLAIN, 20));
        lRollNo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 2;
        contentPanel.add(lRollNo, gbc);

        lEmail = new JLabel("Email: " + candi.email);
        lEmail.setFont(new Font("Arial", Font.PLAIN, 20));
        lEmail.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 3;
        contentPanel.add(lEmail, gbc);

        btnStartQuiz = createButton("Start Quiz");
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        contentPanel.add(btnStartQuiz, gbc);

        btnShowResults = createButton("Show Results");
        gbc.gridx = 1;
        contentPanel.add(btnShowResults, gbc);

        btnHome = createButton("Logout");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        contentPanel.add(btnHome, gbc);

        frame.add(contentPanel, BorderLayout.CENTER);

        // Add action listeners
        btnStartQuiz.addActionListener(this);
        btnShowResults.addActionListener(this);
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
        } else if (ae.getSource().equals(btnStartQuiz)) {
            int numberOfQuestions = getNumberOfQuestionsFromDatabase();
            frame.dispose();
            new QuestionFrame(numberOfQuestions, numberOfQuestions, candi.cid);
        } else if (ae.getSource().equals(btnShowResults)) {
            frame.dispose();
            new Result(candi.cid);
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
}
