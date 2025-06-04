package rdv;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.FileAlreadyExistsException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MembersManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField TbxSearch;
	private JTextField TbxLname;
	private JTextField TbxFname;
	private JTextField TbxMI;
	private JTextField TbxExtension;
	private JTextField TbxAddress;
	private JTextField TbxPhoneNo;
	private JButton BtnAdd;
	private JButton BtnUpdate;
	private JTable TblMembers;
	private JPanel panel;
	private DefaultTableModel model;
	private int selectedMemberId;
	private LibraryManagement libMgmt;


	/**
	 * Create the frame.
	 */
	public MembersManagement(LibraryManagement libMgmt) {
		this.libMgmt = libMgmt;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 500);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblMembersManagement = new JLabel("MEMBERS MANAGEMENT");
		lblMembersManagement.setFont(new Font("Century Gothic", Font.BOLD, 22));
		lblMembersManagement.setBounds(10, 11, 437, 28);
		contentPane.add(lblMembersManagement);
		
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
		TbxSearch.setColumns(10);
		TbxSearch.setBounds(744, 15, 230, 20);
		contentPane.add(TbxSearch);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 50, 660, 400);
		contentPane.add(scrollPane);
		
		String[] columnNames = {"ID", "NAME", "DATE ADDED"};
		model = new DefaultTableModel(columnNames, 0);
		TblMembers = new JTable(model);
		TblMembers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableRowAction();
			}
		});
		scrollPane.setViewportView(TblMembers);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new LineBorder(Color.BLACK));
		panel.setBounds(684, 50, 290, 400);
		contentPane.add(panel);
		
		JLabel lblNewLabel_2 = new JLabel("MEMBER INFORMATION");
		lblNewLabel_2.setFont(new Font("Century Gothic", Font.BOLD, 14));
		lblNewLabel_2.setBounds(67, 11, 156, 19);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("LAST NAME");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(10, 41, 185, 14);
		panel.add(lblNewLabel_3);
		
		TbxLname = new JTextField();
		TbxLname.setColumns(10);
		TbxLname.setBounds(10, 66, 270, 20);
		panel.add(TbxLname);
		
		JLabel lblNewLabel_3_1 = new JLabel("FIRST NAME");
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1.setBounds(10, 97, 82, 14);
		panel.add(lblNewLabel_3_1);
		
		TbxFname = new JTextField();
		TbxFname.setColumns(10);
		TbxFname.setBounds(10, 122, 270, 20);
		panel.add(TbxFname);
		
		BtnAdd = new JButton("ADD MEMBER");
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
		BtnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		BtnUpdate.setEnabled(false);
		BtnUpdate.setBounds(10, 366, 270, 23);
		panel.add(BtnUpdate);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("MIDDLE INITAIL");
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1_1.setBounds(10, 153, 131, 14);
		panel.add(lblNewLabel_3_1_1);
		
		TbxMI = new JTextField();
		TbxMI.setColumns(10);
		TbxMI.setBounds(10, 178, 131, 20);
		panel.add(TbxMI);
		
		JLabel lblNewLabel_3_1_1_1 = new JLabel("NAME EXTENSION");
		lblNewLabel_3_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1_1_1.setBounds(149, 153, 131, 14);
		panel.add(lblNewLabel_3_1_1_1);
		
		TbxExtension = new JTextField();
		TbxExtension.setColumns(10);
		TbxExtension.setBounds(149, 178, 131, 20);
		panel.add(TbxExtension);
		
		JLabel lblNewLabel_3_1_2 = new JLabel("ADDRESS");
		lblNewLabel_3_1_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1_2.setBounds(10, 209, 82, 14);
		panel.add(lblNewLabel_3_1_2);
		
		TbxAddress = new JTextField();
		TbxAddress.setColumns(10);
		TbxAddress.setBounds(10, 234, 270, 20);
		panel.add(TbxAddress);
		
		JLabel lblNewLabel_3_1_2_1 = new JLabel("CONTACT NO.");
		lblNewLabel_3_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3_1_2_1.setBounds(10, 265, 103, 14);
		panel.add(lblNewLabel_3_1_2_1);
		
		TbxPhoneNo = new JTextField();
		TbxPhoneNo.setColumns(10);
		TbxPhoneNo.setBounds(10, 290, 270, 20);
		panel.add(TbxPhoneNo);
		
		LoadMembers();
	}
	
	public void addAction() {
		String fname = TbxFname.getText();
		String lname = TbxLname.getText();
		String mi = TbxMI.getText();
		String ex = TbxExtension.getText();
		String phone = TbxPhoneNo.getText();
		String address = TbxAddress.getText();
		String pattern = "^09\\d{0,9}$";
		
		if (!fname.equals("") && !lname.equals("") && !mi.equals("") && !phone.equals("") && !address.equals("") && phone.matches(pattern)) {
			Member member = new Member(lname, fname, mi, ex, phone, address);
			AddMember(member);
		} else {
			JOptionPane.showMessageDialog(panel, "Fill up all fields or invalid format...");
		}
	}
	
	public void updateAction() {
		String fname = TbxFname.getText();
		String lname = TbxLname.getText();
		String mi = TbxMI.getText();
		String ex = TbxExtension.getText();
		String phone = TbxPhoneNo.getText();
		String address = TbxAddress.getText();
		String pattern = "^09\\d{0,9}$";
		String name = lname + ", " +fname + " " + mi + ". " + ex;
		
		if (!fname.equals("") && !lname.equals("") && !mi.equals("") && !phone.equals("") && !address.equals("") && phone.matches(pattern)) {
			int response = JOptionPane.showConfirmDialog(
			        panel,
			        "Are you sure you want to update " + name.toUpperCase() + "?",
			        "Confirm",
			        JOptionPane.YES_NO_OPTION
			    );
			
			if (response == JOptionPane.YES_OPTION) {
				Member member = new Member(lname, fname, mi, ex, phone, address);
				UpdateMember(member, selectedMemberId);
			}
		} else {
			JOptionPane.showMessageDialog(panel, "Fill up all fields or invalid format...");
		}
	}
	
	public void searchAction() {
		String searchValue = TbxSearch.getText();
		
		if (!searchValue.equals("")) {
			SearchMember(searchValue);
		} else {
			LoadMembers();
		}
	}
	
	public void tableRowAction() {
		int selectedBook = TblMembers.getSelectedRow();
		
		if (selectedBook >= 0) {
			String idStr = TblMembers.getValueAt(selectedBook, 0).toString();
			selectedMemberId = Integer.parseInt(idStr);
			String name = TblMembers.getValueAt(selectedBook, 1).toString();
			
			int option = JOptionPane.showOptionDialog(
					TblMembers,
					"What do you want to do with \"" + name.toUpperCase() + "\"?",
					"Options",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					new String[]{"Update", "Delete", "Add Borrowed Entry", "Cancel"},
					"Cancel"
				);
			
			if (option == 0) {
				GetMemberInfo(selectedMemberId);
			} else if (option == 1) {
				int response = JOptionPane.showConfirmDialog(
				        TblMembers,
				        "Are you sure you want to delete " + name.toUpperCase() + "?",
				        "Confirm Deletion",
				        JOptionPane.YES_NO_OPTION
				    );
				
				if (response == JOptionPane.YES_OPTION) {
					DeleteMember(selectedMemberId);
				}
			} else if (option == 2) {
				new BorrowedEntry(this, selectedMemberId, name, libMgmt).setVisible(true);
			}
		}
	}
	
	public void clear() {
		TbxFname.setText("");
		TbxLname.setText("");
		TbxMI.setText("");
		TbxExtension.setText("");
		TbxAddress.setText("");
		TbxPhoneNo.setText("");
	}
	
	public void LoadMembers() {
		model.setRowCount(0);
		boolean found = false;
		String selectQuery = "SELECT * FROM members";

		try {
			Connection conn = DatabaseConn.getConn();
			Statement selectStmt = conn.createStatement();
			ResultSet res = selectStmt.executeQuery(selectQuery);

			while (res.next()) {
				found = true;
				String extension = res.getString("extension");
				if (extension == null) extension = "";

				String name = res.getString("lname").toUpperCase() + ", " +
				              res.getString("fname").toUpperCase() + " " +
				              res.getString("middleinitial").toUpperCase() + ". " +
				              extension.toUpperCase();

				model.addRow(new Object[] {
					res.getInt("id"),
					name.trim(),
					res.getDate("DTAdded")  
				});
			}

			if (!found) {
				model.addRow(new Object[] {"No records found...", "", "", "", ""});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblMembers, "Failed: " + e.getMessage());
		}
	}
	
	public void AddMember(Member mem) {
		boolean found = false;
		String nameValue = "%" + mem.getLname().toLowerCase() + mem.getFname().toLowerCase() + mem.getMI().toLowerCase() + mem.getExtension().toLowerCase() + "%";
		String isExisting = "SELECT * FROM members WHERE CONCAT(lname, fname, middleinitial, extension) LIKE ?";
		String addQuery = "INSERT INTO members (lname, fname, middleinitial, extension, address, phone) VALUES (?,?,?,?,?,?)";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement existingPstmt = conn.prepareStatement(isExisting);
			existingPstmt.setString(1, nameValue);
			ResultSet res = existingPstmt.executeQuery();
			
			while (res.next()) {
				found = true;
				JOptionPane.showMessageDialog(panel, "Member already exist, cannot proceed with action. Add or delete current record...");
			}
			
			if (!found) {
				PreparedStatement addPstmt = conn.prepareStatement(addQuery);
				addPstmt.setString(1, mem.getLname().toLowerCase());
				addPstmt.setString(2, mem.getFname().toLowerCase());
				addPstmt.setString(3, mem.getMI().toLowerCase());
				addPstmt.setString(4, mem.getExtension().toLowerCase());
				addPstmt.setString(5, mem.getAddress().toLowerCase());
				addPstmt.setString(6, mem.getPhone());
				int affected = addPstmt.executeUpdate();
				
				if (affected > 0) {
					JOptionPane.showMessageDialog(TblMembers, "New member alert: " + mem.getLname().toUpperCase() + ", " + mem.getFname().toUpperCase() + " " + mem.getMI().toUpperCase() + ". " + mem.getExtension().toUpperCase());
					clear();
					LoadMembers();
				} else {
					JOptionPane.showMessageDialog(TblMembers, "FAILED TO ADD: " + mem.getLname().toUpperCase() + ", " + mem.getFname().toUpperCase() + " " + mem.getMI().toUpperCase() + ". " + mem.getExtension().toUpperCase());
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblMembers, "FAILED: " + e.getMessage());
		}
	}
	
	public void GetMemberInfo(int memberId) {
		boolean found = false;
		String getInfoquery = "SELECT * FROM members WHERE id=?";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement getPstmt = conn.prepareStatement(getInfoquery);
			getPstmt.setInt(1, memberId);
			ResultSet res = getPstmt.executeQuery();
			
			while (res.next()) {
				found = true;
				
				TbxLname.setText(res.getString("lname").toUpperCase());
				TbxFname.setText(res.getString("fname").toUpperCase());
				TbxMI.setText(res.getString("middleinitial").toUpperCase());
				TbxExtension.setText(res.getString("extension").toUpperCase());
				TbxPhoneNo.setText(res.getString("phone"));
				TbxAddress.setText(res.getString("address").toUpperCase());
				
				BtnAdd.setEnabled(false);
				BtnUpdate.setEnabled(true);
			}
			
			if (!found) {
				JOptionPane.showMessageDialog(TblMembers, "Failed to ftech data... Try again...");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblMembers, "Failed: " + e.getMessage());
		}
	}
	
	public void SearchMember(String searchValue) {
		model.setRowCount(0);
		boolean found = false;
		String search = "%" + searchValue + "%";
		String searchQuery = "SELECT * FROM members WHERE CONCAT(lname, fname, middleinitial, extension) LIKE ?";
		
		try {
			Connection conn  = DatabaseConn.getConn();
			PreparedStatement searchPstmt = conn.prepareStatement(searchQuery);
			searchPstmt.setString(1, search);
			ResultSet res = searchPstmt.executeQuery();
			
			while (res.next()) {
				found = true;
				String extension = res.getString("extension");
				if (extension == null) extension = "";

				String name = res.getString("lname").toUpperCase() + ", " +
				              res.getString("fname").toUpperCase() + " " +
				              res.getString("middleinitial").toUpperCase() + ". " +
				              extension.toUpperCase();

				model.addRow(new Object[] {
					res.getInt("id"),
					name.trim(),
					res.getDate("DTAdded")  
				});
			}

			if (!found) {
				model.addRow(new Object[] {"No records found...", "", "", "", ""});
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblMembers, "Failed: " + e.getMessage());
		}
	}
	
	public void DeleteMember(int memberId) {
		String deleteQuery = "DELETE FROM members WHERE id=?";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement deletePstmt = conn.prepareStatement(deleteQuery);
			deletePstmt.setInt(1, memberId);
			int affected = deletePstmt.executeUpdate();
			
			if (affected > 0) {
				JOptionPane.showMessageDialog(TblMembers, "Successfully deleted member!");
				LoadMembers();
			} else {
				JOptionPane.showMessageDialog(TblMembers, "Failed to delete member... Try again...");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblMembers, "Failed: " + e.getMessage());
		}
	}
	
	public void UpdateMember(Member mem, int memberId) {
		String updateQuery = "UPDATE members SET lname=?, fname=?, middleinitial=?, extension=?, phone=?, address=? WHERE id=?";
		
		try {
			Connection conn = DatabaseConn.getConn();
			PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
			updatePstmt.setString(1, mem.getLname().toLowerCase());
			updatePstmt.setString(2, mem.getFname().toLowerCase());
			updatePstmt.setString(3, mem.getMI().toLowerCase());
			updatePstmt.setString(4, mem.getExtension().toLowerCase());
			updatePstmt.setString(5, mem.getPhone());
			updatePstmt.setString(6, mem.getAddress().toLowerCase());
			updatePstmt.setInt(7, memberId);
			int affected = updatePstmt.executeUpdate();
			
			String name = mem.getLname().toUpperCase() + ", " + mem.getFname().toUpperCase() + " " + mem.getMI().toUpperCase() + ". " + mem.getExtension().toUpperCase();
			
			if (affected > 0) {
				JOptionPane.showMessageDialog(TblMembers, "Successfully updated: " + name);
				clear();
				LoadMembers();
				BtnAdd.setEnabled(true);
				BtnUpdate.setEnabled(false);
			} else {
				JOptionPane.showMessageDialog(TblMembers, "Failed to update: " + name);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(TblMembers, "Failed: " + e.getMessage());
		}
	}

}

class Member {
	String lastName;
	String firstName;
	String middleInitial;
	String extension;
	String phone;
	String address;
	
	public Member(String lname, String fname, String mi, String ex, String phone, String address) {
		this.lastName = lname;
		this.firstName = fname;
		this.middleInitial = mi;
		this.extension = ex;
		this.phone = phone;
		this.address = address;
	}
	
	public String getLname() {
		return lastName;
	}
	
	public String getFname() {
		return firstName;
	}
	
	public String getMI() {
		return middleInitial;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getAddress() {
		return address;
	}
}
