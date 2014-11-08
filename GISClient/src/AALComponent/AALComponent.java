package AALComponent;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import common.Notifications;
import ContextModel.InterfaceContext;
import Mediator.ComponentIf;
import Mediator.TypesNotification;
import Mediator.Subject;

/**
 * the component which show the parsed data
 * @author Vlad Herescu
 *
 */
public class AALComponent implements ComponentIf, ItemListener{

	/**
	 * the panel where the data will be shown
	 */
	public Panel m_panel;
	
	/**
	 * instance of the abstract class which will be instantiated with one of the concrete parser classes 
	 */
	Parser m_parser;
	

	/**
	 * associates for each button name a concret parser
	 */
	HashMap<String, InterfaceParser> m_concrete_parsers;

	/**
	 * the instance of the mediator, that will share all the data from each observer 
	 */
	Subject m_subject;

	
	/**
	 * each component receives from the subject a list of data that implement the interface notifications
	 * I useNotification as a key to know the type of the values
	 */
	HashMap<TypesNotification, ArrayList<Notifications>> m_notifications;
	
	/**
	 * @param _subject : the instance of CASMediator, that receives all the data and then shares it
	 */
	public AALComponent(Subject _subject) {
		m_concrete_parsers = new HashMap<String,InterfaceParser>();
		m_notifications = initNotifications();
		m_panel = initGUI();
		m_subject = _subject;
	}
	
	  /**
	   * Initialize UI in one Panel
	 * @return : the main panel containing the other panels
	   */
	public Panel initGUI() {
		
		Panel gui = new Panel(new BorderLayout());
	    gui.add(initOptionsParser(),      BorderLayout.NORTH);
		return gui;
	}
	
	/**
	 * @return the map of expected notifications initialized
	 */
	public HashMap<TypesNotification, ArrayList<Notifications>> initNotifications() {
		HashMap<TypesNotification, ArrayList<Notifications>> notifications = new HashMap<TypesNotification, ArrayList<Notifications>>();
		return notifications;
	}
	
	/**
	 * @return : the panel where the user can select which parser to use
	 */
	public Panel initOptionsParser()
	{
		Panel panel = new Panel();
		CheckboxGroup m_group = new CheckboxGroup ();       //create group
	    
		Checkbox c1 = new Checkbox("FileBrowser",m_group,true);  //circle button
		Checkbox c2 = new Checkbox("Server",m_group,true);  //square button
		
		panel.add(c1);
		panel.add(c2);
		
		m_concrete_parsers.put("FileBrowser", new FileSystemParser());
		m_concrete_parsers.put("Server", new ServerParser());
		
		
		m_group.setSelectedCheckbox(null);
		
		c1.addItemListener(this);
		c2.addItemListener(this);
		
		return panel;
	}
	
	@Override
	public Panel getPanel() {
		// TODO Auto-generated method stub
		return m_panel;
	}




	@Override
	public void itemStateChanged(ItemEvent e) {
		
		System.out.println(e.getItem().toString());
		InterfaceContext context = m_concrete_parsers.get(e.getItem().toString()).factoryMethod();
		
		//m_subject.communicateContext(new ContextElement[5]);
		
	}

	@Override
	public void update(TypesNotification _notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<TypesNotification> getNotificationsTypes() {
		return m_notifications.keySet();
	}

}
