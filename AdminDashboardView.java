package kokje;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class AdminDashboardView extends JFrame 
{
    private JTable cityWiseUserTable;
    private JButton logoutButton;
    private DefaultTableModel tableModel;

    public AdminDashboardView() 
    {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        
        JLabel titleLabel = new JLabel("Admin Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        
        String[] columnNames = {"City", "User Count"};
        tableModel = new DefaultTableModel(columnNames, 0);
        cityWiseUserTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(cityWiseUserTable);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel();
        logoutButton = new JButton("Logout");
        buttonPanel.add(logoutButton);
        add(buttonPanel, BorderLayout.SOUTH);

        
        loadCityWiseUserCount();

        
        logoutButton.addActionListener(e -> 
        {
            JOptionPane.showMessageDialog(this, "Logging out...");
            System.exit(0);
        }
        );

        setVisible(true);
    }

    
    private void loadCityWiseUserCount()
    {
        try
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_db", "root", "Bappa_morya333");

            String sql = "SELECT city, COUNT(*) AS user_count FROM users GROUP BY city";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            
            tableModel.setRowCount(0);

            
            while (rs.next()) 
            {
                String city = rs.getString("city");
                int userCount = rs.getInt("user_count");
                tableModel.addRow(new Object[]{city, userCount}
                );
            }

            conn.close();
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching city-wise user count: " + e.getMessage());
        }
    }

    public static void main(String[] args) 
    {
        new AdminDashboardView();  
    }
}
