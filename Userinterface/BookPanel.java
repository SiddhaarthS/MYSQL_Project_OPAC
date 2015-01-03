import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BookPanel extends JPanel {
	JTextField title_txt, acno_txt, auth_txt, cat_txt, stat_txt;
	JButton submit;
	JFrame frame = null;

	public void clear() {
		title_txt.setText("");
		acno_txt.setText("");
		auth_txt.setText("");
		cat_txt.setText("");
		stat_txt.setText("");
	}

	public BookPanel() {
		setSize(300, 300);
		setLayout(new GridLayout(6, 2));

		add(new JLabel("Title:"));
		title_txt = new JTextField();
		add(title_txt);

		add(new JLabel("Access No:"));
		acno_txt = new JTextField();
		add(acno_txt);

		add(new JLabel("Author:"));
		auth_txt = new JTextField();
		add(auth_txt);

		add(new JLabel("Category:"));
		cat_txt = new JTextField();
		add(cat_txt);

		add(new JLabel("Status:"));
		stat_txt = new JTextField();
		add(stat_txt);

		submit = new JButton("Submit");
		add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Connectify.pstmt = Connectify.con
							.prepareStatement("insert into books values(?,?,?,?,?);");
					Connectify.pstmt.setString(1, acno_txt.getText());
					Connectify.pstmt.setString(2, title_txt.getText());
					Connectify.pstmt.setString(3, auth_txt.getText());
					Connectify.pstmt.setString(4, cat_txt.getText());
					Connectify.pstmt.setString(5, stat_txt.getText());
					Connectify.pstmt.execute();
					JOptionPane.showMessageDialog(frame,
							"New Book record inserted");
					clear();
				} catch (SQLException ex) {
					clear();
					JOptionPane.showMessageDialog(frame, ex.getMessage());
				}
			}
		});
	}
}
