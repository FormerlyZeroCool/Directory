package com.andrewrs.userinterface;

import com.andrewrs.main.AdminDirectoryMain;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class LocationManagerForm extends ProgFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<LocationData> locations;
	private final JTable table; 
	private final JTextField textFieldLocationNameFilter;
	private final JTextField textFieldLocationRoomFilter;
	private final JTextField textFieldLocationAddressFilter;
	private String nameText,roomText,addressText;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public void refreshTable()
	{

		Comparator<LocationData> compareByName = (LocationData o1, LocationData o2) ->
        o1.getName().toLowerCase().compareTo( o2.getName().toLowerCase() );
        Collections.sort(locations, compareByName);
	    DefaultTableModel contactTableModel = (DefaultTableModel) table
	            .getModel();
		int columns = 4;
		int rows = locations.size();
		int selectedIndex = table.getSelectedRow();
		String data[][] = new String[rows][columns];

		contactTableModel.setRowCount(0);
		
		for(int i = 0;i<locations.size();i++)
		{
			if(locations.get(i).getName().toLowerCase().contains(nameText.toLowerCase()) &&
					locations.get(i).getRoom().toLowerCase().contains(roomText.toLowerCase()) &&
					locations.get(i).getAddress().toLowerCase().contains(addressText.toLowerCase()) )
			{
				data[i][0] = locations.get(i).getId();
				data[i][1] = locations.get(i).getName();
				data[i][2] = locations.get(i).getRoom();
				data[i][3] = locations.get(i).getAddress();
			    contactTableModel.addRow(data[i]);
			}
		}
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
	panel_1.setLayout(new GridLayout(0, 4, 0, 0));
	
	JLabel lblNewLocationName = new JLabel("Name Filter:");
	panel_1.add(lblNewLocationName);
	
	JLabel lblRoomNumber = new JLabel("Room Number Filter:");
	panel_1.add(lblRoomNumber);
	
	JLabel lblAddress = new JLabel("Address Filter:");
	panel_1.add(lblAddress);
	
	JLabel label = new JLabel("");
	panel_1.add(label);
	
	textFieldLocationNameFilter = new JTextField();
	panel_1.add(textFieldLocationNameFilter);
	textFieldLocationNameFilter.setColumns(10);
	textFieldLocationNameFilter.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			//if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					nameText = nameText + e.getKeyChar();
				else
					if(nameText.length() > 0)
						nameText = nameText.substring(0, nameText.length()-1);
				refreshTable();
			}
		}
	});
	nameText = textFieldLocationNameFilter.getText();
	
	textFieldLocationRoomFilter = new JTextField();
	textFieldLocationRoomFilter.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			//if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					roomText = roomText + e.getKeyChar();
				else
					if(roomText.length() > 0)
						roomText = roomText.substring(0, roomText.length()-1);
				refreshTable();
			}
		}
	});
	panel_1.add(textFieldLocationRoomFilter);
	textFieldLocationRoomFilter.setColumns(10);
	roomText = textFieldLocationRoomFilter.getText();
	
	textFieldLocationAddressFilter = new JTextField();
	panel_1.add(textFieldLocationAddressFilter);
	textFieldLocationAddressFilter.setColumns(10);
	textFieldLocationAddressFilter.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			//if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				if(e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
					addressText = addressText + e.getKeyChar();
				else
					if(addressText.length() > 0)
						addressText = addressText.substring(0, addressText.length()-1);
				refreshTable();
			}
		}
	});
	addressText = textFieldLocationAddressFilter.getText();
	
	JLabel label_1 = new JLabel("");
	panel_1.add(label_1);
	final JButton btnViewOperations = new JButton("View Operations");
	panel_1.add(btnViewOperations);
	btnViewOperations.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			 for(int j=0;j<locations.size();j++)
			 {
				 if(table.getSelectedRow()!=-1)
				 if(locations.get(j).getId().equals(table.getValueAt(table.getSelectedRow(), 0)))
				 {
					 ProgramState.setState("OperationManager");
				 }
			 }
				 
			 
		}
	});
	
	final JButton btnNewLocation = new JButton("Save New Location");
	panel_1.add(btnNewLocation);
	
	btnNewLocation.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			table.clearSelection();
			ProgramState.setState("OperationManager");
		}

	});
	
	JButton btnRefreshTable = new JButton("Save/Refresh");
	panel_1.add(btnRefreshTable);
	btnRefreshTable.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(table.getSelectedRow()>=0)
				saveSelectedRow();
			refreshLocationsData();
			refreshTable();
			
		}
	});
	final JButton btnDeleteLocation = new JButton("Delete Location");
	panel_1.add(btnDeleteLocation);
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
	String headers[]= {"ID","Name","Room","Address"};
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

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
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
		return locations.get(index).locationOperations;
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
			operationsManagerForm.clearTable();
			operationsManagerForm.setData(
					this.getLocationOperationsData(
							this.getSelectedLocationIndex()));
		}
		else
		{
			operationsManagerForm.setNewLocationPanesVisibility(true);
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
