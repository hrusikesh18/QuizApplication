package quizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Result implements ActionListener {
    JFrame frame;

    public Result(int cid) {
        // Fetch candidate details (candidate class used)
        CandidateInfo candidateInfo = CandidateInfo.fetchCandidateDetails(cid);

        // Create JFrame to display the result
        frame = new JFrame("Quiz Result");
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

        JLabel resultLabel = new JLabel("RESULT");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 50));
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(resultLabel, BorderLayout.CENTER);

        frame.add(headerPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name: " + candidateInfo.getName());
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(nameLabel, gbc);

        JLabel emailLabel = new JLabel("Email: " + candidateInfo.getEmail());
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(emailLabel, gbc);

        JLabel rollNoLabel = new JLabel("Roll No: " + candidateInfo.getRollNo());
        rollNoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        rollNoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(rollNoLabel, gbc);

        JLabel correctLabel = new JLabel("Correct Answers: " + candidateInfo.getCorrect());
        correctLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        correctLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(correctLabel, gbc);

        // Create a button panel for the close button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton closeButton = createButton("Close");
        buttonPanel.add(closeButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        contentPanel.add(buttonPanel, gbc);

        frame.add(contentPanel, BorderLayout.CENTER);

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

        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        if (command.equals("Close")) {
            frame.dispose();
        }
    }

//    public static void main(String[] args) {
//        new Result(1);
//    }
}
