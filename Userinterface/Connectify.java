import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Connectify {
	public static Connection con;
	public static PreparedStatement pstmt;
	public static Statement stmt;
	public static ResultSet rs;

	public static void main(String args[]) {
		String url, dbna, driver, una, pass;
		JFrame frame = null;

		url = "jdbc:mysql://localhost:3306/";
		dbna = "opac";
		driver = "com.mysql.jdbc.Driver";
		una = "root";
		pass = "";

		try {
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url + dbna, una, pass);
			// JOptionPane.showMessageDialog(frame,"Connection Established");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					MainFrame mainFrame = new MainFrame();
					mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					mainFrame.setVisible(true);
					mainFrame.setAlwaysOnTop(true);
				}
			});
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Connection not established");
			e.printStackTrace();
		}
	}
}

class MainFrame extends JFrame {
	public JMenuBar menuBar;
	public JMenu entry, search;
	public JMenuItem book, member, title, author, issue, ret, list;
	public JPanel cur;
	public JFrame frame;

	public MainFrame() {
		setTitle("OPAC");
		setSize(500, 500);

		frame = null;

		cur = new JPanel();
		menuBar = new JMenuBar();

		entry = new JMenu("Entry");
		search = new JMenu("Search");
		issue = new JMenuItem("Issue");
		ret = new JMenuItem("Return");
		list = new JMenuItem("List");
		issue.setMaximumSize(new Dimension(50, 50));
		ret.setMaximumSize(new Dimension(50, 50));

		menuBar.add(entry);
		menuBar.add(search);
		menuBar.add(issue);
		menuBar.add(ret);
		menuBar.add(list);

		book = new JMenuItem("Books");
		member = new JMenuItem("Member");
		title = new JMenuItem("Title");
		author = new JMenuItem("Author");

		entry.add(book);
		entry.add(member);

		search.add(title);
		search.add(author);

		add(menuBar, BorderLayout.NORTH);

		book.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				entry_pressed(event, 1);
			}
		});

		member.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				entry_pressed(event, 2);
			}
		});

		title.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				title_pressed(event);
			}
		});

		author.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				author_pressed(event);
			}
		});

		issue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				issue_pressed(event);
			}
		});

		ret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ret_pressed(event);
			}
		});

		list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				list_pressed(event);
			}
		});

	}

	public void list_pressed(ActionEvent e) {
		getContentPane().remove(cur);
		ListPanel lsp = new ListPanel();
		cur = lsp;
		getContentPane().add(cur, BorderLayout.CENTER);
		getContentPane().validate();
		getContentPane().repaint();
	}

	public void author_pressed(ActionEvent e) {
		getContentPane().remove(cur);
		AuthorSearchPanel tsp = new AuthorSearchPanel();
		cur = tsp;
		getContentPane().add(cur, BorderLayout.CENTER);
		getContentPane().validate();
		getContentPane().repaint();
	}

	public void title_pressed(ActionEvent e) {
		getContentPane().remove(cur);
		TitleSearchPanel tsp = new TitleSearchPanel();
		cur = tsp;
		getContentPane().add(cur, BorderLayout.CENTER);
		getContentPane().validate();
		getContentPane().repaint();
	}

	public void entry_pressed(ActionEvent e, int val) {
		String ip = JOptionPane.showInputDialog(frame, "Enter Password");
		if ((ip != null) && (ip.compareTo("123") == 0)) {
			if (val == 1) {
				getContentPane().remove(cur);
				BookPanel mypanel = new BookPanel();
				cur = mypanel;
				getContentPane().add(cur, BorderLayout.CENTER);
				getContentPane().validate();
				getContentPane().repaint();
			} else if (val == 2) {
				getContentPane().remove(cur);
				MemberPanel mypanel = new MemberPanel();
				cur = mypanel;
				getContentPane().add(cur, BorderLayout.CENTER);
				getContentPane().validate();
				getContentPane().repaint();
			}
		} else
			JOptionPane.showMessageDialog(frame, "Invalid Password");
	}

	public void issue_pressed(ActionEvent e) {
		getContentPane().remove(cur);
		IssuePanel ip = new IssuePanel();
		cur = ip;
		getContentPane().add(cur, BorderLayout.CENTER);
		getContentPane().validate();
		getContentPane().repaint();
	}

	public void ret_pressed(ActionEvent e) {
		getContentPane().remove(cur);
		ReturnPanel rp = new ReturnPanel();
		cur = rp;
		getContentPane().add(cur, BorderLayout.CENTER);
		getContentPane().validate();
		getContentPane().repaint();
	}
}
