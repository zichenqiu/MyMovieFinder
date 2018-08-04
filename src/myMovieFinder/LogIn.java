package myMovieFinder;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;

public class LogIn {
	private JFrame frame;
	private JTextField txtUserName;
	private JPasswordField passwordField;
	private Context context;

	public static void run(Context context) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn window = new LogIn(context);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LogIn(Context context) {
		this.context = context;
		initialize();
		// Make sure Connect has a valid database connection.
		Connect.getConnection();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 272, 236);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("My Movie Finder Login");
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLogin.setBounds(25, 22, 186, 46);
		frame.getContentPane().add(lblLogin);
		
		txtUserName = new JTextField();
		txtUserName.setForeground(Color.BLACK);
		txtUserName.setBounds(104, 71, 107, 20);
		frame.getContentPane().add(txtUserName);
		txtUserName.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(104, 102, 107, 20);
		frame.getContentPane().add(passwordField);
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setBounds(25, 69, 80, 20);
		frame.getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(25, 100, 69, 20);
		frame.getContentPane().add(lblPassword);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String uid;
				String pw;
				try {
					uid = txtUserName.getText();
					pw = new String(passwordField.getPassword());
					System.out.println("uid: " + uid + ", password: " + pw);
					// TODO: Update Query for user ID
					String userQry = "SELECT * from users WHERE uid=" + uid + " and password=" + pw;
					int result = Connect.checkUser(userQry);
					if(result > 0) {
						System.out.println("User " + uid + " found!");
						frame.dispose();
						context.userId = result;
						FindMovies findMovies = new FindMovies(context);
						findMovies.run(context);
					}
					String strResult = "Invalid User ID and Password Combo.\n Please try again.";
					lblLogin.setText(strResult);
				}catch(Exception e1) {
					// handle bad data
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnNewButton.setBounds(25, 131, 90, 41);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
				MainGUI.main(null);
			}
		});
		btnCancel.setBounds(121, 131, 90, 41);
		frame.getContentPane().add(btnCancel);
	}
}