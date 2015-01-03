import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class ListPanel extends JPanel {
	JTable table;
	JTextField mem_id;
	JScrollPane sp;
	JButton submit;
	JPanel n_panel, c_panel, s_panel;
	JFrame frame;
	String colNa[] = { "Book Access No", "Fine" };
	Object[][] data = { { null, null }, { null, null }, { null, null },
			{ null, null }, { null, null } };

	public ListPanel() {
		setLayout(new BorderLayout());

		n_panel = new JPanel(new FlowLayout());
		c_panel = new JPanel(new FlowLayout());
		s_panel = new JPanel(new FlowLayout());

		mem_id = new JTextField("             ");
		submit = new JButton("Submit");

		n_panel.add(new JLabel("Member ID"));
		n_panel.add(mem_id);

		table = new JTable(data, colNa);
		sp = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		c_panel.add(sp);
		s_panel.add(submit);

		n_panel.setVisible(true);
		c_panel.setVisible(true);
		s_panel.setVisible(true);

		add(n_panel, BorderLayout.NORTH);
		add(c_panel, BorderLayout.CENTER);
		add(s_panel, BorderLayout.SOUTH);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buttonPressed();
			}
		});
	}

	public int calcFine(int bno) {
		String is_dt, ret_dt = "";
		ResultSet rs1;
		int no_d = 0;
		try {
			Connectify.pstmt = Connectify.con
					.prepareStatement("select issue_dt,ret_dt from issue where m_id="
							+ mem_id.getText() + " and b_ano=" + bno + ";");
			rs1 = Connectify.pstmt.executeQuery();
			while (rs1.next()) {
				is_dt = rs1.getString(1);
				ret_dt = rs1.getString(2);
			}
			Connectify.pstmt = Connectify.con
					.prepareStatement("select datediff(curdate(),'"
							+ ret_dt.trim() + "');");
			rs1 = Connectify.pstmt.executeQuery();
			while (rs1.next())
				no_d = rs1.getInt(1);
		} catch (Exception e) {
		} finally {
			return (no_d * 2);
		}
	}

	public void buttonPressed() {
		int ct = 0, flag = 0, b_no = 0;
		try {
			Connectify.pstmt = Connectify.con
					.prepareStatement("select b_ano from issue where m_id="
							+ mem_id.getText().trim() + ";");
			Connectify.rs = Connectify.pstmt.executeQuery();
			while (Connectify.rs.next()) {
				flag++;
				b_no = Connectify.rs.getInt(1);
				
				Object x = String.valueOf(b_no);
				Object y = String.valueOf(calcFine(b_no));
				
				table.getModel().setValueAt(x, ct, 0);
				table.getModel().setValueAt(y, ct, 1);
				ct++;
			}
		} catch (Exception e) {
			if (flag == 0)
				JOptionPane.showMessageDialog(frame,
						"Member ID hasnt been issued any book");
		}
	}
}
