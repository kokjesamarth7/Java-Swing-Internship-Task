package kokje;


import javax.swing.*;
import java.awt.*;

public class LoginView 
{
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JCheckBox rememberMeCheckBox;
    private JButton loginButton, exitButton;

    public LoginView()
    {
        frame = new JFrame("Login");
        frame.setLayout(new GridLayout(4, 2));
        frame.setSize(500, 500);

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        rememberMeCheckBox = new JCheckBox("Remember Me");
        loginButton = new JButton("Login");
        exitButton = new JButton("Exit");

        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("Password:"));
        frame.add(passwordField);
        frame.add(rememberMeCheckBox);
        frame.add(loginButton);
        frame.add(exitButton);

        loginButton.addActionListener(e -> onLoginClick());
        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void onLoginClick() 
    {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        UserDAO userDAO = new UserDAO();
        try 
        {
            if (userDAO.validateUser(username, password))
            {
                User user = userDAO.getUserByUsername(username);
                if ("ADMIN".equals(user.getRole())) 
                {
                    new AdminDashboardView();
                }
                else
                {
                    new UserProfileView(user);
                }
                frame.dispose();  
            } 
            else 
            {
                JOptionPane.showMessageDialog(frame, "Invalid credentials.");
            }
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}