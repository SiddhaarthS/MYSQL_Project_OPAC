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

public class TitleSearchPanel extends JPanel {
	JTextField title_src, acc_src, auth_src, cat_src, stat_src, memid_src;
	JLabel memid_l;
	JButton search;
	JFrame frame = null;

	public TitleSearchPanel() {
		setSize(400, 400);
		setLayout(new GridLayout(7, 2));

		add(new JLabel("Title:"));
		title_src = new JTextField();
		add(title_src);

		add(new JLabel("Access ID:"));
		acc_src = new JTextField();
		acc_src.setEnabled(false);
		add(acc_src);

		add(new JLabel("Author:"));
		auth_src = new JTextField();
		auth_src.setEnabled(false);
		add(auth_src);

		add(new JLabel("Category:"));
		cat_src = new JTextField();
		cat_src.setEnabled(false);
		add(cat_src);

		add(new JLabel("Total Count:"));
		stat_src = new JTextField();
		stat_src.setEnabled(false);
		add(stat_src);

		memid_l = new JLabel("Issue Count:");
		memid_l.setVisible(true);
		// memid_l.setEnabled(false);
		add(memid_l);
		memid_src = new JTextField();
		memid_src.setVisible(true);
		memid_src.setEnabled(false);
		add(memid_src);

		search = new JButton("Search");
		add(search);
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					
					//when there are myltiple copies of the same book with different access no
					int iss_ct = 0, tot_ct = 0;
					Connectify.pstmt = Connectify.con
							.prepareStatement("select * from books where title='"
									+ title_src.getText().trim() + "';");
					Connectify.rs = Connectify.pstmt.executeQuery();
					while (Connectify.rs.next()) {
						if (tot_ct != 0)
							acc_src.setText(acc_src.getText() + ","
									+ Connectify.rs.getString(1));
						else
						acc_src.setText(Connectify.rs.getString(1));
						auth_src.setText(Connectify.rs.getString(3));
						cat_src.setText(Connectify.rs.getString(4));
						if (Connectify.rs.getString(5).equalsIgnoreCase(
								"Issued"))
							iss_ct++;
						tot_ct++;
					}
					stat_src.setText(tot_ct + "");
					memid_src.setText(iss_ct + "");
					/*
					 * try { Connectify.pstmt=Connectify.con.prepareStatement(
					 * "select m_id from issue join books where b_ano=a_no and author ='"
					 * +auth_src.getText()+"';");
					 * Connectify.rs=Connectify.pstmt.executeQuery();
					 * while(Connectify.rs.next()) {
					 * memid_src.setText(Connectify.rs.getString(1));
					 * memid_src.setVisible(true); memid_l.setVisible(true); } }
					 * catch(Exception e) {
					 * JOptionPane.showMessageDialog(frame,"Record not found");
					 * }
					 */
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(frame, "NO RECORDS FOUND");
				}
			}
		});
	}
}
