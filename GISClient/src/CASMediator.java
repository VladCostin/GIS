import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

import common.Notifications;
import AALComponent.AALComponent;
import ContextManagementComponent.ContextManagement;
import GIS.GISComponent;
import GPSComponent.GPS;
import Mediator.ComponentIf;
import Mediator.MediatorIF;
import Mediator.TypesNotification;
import Mediator.Subject;
import POIComponent.POIComponent;

/**
 * @author Vlad Herescu
 *
 */
public class CASMediator implements MediatorIF, ActionListener, Subject{

	/**
	 * the list of components that collaborate using the MediatorIf
	 */
	ArrayList<ComponentIf> m_components; 
	
	
	/**
	 * specifying the components and adding them into the list
	 */
	
	JFrame m_frame;
	 
	
	/**
	 *  the MEDIATOR saves the notifications received from the observer
	 *  because the notifications are stored as a set of notifications interface, 
	 *  I use as key to identify the type of them 
	 */
	HashMap<TypesNotification,ArrayList<Notifications>> m_notifications;
	
	/**
	 * initializing the window and the components of the mediator 
	 */
	public CASMediator() {
		
		m_notifications = new HashMap<TypesNotification, ArrayList<Notifications>>();
	}
	
	/**
	 * specifying the components of the 
	 */
	public void initComponents() {
		m_components = new ArrayList<ComponentIf>();
		m_components.add(new GISComponent(this));
		m_components.add(new AALComponent(this));
		m_components.add(new POIComponent(this));
		m_components.add(new ContextManagement(this));
		m_components.add(new GPS(this));
		
	}

	private void initFrame() {
		m_frame = new JFrame();
		m_frame = new JFrame("a test frame");
		m_frame.setSize(840,720);
		
		
		setTheMenu();
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setVisible(true);

		
	}

	/**
	 * setting the menu
	 */
	public void setTheMenu() {
		JMenuBar menuBar;
		JMenu menu;
		JRadioButtonMenuItem menuItemGIS, menuItemAAL, menuItemPOI, menuItemManage;
		ButtonGroup group;
		
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		group = new ButtonGroup();
		menuItemGIS = new JRadioButtonMenuItem("GIS");
		menuItemAAL = new JRadioButtonMenuItem("AAL");
		menuItemPOI = new JRadioButtonMenuItem("POI");
		menuItemManage = new JRadioButtonMenuItem("Manager");
		menuItemManage = new JRadioButtonMenuItem("GPS");
		
		
		menuItemGIS.addActionListener(this);
		menuItemAAL.addActionListener(this);
		menuItemPOI.addActionListener(this);
		menuItemManage.addActionListener(this); 
		
		menu.add(menuItemGIS);
		menu.add(menuItemAAL);
		menu.add(menuItemPOI);
		menu.add(menuItemManage);
		
		group.add(menuItemAAL);
		group.add(menuItemGIS);
		group.add(menuItemPOI);
		group.add(menuItemManage);
		
		menuBar.add(menu);
		
		
		m_frame.setJMenuBar(menuBar);
		
	}

	/**the main method of the project
	 * @param arg : no arguments are added
	 */
	public static void main(String arg[])
	{

		CASMediator mediator = new CASMediator();	
		mediator.initFrame(); 
		mediator.initComponents();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JRadioButtonMenuItem menuItem = (JRadioButtonMenuItem) e.getSource();
		if(menuItem.getText().equals("GIS"))
			loadPanel(0);
		if(menuItem.getText().equals("AAL"))
			loadPanel(1);
		if(menuItem.getText().equals("POI"))
			loadPanel(2);
		if(menuItem.getText().equals("Manager"))
			loadPanel(3);

	}
	
	/**
	 * @param _nrPanel : the id of the component whose panel will be loaded 
	 */
	public void loadPanel(int _nrPanel)
	{
		m_frame.setContentPane(m_components.get(_nrPanel).getPanel());
		m_frame.validate();
		m_frame.repaint();
	}

	@Override
	public void communicateGeoObject(ArrayList<Notifications> _geoObjects)
	{
		 m_notifications.put(TypesNotification.GEO_OBJECT, _geoObjects);
		
		for(ComponentIf component : m_components)
		{
			component.update(TypesNotification.GEO_OBJECT);
		}
		
		
		
	}

	@Override
	public void communicateContext(ArrayList<Notifications> _contextElements) 
	{

		m_notifications.put(TypesNotification.CONTEXT_ELEMENT, _contextElements);
		for(ComponentIf component : m_components)
		{
			component.update(TypesNotification.CONTEXT_ELEMENT);
		}
		
	}

	@Override
	public void communicatePOI(ArrayList<Notifications> _POIObjects) {
		
		
		System.out.println("intra aici");
		
		m_notifications.put(TypesNotification.POI_OBJECT, _POIObjects);
		for(ComponentIf component : m_components)
		{
			component.update(TypesNotification.POI_OBJECT);
		}
		
	}
	
	public void communicateContextSituation(ArrayList<Notifications> _contextSituations)
	{
		m_notifications.put(TypesNotification.CONTEXT_SITUATION, _contextSituations);
		for(ComponentIf component : m_components)
		{
			component.update(TypesNotification.CONTEXT_SITUATION);
		}
	}
	

	@Override
	public ArrayList<Notifications> communicateNotifications(
			TypesNotification _notification) 
	{
		return m_notifications.get(_notification);
	}
	
}
