package school;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;


import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class schoolWindow {

	private JFrame frame;
	private JTextField txtname;
	private JTextField txtmobile;
	private JTextField txtcourse;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					schoolWindow window = new schoolWindow();
					window.frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	
	
	
	public schoolWindow() {
		initialize();
		table_update();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	
	
	Connection con1;
	PreparedStatement insert;
	
	private void table_update()
	{
		int c;
		
		try {
			
			
			Class.forName("com.mysql.jdbc.Driver");
			con1 = DriverManager.getConnection("jdbc:mysql://localhost/school","root","");
			
			insert = con1.prepareStatement("select * from record");
			ResultSet rs = insert.executeQuery();
			ResultSetMetaData Rss = (ResultSetMetaData) rs.getMetaData();
			c = Rss.getColumnCount();
			
			DefaultTableModel Df = (DefaultTableModel)table.getModel();
 			Df.setRowCount(0);
 			
 			while(rs.next())
 			{
 				Vector<String> v2 = new Vector<String>();
 				
 				for(int a=1; a<=c; a++)
 				{
 					v2.add(rs.getString("id"));
 					v2.add(rs.getString("name"));
 					v2.add(rs.getString("mobile"));
 					v2.add(rs.getString("course"));
 				}
 				
 				Df.addRow(v2);
 			}
			
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void initialize() {
		
	
		
		frame = new JFrame();
		frame.setBounds(100, 100, 580, 359);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STUDENT REGISTRATION");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(175, 23, 221, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 61, 243, 216);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Student Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(10, 40, 80, 14);
		panel.add(lblNewLabel_1);
		
		txtname = new JTextField();
		txtname.setBounds(100, 37, 133, 20);
		panel.add(txtname);
		txtname.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Mobile");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_1.setBounds(10, 78, 80, 14);
		panel.add(lblNewLabel_1_1);
		
		txtmobile = new JTextField();
		txtmobile.setColumns(10);
		txtmobile.setBounds(100, 75, 133, 20);
		panel.add(txtmobile);
		
		JLabel lblNewLabel_1_2 = new JLabel("Course");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1_2.setBounds(10, 119, 80, 14);
		panel.add(lblNewLabel_1_2);
		
		txtcourse = new JTextField();
		txtcourse.setColumns(10);
		txtcourse.setBounds(100, 116, 133, 20);
		panel.add(txtcourse);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = txtname.getText();
				String mobile = txtmobile.getText();
				String course = txtcourse.getText();
				
				
				
				
				try {
					
					
					Class.forName("com.mysql.jdbc.Driver");
					con1 = DriverManager.getConnection("jdbc:mysql://localhost/school","root","");
					
					insert = con1.prepareStatement("insert into record(name,mobile,course)values(?,?,?)");
					insert.setString(1, name);
					insert.setString(2, mobile);
					insert.setString(3, course);
					insert.executeUpdate();
					
					JOptionPane.showMessageDialog( btnAdd, "Record Added", "Message", 0);
					table_update();
					
					txtname.setText("");
					txtmobile.setText("");
					txtcourse.setText("");
					
					txtname.requestFocus();
					
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(13, 166, 66, 23);
		panel.add(btnAdd);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel Df = (DefaultTableModel)table.getModel();
				int selectedIndex = table.getSelectedRow();
				
				try {
					
                	int id = Integer.parseInt(Df.getValueAt(selectedIndex, 0).toString());
                	
                	int dialogResult = JOptionPane.showConfirmDialog(null, "Do you want to delete the record", "Warning", JOptionPane.YES_NO_OPTION);
                	
                	if(dialogResult == JOptionPane.YES_OPTION)
                	{
                		Class.forName("com.mysql.jdbc.Driver");
    					con1 = DriverManager.getConnection("jdbc:mysql://localhost/school","root","");
    					
    					insert = con1.prepareStatement("delete from record where id=? ");
    					
    					insert.setInt(1, id);
    					

    					insert.executeUpdate();
    					
    					JOptionPane.showMessageDialog( btnDelete, "Deleted", "Message", 0);
    					table_update();
    					
    					txtname.setText("");
    					txtmobile.setText("");
    					txtcourse.setText("");
    					
    					txtname.requestFocus();
                	}
                	
					
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnDelete.setBounds(157, 166, 70, 23);
		panel.add(btnDelete);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel Df = (DefaultTableModel)table.getModel();
				int selectedIndex = table.getSelectedRow();
				
                try {
					
                	int id = Integer.parseInt(Df.getValueAt(selectedIndex, 0).toString());
                	String name = txtname.getText();
                	String mobile = txtmobile.getText();
                	String course = txtcourse.getText();
					
					Class.forName("com.mysql.jdbc.Driver");
					con1 = DriverManager.getConnection("jdbc:mysql://localhost/school","root","");
					
					insert = con1.prepareStatement("update record set name=?, mobile=?, course=? where id=? ");
					
					insert.setString(1, name);
					insert.setString(2, mobile);
					insert.setString(3, course);
					insert.setInt(4, id);
					insert.executeUpdate();
					
					JOptionPane.showMessageDialog( btnEdit, "Record Updated", "Message", 0);
					table_update();
					
					txtname.setText("");
					txtmobile.setText("");
					txtcourse.setText("");
					
					txtname.requestFocus();
					
					
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnEdit.setBounds(87, 166, 60, 23);
		panel.add(btnEdit);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(263, 71, 291, 203);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel Df = (DefaultTableModel)table.getModel();
				int selectedIndex = table.getSelectedRow();
				
				txtname.setText(Df.getValueAt(selectedIndex, 1).toString());
				txtmobile.setText(Df.getValueAt(selectedIndex, 2).toString());
				txtcourse.setText(Df.getValueAt(selectedIndex, 3).toString());
			}
		});
		table.setForeground(new Color(0, 0, 0));
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Name", "Mobile", "Course"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] {
				Integer.class, String.class, Integer.class, String.class
			};
			public Class<?> getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
	}
}
