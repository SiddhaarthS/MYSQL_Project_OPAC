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

class IssuePanel extends JPanel {
	JFrame frame;
	JTextField book_accessno, mem_id, iss_date, ret_date, fine;
	JButton submit;

	public void clear() {
		book_accessno.setText("");
		mem_id.setText("");
		iss_date.setText("");
		ret_date.setText("");
		fine.setText("");
	}

	public IssuePanel() {
		setSize(300, 300);
		setLayout(new GridLayout(6, 2));
		book_accessno = new JTextField();
		mem_id = new JTextField();
		iss_date = new JTextField("yyyy-mm-dd");
		ret_date = new JTextField();
		ret_date.setEnabled(false);
		fine = new JTextField();
		fine.setVisible(false);
		this.add(new JLabel("Book Access No:"));
		this.add(book_accessno);
		this.add(new JLabel("Member Id:"));
		this.add(mem_id);
		this.add(new JLabel("Issue Date:"));
		this.add(iss_date);
		this.add(new JLabel("Return Date:"));
		this.add(ret_date);
		this.add(new JLabel());
		this.add(fine);
		submit = new JButton("Submit");
		this.add(submit);
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				submitButtonPressed();
			}
		});
	}

	public void submitButtonPressed() {
		int tempct = 0, st_id = 1, nm;
		String cat = "", rtd, idt;
		try {
			// System.out.println("id="+mem_id.getText());
			Connectify.pstmt = Connectify.con
					.prepareStatement("select count(*) from issue where m_id="
							+ mem_id.getText());
			Connectify.rs = Connectify.pstmt.executeQuery();
			while (Connectify.rs.next())
				tempct = Connectify.rs.getInt(1);

			Connectify.pstmt = Connectify.con
					.prepareStatement("select category from issue join member where m_id=id and m_id="
							+ mem_id.getText());
			Connectify.rs = Connectify.pstmt.executeQuery();
			while (Connectify.rs.next())
				cat = Connectify.rs.getString(1);

			if (cat.equalsIgnoreCase("student"))
				st_id = 1;
			else if (cat.equalsIgnoreCase("staff"))
				st_id = 2;
			if ((st_id == 1) && ((tempct + 1) > 3))
				JOptionPane.showMessageDialog(frame,
						"More than 3 books cannot be borrowed by a student");
			else if ((st_id == 2) && ((tempct + 1) > 5))
				JOptionPane.showMessageDialog(frame,
						"More than 5 books cannot be borrowed by a student");
			else {
				Connectify.pstmt = Connectify.con
						.prepareStatement("update books set status='issued' where a_no="
								+ book_accessno.getText());
				Connectify.pstmt.execute();
				idt = iss_date.getText().trim();
				cat = idt.substring(5, 7);
				rtd = idt.substring(0, 5);
				nm = Integer.parseInt(cat);
				if (st_id == 1) {
					nm = nm + 3;
					if (nm > 12) {
						nm = nm - 12;
						rtd = "" + (Integer.parseInt(rtd.substring(0, 4)) + 1)
								+ "-";
					}
					rtd = rtd + "" + nm + "" + idt.substring(7, 10);
				} else if (st_id == 2) {
					nm = nm + 5;
					if (nm > 12) {
						nm = nm - 12;
						rtd = "" + (Integer.parseInt(rtd.substring(0, 4)) + 1)
								+ "-";
					}
					rtd = rtd + "" + (Integer.parseInt(cat) + 5) + ""
							+ idt.substring(7, 10);
				}
				ret_date.setText(rtd);

				Connectify.pstmt = Connectify.con
						.prepareStatement("insert into issue(b_ano,m_id,issue_dt,ret_dt) values(?,?,?,?);");
				Connectify.pstmt.setString(1, book_accessno.getText());
				Connectify.pstmt.setString(2, mem_id.getText());
				Connectify.pstmt.setString(3, iss_date.getText());
				Connectify.pstmt.setString(4, rtd);
				Connectify.pstmt.execute();

				JOptionPane.showMessageDialog(frame, "Book Issued");
			}
			clear();
		} catch (SQLException ex) {
			clear();
			JOptionPane.showMessageDialog(frame, ex.getMessage());
		}
	}
}
