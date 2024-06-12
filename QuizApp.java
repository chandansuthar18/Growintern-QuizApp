package org;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuizApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        QuizFrame frame = new QuizFrame();
            frame.setVisible(true);
        });
    }
}
class QuizFrame extends JFrame {
    public QuizFrame() {
        setTitle("Quiz Application");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JComboBox<String> languageComboBox = new JComboBox<>(new String[]{"Java", "C++", "Python", "HTML"});
        JButton startButton = new JButton("Start Quiz");

        startButton.addActionListener(e -> {
            String selectedLanguage = (String) languageComboBox.getSelectedItem();
            if (selectedLanguage != null) {
                dispose();
                Quiz quiz = new Quiz(selectedLanguage);
                quiz.setVisible(true);
            }
        });

        // Create a sub-panel to hold the combo box and button
        JPanel selectionPanel = new JPanel();
        selectionPanel.add(new JLabel("Select Programming Language:"));
        selectionPanel.add(languageComboBox);
        selectionPanel.add(startButton);

        // Add the selection panel to the main panel
        panel.add(selectionPanel);

        // Add a vertical glue to push the footer to the bottom
        panel.add(Box.createVerticalGlue());

        // Add the label with Chandan Kumar's text to the bottom of the main panel
        JLabel footerLabel = new JLabel("Created by Chandan Kumar", JLabel.RIGHT);
        panel.add(footerLabel);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            QuizFrame quizFrame = new QuizFrame();
            quizFrame.setVisible(true);
        });
    }
}

class Quiz extends JFrame {
    private QuizController controller;

    public Quiz(String language) {
        setTitle("Quiz - " + language);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        controller = new QuizController(language);
        add(controller.getQuizPanel());
    }
}

class Question {
    private String question;
    private String[] options;
    private int correctOptionIndex;

    public Question(String question, String[] options, int correctOptionIndex) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(int selectedOptionIndex) {
        return selectedOptionIndex == correctOptionIndex;
    }
}
class QuizController {
    private JPanel quizPanel;
    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JButton nextButton;
    private int currentQuestionIndex;
    private int score;
    private java.util.List<Question> questions;

