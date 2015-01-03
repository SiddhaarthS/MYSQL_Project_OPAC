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

public class MemberPanel extends JPanel {
	JTextField memna_txt, memid_txt, memcat_txt, memdep_txt, memyr_txt,
			memct_txt;
	JButton insert;
	JFrame frame = null;

	public void clear() {
		memna_txt.setText("");
		memid_txt.setText("");
		memcat_txt.setText("");
		memdep_txt.setText("");
		memyr_txt.setText("");
	}

	public MemberPanel() {
		setSize(300, 300);
		setLayout(new GridLayout(7, 2));

		add(new JLabel("Name:"));
		memna_txt = new JTextField();
		add(memna_txt);

		add(new JLabel("Member ID:"));
		memid_txt = new JTextField();
		add(memid_txt);

		add(new JLabel("Category:"));
		memcat_txt = new JTextField();
		add(memcat_txt);

		add(new JLabel("Dept:"));
		memdep_txt = new JTextField();
		add(memdep_txt);

		add(new JLabel("Year:"));
		memyr_txt = new JTextField();
		add(memyr_txt);

		add(new JLabel(""));
		memct_txt = new JTextField();
		memct_txt.setVisible(false);
		add(memct_txt);

		insert = new JButton("Insert");
		add(insert);
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					Connectify.pstmt = Connectify.con
							.prepareStatement("insert into member values(?,?,?,?,?);");
					Connectify.pstmt.setString(1, memid_txt.getText());
					Connectify.pstmt.setString(2, memna_txt.getText());
					Connectify.pstmt.setString(3, memcat_txt.getText());
					Connectify.pstmt.setString(4, memdep_txt.getText());
					Connectify.pstmt.setString(5, memyr_txt.getText());

					Connectify.pstmt.execute();
					JOptionPane.showMessageDialog(frame,
							"New Member record inserted");
					clear();
				} catch (SQLException ex) {
					clear();
					JOptionPane.showMessageDialog(frame, ex.getMessage());
				}
			}
		});
	}
}
