package rdv;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.PublicKey;

import javax.swing.JTextField;

public class LibraryManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable TblRecords;
	private DefaultTableModel model;
	private int selectedRecordId;
	private JTextField TbxSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryManagement frame = new LibraryManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LibraryManagement() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 711, 477);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel topPanel = new JPanel();
		contentPane.add(topPanel, BorderLayout.NORTH);
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("LIBRARY MANAGEMENT SYSTEM");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 22));
		topPanel.add(lblNewLabel);
		
		Component horizontalStrut = Box.createHorizontalStrut(70);
		topPanel.add(horizontalStrut);
		
		JButton BtnBooks = new JButton("BOOKS");
		BtnBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BooksManagement bm = new BooksManagement(); 
				bm.setVisible(true);
			}
		});
		BtnBooks.setBackground(new Color(40, 167, 69)); 
		BtnBooks.setForeground(Color.WHITE);           
		BtnBooks.setFocusPainted(false);
		BtnBooks.setFont(new Font("Tahoma", Font.PLAIN, 12));
		topPanel.add(BtnBooks);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(20);
		topPanel.add(horizontalStrut_1);
		
		JButton BtnMembers = new JButton("MEMBERS");
		BtnMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MembersManagement mm = new MembersManagement(LibraryManagement.this); 
				mm.setVisible(true);
			}
		});
		BtnMembers.setBackground(new Color(40, 167, 69)); 
		BtnMembers.setForeground(Color.WHITE);           
		BtnMembers.setFocusPainted(false);
		BtnMembers.setFont(new Font("Tahoma", Font.PLAIN, 12));
		topPanel.add(BtnMembers);
		
		Component horizontalStrut_1_1 = Box.createHorizontalStrut(20);
		topPanel.add(horizontalStrut_1_1);
		
		JLabel lblNewLabel_2 = new JLabel("SEARCH");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		topPanel.add(lblNewLabel_2);
		
		TbxSearch = new JTextField();
		TbxSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchAction();
			}
		});
		topPanel.add(TbxSearch);
		TbxSearch.setColumns(30);
		
		JPanel botPanel = new JPanel();
		contentPane.add(botPanel, BorderLayout.SOUTH);
		
		JLabel lblNewLabel_1 = new JLabel("PROGRAMMED BY: VENTURA, REMY DIANNE I. | BSIT-3A");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 11));
		botPanel.add(lblNewLabel_1);
		
		JScrollPane tablePane = new JScrollPane();
		contentPane.add(tablePane, BorderLayout.CENTER);
		
		String[] columnNames = {"ID", "BOOK BORROWED", "BORROWER", "DATE BORROWED", "DATE RETURNED", "STATUS"};
		model = new DefaultTableModel(columnNames, 0);
		TblRecords = new JTable(model);
		TblRecords.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableRowAction();
			}
		});
		tablePane.setViewportView(TblRecords);
		
		LoadBooksBorrowed();
	}
	
	public void searchAction() {
		String search =  TbxSearch.getText();
		
		if (!search.equals("")) {
			searchRecord(search);
		} else {
			LoadBooksBorrowed();
		}
	}
	
	public void tableRowAction() {
		int selectedBook = TblRecords.getSelectedRow();
		
		if (selectedBook >= 0) {
			String idStr = TblRecords.getValueAt(selectedBook, 0).toString();
			String book = TblRecords.getValueAt(selectedBook, 1).toString();
			String borrower = TblRecords.getValueAt(selectedBook, 2).toString();
			selectedRecordId = Integer.parseInt(idStr);
			
			int option = JOptionPane.showOptionDialog(
					TblRecords,
					book + " borrowed by " + borrower,
					"Options",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					new String[]{"Returned", "Cancel"},
					"Cancel"
				);
			
			if (option == 0) {
				int response = JOptionPane.showConfirmDialog(
				        TblRecords,
				        "Book returned?",
				        "Confirm",
				        JOptionPane.YES_NO_OPTION
				    );
				
				if (response == JOptionPane.YES_OPTION) {
					updateReturnedBook(selectedRecordId);
				}
			} 
		}
	}
	
	public void LoadBooksBorrowed() {
		model.setRowCount(0);
		boolean found = false;
		String selectQuery = "SELECT borrowed.id AS brid, borrowed.bookBorrowed AS bbid, borrowed.status AS stat, CONCAT(books.title, ' - ', books.author, ' (', books.category, ')') AS book, borrowed.dateBorrowed AS DTBorrowed, borrowed.DTreturned AS DTReturned, borrowed.borrower AS member, books.bid AS bid, CONCAT(members.lname, ', ', members.fname, ' ', members.middleinitial, '. ', members.extension) AS name"
		        + " FROM borrowed JOIN books ON borrowed.bookBorrowed = books.bid JOIN members ON borrowed.borrower = members.id ORDER BY DTBorrowed DESC";
		
		try {
			Connection conn = DatabaseConn.getConn();
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(selectQuery);
			
			while (res.next()) {
				found = true;
				model.addRow(new Object[] {
						res.getInt("brid"),
						res.getString("book").toUpperCase(),
						res.getString("name").toUpperCase(),
						res.getDate("DTBorrowed"),
						res.getDate("DTReturned"),
						res.getString("stat").toUpperCase()
				});
			}
			
			if (!found) {
				model.addRow(new Object[]{"No records found.", "", "", "", "", ""});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblRecords, "Failed: " + e.getMessage());
		}
	}
	
	public void updateReturnedBook(int recordId) {
		String updateQuery = "UPDATE borrowed SET DTreturned=NOW(), status='returned' WHERE id=?";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
			updatePstmt.setInt(1, recordId);
			int affected = updatePstmt.executeUpdate();
			
			if ( affected > 0) {
				JOptionPane.showMessageDialog(TblRecords, "Updated status to RETURNED");
				LoadBooksBorrowed();
			} else {
				JOptionPane.showMessageDialog(TblRecords, "Failed to update... Try again...");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblRecords, "Failed: " + e.getMessage());
		}
	}
	
	public void searchRecord(String searchValue) {
		model.setRowCount(0);
		String search = "%" + searchValue + "%";
		boolean found = false;
		String searchQuery = "SELECT borrowed.id AS brid, borrowed.bookBorrowed AS bbid, borrowed.status AS stat, CONCAT(books.title, ' - ', books.author, ' (', books.category, ')') AS book, borrowed.dateBorrowed AS DTBorrowed, borrowed.DTreturned AS DTReturned, borrowed.borrower AS member, books.bid AS bid, CONCAT(members.lname, ', ', members.fname, ' ', members.middleinitial, '. ', members.extension) AS name"
		        + " FROM borrowed JOIN books ON borrowed.bookBorrowed = books.bid JOIN members ON borrowed.borrower = members.id WHERE CONCAT(books.title, books.author, members.lname, members.fname, members.middleinitial, members.extension) LIKE ?";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement searchPstmt = conn.prepareStatement(searchQuery);
			searchPstmt.setString(1, search);
			ResultSet res = searchPstmt.executeQuery();
			
			while (res.next()) {
				found = true;
				model.addRow(new Object[] {
						res.getInt("brid"),
						res.getString("book").toUpperCase(),
						res.getString("name").toUpperCase(),
						res.getDate("DTBorrowed"),
						res.getDate("DTReturned"),
						res.getString("stat").toUpperCase()
				});
			}
			
			if (!found) {
				model.addRow(new Object[]{"No records found.", "", "", "", "", ""});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblRecords, "Failed: " + e.getMessage());
		}
	}

}
