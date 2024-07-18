package quizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

class AdminDashboard implements ActionListener {

    JFrame frame;
    JLabel lTitle, lWelcome;
    JButton btnAddQuiz, btnViewAllResults, btnViewIndividualResult, btnLogout;

    AdminDashboard(String adminUsername) {
        frame = new JFrame("Admin Dashboard");
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

        lTitle = new JLabel("Admin Dashboard");
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

        lWelcome = new JLabel("Welcome, " + adminUsername);
        lWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(lWelcome, gbc);

        btnAddQuiz = createButton("Add Quiz", "icons/add.png");
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        contentPanel.add(btnAddQuiz, gbc);

        btnViewAllResults = createButton("View All Results", "icons/results.png");
        gbc.gridy = 2;
        contentPanel.add(btnViewAllResults, gbc);

        btnViewIndividualResult = createButton("Individual Result", "icons/individual.png");
        gbc.gridy = 3;
        contentPanel.add(btnViewIndividualResult, gbc);

        btnLogout = createButton("Logout", "icons/logout.png");
        gbc.gridy = 4;
        contentPanel.add(btnLogout, gbc);

        frame.add(contentPanel, BorderLayout.CENTER);

        // Add action listeners
        btnAddQuiz.addActionListener(this);
        btnViewAllResults.addActionListener(this);
        btnViewIndividualResult.addActionListener(this);
        btnLogout.addActionListener(this);

        frame.setVisible(true);
    }

    private JButton createButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setIcon(new ImageIcon(iconPath));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
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
        if (ae.getSource().equals(btnAddQuiz)) {
            frame.dispose();
            new AddQuiz();
        } else if (ae.getSource().equals(btnViewAllResults)) {
            viewAllResults();
        } else if (ae.getSource().equals(btnViewIndividualResult)) {
            String studentRollNo = JOptionPane.showInputDialog(frame, "Enter student's name:");
            if (studentRollNo != null && !studentRollNo.isEmpty()) {
                viewIndividualResult(studentRollNo);
            }
        } else if (ae.getSource().equals(btnLogout)) {
            frame.dispose();
            new HomePage();
        }
    }

    private void viewAllResults() {
        JFrame resultsFrame = new JFrame("All Student Results");
        resultsFrame.setBounds(0, 0, 1540, 820);
        resultsFrame.setLayout(new BorderLayout());

        String[] columnNames = {"Roll No", "Name", "Score"};
        Object[][] data = getAllResultsFromDatabase();

        JTable table = new JTable(data, columnNames);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);

        resultsFrame.add(scrollPane, BorderLayout.CENTER);
        resultsFrame.setVisible(true);
    }

    private Object[][] getAllResultsFromDatabase() {
        Object[][] data = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = stmt.executeQuery("SELECT roll, name, correct FROM candidate");

            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            data = new Object[rowCount][3];
            int i = 0;
            while (resultSet.next()) {
                data[i][0] = resultSet.getString("roll");
                data[i][1] = resultSet.getString("name");
                data[i][2] = resultSet.getInt("correct");
                i++;
            }

            resultSet.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(frame, "Error fetching results. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return data;
    }

    private void viewIndividualResult(String name) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345");
            String query = "SELECT * FROM candidate WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String resultMessage = "Roll No: " + resultSet.getString("roll") + "\n"
                        +"Name: " + resultSet.getString("name")+ "\n"
                        +"Score: " + resultSet.getInt("correct")+ "\n";
                JOptionPane.showMessageDialog(frame, resultMessage, "Individual Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "No result found for username: " + name, "Error", JOptionPane.ERROR_MESSAGE);
            }

            resultSet.close();
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(frame, "Error fetching result. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Uncomment the main method if you want to run this class directly
//    public static void main(String[] args) {
//        new AdminDashboard("Admin");
//    }
}
