package kokje;


import javax.swing.*;
import java.awt.*;

public class UserProfileView 
{
    private JFrame frame;
    public UserProfileView(User user)
    {
        frame = new JFrame("User Profile");
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(4, 2));

        JTextField usernameField = new JTextField(user.getUsername());
        JTextField cityField = new JTextField(user.getCity());
        JButton saveButton = new JButton("Save Changes");

        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("City:"));
        frame.add(cityField);
        frame.add(saveButton);

        saveButton.addActionListener(e -> 
        {
            user.setCity(cityField.getText());
            
            try 
            {
                new UserDAO().updateUser(user);
                JOptionPane.showMessageDialog(frame, "Profile updated successfully.");
            }
            catch (Exception ex) 
            {
                ex.printStackTrace();
            }
        });

        frame.setVisible(true);
    }
}