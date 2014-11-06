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

import GIS.DummyServer;
import GIS.InterfaceConstants;
import GIS.OSMServer;
import GIS.POIObject;
import GIS.SDEServer;
import Mediator.ComponentIf;
import Mediator.ConstantsId;
import Mediator.Notification;
import Mediator.Subject;

import java.awt.Point;

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
	ArrayList<POIObject> m_poi;
	
	/**
	 * the user selects the types of the POI to be shown on the map
	 */
	public Panel m_panelSelect;
	
	/**
	 * the instance of the mediator/subject , to call the method to send 
	 * to all components that are interested in POI Components
	 */
	Subject m_subject;
	
	
	public POIComponent() 
	{
		m_panel = initGUI();
		m_poi = initPOI();
		m_panelSelect = initPanelSelect();
	}
	
	/**
	 * hard codes the list of points of interest
	 * @return
	 */
	public ArrayList<POIObject> initPOI() {

		ArrayList<POIObject> points = new ArrayList<POIObject>();
		
		points.add(new POIObject("Linz Universitat", ConstantsId.school, new Point(3891659,5366608 ) ));
		points.add(new POIObject("Reiffeisen bank", ConstantsId.bank, new Point(3891659,5366608 ) ));
		
		return points;
		
	}

	public Panel initGUI() 
	{
		
		Panel gui = new Panel(new BorderLayout());
	    gui.add(initOptionsParser(),      BorderLayout.NORTH);
	    return gui;
	}
	
	

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
		buttonSelect.addActionListener(this); 
		buttonSelect.setSize(200, 30);
		select.add(buttonSelect);
		
		return select;
		
		
	}
	

	@Override
	public Panel getPanel() {
		// TODO Auto-generated method stub
		return m_panel;
	}

	@Override
	public void update(Notification _notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		System.out.println("intra aici");
		switch(e.getItem().toString())
		{
			case InterfaceOptionsManagePOI.optionSelect :
				
				
				System.out.println("intra aici");
				m_panel.add(m_panelSelect,      BorderLayout.CENTER);

				break;
		}
		
		m_panel.validate();
		m_panel.repaint();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch( e.getActionCommand() )
		{
			case InterfaceOptionsManagePOI.buttonLoad :
				
				
		}
		
		
	}



}



