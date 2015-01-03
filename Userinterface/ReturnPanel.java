import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class ReturnPanel extends JPanel {
	JTextField book_accessno, mem_id, iss_date, ret_date, fine;
	JButton submit;
	JFrame frame;

	public void clear() {
		book_accessno.setText("");
		mem_id.setText("");
		iss_date.setText("");
		ret_date.setText("");
		fine.setText("");
	}

	public ReturnPanel() {
		setSize(300, 300);
		setLayout(new GridLayout(6, 2));
		book_accessno = new JTextField();
		mem_id = new JTextField();
		iss_date = new JTextField();
		ret_date = new JTextField();
		fine = new JTextField();
		this.add(new JLabel("Book Access No:"));
		this.add(book_accessno);
		this.add(new JLabel("Member Id:"));
		this.add(mem_id);
		this.add(new JLabel("Issue Date:"));
		this.add(iss_date);
		this.add(new JLabel("Return Date:"));
		this.add(ret_date);
		this.add(new JLabel("Fine:"));
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
		try {
			int no_d = 0;
			String is_dt = "", ret_dt = "";
			Connectify.pstmt = Connectify.con
					.prepareStatement("select issue_dt,ret_dt from issue where m_id="
							+ mem_id.getText()
							+ " and b_ano="
							+ book_accessno.getText() + ";");
			Connectify.rs = Connectify.pstmt.executeQuery();
			while (Connectify.rs.next()) {
				is_dt = Connectify.rs.getString(1);
				ret_dt = Connectify.rs.getString(2);
			}
			iss_date.setText(is_dt);
			ret_date.setText(ret_dt);

			Connectify.pstmt = Connectify.con
					.prepareStatement("select datediff(curdate(),'"
							+ ret_dt.trim() + "');");
			Connectify.rs = Connectify.pstmt.executeQuery();
			while (Connectify.rs.next())
				no_d = Connectify.rs.getInt(1);
			if (no_d > 0)
				fine.setText(" " + (no_d * 2));

			Connectify.pstmt = Connectify.con
					.prepareStatement("delete from issue where m_id="
							+ mem_id.getText() + " and b_ano="
							+ book_accessno.getText() + ";");
			Connectify.pstmt.execute();
			Connectify.pstmt = Connectify.con
					.prepareStatement("update books set status='available' where a_no="
							+ book_accessno.getText() + ";");
			Connectify.pstmt.execute();
			if (no_d == 0)
				JOptionPane.showMessageDialog(frame,
						"Book Returned without fine");
			else
				JOptionPane.showMessageDialog(frame, "Book Returned with Rs."
						+ (no_d * 2) + " fine");
			clear();
		} catch (Exception ex) {
			clear();
			JOptionPane.showMessageDialog(frame, ex.getMessage());
		}
	}
}