    public QuizController(String language) {
        quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));

        questionLabel = new JLabel();
        questionLabel.setFont(questionLabel.getFont().deriveFont(Font.PLAIN, 24)); // Increase text size
        quizPanel.add(questionLabel);

        optionButtons = new JRadioButton[4]; // Assuming 4 options per question
        ButtonGroup optionGroup = new ButtonGroup();
        for (int i = 0; i < optionButtons.length; i++) {
            optionButtons[i] = new JRadioButton();
            optionGroup.add(optionButtons[i]);
            quizPanel.add(optionButtons[i]);
        }
        System.out.println("optionGroup = " + optionGroup);
        nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            if (currentQuestionIndex < questions.size()) {
                // Check if an option is selected
                if (optionButtons[getSelectedOption()].isSelected()) {
                    // Check if the selected option is correct
                    if (questions.get(currentQuestionIndex).isCorrect(getSelectedOption())) {
                        score++;
                        JOptionPane.showMessageDialog(null, "Correct!"); // Feedback for correct answer
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Wrong!"); // Feedback for wrong answer
                    }
                    currentQuestionIndex++;
                    if (currentQuestionIndex < questions.size()) {
                        displayQuestion(currentQuestionIndex);
                    } else {
                        JOptionPane.showMessageDialog(null, "Quiz completed. Score: " + score + "/" + questions.size());
                        // Optionally, you can save the score or provide options to restart the quiz.
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select an option.");
                }
            }
        });
        quizPanel.add(nextButton);

        currentQuestionIndex = 0;
        score = 0;
        loadQuestions(language);
        displayQuestion(currentQuestionIndex);
    }

    private void loadQuestions(String language) {
        switch (language) {
            case "Java":
                questions = loadJavaQuestions();
                break;
            case "C++":
                questions = loadCpQuestions();
                break;
            case "Python":
                questions = loadPythonQuestions();
                break;
            case "HTML":
                questions = loadHtmlQuestions();
                break;
            default:
                // Handle unknown language or missing cases appropriately
                System.err.println("Unknown language: " + language);
                questions = new ArrayList<>(); // Initialize an empty list
                break;
        }
    }
    public ArrayList<Question> loadJavaQuestions() {
        ArrayList<Question> javaQuestions = new ArrayList<>();
        javaQuestions.add(new Question("What is Java?", new String[]{"A type of coffee","A programming language", "A type of cake", "A planet"}, 1));
        javaQuestions.add(new Question("What does 'System.out.println()' do in Java?", new String[]{"Prints output to the console", "Reads input from the console", "Declares a variable", "Loops through an array"}, 0));
        javaQuestions.add(new Question("What does 'Scanner' class do in Java?", new String[]{"Reads input from the console", "Prints output to the console", "Declares a variable", "Loops through an array"}, 0));
        javaQuestions.add(new Question("What is the 'main' method in Java?", new String[]{"Entry point of the program", "A method for mathematical operations", "A built-in Java library", "A loop structure"}, 0));
        javaQuestions.add(new Question("Which symbol is used for comments in Java?", new String[]{"/* */","//",  "#", "--"}, 1));
        javaQuestions.add(new Question("What is a variable in Java?", new String[]{ "A function","A container to store data", "A loop structure", "A type of control flow statement"}, 1));
        javaQuestions.add(new Question("What is the data type for whole numbers in Java?", new String[]{ "double", "char", "string","int",}, 3));
        javaQuestions.add(new Question("What does '++' operator do in Java?", new String[]{"Increment a variable by 1", "Decrement a variable by 1", "Multiply a variable by itself", "Divide a variable by itself"}, 0));
        javaQuestions.add(new Question("What does '--' operator do in Java?", new String[]{ "Increment a variable by 1", "Multiply a variable by itself","Decrement a variable by 1", "Divide a variable by itself"}, 2));
        javaQuestions.add(new Question("What is the data type for decimal numbers in Java?", new String[]{ "int", "double","char", "string"}, 1));
        javaQuestions.add(new Question("Which of the following is not a valid variable name in Java?", new String[]{"2var", "_var", "var2", "var_"}, 0));
        javaQuestions.add(new Question("What is the symbol for logical AND in Java?", new String[]{"&&", "||", "!", "&"}, 0));
        javaQuestions.add(new Question("What is the symbol for logical OR in Java?", new String[]{ "&&", "!", "|","||"}, 3));
        javaQuestions.add(new Question("What does '==' operator do in Java?", new String[]{"Checks for equality", "Assigns a value", "Increments a variable", "Decrements a variable"}, 0));
        javaQuestions.add(new Question("What does '!=' operator do in Java?", new String[]{"Checks for inequality", "Assigns a value", "Increments a variable", "Decrements a variable"}, 0));
        javaQuestions.add(new Question("Which loop is known as 'entry-controlled loop' in Java?", new String[]{"for loop", "while loop", "do-while loop", "if statement"}, 0));
        javaQuestions.add(new Question("Which loop is known as 'exit-controlled loop' in Java?", new String[]{ "for loop", "while loop","do-while loop", "if statement"}, 2));
        javaQuestions.add(new Question("What is the syntax for the 'for' loop in Java?", new String[]{"for (initialization; condition; update)", "while (condition) { }", "do { } while (condition)", "if (condition) { }"}, 0));
        javaQuestions.add(new Question("What is the syntax for the 'while' loop in Java?", new String[]{"while (condition) { }", "for (initialization; condition; update)", "do { } while (condition)", "if (condition) { }"}, 0));
        javaQuestions.add(new Question("What is the syntax for the 'do-while' loop in Java?", new String[]{"do { } while (condition)", "for (initialization; condition; update)", "while (condition) { }", "if (condition) { }"}, 0));
        javaQuestions.add(new Question("What is the purpose of 'break' statement in Java?", new String[]{ "Skips to the next iteration of the loop", "Resumes execution after the loop","Terminates the loop", "Declares a variable"}, 2));
        javaQuestions.add(new Question("What is the purpose of 'continue' statement in Java?", new String[]{"Skips to the next iteration of the loop", "Terminates the loop", "Resumes execution after the loop", "Declares a variable"}, 0));
        javaQuestions.add(new Question("What is the purpose of 'return' statement in Java?", new String[]{"Returns a value from a function", "Terminates the loop", "Skips to the next iteration of the loop", "Declares a variable"}, 0));
        javaQuestions.add(new Question("What is the purpose of 'void' keyword in Java?", new String[]{"Indicates that the function does not return a value", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        javaQuestions.add(new Question("What is the purpose of 'const' keyword in Java?", new String[]{ "Declares a variable", "Marks the end of a function","Declares a constant variable", "Marks the beginning of a function"}, 2));
        javaQuestions.add(new Question("What is the purpose of 'static' keyword in Java?", new String[]{"Specifies that the variable retains its value between function calls", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));

        return javaQuestions;
    }



    public ArrayList<Question> loadCpQuestions() {
        ArrayList<Question> cPlusPlusQuestions = new ArrayList<>();
        cPlusPlusQuestions.add(new Question("What is C++?", new String[]{"A type of coffee", "A type of cake", "A planet","A programming language"}, 3));
        cPlusPlusQuestions.add(new Question("What does 'cout' do in C++?", new String[]{"Prints output to the console", "Reads input from the console", "Declares a variable", "Loops through an array"}, 0));
        cPlusPlusQuestions.add(new Question("What does 'cin' do in C++?", new String[]{"Prints output to the console","Reads input from the console", "Declares a variable", "Loops through an array"}, 1));
        cPlusPlusQuestions.add(new Question("What is the 'main' function in C++?", new String[]{"Entry point of the program", "A function for mathematical operations", "A built-in C++ library", "A loop structure"}, 0));
        cPlusPlusQuestions.add(new Question("Which symbol is used for comments in C++?", new String[]{"//", "/* */", "#", "--"}, 0));
        cPlusPlusQuestions.add(new Question("What is a variable in C++?", new String[]{ "A function", "A loop structure","A container to store data", "A type of control flow statement"}, 2));
        cPlusPlusQuestions.add(new Question("What is the data type for whole numbers in C++?", new String[]{"int", "double", "char", "string"}, 0));
        cPlusPlusQuestions.add(new Question("What does '++' operator do in C++?", new String[]{"Increment a variable by 1", "Decrement a variable by 1", "Multiply a variable by itself", "Divide a variable by itself"}, 0));
        cPlusPlusQuestions.add(new Question("What does '--' operator do in C++?", new String[]{"Decrement a variable by 1", "Increment a variable by 1", "Multiply a variable by itself", "Divide a variable by itself"}, 0));
        cPlusPlusQuestions.add(new Question("What is the data type for decimal numbers in C++?", new String[]{"double", "int", "char", "string"}, 0));
        cPlusPlusQuestions.add(new Question("Which of the following is not a valid variable name in C++?", new String[]{"2var", "_var", "var2", "var_"}, 0));
        cPlusPlusQuestions.add(new Question("What is the symbol for logical AND in C++?", new String[]{"&&", "||", "!", "&"}, 0));
        cPlusPlusQuestions.add(new Question("What is the symbol for logical OR in C++?", new String[]{"||", "&&", "!", "|"}, 0));
        cPlusPlusQuestions.add(new Question("What does '==' operator do in C++?", new String[]{"Checks for equality", "Assigns a value", "Increments a variable", "Decrements a variable"}, 0));
        cPlusPlusQuestions.add(new Question("What does '!=' operator do in C++?", new String[]{"Checks for inequality", "Assigns a value", "Increments a variable", "Decrements a variable"}, 0));
        cPlusPlusQuestions.add(new Question("Which loop is known as 'entry-controlled loop' in C++?", new String[]{"for loop", "while loop", "do-while loop", "if statement"}, 0));
        cPlusPlusQuestions.add(new Question("Which loop is known as 'exit-controlled loop' in C++?", new String[]{"do-while loop", "for loop", "while loop", "if statement"}, 0));
        cPlusPlusQuestions.add(new Question("What is the syntax for the 'for' loop in C++?", new String[]{"for (initialization; condition; update)", "while (condition) { }", "do { } while (condition)", "if (condition) { }"}, 0));
        cPlusPlusQuestions.add(new Question("What is the syntax for the 'while' loop in C++?", new String[]{"while (condition) { }", "for (initialization; condition; update)", "do { } while (condition)", "if (condition) { }"}, 0));
        cPlusPlusQuestions.add(new Question("What is the syntax for the 'do-while' loop in C++?", new String[]{"do { } while (condition)", "for (initialization; condition; update)", "while (condition) { }", "if (condition) { }"}, 0));
        cPlusPlusQuestions.add(new Question("What is the purpose of 'break' statement in C++?", new String[]{"Terminates the loop", "Skips to the next iteration of the loop", "Resumes execution after the loop", "Declares a variable"}, 0));
        cPlusPlusQuestions.add(new Question("What is the purpose of 'continue' statement in C++?", new String[]{"Skips to the next iteration of the loop", "Terminates the loop", "Resumes execution after the loop", "Declares a variable"}, 0));
        cPlusPlusQuestions.add(new Question("What is the purpose of 'return' statement in C++?", new String[]{"Returns a value from a function", "Terminates the loop", "Skips to the next iteration of the loop", "Declares a variable"}, 0));
        cPlusPlusQuestions.add(new Question("What is the purpose of 'void' keyword in C++?", new String[]{"Indicates that the function does not return a value", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        cPlusPlusQuestions.add(new Question("What is the purpose of 'const' keyword in C++?", new String[]{"Declares a constant variable", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        cPlusPlusQuestions.add(new Question("What is the purpose of 'static' keyword in C++?", new String[]{"Specifies that the variable retains its value between function calls", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        cPlusPlusQuestions.add(new Question("What is the purpose of 'new' keyword in C++?", new String[]{"Allocates memory for a new object", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        cPlusPlusQuestions.add(new Question("What is the purpose of 'delete' keyword in C++?", new String[]{"Deallocates memory for an object", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        return cPlusPlusQuestions;
    }

    public ArrayList<Question> loadPythonQuestions() {
        ArrayList<Question> pythonQuestions = new ArrayList<>();
        pythonQuestions.add(new Question("What is Python?", new String[]{"A type of snake","A programming language", "A type of coffee", "A planet"}, 1));
        pythonQuestions.add(new Question("What does 'print()' do in Python?", new String[]{"Reads input from the console", "Declares a variable", "Loops through a list","Prints output to the console"}, 3));
        pythonQuestions.add(new Question("What does 'input()' do in Python?", new String[]{"Reads input from the console", "Prints output to the console", "Declares a variable", "Loops through a list"}, 0));
        pythonQuestions.add(new Question("What is the 'if' statement in Python?", new String[]{ "Loop statement", "Function statement","Conditional statement", "Class statement"}, 2));
        pythonQuestions.add(new Question("Which symbol is used for comments in Python?", new String[]{"#", "//", "/* */", "--"}, 0));
        pythonQuestions.add(new Question("What is a variable in Python?", new String[]{"A container to store data", "A function", "A loop structure", "A type of control flow statement"}, 0));
        pythonQuestions.add(new Question("What is the data type for whole numbers in Python?", new String[]{"int", "float", "str", "list"}, 0));
        pythonQuestions.add(new Question("What does '+' operator do in Python?", new String[]{"Concatenates two strings", "Adds two numbers", "Subtracts two numbers", "Multiplies two numbers"}, 0));
        pythonQuestions.add(new Question("What does '==' operator do in Python?", new String[]{"Checks for equality", "Assigns a value", "Increments a variable", "Decrements a variable"}, 0));
        pythonQuestions.add(new Question("What is the data type for lists in Python?", new String[]{ "tuple","list", "dict", "set"}, 1));
        pythonQuestions.add(new Question("What is the purpose of 'for' loop in Python?", new String[]{"Iterates over items of a sequence", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the syntax for defining a function in Python?", new String[]{"def functionName():", "function functionName():", "functionName():", "def functionName()"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'return' statement in Python?", new String[]{ "Terminates the loop", "Skips to the next iteration of the loop","Returns a value from a function", "Declares a variable"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'import' keyword in Python?", new String[]{"Imports modules or packages", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'class' keyword in Python?", new String[]{ "Declares a variable", "Marks the end of a function", "Marks the beginning of a function","Defines a class"}, 3));
        pythonQuestions.add(new Question("What is the purpose of 'self' parameter in Python?", new String[]{"Refers to the current instance of the class", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'inheritance' in Python?", new String[]{"Allows a class to inherit properties and behaviors from another class", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'try-except' block in Python?", new String[]{"Handles exceptions", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'lambda' function in Python?", new String[]{"Creates anonymous functions", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'map' function in Python?", new String[]{"Applies a function to all items in a list", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'filter' function in Python?", new String[]{ "Declares a variable", "Marks the end of a function", "Filters elements from a list","Marks the beginning of a function"}, 2));
        pythonQuestions.add(new Question("What is the purpose of 'reduce' function in Python?", new String[]{"Applies a rolling computation to sequential pairs of values", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'enumerate' function in Python?", new String[]{"Returns an enumerate object", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'zip' function in Python?", new String[]{"Returns an iterator of tuples", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'set' data type in Python?", new String[]{ "Declares a variable","Stores unique elements", "Marks the end of a function", "Marks the beginning of a function"}, 1));
        pythonQuestions.add(new Question("What is the purpose of 'dictionary' data type in Python?", new String[]{"Stores key-value pairs", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'tuple' data type in Python?", new String[]{ "Declares a variable", "Marks the end of a function", "Marks the beginning of a function","Stores immutable sequences"}, 3));
        pythonQuestions.add(new Question("What is the purpose of 'list comprehension' in Python?", new String[]{"Provides a concise way to create lists", "Declares a variable", "Marks the end of a function", "Marks the beginning of a function"}, 0));
        pythonQuestions.add(new Question("What is the purpose of 'range' function in Python?", new String[]{"Declares a variable", "Marks the end of a function", "Generates a sequence of numbers", "Marks the beginning of a function"}, 1));
        return pythonQuestions;
    }

    public ArrayList<Question> loadHtmlQuestions() {
        ArrayList<Question> htmlQuestions = new ArrayList<>();
        htmlQuestions.add(new Question("What is HTML?", new String[]{"HyperText Markup Language", "HyperText Media Language", "Highly Typed Markup Language", "Hyperlink Text Markup Language"}, 0));
        htmlQuestions.add(new Question("What does the acronym 'HTML' stand for?", new String[]{ "HyperText Media Language", "Highly Typed Markup Language", "Hyperlink Text Markup Language","HyperText Markup Language",}, 3));
        htmlQuestions.add(new Question("Which tag is used to define a hyperlink in HTML?", new String[]{"<a>", "<link>", "<h>", "<p>"}, 0));
        htmlQuestions.add(new Question("What does the '<p>' tag stand for in HTML?", new String[]{ "Position","Paragraph", "Page", "Pointer"}, 1));
        htmlQuestions.add(new Question("What is the purpose of the '<img>' tag in HTML?", new String[]{"To embed an image", "To create a hyperlink", "To define a paragraph", "To format text"}, 0));
        htmlQuestions.add(new Question("Which tag is used to define a table in HTML?", new String[]{ "<tr>", "<td>","<table>", "<th>"}, 2));
        htmlQuestions.add(new Question("What is the purpose of the '<div>' tag in HTML?", new String[]{ "To create a hyperlink", "To format text","To define a division or a section", "To define a paragraph"}, 2));
        htmlQuestions.add(new Question("What is the purpose of the '<head>' tag in HTML?", new String[]{"To contain meta-information about the document", "To define a division or a section", "To create a hyperlink", "To format text"}, 0));
        htmlQuestions.add(new Question("Which tag is used to create a line break in HTML?", new String[]{"<br>", "<hr>", "<p>", "<b>"}, 0));
        htmlQuestions.add(new Question("What is the purpose of the '<ol>' tag in HTML?", new String[]{"To create an ordered list", "To create an unordered list", "To format text", "To define a division or a section"}, 0));
        htmlQuestions.add(new Question("Which tag is used to create a numbered list in HTML?", new String[]{ "<ul>","<ol>", "<li>", "<a>"}, 1));
        htmlQuestions.add(new Question("What is the purpose of the '<ul>' tag in HTML?", new String[]{"To create an unordered list", "To create an ordered list", "To format text", "To define a division or a section"}, 0));
        htmlQuestions.add(new Question("Which tag is used to create a bulleted list in HTML?", new String[]{ "<ol>", "<li>", "<a>","<ul>"}, 3));
        htmlQuestions.add(new Question("What is the purpose of the '<form>' tag in HTML?", new String[]{"To create an interactive form for user input", "To create a hyperlink", "To define a paragraph", "To format text"}, 0));
        htmlQuestions.add(new Question("Which tag is used to create an input field for submitting a file in HTML?", new String[]{"<input type='file'>", "<input type='text'>", "<input type='submit'>", "<input type='button'>"}, 0));
        htmlQuestions.add(new Question("What is the purpose of the '<select>' tag in HTML?", new String[]{ "To create a radio button","To create a drop-down list", "To create a checkbox", "To create an input field"}, 1));
        htmlQuestions.add(new Question("Which tag is used to create an option in a drop-down list in HTML?", new String[]{"<option>", "<select>", "<input>", "<list>"}, 0));
        htmlQuestions.add(new Question("What is the purpose of the '<textarea>' tag in HTML?", new String[]{"To create a multi-line text input field", "To create a single-line text input field", "To create a password input field", "To create an email input field"}, 0));
        htmlQuestions.add(new Question("Which tag is used to create a link to an external style sheet in HTML?", new String[]{"<link>", "<style>", "<script>", "<css>"}, 0));
        htmlQuestions.add(new Question("What is the purpose of the '<meta>' tag in HTML?", new String[]{"To contain meta-information about the document", "To create a hyperlink", "To format text", "To define a paragraph"}, 0));
        htmlQuestions.add(new Question("What does the '<title>' tag specify in HTML?", new String[]{"The title of the document", "The heading of the document", "The paragraph of the document", "The section of the document"}, 0));
        htmlQuestions.add(new Question("What is the purpose of the '<iframe>' tag in HTML?", new String[]{ "To create a hyperlink", "To format text", "To embed another HTML document within the current document","To define a paragraph"}, 2));
        htmlQuestions.add(new Question("Which tag is used to create an inline style in HTML?", new String[]{"<style>", "<link>", "<head>", "<css>"}, 0));
        htmlQuestions.add(new Question("What is the purpose of the '<strong>' tag in HTML?", new String[]{ "To create a hyperlink","To define strong importance", "To format text", "To define a paragraph"}, 1));
        htmlQuestions.add(new Question("What is the purpose of the '<em>' tag in HTML?", new String[]{"To define emphasized text", "To create a hyperlink", "To format text", "To define a paragraph"}, 0));
        htmlQuestions.add(new Question("Which tag is used to create a subscript in HTML?", new String[]{"<sub>", "<sup>", "<span>", "<div>"}, 0));
        htmlQuestions.add(new Question("Which tag is used to create a superscript in HTML?", new String[]{ "<sub>", "<span>", "<sup>","<div>"}, 2));
        htmlQuestions.add(new Question("What is the purpose of the '<abbr>' tag in HTML?", new String[]{"To define an abbreviation", "To create a hyperlink", "To format text", "To define a paragraph"}, 0));
        htmlQuestions.add(new Question("What is the purpose of the '<cite>' tag in HTML?", new String[]{ "To create a hyperlink", "To format text", "To define a paragraph","To define the title of a work"}, 3));
        htmlQuestions.add(new Question("Which tag is used to create a citation in HTML?", new String[]{"<cite>", "<abbr>", "<span>", "<div>"}, 0));
        htmlQuestions.add(new Question("What is the purpose of the '<code>' tag in HTML?", new String[]{"To define computer code", "To create a hyperlink", "To format text", "To define a paragraph"}, 0));
        return htmlQuestions;
    }

    private void displayQuestion(int index) {
        if (!questions.isEmpty()) {
            if (index >= 0 && index < questions.size()) {
                Question currentQuestion = questions.get(index);
                questionLabel.setText(currentQuestion.getQuestion());
                String[] options = currentQuestion.getOptions();
                for (int i = 0; i < optionButtons.length; i++) {
                    if (i < options.length) {
                        optionButtons[i].setText(options[i]);
                    } else {
                        optionButtons[i].setText(""); // Clear text for extra buttons
                    }
                }
            } else {
                // Handle the case where index is out of bounds
                // For example, display an error message or take appropriate action
                System.err.println("Index out of bounds: " + index);
            }
        } else {
            // Handle the case where questions ArrayList is empty
            // For example, display an error message or take appropriate action
            System.err.println("No questions available.");
        }
    }
    private int getSelectedOption() {
        for (int i = 0; i < optionButtons.length; i++) {
            if (optionButtons[i].isSelected()) {
                return i;
            }
        }
        // If no option is selected, return -1 or handle the case appropriately
        return -1;
    }

    public JPanel getQuizPanel() {
            return quizPanel;
        }
    }