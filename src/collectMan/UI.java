package collectMan;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

import net.proteanit.sql.DbUtils;

import java.io.*;
import java.sql.*;
import java.text.*;


public class UI extends JPanel implements ActionListener {
	private JFrame CollectMan;
	private JTable itemTable;
	DefaultTableModel model = new DefaultTableModel();
	RowSorter<TableModel> sorter;
	Connection connection = null;
	JScrollPane sp = new JScrollPane();
	//Panels
	JPanel mainPane = new JPanel();
	JPanel northPane = new JPanel();
	JPanel centerPane= new JPanel();
	JPanel eastPane = new JPanel();
	JPanel westPane = new JPanel();
	JPanel southPane = new JPanel();

	//Buttons
	JButton btnQuit = new JButton("Quit");
	JButton btnOpenForm = new JButton("Open Form");
	JButton btnCloseForm = new JButton("Close Form");
	JButton btnGetDetails = new JButton("Get Details");
	JButton btnInsert = new JButton("Insert");
	JButton btnRemove = new JButton("Remove");
	JButton btnPrintDetails = new JButton("Print Details");
	JButton btnSearch = new JButton("Search");
	JButton btnOpenHelp = new JButton("Open Help");
	JButton btnCloseHelp = new JButton("Close Help");
	JButton btnUpdate = new JButton("Update");
	
	//Labels
	JLabel lblSearch = new JLabel("Search: ");
	JLabel lblID = new JLabel("ID: ");
	JLabel lblItemName = new JLabel("Item Name: ");
	JLabel lblOrigin = new JLabel("Origin: ");
	JLabel lblYear = new JLabel("Year: ");
	JLabel lblNotes = new JLabel("Notes: ");
	JLabel lblValue = new JLabel("Value: ");
	JLabel lblStatus = new JLabel("Status: ");
	JLabel lblImage = new JLabel("Image: ");
	
	//Textfields
	JTextField txtSearch = new JTextField(15);
	JTextField txtID = new JTextField(15);
	JTextField txtItemName = new JTextField(15);
	JTextField txtOrigin = new JTextField(15);
	JTextField txtYear = new JTextField(15);
	JTextField txtNotes = new JTextField(15);
	JTextField txtValue = new JTextField(15);
	JTextField txtStatus = new JTextField(15);
	JTextField txtImage = new JTextField(15);
	 
	//Textarea
	JTextArea taHelpText = new JTextArea("To Search: "
			+ "\nType in what you want to search in the textfield next to search. Then click 'Search'. This searches the 'Notes' field."
			+ "\n"
			+ "\nTo Add a new Item:"
			+ "\nClick 'Open Form' and while ID: is (new), fill in the all the information and click insert."
			+ "\n"
			+ "\nTo Get details of an Item:"
			+ "\nClick 'Open Form' then select the relevant ID of the Item then click 'Get Details'."
			+ "\n"
			+ "\nTo Update details of an Item:"
			+ "\nClick 'Open Form' then select the relevant ID of Item then click 'Get Details' to obtain the current details. Make your changes then click 'Update'."
			+ "\n"
			+ "\nTo Remove an Item"
			+ "\nClick 'Open Form' and select the Items ID from the dropdown list, then click remove.", 16,30); 
	
	//ComboBox
	String[] optionStr = {"Y", "N"};
	JComboBox cboForSale = new JComboBox(optionStr);
	DefaultComboBoxModel cboModel = new DefaultComboBoxModel();
	JComboBox cboID = new JComboBox(cboModel);
	
	
	public static void main(String[] args) {
		UI ui = new UI();
		ui.CollectMan.setVisible(true);	 
	}
	
	
	
	public UI(){
		connection = sqliteConnection.getDbConnection();
		initialize();
	}
	
