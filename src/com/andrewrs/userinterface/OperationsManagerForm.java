package com.andrewrs.userinterface;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.andrewrs.jsonparser.JsonObjectification;
import com.andrewrs.main.AdminDirectoryMain;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;

public class OperationsManagerForm extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LocationData location;
	private OperationsData operations;
	private final JTable table;
	private final DayOfWeekSelector daySelector;
	private final ComboTime comboStartTimes,comboEndTimes;
	private final JLabel lblLocationAddressAndRoom,lblLocationName;
	private final TextBoxPanel newLocationName,newLocationRoom,newLocationAddress;
	public void refreshOperationsData()
	{

		try {
			String json = AdminDirectoryMain.HTTP.getDataById("locations",location.getId());
			JsonObjectification objectifiedJson = new JsonObjectification(json);
			operations = new OperationsData(objectifiedJson.jsonObject.getChild("operations"),location);
			location.setOperations(operations);
			refreshTable();
		} catch (Exception e) {
			e.printStackTrace();
		}
		updateLocationInfoLabels();
	}
	private String [][] tableData;
	private String[] idList;
	private void refreshTable()
	{
	    DefaultTableModel contactTableModel = (DefaultTableModel) table
	            .getModel();
		int columns = 3;
		int rows = 
				operations.mon.size()+
				operations.tue.size()+
				operations.wed.size()+
				operations.thu.size()+
				operations.fri.size()+
				operations.sat.size()+
				operations.sun.size();
		int selectedIndex=table.getSelectedRow();
		tableData = new String[rows][columns];
		idList = new String[rows];
		
		contactTableModel.setRowCount(0);

		
		int counter = 0;
		for(int i = 0;i<operations.times.size();i++)
		{
			for(TimeBlock time : operations.times.get(i))
			{
				StringBuilder startTimeStr = new StringBuilder(Integer.toString(time.getStartHour()));
				startTimeStr.append(":");
				startTimeStr.append(time.getStartMinute()>9?Integer.toString(time.getStartMinute()):"0"+Integer.toString(time.getStartMinute()));
				startTimeStr.append(" ");
				startTimeStr.append(time.isStartAm()?"am":"pm");
				StringBuilder endTimeStr = new StringBuilder(Integer.toString(time.getEndHour()));
				endTimeStr.append(":");
				endTimeStr.append(time.getEndMinute()>9?Integer.toString(time.getEndMinute()):"0"+Integer.toString(time.getEndMinute()));
				endTimeStr.append(" ");
				endTimeStr.append(time.isEndAm()?"am":"pm");

					idList[counter] = time.getId();//Must be set to save updated operations
					tableData[counter][0] = time.getDayOfWeek().substring(0, 1).toUpperCase()+
							time.getDayOfWeek().substring(1).toLowerCase();
					tableData[counter][1] = startTimeStr.toString();
					tableData[counter][2] = endTimeStr.toString();
				    contactTableModel.addRow(tableData[counter]);
				    counter++;
			}
			
		}
		table.repaint();
			if(selectedIndex != -1 && table.getRowCount() > selectedIndex)
				table.setRowSelectionInterval(selectedIndex, selectedIndex);

	}

	public void setData(OperationsData data)
	{
		operations = data;
		location = data.parent;
		System.out.println("From Operations.setData() "+data.jsonify());
		this.setTitle(location.getName()+" Operations");
		updateLocationInfoLabels();
		refreshTable();
	}
	public OperationsManagerForm()
	{
		
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setSize(700,650);

	JPanel panel_1 = new JPanel();
	getContentPane().add(panel_1, BorderLayout.SOUTH);
	panel_1.setLayout(new GridLayout(4, 1, 0, 0));

	JPanel panel_3 = new JPanel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void repaint() {}
	};
	panel_3.setLayout(new GridLayout(1, 4, 0, 0));
	JPanel panel_4 = new JPanel();
	panel_4.setLayout(new GridLayout(0, 4, 0, 0));
	panel_4.setBounds(0, 0, this.getWidth(), 40);
	JPanel panel_5 = new JPanel();

	panel_1.add(panel_3);
	panel_1.add(panel_4);
	
	JPanel panel = new JPanel();
	panel_4.add(panel);
	panel.setLayout(new GridLayout(2, 1, 0, 0));
	
	JLabel lblStartTime = new JLabel("Start Time:");
	panel.add(lblStartTime);
	
	comboStartTimes = new ComboTime();
	panel.add(comboStartTimes);
	comboStartTimes.setMaximumRowCount(10);
	comboStartTimes.setBounds(0, 0, this.getWidth()/4, 30);

	
	JPanel panel_6 = new JPanel();
	panel_4.add(panel_6);
	panel_6.setLayout(new GridLayout(2, 1, 0, 0));

	JLabel lblEndTime = new JLabel("End Time:");
	panel_6.add(lblEndTime);
	
	comboEndTimes = new ComboTime();
	panel_6.add(comboEndTimes);
	
	comboEndTimes.addKeyListener(new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && location.getId().length()>0)
				saveNewOperationTime();//calls refreshOperationsData
			
		}
	});
	comboStartTimes.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			comboEndTimes.setSelectedIndex(comboStartTimes.getSelectedIndex());
		}
	});
	
	JPanel panel_8 = new JPanel();
	panel_4.add(panel_8);
	panel_8.setLayout(new GridLayout(2, 1, 0, 0));
	
	JLabel lblLocationNameTag = new JLabel("Location Name:");
	panel_8.add(lblLocationNameTag);
	
	lblLocationName = new JLabel("12345678901234567890");
	panel_8.add(lblLocationName);
	
	JPanel panel_14 = new JPanel();
	panel_4.add(panel_14);
	panel_14.setLayout(new GridLayout(2, 1, 0, 0));
	
	JLabel lblLocationAddressTag = new JLabel("Location Address");
	panel_14.add(lblLocationAddressTag);
	
	lblLocationAddressAndRoom = new JLabel("12345678901234567890");
	panel_14.add(lblLocationAddressAndRoom);
	
	panel_1.add(panel_5);
	JPanel panel_2 = new JPanel();
	getContentPane().add(panel_2, BorderLayout.CENTER);
	panel_2.setLayout(new BorderLayout(0, 0));
	
	
	table=new JTable();
	panel_2.add(table, BorderLayout.CENTER);
	String headers[]= {"Day","Start Time","End Time"};
    DefaultTableModel contactTableModel = (DefaultTableModel) table
            .getModel();
    contactTableModel.setColumnIdentifiers(headers);


	ComboDayOfWeek day = new ComboDayOfWeek();
	ComboTime startTime = new ComboTime();
	ComboTime endTime = new ComboTime();
    table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(day));
    table.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(startTime));
    table.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(endTime));
    
    
	getContentPane().add(new JScrollPane(table));
	panel_5.setLayout(new GridLayout(0, 4, 0, 0));
	
	newLocationName = new TextBoxPanel("Location Name:");
	panel_5.add(newLocationName);
	

	newLocationRoom = new TextBoxPanel("Room Number:");
	panel_5.add(newLocationRoom);
		
		newLocationAddress = new TextBoxPanel("Address:");
		panel_5.add(newLocationAddress);
		JPanel panel_12 = new JPanel();
		panel_5.add(panel_12);
	panel_3.setBounds(0, 0, this.getWidth(), 60);
	daySelector=new DayOfWeekSelector(panel_3.getBounds());
	panel_3.add(daySelector);
	panel_12.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
	
	JPanel panel_9 = new JPanel();
	FlowLayout flowLayout = (FlowLayout) panel_9.getLayout();
	flowLayout.setAlignment(FlowLayout.LEFT);
	panel_1.add(panel_9);
	final JButton btnDeleteOperation = new JButton("Delete Time");
	panel_9.add(btnDeleteOperation);
	btnDeleteOperation.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {

			boolean saveError = false;
			setTitle(location.getName()+" Operations Deleting Selected...");
		    String id = "";
		    if(table.getSelectedRow() != -1)
		    id = idList[table.getSelectedRow()];
		    TimeBlock toBeDeleted = operations.findTimeBlockById(id);
			try {
				toBeDeleted.delete();
			} catch (Exception e1) {
				e1.printStackTrace();
				setTitle(location.getName()+" Operations Failed to Delete");
				saveError = true;
			}
			refreshOperationsData();
			if(!saveError)
			{
				StringBuilder deletedData = new StringBuilder(toBeDeleted.getDayOfWeek());
				deletedData.append(' ');
				deletedData.append(Integer.toString(toBeDeleted.getStartHour()));
				deletedData.append(":");
				deletedData.append(toBeDeleted.getStartMinute()>9?Integer.toString(toBeDeleted.getStartMinute()):"0"+Integer.toString(toBeDeleted.getStartMinute()));
				deletedData.append(" ");
				deletedData.append(toBeDeleted.isStartAm()?"am":"pm");
				deletedData.append(" - ");
				deletedData.append(Integer.toString(toBeDeleted.getEndHour()));
				deletedData.append(":");
				deletedData.append(toBeDeleted.getEndMinute()>9?Integer.toString(toBeDeleted.getEndMinute()):"0"+Integer.toString(toBeDeleted.getEndMinute()));
				deletedData.append(" ");
				deletedData.append(toBeDeleted.isEndAm()?"am":"pm");
				setTitle(location.getName()+" Operations  Deleted: " + deletedData.toString());
						
			}
		}
	});
	
	JButton btnRefreshTable = new JButton("Save/Refresh");
	panel_9.add(btnRefreshTable);
	final JButton btnBackToLocations = new JButton("Back to Locations");
	panel_9.add(btnBackToLocations);
	btnBackToLocations.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
				location = null;
				operations = null;
				newLocationName.textField.setText("");
				daySelector.clear();
				ProgramState.setState(ProgramState.MANAGE_LOCATIONS);
		}
	});
	btnRefreshTable.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(newLocationName.textField.getText().length()>0)
				saveNewLocation();

			if(newLocationName.textField.getText().length()>0 || !newLocationName.isVisible())
			saveOperations();
			else
				setTitle("Error Saving, Please enter a Name");
			
		}
	});
	table.addKeyListener(new KeyListener() {
		public void keyPressed(KeyEvent e) {
			
			if(e.getKeyCode()==KeyEvent.VK_ENTER && table.getSelectedRow()!=-1)
			{
				if(newLocationName.textField.getText().length()>0)
					saveNewLocation();

				if(newLocationName.textField.getText().length()>0 || !newLocationName.isVisible())
				saveOperations();
				else
					setTitle("Error Saving, Please enter a Name");
			}
			else if(e.getKeyCode()==KeyEvent.VK_BACK_SLASH && table.getSelectedRow()!=-1)
			{
			    DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
				try {
					operations.findTimeBlockById((String)tableModel.getValueAt(table.getSelectedRow(),0)).delete();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				refreshOperationsData();
			}
			else if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			{
				 ProgramState.setState(ProgramState.MANAGE_LOCATIONS);				
			}
		}

		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
	});	
	updateLocationInfoLabels();
	this.setSize(700,650);
	}

	private void saveOperations() 
	{

		this.setTitle(location.getName()+" Operations Saving Data...");
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for(int index = 0;index<model.getRowCount();index++)
		{
			TimeBlock data = findTimeBlockById(idList[index]);
			String startTime[] = ((String) (model.getValueAt(index, 1))).split("(:)|( )");
			String endTime[] = ((String) (model.getValueAt(index, 2))).split("(:)|( )");
			data.setDayOfWeek((String)(model.getValueAt(index, 0)));
			data.setStartHour(Integer.parseInt(startTime[0]));
			data.setStartMinute(Integer.parseInt(startTime[1]));
			data.setStartAm(startTime[2].charAt(0)=='a'?true:false);
			data.setEndHour(Integer.parseInt(endTime[0]));
			data.setEndMinute(Integer.parseInt(endTime[1]));
			data.setEndAm(endTime[2].charAt(0)=='a'?true:false);
		}
		saveNewOperationTime();

		this.setTitle(location.getName()+" Operations Save Complete.");

	}
	private TimeBlock findTimeBlockById(String id)
	{
		TimeBlock data = null;
		for(int i =0;i<operations.times.size();i++)
		{
			for(TimeBlock current : operations.times.get(i))
			{
				if(current.getId().equals(id))
				{
					data = current;
				}
			}
		}
		return data;
	}
	private final String am="am";
	private void saveNewOperationTime()
	{	


		this.setTitle(location.getName()+" Operations  Saving New Operations...");
		System.out.println("Data Before Save New Operations: "+location.jsonify());
		String start = (String) comboStartTimes.getSelectedItem();
		System.out.println(start);
		String startHour = start.split(":")[0];
		String startMinute = start.split(":")[1].split(" ")[0];
		boolean isStartAm = start.split(":")[1].split(" ")[1].equals(am);
		
		String end = (String) comboEndTimes.getSelectedItem();
		String endHour = end.split(":")[0];
		String endMinute = end.split(":")[1].split(" ")[0];
		boolean isEndAm = end.split(":")[1].split(" ")[1].equals(am);
		
		for(int i=0;i<7;i++)
		{
			if(daySelector.isSelectedByIndex(i))
			{
				operations.addTimeBlock(daySelector.getDayName(i),
						startHour,
						startMinute,
						isStartAm,
						endHour,
						endMinute,
						isEndAm);
			}
		}
		daySelector.clear();
		comboStartTimes.setSelectedIndex(0);
		comboEndTimes.setSelectedIndex(0);
		System.out.println("Data After Save New Operations: "+location.jsonify());
		try {
			AdminDirectoryMain.HTTP.putJsonString("locations/"+location.getId(), location.jsonify());
		} catch (Exception e) {
			e.printStackTrace();
		}

		refreshOperationsData();

		this.setTitle(location.getName()+" Operations  Save Complete.");
	}
	@Override
	public void repaint()
	{
		super.repaint();
		daySelector.setSize(daySelector.getBounds());
		daySelector.repaint();
	}
	private void updateLocationInfoLabels()
	{
		this.setTitle(location!=null?location.getName()+" Operations":"New Location");
		this.lblLocationName.setText(location!=null?location.getName():"");
		this.lblLocationAddressAndRoom.setText(location!=null?location.getAddress()+" "+location.getRoom():"");
	}
	public void setNewLocationPanesVisibility(boolean isVisible) 
	{	
		System.out.println("Setting Up for new Operations in Method: setNewLocationPanesVisibility");
		updateLocationInfoLabels();
		newLocationName.setVisible(isVisible);
		newLocationRoom.setVisible(isVisible);
		newLocationAddress.setVisible(isVisible);
		((DefaultTableModel) table.getModel()).setRowCount(0);
	}
	private void saveNewLocation()
	{
		
		this.setTitle("Locations Editor Saving New Location");
		LocationData newData = new LocationData(newLocationName.textField.getText(),
				newLocationRoom.textField.getText(),
				newLocationAddress.textField.getText());
		try {
			String response = AdminDirectoryMain.HTTP.postJsonStringReturnResp("locations", newData.jsonify());
			JsonObjectification data = new JsonObjectification(response);
			newData.setId(data.jsonObject.getChild("_id").getData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.setData(newData.locationOperations);
		this.setTitle("Locations Editor New Location Saved");
	}
}
