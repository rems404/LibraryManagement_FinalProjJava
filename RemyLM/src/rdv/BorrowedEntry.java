package rdv;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BorrowedEntry extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField TbxBook;
	private JButton BtnAdd;
	private MembersManagement mlParent;
	private int selectedBookId;
	private LibraryManagement lmParent;

	/**
	 * Create the frame.
	 */
	public BorrowedEntry(MembersManagement membersManagement, int memberId, String name, LibraryManagement lm) {
		this.lmParent = lm;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 503, 311);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("NEW BOOK BORROWED ENTRY");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 20));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("BORROWER:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(29, 24, 418, 14);
		panel_1.add(lblNewLabel_1);
		
		JTextField TbxName = new JTextField(name);
		TbxName.setEditable(false);
		TbxName.setBounds(29, 49, 418, 20);
		panel_1.add(TbxName);
		TbxName.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("BOOK BORROWED (ENTER TO SEARCH):");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(29, 100, 418, 14);
		panel_1.add(lblNewLabel_1_1);
		
		TbxBook = new JTextField();
		TbxBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchValue = TbxBook.getText();
				
				if (!searchValue.equals("")) {
					SearchBook(searchValue);
				}
			}
		});
		TbxBook.setColumns(10);
		TbxBook.setBounds(29, 125, 418, 20);
		panel_1.add(TbxBook);
		
		BtnAdd = new JButton("ADD ENTRY");
		BtnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bookTitle = TbxBook.getText();
				if (selectedBookId != 0) {
					AddEntry(memberId, selectedBookId, name, bookTitle);
				} else {
					JOptionPane.showMessageDialog(contentPane, "No selected book... Try again...");
				}
			}
		});
		BtnAdd.setBackground(new Color(40, 167, 69)); 
		BtnAdd.setForeground(Color.WHITE);           
		BtnAdd.setFocusPainted(false);
		BtnAdd.setEnabled(false);
		BtnAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		BtnAdd.setBounds(29, 176, 418, 23);
		panel_1.add(BtnAdd);
	}
	
	public void SearchBook(String searchValue) {
		String search = "%" + searchValue + "%";
		String searchQuery = "SELECT * FROM books WHERE title LIKE ?";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement searchPstmt = conn.prepareStatement(searchQuery);
			searchPstmt.setString(1, search);
			ResultSet res = searchPstmt.executeQuery();
			
			while (res.next()) {
				TbxBook.setText(res.getString("title").toUpperCase());
				selectedBookId = res.getInt("bid");
				BtnAdd.setEnabled(true);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(contentPane, "Failed: " + e.getMessage());
		}
	}
	
	public void AddEntry(int memberId, int bookId, String name, String book) {
		boolean found = false;
		String hasBorrowed = "SELECT * FROM borrowed WHERE bookBorrowed=? AND borrower=? AND status='borrowed'";
		String addQuery = "INSERT INTO borrowed (bookBorrowed, borrower) VALUES (?, ?)";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement hasBorrowedPstmt = conn.prepareStatement(hasBorrowed);
			hasBorrowedPstmt.setInt(1, bookId);
			hasBorrowedPstmt.setInt(2, memberId);
			ResultSet res = hasBorrowedPstmt.executeQuery();
			
			while (res.next()) {
				found = true;
				JOptionPane.showMessageDialog(contentPane, name.toUpperCase() + " has borrowed " + book.toUpperCase() + "and is not yet returned. Cannot proceed with action.");
			}
			
			if (!found) {
				PreparedStatement addPstmt = conn.prepareStatement(addQuery);
				addPstmt.setInt(1, bookId);
				addPstmt.setInt(2, memberId);
		        int rows = addPstmt.executeUpdate();
	
		        if (rows > 0) {
		        	JOptionPane.showMessageDialog(this, "Borrowing recorded successfully!");
		        	lmParent.LoadBooksBorrowed();
		        	dispose(); 
		        } else {
		        	JOptionPane.showMessageDialog(this, "Failed to record borrowing.");
		        }
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
		}
	}

}
