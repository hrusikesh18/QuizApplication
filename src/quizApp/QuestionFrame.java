package quizApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class QuestionFrame implements ActionListener {
    JFrame frame;
    JLabel questionLabel,questionNumberLabel,attemptedLabel,notAttemptedLabel;
    JPanel centerPanel,northPanel,westPanel,eastPanel,southPanel;
    JRadioButton option1,option2,option3,option4;
    ButtonGroup optionGroup;
    int cid,numberOfQuestion,currentQuestionNumber = 1,attemptedQuestions = 0,notAttemptedQuestions,correctAnswer=0;
    JButton[] questionButtons;
    int[] attempted;
    private String[] givenAnswers;
    private boolean[] isAttempted;

    Question[] questions;
    CandidateInfo candidateInfos;
    public QuestionFrame(int numberOfQuestion, int time,int cid) {
        //Initialization of all the important part
        this.cid=cid;
        this.numberOfQuestion = numberOfQuestion;
        notAttemptedQuestions = numberOfQuestion;
        attempted = new int[numberOfQuestion];
        givenAnswers = new String[numberOfQuestion];
        isAttempted = new boolean[numberOfQuestion];
        questions = Question.fetchQuestions(numberOfQuestion);
        candidateInfos=CandidateInfo.fetchCandidateDetails(cid);

        frame = new JFrame("Quiz Interface");
        frame.setBounds(0, 0, 1540, 820);


        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        northPanel = new JPanel();
        southPanel = new JPanel();
        eastPanel = new JPanel();
        westPanel = new JPanel();
        centerPanel = new JPanel();


        northPanel.setBackground(new Color(70, 130, 180));
        southPanel.setBackground(Color.WHITE);
        eastPanel.setBackground(Color.WHITE);
        westPanel.setBackground(Color.WHITE);
        centerPanel.setBackground(Color.WHITE);


        northPanel.setPreferredSize(new Dimension(0, 100));
        southPanel.setPreferredSize(new Dimension(0, 50));
        eastPanel.setPreferredSize(new Dimension(310, 0));
        westPanel.setPreferredSize(new Dimension(300, 0));


        //north panel
        northPanel.setLayout(new BorderLayout());


        JLabel quizNameLabel = new JLabel("General Knowledge Quiz");
        quizNameLabel.setForeground(Color.WHITE);
        quizNameLabel.setFont(new Font("Arial", Font.BOLD, 50));
        quizNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        quizNameLabel.setVerticalAlignment(SwingConstants.CENTER);
        northPanel.add(quizNameLabel, BorderLayout.CENTER);


        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        northPanel.add(separator, BorderLayout.SOUTH);


        JLabel timerLabel = new JLabel("00:00");
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        northPanel.add(timerLabel, BorderLayout.SOUTH);

        // Create and start a timer to update the timer label every second
        Timer timer = new Timer(1000, new ActionListener() {
            int remainingSeconds = time * 60;

            @Override
            public void actionPerformed(ActionEvent e) {
                remainingSeconds--;
                if (remainingSeconds < 0) {
                    JOptionPane.showMessageDialog(frame, "Quiz submitted successfully!");
                    candidateInfos.setCorrect(correctAnswer,cid);
                    frame.dispose();
                    new Result(cid);
                    return;
                }
                int minutes = remainingSeconds / 60;
                int seconds = remainingSeconds % 60;
                String timeString = String.format("%02d:%02d", minutes, seconds);
                timerLabel.setText(timeString);
            }
        });
        timer.start();

        mainPanel.add(northPanel, BorderLayout.NORTH);

        //west panel

        JLabel nameLabel = new JLabel("Name: "+candidateInfos.getName());
        JLabel emailLabel = new JLabel("Email: "+candidateInfos.getEmail());
        JLabel rollNoLabel = new JLabel("Roll No: "+candidateInfos.getRollNo());


        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        rollNoLabel.setFont(new Font("Arial", Font.PLAIN, 18));


        nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        rollNoLabel.setHorizontalAlignment(SwingConstants.LEFT);

        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setBorder(BorderFactory.createEmptyBorder(40, 10, 500, 10));
        westPanel.add(nameLabel);
        westPanel.add(emailLabel);
        westPanel.add(rollNoLabel);

        mainPanel.add(westPanel, BorderLayout.WEST);

        //center panel

        questionLabel = new JLabel(currentQuestionNumber + ": "+questions[currentQuestionNumber-1].getQuestionText());
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        questionLabel.setVerticalAlignment(SwingConstants.CENTER);



        questionLabel.setBorder(BorderFactory.createEmptyBorder(40, 40, 0, 0));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(questionLabel);


        option1 = new JRadioButton("A."+questions[currentQuestionNumber-1].getOptionA());
        option1.setFont(new Font("Arial", Font.PLAIN, 18));
        option1.setBackground(Color.WHITE);
        option2 = new JRadioButton("B."+questions[currentQuestionNumber-1].getOptionB());
        option2.setFont(new Font("Arial", Font.PLAIN, 18));
        option2.setBackground(Color.WHITE);
        option3 = new JRadioButton("C."+questions[currentQuestionNumber-1].getOptionC());
        option3.setFont(new Font("Arial", Font.PLAIN, 18));
        option3.setBackground(Color.WHITE);
        option4 = new JRadioButton("D."+questions[currentQuestionNumber-1].getOptionD());
        option4.setFont(new Font("Arial", Font.PLAIN, 18));
        option4.setBackground(Color.WHITE);


        option1.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 0));
        option2.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 0));
        option3.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 0));
        option4.setBorder(BorderFactory.createEmptyBorder(0, 70, 0, 0));


        optionGroup = new ButtonGroup();
        optionGroup.add(option1);
        optionGroup.add(option2);
        optionGroup.add(option3);
        optionGroup.add(option4);


        centerPanel.add(option1);
        centerPanel.add(option2);
        centerPanel.add(option3);
        centerPanel.add(option4);


        JSeparator leftSeparator = new JSeparator(SwingConstants.VERTICAL);
        JSeparator rightSeparator = new JSeparator(SwingConstants.VERTICAL);

        JPanel centerHolderPanel = new JPanel(new BorderLayout());
        centerHolderPanel.add(leftSeparator, BorderLayout.WEST);
        centerHolderPanel.add(rightSeparator, BorderLayout.EAST);
        centerHolderPanel.add(centerPanel, BorderLayout.CENTER);

        mainPanel.add(centerHolderPanel, BorderLayout.CENTER);

        //east panel
        questionButtons = new JButton[numberOfQuestion];

        JPanel questionButtonsPanel = new JPanel();
        questionButtonsPanel.setLayout(new GridLayout(0, 5, 5, 5));
        for (int i = 0; i < numberOfQuestion; i++) {
            JButton questionButton = new JButton("" + (i + 1) + "");
            questionButton.addActionListener(this);
            questionButtons[i] = questionButton;
            questionButtonsPanel.add(questionButton);
        }


        JScrollPane scrollPaneq = new JScrollPane(questionButtonsPanel);
        scrollPaneq.setPreferredSize(new Dimension(310, 305)); // Set preferred size for scroll pane
        scrollPaneq.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPaneq.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Disable horizontal scroll bar


        JPanel topPanel = new JPanel(new GridLayout(4, 0, 100, 10));
        topPanel.setPreferredSize(new Dimension(395, 100));
        JLabel blankLabel = new JLabel("");
        topPanel.add(blankLabel);
        topPanel.setBackground(Color.WHITE);

        blankLabel = new JLabel("");
        topPanel.add(blankLabel);

        questionNumberLabel = new JLabel("Total Questions: " + numberOfQuestion);
        questionNumberLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        questionNumberLabel.setHorizontalAlignment(SwingConstants.LEFT);
        topPanel.add(questionNumberLabel);


        JPanel bottomPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        bottomPanel.setPreferredSize(new Dimension(300, 150));
        bottomPanel.setBackground(Color.WHITE);
        attemptedLabel = new JLabel("");
        bottomPanel.add(attemptedLabel);

        attemptedLabel = new JLabel("Attempted: " + attemptedQuestions);
        attemptedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        attemptedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        bottomPanel.add(attemptedLabel);

        notAttemptedLabel = new JLabel("Not Attempted: " + notAttemptedQuestions);
        notAttemptedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        notAttemptedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        bottomPanel.add(notAttemptedLabel);

        eastPanel.add(topPanel, BorderLayout.NORTH);
        eastPanel.add(scrollPaneq, BorderLayout.CENTER);
        eastPanel.add(bottomPanel, BorderLayout.SOUTH);


        mainPanel.add(eastPanel, BorderLayout.EAST);

        //south panel
        JButton nextButton = new JButton("Save & Next");
        nextButton.addActionListener(this);
        JButton previousButton = new JButton("Previous");
        previousButton.addActionListener(this);
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        JSeparator southSeparator = new JSeparator(SwingConstants.HORIZONTAL);
        southPanel.setLayout(new BorderLayout());
        southPanel.add(southSeparator, BorderLayout.NORTH);


        JPanel southButtonPanel = new JPanel();
        southButtonPanel.add(previousButton);
        southButtonPanel.add(nextButton);
        southButtonPanel.add(submitButton);

        southPanel.add(southButtonPanel, BorderLayout.CENTER);

        mainPanel.add(southPanel, BorderLayout.SOUTH);



        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        String GivenAnswer = "";
        int direction = 0;

        switch (command) {
            case "Submit":
                JOptionPane.showMessageDialog(frame, "Quiz submitted successfully!");
                candidateInfos.setCorrect(correctAnswer,cid);
                frame.dispose();
                new Result(cid);
                break;
            case "Previous":
                direction = -1;
                if (currentQuestionNumber + direction >= 1 && currentQuestionNumber + direction <= numberOfQuestion) {
                    currentQuestionNumber += direction;
                    updateQuestionDisplay();
                }
                break;
            case "Save & Next":
                if (option1.isSelected()) {
                    GivenAnswer = option1.getText();
                } else if (option2.isSelected()) {
                    GivenAnswer = option2.getText();
                } else if (option3.isSelected()) {
                    GivenAnswer = option3.getText();
                } else if (option4.isSelected()) {
                    GivenAnswer = option4.getText();
                }

                if (!GivenAnswer.isEmpty()) {
                    if (!isAttempted[currentQuestionNumber - 1]) {
                        if (GivenAnswer.substring(2).equals(questions[currentQuestionNumber - 1].getCorrectOption())) {
                            correctAnswer++;
                        }
                        isAttempted[currentQuestionNumber - 1] = true;
                        attemptedQuestions++;
                        notAttemptedQuestions--;
                    } else if (!GivenAnswer.equals(givenAnswers[currentQuestionNumber - 1])) {
                        // update when answer is changed.
                        if (givenAnswers[currentQuestionNumber - 1].equals(questions[currentQuestionNumber - 1].getCorrectOption())) {
                            correctAnswer--;
                        }
                        if (GivenAnswer.equals(questions[currentQuestionNumber - 1].getCorrectOption())) {
                            correctAnswer++;
                        }
                    }
                    givenAnswers[currentQuestionNumber - 1] = GivenAnswer;
                    questionButtons[currentQuestionNumber - 1].setBackground(Color.GREEN);
                    attemptedLabel.setText("Attempted: " + attemptedQuestions);
                    notAttemptedLabel.setText("Not Attempted: " + notAttemptedQuestions);
                }

                direction = 1;
                if (currentQuestionNumber + direction >= 1 && currentQuestionNumber + direction <= numberOfQuestion) {
                    currentQuestionNumber += direction;
                    updateQuestionDisplay();
                }
                break;
            default:
                int questionIndex = Integer.parseInt(command) - 1;
                currentQuestionNumber = questionIndex + 1;
                updateQuestionDisplay();
                break;
        }
    }




    private void updateQuestionDisplay() {
        // Update question
        questionLabel.setText(currentQuestionNumber + ": " + questions[currentQuestionNumber - 1].getQuestionText());

        option1.setText("A." + questions[currentQuestionNumber - 1].getOptionA());
        option2.setText("B." + questions[currentQuestionNumber - 1].getOptionB());
        option3.setText("C." + questions[currentQuestionNumber - 1].getOptionC());
        option4.setText("D." + questions[currentQuestionNumber - 1].getOptionD());

        // If the question was previously attempted, updating here
        if (isAttempted[currentQuestionNumber - 1]) {
            questionButtons[currentQuestionNumber - 1].setBackground(Color.GREEN);
        } else {
            questionButtons[currentQuestionNumber - 1].setBackground(null);
        }

        clearSelection();

        // Set the previously selected answer
        String givenAnswer = givenAnswers[currentQuestionNumber - 1];
        if (givenAnswer != null) {
            if (givenAnswer.equals("A." + questions[currentQuestionNumber - 1].getOptionA())) {
                option1.setSelected(true);
            } else if (givenAnswer.equals("B." + questions[currentQuestionNumber - 1].getOptionB())) {
                option2.setSelected(true);
            } else if (givenAnswer.equals("C." + questions[currentQuestionNumber - 1].getOptionC())) {
                option3.setSelected(true);
            } else if (givenAnswer.equals("D." + questions[currentQuestionNumber - 1].getOptionD())) {
                option4.setSelected(true);
            }
        }
    }
    private void clearSelection() {
        optionGroup.clearSelection();
    }
}