	public void loadTable(){
		try{
			
			itemTable = new JTable();
			String myQuery = "SELECT * FROM Item";
			PreparedStatement pst = connection.prepareStatement(myQuery);
			ResultSet rs = pst.executeQuery();
			itemTable.setModel(DbUtils.resultSetToTableModel(rs));
			
			itemTable.setPreferredScrollableViewportSize(new Dimension(600, 250));
			itemTable.setFillsViewportHeight(true);
			
		          
			JScrollPane tableScrollPane = new JScrollPane(itemTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
													JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
			centerPane.add(tableScrollPane);
			itemTable.setAutoCreateRowSorter(true);
			tableScrollPane.setVisible(true);
			rs.close();
			pst.close();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void refreshTable(){
		try{
			Connection connection = sqliteConnection.getDbConnection();
			String myQuery = "SELECT * FROM Item";
			PreparedStatement pst = connection.prepareStatement(myQuery);
			ResultSet rs = pst.executeQuery();
			itemTable.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			pst.close();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void loadId(){
		try{
			Connection connection = sqliteConnection.getDbConnection();
			String myQuery = "SELECT ID FROM Item ORDER BY ID ASC";
			PreparedStatement pst =  connection.prepareStatement(myQuery);
			ResultSet rs = pst.executeQuery();
				cboID.removeAllItems();
				cboID.addItem("(new)");
				while(rs.next()){
					cboID.addItem(rs.getString("ID"));
				
				
			}
        connection.close();
        } catch (Exception e) {
             e.printStackTrace();
        }
	}
	
	private void initialize(){
		//Creating JFrame and modifying settings
		loadTable();
		loadId();
		
		CollectMan = new JFrame();
		CollectMan.setTitle("CollectMan");
		CollectMan.setResizable(false);
		CollectMan.setSize(800,400);
		CollectMan.setVisible(true);
		CollectMan.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		westPane.setBorder(new EmptyBorder(30, 30, 30, 30));
		
		//Assigning Action Listeners
		btnOpenHelp.addActionListener(this);
		btnCloseHelp.addActionListener(this);
		btnQuit.addActionListener(this);
		btnOpenForm.addActionListener(this);
		btnCloseForm.addActionListener(this);
		btnGetDetails.addActionListener(this);
		btnInsert.addActionListener(this);
		btnRemove.addActionListener(this);
		btnPrintDetails.addActionListener(this);
		btnSearch.addActionListener(this);
		btnUpdate.addActionListener(this);
		cboID.addActionListener(this);
		
		//Setting Layouts
		mainPane.setLayout(new BorderLayout());
		southPane.setLayout(new FlowLayout());
		westPane.setLayout(new GridBagLayout());
		GridBagConstraints gbc =  new GridBagConstraints();
		
		CollectMan.add(mainPane);
		mainPane.add(northPane, BorderLayout.NORTH);
		mainPane.add(eastPane, BorderLayout.EAST);
		mainPane.add(centerPane, BorderLayout.CENTER);
		mainPane.add(westPane, BorderLayout.WEST);
		mainPane.add(southPane, BorderLayout.SOUTH);
		
		
		northPane.add(lblSearch);
		northPane.add(txtSearch);
		northPane.add(btnSearch);
		northPane.add(btnOpenHelp);
		northPane.add(btnCloseHelp);
		btnCloseHelp.setVisible(false);

		westPane.setVisible(false);
		westPane.setSize(200,400);
		
		gbc.gridx= 0;
		gbc.gridy= 0;
		westPane.add(lblID, gbc);
		gbc.gridx= 1;
		gbc.gridy= 0;
		westPane.add(cboID, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		westPane.add(lblItemName, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		westPane.add(txtItemName, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		westPane.add(lblOrigin, gbc);
		gbc.gridx = 1;
		gbc.gridy = 2;
		westPane.add(txtOrigin, gbc);
		gbc.gridx = 0;
		gbc.gridy = 3;
		westPane.add(lblYear, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		westPane.add(txtYear, gbc);
		gbc.gridx = 0;
		gbc.gridy = 4;
		westPane.add(lblNotes, gbc);
		gbc.gridx = 1;
		gbc.gridy = 4;
		westPane.add(txtNotes, gbc);
		gbc.gridx = 0;
		gbc.gridy = 5;
		westPane.add(lblValue, gbc);
		gbc.gridx = 1;
		gbc.gridy = 5;
		westPane.add(txtValue, gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		westPane.add(lblStatus, gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		westPane.add(cboForSale, gbc);
		gbc.gridx = 0;
		gbc.gridy = 9;
		westPane.add(btnInsert, gbc);
		gbc.gridx = 1;
		gbc.gridy = 9;
		westPane.add(btnUpdate, gbc);
		gbc.gridx = 0;
		gbc.gridy = 10;
		westPane.add(btnRemove, gbc);
		gbc.gridx = 1;
		gbc.gridy = 10;
		westPane.add(btnGetDetails, gbc);
		
		southPane.add(btnCloseForm);
		btnCloseForm.setVisible(false);
		southPane.add(btnOpenForm);
		southPane.add(btnQuit);
		sp = new JScrollPane(taHelpText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED );
		taHelpText.setLineWrap(true);
		taHelpText.setWrapStyleWord(true);
		taHelpText.setEditable(false);
		eastPane.add(sp);
		
		
		eastPane.setVisible(false);
		
		btnUpdate.setEnabled(false);
		btnGetDetails.setEnabled(false);
		btnRemove.setEnabled(false);
		
	}
	public void search(String text){
		try{
			connection = sqliteConnection.getDbConnection();
			String myQuery = "SELECT * FROM Item WHERE Notes LIKE '%" + text + "%'";
			PreparedStatement pst = connection.prepareStatement(myQuery);
			ResultSet rs = pst.executeQuery();
			itemTable.setModel(DbUtils.resultSetToTableModel(rs));
			rs.close();
			pst.close();
		}
		catch(Exception e){
			JOptionPane.showMessageDialog(null, e);
		}
		
	     txtSearch.setText("");
	}
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == cboID){
			if(cboID.getSelectedIndex() > 0){
				btnUpdate.setEnabled(true);
				btnGetDetails.setEnabled(true);
				btnInsert.setEnabled(false);
				btnRemove.setEnabled(true);
			}
			else{
				btnUpdate.setEnabled(false);
				btnGetDetails.setEnabled(false);
				btnInsert.setEnabled(true);
				btnRemove.setEnabled(false);
			}
		}
		if(e.getSource() == btnSearch){
			String text = txtSearch.getText();	
			search(text);
		}
		if(e.getSource() == btnQuit){
			System.exit(0);
		}
		if(e.getSource() == btnOpenHelp){
			eastPane.setVisible(true);
			btnCloseHelp.setVisible(true);
			btnOpenHelp.setVisible(false);
			CollectMan.setSize(1000,400);
		}
		if(e.getSource() == btnCloseHelp){
			eastPane.setVisible(false);
			btnCloseHelp.setVisible(false);
			btnOpenHelp.setVisible(true);
			CollectMan.setSize(800,400);
			if(e.getSource() == btnCloseHelp && westPane.isVisible()){
				CollectMan.setSize(1000,400);
			}
		}
		if(e.getSource() == btnCloseForm){
			btnCloseForm.setVisible(false);
			btnOpenForm.setVisible(true);
			westPane.setVisible(false);
			CollectMan.setSize(800,400);
			if(e.getSource() == btnCloseForm && eastPane.isVisible()){
				CollectMan.setSize(1000,400);
			}
		}
		if(e.getSource() == btnOpenForm){
			btnOpenForm.setVisible(false);
			btnCloseForm.setVisible(true);
			westPane.setVisible(true);
			CollectMan.setSize(1000,400);
		}
		if(e.getSource() == btnOpenForm && eastPane.isVisible() ||
				e.getSource() == btnOpenHelp && westPane.isVisible()){
			CollectMan.setSize(1300,400);
		}
		if(e.getSource() == btnInsert){
			
			try{
				String status = cboForSale.getSelectedItem().toString();
				JOptionPane.showMessageDialog(null, "Success");
				String item = txtItemName.getText().trim();
				String origin = txtOrigin.getText().trim();
				String notes = txtNotes.getText().trim();
				String year = txtYear.getText().trim();
				String value = txtValue.getText().trim();
				collectMan.CollectMan.insert(item,origin,notes,year,value,status);
				}
				catch(Exception ex){
				ex.printStackTrace();
				} 
			refreshTable();
			loadId();
			
		}
		if(e.getSource() == btnRemove){
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure?");
			if(reply == JOptionPane.YES_OPTION){
				
				String selected = cboID.getSelectedItem().toString();
				JOptionPane.showMessageDialog(null, selected);
				
				collectMan.CollectMan.delete(selected);
				refreshTable();
				loadId();
			}
		}	
		if(e.getSource() == btnGetDetails){
			try {
				connection = sqliteConnection.getDbConnection();
				String id = cboID.getSelectedItem().toString();
				String myQuery = "SELECT Item, Origin, Year, Notes, Value, Status FROM Item WHERE [ID] =" + id + "";
				PreparedStatement pst = connection.prepareStatement(myQuery);
				ResultSet rs = pst.executeQuery();
				while(rs.next()){
					txtItemName.setText(rs.getString("Item"));
					txtOrigin.setText(rs.getString("Origin"));
					txtYear.setText(rs.getString("Year"));
					txtNotes.setText(rs.getString("Notes"));
					txtValue.setText(rs.getString("Value"));
					String getStatusStr = rs.getString("Status").trim();
					if(getStatusStr.equals("N")){
						cboForSale.setSelectedIndex(1);
					}
					else{cboForSale.setSelectedIndex(0);}
				}
				
				pst.execute();
				pst.close();
			} catch (Exception e1) {
				
				e1.printStackTrace();
			}
			
			
		}
		
		if(e.getSource()== btnUpdate){
			int reply = JOptionPane.showConfirmDialog(null, "Are you sure?");
			if(reply == JOptionPane.YES_OPTION){
				try {
					
					
					String id = (String)cboID.getSelectedItem().toString();
					String name = txtItemName.getText();
					String origin = txtOrigin.getText();
					String notes = txtNotes.getText();
					String yearStr = txtYear.getText();
					String valueStr = txtValue.getText().trim();
					String statusStr = (String)cboForSale.getSelectedItem().toString();
					collectMan.CollectMan.update(id, name, origin, notes, yearStr, valueStr, statusStr);
				} catch (Exception e1) {
					
					e1.printStackTrace();
				}
				refreshTable();
				loadId();
				
			}
			
		}
		
	}	

}
