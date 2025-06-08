package rdv;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.border.LineBorder;


import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BooksManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField TbxSearch;
	private JTable TblBooks;
	private DefaultTableModel model;
	private JTextField TbxTitle;
	private JTextField TbxAuthor;
	private JComboBox CbxCat;
	private JPanel panel;
	private JButton BtnAdd;
	private JButton BtnUpdate;
	private int selectedBookId;
	private LibraryManagement lm;

	/**
	 * Create the frame.
	 */
	public BooksManagement(LibraryManagement lm) {
		this.lm = lm;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("BOOKS MANAGEMENT");
		lblNewLabel.setFont(new Font("Century Gothic", Font.BOLD, 22));
		lblNewLabel.setBounds(10, 11, 437, 28);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("SEARCH");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(684, 18, 64, 14);
		contentPane.add(lblNewLabel_1);
		
		TbxSearch = new JTextField();
		TbxSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchAction();
			}
		});
		TbxSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		TbxSearch.setBounds(744, 15, 230, 20);
		contentPane.add(TbxSearch);
		TbxSearch.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 660, 400);
		contentPane.add(scrollPane);
		
		String[] columnNames = {"ID", "TITLE", "AUTHOR", "CATEGORY"};
		model = new DefaultTableModel(columnNames, 0);
		TblBooks = new JTable(model);
		TblBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableRowAction();
			}
		});
		scrollPane.setViewportView(TblBooks);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBounds(684, 50, 290, 400);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("BOOK INFORMATION");
		lblNewLabel_2.setBounds(74, 11, 141, 19);
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 14));
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("TITLE");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(10, 78, 46, 14);
		panel.add(lblNewLabel_3);
		
		TbxTitle = new JTextField();
		TbxTitle.setBounds(10, 103, 270, 20);
		panel.add(TbxTitle);
		TbxTitle.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("AUTHOR");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1.setBounds(10, 158, 82, 14);
		panel.add(lblNewLabel_3_1);
		
		TbxAuthor = new JTextField();
		TbxAuthor.setColumns(10);
		TbxAuthor.setBounds(10, 183, 270, 20);
		panel.add(TbxAuthor);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("CATEGORY");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1_1.setBounds(10, 242, 82, 14);
		panel.add(lblNewLabel_3_1_1);
		
		String[] categories = {"Fiction", "Science", "Classic", "Programming", "Biography", "History", "Fantasy", "Thriller"};
		CbxCat = new JComboBox(categories);
		CbxCat.setSelectedIndex(-1);
		CbxCat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CbxCat.setBounds(10, 267, 270, 22);
		panel.add(CbxCat);
		
		BtnAdd = new JButton("ADD BOOK");
		BtnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addAction();
			}
		});
		BtnAdd.setBackground(new Color(40, 167, 69)); 
		BtnAdd.setForeground(Color.WHITE);           
		BtnAdd.setFocusPainted(false);
		BtnAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		BtnAdd.setBounds(10, 332, 270, 23);
		panel.add(BtnAdd);
		
		BtnUpdate = new JButton("UPDATE");
		BtnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateAction();
			}
		});
		BtnUpdate.setBackground(new Color(40, 167, 69)); 
		BtnUpdate.setForeground(Color.WHITE);           
		BtnUpdate.setFocusPainted(false);
		BtnUpdate.setEnabled(false);
		BtnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		BtnUpdate.setBounds(10, 366, 270, 23);
		panel.add(BtnUpdate);
		
		LoadBooks();
	}
	
	public void searchAction() {
		String searchValue = TbxSearch.getText();
		
		if (!searchValue.equals("")) {
			SearchBooks(searchValue);
		} else {
			LoadBooks();
		}
	}
	
	public void addAction() {
		String title = TbxTitle.getText();
		String author = TbxAuthor.getText();
		String category = (String)CbxCat.getSelectedItem();
		
		if (!title.equals("") && !author.equals("") && category != null) {
			int confirm = JOptionPane.showConfirmDialog(panel,
					        "Are you sure you want to add " + title.toUpperCase() + "?",
					        "Confirm",
					        JOptionPane.YES_NO_OPTION);
			
			if (confirm == JOptionPane.YES_OPTION) {
				Book newBook = new Book(title, author, category);
				AddNewBook(newBook);
			}
		} else {
			JOptionPane.showMessageDialog(panel, "Fill up all fields...");
		}
	}
	
	public void tableRowAction() {
		int selectedBook = TblBooks.getSelectedRow();

		if (selectedBook >= 0) {
			String idStr = TblBooks.getValueAt(selectedBook, 0).toString();
			selectedBookId = Integer.parseInt(idStr);
			String title = TblBooks.getValueAt(selectedBook, 1).toString();
			String author = TblBooks.getValueAt(selectedBook, 2).toString();
			String category = TblBooks.getValueAt(selectedBook, 3).toString();

			int option = JOptionPane.showOptionDialog(
				TblBooks,
				"What do you want to do with \"" + title.toUpperCase() + "\"?",
				"Options",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				new String[]{"Update", "Delete", "Cancel"},
				"Cancel"
			);
			
			if (option == 0) {
				populate(title, author, category);
			} else if (option == 1) {
				deleteAction(title, selectedBookId);
			}
		}
	}
	
	public void updateAction() {
		String title = TbxTitle.getText();
		String author = TbxAuthor.getText();
		String category = (String)CbxCat.getSelectedItem();
		
		if (!title.equals("") && !author.equals("") && category != null) {
			int option = JOptionPane.showConfirmDialog(
			        panel,
			        "Are you sure you want to save " + title.toUpperCase() + "'s new information?",
			        "Confirm Update",
			        JOptionPane.YES_NO_OPTION
			    );
			
			if (option == JOptionPane.YES_OPTION) {
				Book newInfo = new Book(title, author, category);
				UpdateBook(newInfo, selectedBookId);
			}
		} else {
			JOptionPane.showMessageDialog(panel, "Fill up all fields...");
		}
	}
	
	public void deleteAction(String title, int bookId) {
		int response = JOptionPane.showConfirmDialog(
		        TblBooks,
		        "Are you sure you want to delete " + title.toUpperCase() + "?",
		        "Confirm Deletion",
		        JOptionPane.YES_NO_OPTION
		    );
		
		if (response == JOptionPane.YES_OPTION && bookId != 0) {
			DeleteBook(bookId);
		}
	}
	
	public void LoadBooks() {
		model.setRowCount(0);
		boolean found = false;
		String selectQuery = "SELECT * FROM books";
		
		try {
			Connection conn = DatabaseConn.getConn();
			Statement stmt = conn.createStatement();
			ResultSet res = stmt.executeQuery(selectQuery);
			
			while (res.next()) {
				found = true;
				model.addRow(new Object[] {
						res.getString("bid"),
						res.getString("title").toUpperCase(),
						res.getString("author").toUpperCase(),
						res.getString("category")
				});
			}
			
			if (!found) {
				model.addRow(new Object[] {
						"No books found...", "", "", ""
				});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblBooks, "Failed: " + e.getMessage());
		}
	}
	
	public void SearchBooks(String searchValue) {
		model.setRowCount(0);
		String searchVal = "%" + searchValue.toLowerCase() + "%"; 
		String searchQuery = "SELECT * FROM books WHERE LOWER(title) LIKE ? OR LOWER(author) LIKE ?";

		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement searchPstmt = conn.prepareStatement(searchQuery);
			searchPstmt.setString(1, searchVal);
			searchPstmt.setString(2, searchVal);
			ResultSet res = searchPstmt.executeQuery();

			boolean found = false;
			while (res.next()) {
				found = true;
				model.addRow(new Object[] {
					res.getString("bid"),
					res.getString("title").toUpperCase(),
					res.getString("author").toUpperCase(),
					res.getString("category")
				});
			}

			if (!found) {
				model.addRow(new Object[] {"No books found...", "", "", ""});
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblBooks, "Failed: " + e.getMessage());
		}
	}
	
	public void AddNewBook(Book book) {
		String isExisting = "SELECT * FROM books WHERE LOWER(title) = ? AND LOWER(author) = ?";
		String addNew = "INSERT INTO books (title, author, category) VALUES (?, ?, ?)";

		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement existingPstmt = conn.prepareStatement(isExisting);
			existingPstmt.setString(1, book.getTitle());
			existingPstmt.setString(2, book.getAuthor());
			ResultSet rs = existingPstmt.executeQuery();

			if (rs.next()) {
				JOptionPane.showMessageDialog(TblBooks, "Book already added! Cannot proceed with action. Delete or update existing record.");
			} else {
				PreparedStatement addPstmt = conn.prepareStatement(addNew);
				addPstmt.setString(1, book.getTitle().toLowerCase());
				addPstmt.setString(2, book.getAuthor().toLowerCase());
				addPstmt.setString(3, book.getCategory());

				int addSuccessful = addPstmt.executeUpdate();

				if (addSuccessful > 0) {
					JOptionPane.showMessageDialog(TblBooks, "New book added: " + book.getTitle().toUpperCase());
					LoadBooks();
					clear();
				} else {
					JOptionPane.showMessageDialog(TblBooks, "Failed to add " + book.getTitle().toUpperCase() + "... Try again...");
				}
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblBooks, "Failed: " + e.getMessage());
		}
	}
	
	public void UpdateBook(Book book, int id) {
		String updateQuery = "UPDATE books SET title=?, author=?, category=? WHERE bid=?";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
			updatePstmt.setString(1, book.getTitle().toLowerCase());
			updatePstmt.setString(2, book.getAuthor().toLowerCase());
			updatePstmt.setString(3, book.getCategory());
			updatePstmt.setInt(4, id);
			int affected = updatePstmt.executeUpdate();
			
			if (affected > 0) {
				JOptionPane.showMessageDialog(TblBooks, "Information updated!");
				clear();
				LoadBooks();
				lm.LoadBooksBorrowed();
			} else {
				JOptionPane.showMessageDialog(TblBooks, "Failed to update... try again...");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblBooks, "Failed: " + e.getMessage());
		}
	}
	
	public void DeleteBook(int bookId) {
		String deleteQuery = "DELETE FROM books WHERE bid=?";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement deletePstmt = conn.prepareStatement(deleteQuery);
			deletePstmt.setInt(1, bookId);
			int affected = deletePstmt.executeUpdate();
			
			if (affected > 0) {
				JOptionPane.showMessageDialog(TblBooks, "Successfully deleted book!");
				LoadBooks();
				lm.LoadBooksBorrowed();
			} else {
				JOptionPane.showMessageDialog(TblBooks, " Failed to delete book... Try again...");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblBooks, "Failed: " + e.getMessage());
		}
	}

	public void clear() {
		TbxTitle.setText("");
		TbxAuthor.setText("");
		CbxCat.setSelectedIndex(-1);
		BtnAdd.setEnabled(true);
		BtnUpdate.setEnabled(false);
	}
	
	public void populate(String title, String author, String category) {
		TbxTitle.setText(title);
		TbxAuthor.setText(author);
		CbxCat.setSelectedItem(category);
		BtnAdd.setEnabled(false);
		BtnUpdate.setEnabled(true);
	}
}

class Book {
	String title;
	String author;
	String category;
	
	public Book(String title, String author, String category) {
		this.title = title;
		this.author = author;
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getCategory() {
		return category;
	}
}
