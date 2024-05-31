import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton resetButton;

    public LoginScreen() {
        setTitle("Login Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        resetButton = new JButton("Reset Password");

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);

        panel.add(new JLabel("Usuário:"));
        panel.add(usernameField);
        panel.add(new JLabel("Senha:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(resetButton);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (authenticateUser(username, password)) {
                
                MainMenuScreen mainMenu = new MainMenuScreen();
                mainMenu.setVisible(true);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Credenciais inválidas. Tente novamente.");
            }
        } else if (e.getSource() == resetButton) {
        
            JOptionPane.showMessageDialog(this, "Funcionalidade de redefinição de senha não implementada.");
        }
    }

    private boolean authenticateUser(String username, String password) {
        /
        String url = "jdbc:mysql://banco_de_dados";
        String user = "seu_usuario";
        String pass = "sua_senha";

        try (Connection connection = DriverManager.getConnection(url, user, pass)) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, username);
                statement.setString(2, password);
                try (ResultSet resultSet = statement.executeQuery()) {
                    return resultSet.next(); 
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginScreen().setVisible(true);
        });
    }
}
