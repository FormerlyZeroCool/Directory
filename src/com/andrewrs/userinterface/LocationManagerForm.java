package com.andrewrs.userinterface;

import com.andrewrs.main.AdminDirectoryMain;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.andrewrs.jsonparser.JsonObject;
import com.andrewrs.jsonparser.JsonObjectification;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class LocationManagerForm extends ProgFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<LocationData> locations;
	private ArrayList<LocationData> filteredLocations;
	private final JTable table; 
	private final JTextField textFieldLocationNameFilter;
	private final JTextField textFieldLocationRoomFilter;
	private final JTextField textFieldLocationAddressFilter;
	private final JTextField textFieldLocationEmailFilter;
	private final JTextField textFieldLocationPhoneFilter;
	public void refreshLocationsData()
	{
		locations.clear();
		try {
			String json = AdminDirectoryMain.HTTP.getData("locations");
			JsonObjectification objectifiedJson = new JsonObjectification(json);
			for(JsonObject location:objectifiedJson.jsonObject.children)
			{
				locations.add(new LocationData(location));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	public void refreshTable()
	{
		Collections.sort(locations,new LocationDataComparator());
		filteredLocations = new ArrayList<LocationData>();
	    DefaultTableModel contactTableModel = (DefaultTableModel) table
	            .getModel();
		int columns = 6;
		int rows = locations.size();
		int selectedIndex = table.getSelectedRow();
		String data[][] = new String[rows][columns];

		contactTableModel.setRowCount(0);
		
		for(int i = 0;i<locations.size();i++)
		{
			if(locations.get(i).getName().toLowerCase().contains(textFieldLocationNameFilter.getText().toLowerCase()) &&
					locations.get(i).getRoom().toLowerCase().contains(textFieldLocationRoomFilter.getText().toLowerCase()) &&
					locations.get(i).getAddress().toLowerCase().contains(textFieldLocationAddressFilter.getText().toLowerCase()) &&
					locations.get(i).getContactEmail().toLowerCase().contains(textFieldLocationEmailFilter.getText().toLowerCase()) &&
					locations.get(i).getTelephone().replaceAll("[^0-9]", "").contains(textFieldLocationPhoneFilter.getText().replaceAll("[^0-9]", "")))
			{
				data[i][0] = locations.get(i).getId();
				data[i][1] = locations.get(i).getName();
				data[i][2] = locations.get(i).getRoom();
				data[i][3] = locations.get(i).getAddress();
				data[i][4] = locations.get(i).getContactEmail();
				data[i][5] = locations.get(i).getTelephone();
			    contactTableModel.addRow(data[i]);
			    filteredLocations.add(locations.get(i));
			}
		}
		   table.getColumnModel().getColumn(0).setMinWidth(0);
		   table.getColumnModel().getColumn(0).setMaxWidth(0);
		   table.getColumnModel().getColumn(0).setWidth(0);
			if(selectedIndex != -1 && selectedIndex < table.getRowCount())
				table.setRowSelectionInterval(selectedIndex, selectedIndex);

	}
	public LocationManagerForm(UserData user)
	{
		super("LocationManager");
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setTitle("Locations Editor");
	locations = new ArrayList<LocationData>();
	JPanel panel_1 = new JPanel();
	getContentPane().add(panel_1, BorderLayout.SOUTH);
	panel_1.setLayout(new GridLayout(0, 5, 0, 0));
	
	TextBoxPanel tpNewLocationName = new TextBoxPanel("Name Filter:");
	panel_1.add(tpNewLocationName);
	textFieldLocationNameFilter = tpNewLocationName.textField;
	textFieldLocationNameFilter.setColumns(10);
	textFieldLocationNameFilter.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			refreshTable();
		}
	});
	
	TextBoxPanel tpRoomNumber = new TextBoxPanel("Room Number Filter:");
	panel_1.add(tpRoomNumber);
	textFieldLocationRoomFilter = tpRoomNumber.textField;
	textFieldLocationRoomFilter.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			refreshTable();
		}
	});
	textFieldLocationRoomFilter.setColumns(10);
	
	TextBoxPanel tpAddress = new TextBoxPanel("Address Filter:");
	panel_1.add(tpAddress);
	textFieldLocationAddressFilter = tpAddress.textField;
	textFieldLocationAddressFilter.setColumns(10);
	textFieldLocationAddressFilter.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			refreshTable();
			
		}
	});
	

	TextBoxPanel tpEmailFilter = new TextBoxPanel("Email Filter:");
	panel_1.add(tpEmailFilter);
	textFieldLocationEmailFilter = tpEmailFilter.textField;
	tpEmailFilter.textField.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			refreshTable();
		}
	});
	
	TextBoxPanel tpPhoneFilter = new TextBoxPanel("Phone Filter:");
	textFieldLocationPhoneFilter = tpPhoneFilter.textField;
	tpPhoneFilter.textField.addKeyListener(new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			refreshTable();
		}
	});
	panel_1.add(tpPhoneFilter);
	
	
	JPanel panel = new JPanel();
	panel_1.add(panel);
	panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	final JButton btnViewOperations = new JButton("View Operations");
	panel.add(btnViewOperations);
	btnViewOperations.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			 for(int j=0;j<locations.size();j++)
			 {
				 if(table.getSelectedRow()!=-1)
				 if(locations.get(j).getId().equals(table.getValueAt(table.getSelectedRow(), 0)))
				 {
					 ProgramState.setState("OperationManager");
					 AdminDirectoryMain.OPERATIONSMANAGERFORM.setData(
								getLocationOperationsData(getSelectedLocationIndex()));
				 }
			 }
				 
			 
		}
	});
	
	JPanel panel_3 = new JPanel();
	panel_1.add(panel_3);
	
	final JButton btnNewLocation = new JButton("Save New Location");
	panel_3.add(btnNewLocation);
	
	btnNewLocation.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			table.clearSelection();
			ProgramState.setState("OperationManager");
		}

	});
	
	JPanel panel_4 = new JPanel();
	panel_1.add(panel_4);
	
	JButton btnRefreshTable = new JButton("Save/Refresh");
	panel_4.add(btnRefreshTable);
	btnRefreshTable.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(table.getSelectedRow()>=0)
				saveSelectedRow();
			refreshLocationsData();
			refreshTable();
			
		}
	});

	panel_1.add(new JLabel(""));
	panel_1.add(new JLabel(""));
	panel_1.add(new JLabel(""));
	panel_1.add(new JLabel(""));
	panel_1.add(new JLabel(""));
	panel_1.add(new JLabel(""));
	
	JPanel panel_5 = new JPanel();
	panel_1.add(panel_5);
	final JButton btnDeleteLocation = new JButton("Delete Location");
	panel_5.add(btnDeleteLocation);
	btnDeleteLocation.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			locations.get(table.getSelectedRow()).deleteOnWs();
			refreshLocationsData();
			refreshTable();
		}
	});
	
	JPanel panel_2 = new JPanel();
	getContentPane().add(panel_2, BorderLayout.CENTER);
	panel_2.setLayout(new BorderLayout(0, 0));
	DefaultTableModel model = new DefaultTableModel()
	{
	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override 
	    public boolean isCellEditable(int row, int column)
	    {
			boolean isEditable = true;
	        if(column == 0)
	        	isEditable = false;
	        return isEditable;
	    }
	};
	table=new JTable(model);
	panel_2.add(table, BorderLayout.CENTER);
	String headers[]= {"ID","Name","Room","Address","Contact Email","Telephone"
			};
    DefaultTableModel contactTableModel = (DefaultTableModel) table
            .getModel();
    contactTableModel.setColumnIdentifiers(headers);
	getContentPane().add(new JScrollPane(table));
	if(user.isAdmin())
	{
		AdminMenu menu= new AdminMenu();
		getContentPane().add(menu, BorderLayout.NORTH);
	}
	table.addKeyListener(new KeyListener() {
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode()==KeyEvent.VK_ENTER && table.getSelectedRow()!=-1)
			{
				saveSelectedRow();
			}
		}

		public void keyTyped(KeyEvent e) {}

		public void keyReleased(KeyEvent e) {}
	});
	refreshLocationsData();
	refreshTable();
	this.setSize(700,650);
	}

	private void saveSelectedRow() 
	{
		this.setTitle("Locations Editor Saving Changes to Highlighted Location");
	    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		LocationData loc = findLocationById((String) tableModel.getValueAt((table.getSelectedRow()>-1?table.getSelectedRow():0),0));
		loc.setName((String) tableModel.getValueAt(table.getSelectedRow(),1));
		loc.setRoom((String) tableModel.getValueAt(table.getSelectedRow(),2));
		loc.setAddress((String) tableModel.getValueAt(table.getSelectedRow(),3));
		loc.setContactEmail((String) tableModel.getValueAt(table.getSelectedRow(),4));
		loc.setTelephone((String) tableModel.getValueAt(table.getSelectedRow(),5));
		loc.save();
		refreshLocationsData();
		refreshTable();

		this.setTitle("Locations Editor Changes to: "+loc.getName()+" Complete.");
	}
	private LocationData findLocationById(String id) 
	{
		LocationData data = null;
		
		for(int i = 0;i<locations.size() && data==null ;i++)
		{
			if(locations.get(i).getId().equals(id))
			{
				data=locations.get(i);
			}
		}
		return data;
	}
	public int getSelectedLocationIndex()
	{
		return table.getSelectedRow();
	}
	public OperationsData getLocationOperationsData(int index) 
	{
		return filteredLocations.get(index).locationOperations;
	}
	@Override
	public void onLoad() 
	{
		this.setTitle("Locations Editor");
		this.refreshLocationsData();
		this.refreshTable();
	}
	@Override
	public void onClose() 
	{
		
		OperationsManagerForm operationsManagerForm = AdminDirectoryMain.OPERATIONSMANAGERFORM;
		if(this.getSelectedLocationIndex()>-1)
		{
			operationsManagerForm.setNewLocationPanesVisibility(false);
			//
		}
		else
		{
			operationsManagerForm.setNewLocationPanesVisibility(true);
			operationsManagerForm.clearTable();
		}
	}
	@Override
	public void whileRunning() {}

	//May use this in the future
	/*private int getIndexByLocation(LocationData data) 
	{
		int index = -1;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int i = 0;i<model.getRowCount() && index == -1;i++)
		{
			if(model.getValueAt(i, 1).equals(data.getName()) &&
					model.getValueAt(i, 2).equals(data.getRoom()) &&
					model.getValueAt(i, 3).equals(data.getAddress()))
			{
				index = i;
			}
		}
		return index;
	}*/
}
