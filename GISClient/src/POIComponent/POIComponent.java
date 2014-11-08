package POIComponent;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import GIS.POIObject;
import Mediator.ComponentIf;
import Mediator.Notification;
import Mediator.Subject;

import java.awt.Point;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import common.ConstantsId;
import common.Notifications;

/**
 * manages and distributes at least two different types of POI objects (point objects
 * @author Vlad Herescu
 *
 */
public class POIComponent implements ComponentIf, ItemListener, ActionListener{

	/**
	 * the main panel, containing the others : the panel where he can select the action
	 * 
	 */
	public Panel m_panel;
	
	/**
	 * the list of Point of objects hardcoded/ later in the database
	 */
	ArrayList<Notifications> m_poi;
	
	/**
	 * the user selects the types of the POI to be shown on the map
	 */
	public Panel m_panelSelect;
	
	/**
	 * the instance of the mediator/subject , to call the method to send 
	 * to all components that are interested in POI Components
	 */
	Subject m_subject;
	
	/**
	 * each component receives from the subject a list of data that implement the interface notifications
	 * I useNotification as a key to know the type of the values
	 */
	HashMap<Notification, ArrayList<Notifications>> m_notifications;
	
	
	/**
	 * in the user interface the user sees the types of POI that can be shwon on the map
	 * for each name an ID is associated 
	 */
	HashMap<String, Integer> m_POINames_ID;
	
	/**
	 * the list contained in the panel where the user can select his types of POI to be shown
	 */
	JList<String> m_list;
	
	
	/**
	 * @param _subject : the subject the POI is interested in , the mediator
	 */
	public POIComponent(Subject _subject) 
	{
		m_POINames_ID = initGUIPoi();
		m_panel = initGUI();
		m_poi = initPOI();
		m_panelSelect = initPanelSelect();
		m_notifications = initNotifications();
		m_subject = _subject;
		
	}
	
	/**
	 * @return : the map typeName-idType that identify the POI
	 */
	public HashMap<String, Integer> initGUIPoi()
	{
		
		HashMap<String, Integer> POINames_ID = new HashMap<String,Integer>();
		POINames_ID.put("School", ConstantsId.school);
		POINames_ID.put("Bank", ConstantsId.bank);
		POINames_ID.put("Police", ConstantsId.police);
		POINames_ID.put("Post Office", ConstantsId.post_office);
		return POINames_ID;
		
	}

	/**
	 * @return : the map containing the set of notifications the POI Component is interested in and the notifications received
	 */
	public HashMap<Notification, ArrayList<Notifications>> initNotifications()
	{
	
		HashMap<Notification, ArrayList<Notifications>> notifications = new HashMap<Notification, ArrayList<Notifications>>();
		return notifications;
	}

	/**
	 * hard codes the list of points of interest
	 * @return : the hardcoded POI
	 */
	public ArrayList<Notifications> initPOI()
	{

		ArrayList<Notifications> points = new ArrayList<Notifications>();
		
		points.add(new POIObject("Linz Universitat", ConstantsId.school, new Point(3891659,5366608 ) ));
		points.add(new POIObject("Reiffeisen bank", ConstantsId.bank, new Point(3891720,5366012 ) ));
		points.add(new POIObject("Polizei", ConstantsId.police, new Point(3891600,5366400 ) ));
		points.add(new POIObject("Die Post Linz", ConstantsId.post_office, new Point(3892400,5367400 ) ));
		
		return points;
		
	}

	/**
	 * @return : the main Panel of the window, the container for the other panels
	 */
	public Panel initGUI() 
	{
		
		Panel gui = new Panel(new BorderLayout());
	    gui.add(initOptionsParser(),      BorderLayout.NORTH);
	    return gui;
	}
	
	

	/**
	 * @return : the panel from which the user can select the operation : to delete, to load or to add
	 */
	public Component initOptionsParser() {
		
		
		  Panel options = new Panel(new FlowLayout());
		  CheckboxGroup cg1 = new CheckboxGroup ();       //create group
		  Checkbox c1 = new Checkbox(InterfaceOptionsManagePOI.optionAdd,cg1,true);  //circle button
		  Checkbox c2 = new Checkbox(InterfaceOptionsManagePOI.optionDelete,cg1,true);  //square button
		  Checkbox c3 = new Checkbox(InterfaceOptionsManagePOI.optionSelect,cg1,true);
		  options.add(c1);
		  options.add(c2);
		  options.add(c3);
		  
		  c1.addItemListener(this);
		  c2.addItemListener(this);
		  c3.addItemListener(this);
		  
		  cg1.setSelectedCheckbox(null);
		  
		  return options;
	}
	
	/**
	 * @return : the panel where the user can select the types of POI and sent them to the GIS Component
	 */
	public Panel initPanelSelect()
	{
		Panel select = new Panel(new FlowLayout());
		Button buttonSelect = new Button(InterfaceOptionsManagePOI.buttonLoad);
		DefaultListModel<String> model = new DefaultListModel<String>(); 
		m_list = new JList<String>(model);
		JScrollPane pane = new JScrollPane(m_list);
		
		buttonSelect.addActionListener(this); 
		buttonSelect.setSize(200, 30);
		select.add(buttonSelect);
		  
		  
		
		  
		for (String type : m_POINames_ID.keySet())
			  model.addElement(type);
		
		select.add(pane);
		
		
		return select;
		
		
	}
	

	@Override
	public Panel getPanel() {
		// TODO Auto-generated method stub
		return m_panel;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e)
	{
		
		System.out.println("intra aici");
		switch(e.getItem().toString())
		{
			case InterfaceOptionsManagePOI.optionSelect :
				
				m_panel.add(m_panelSelect,      BorderLayout.CENTER);

				break;
		}
		
		m_panel.validate();
		m_panel.repaint();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		switch( e.getActionCommand() )
		{
			case InterfaceOptionsManagePOI.buttonLoad :
				
				List<String> selectedItemsNames =  m_list.getSelectedValuesList();
				List<Integer> selectedItemsId = new ArrayList<Integer>();
				ArrayList<Notifications> sendPOI = new ArrayList<Notifications>();
				
				for(String selectedItem : selectedItemsNames)
					selectedItemsId.add( m_POINames_ID.get(selectedItem));
				
				for( Notifications notification : m_poi)
				{
						POIObject poi = (POIObject) notification;
						if(selectedItemsId.contains(poi.getType()))
							sendPOI.add(poi);
				}
				
				
				m_subject.communicatePOI(sendPOI);
				break;
					
		}
		
		
	}

	@Override
	public Set<Notification> getNotificationsTypes() {
		return m_notifications.keySet();
	}

	@Override
	public void update(Notification _notification) {
		// TODO Auto-generated method stub
		
	}



}


