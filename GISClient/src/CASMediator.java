
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

import AALComponent.AALComponent;
import ContextModel.ContextElement;
import GIS.GISComponent;
import GIS.GeoObject;
import Mediator.ComponentIf;
import Mediator.MediatorIF;
import Mediator.Notification;
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
	 * initializing the window and the components of the mediator 
	 */
	public CASMediator() {
		
		
		
		initFrame();
		initComponents();
	}
	
	/**
	 * specifying the components of the 
	 */
	public void initComponents() {
		m_components = new ArrayList<ComponentIf>();
		m_components.add(new GISComponent(this));
		m_components.add(new AALComponent(this));
		m_components.add(new POIComponent());
		
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
		JRadioButtonMenuItem menuItemGIS, menuItemGPS;
		ButtonGroup group;
		
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		group = new ButtonGroup();
		menuItemGIS = new JRadioButtonMenuItem("GIS");
		menuItemGPS = new JRadioButtonMenuItem("AAL");
		menuItemGPS = new JRadioButtonMenuItem("POI");
		
		
		menuItemGIS.addActionListener(this);
		menuItemGPS.addActionListener(this);
		
		menu.add(menuItemGIS);
		menu.add(menuItemGPS);
		group.add(menuItemGPS);
		group.add(menuItemGIS);
		
		menuBar.add(menu);
		
		
		m_frame.setJMenuBar(menuBar);
		
	}

	public static void main(String arg[])
	{
		CASMediator mediator = new CASMediator();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JRadioButtonMenuItem menuItem = (JRadioButtonMenuItem) e.getSource();
		String text = menuItem.getText();
		if(menuItem.getText().equals("GIS"))
		{
			System.out.println("intra aici");
			m_frame.setContentPane(m_components.get(0).getPanel());
			m_frame.validate();
			m_frame.repaint();
			
		}
		if(menuItem.getText().equals("AAL"))
		{
			m_frame.setContentPane(m_components.get(1).getPanel());
			m_frame.validate();
			m_frame.repaint();
		}
		if(menuItem.getText().equals("POI"))
		{
			
		}

		
	}

	@Override
	public void communicateGeoObject(GeoObject[] _obj)
	{
		 
		
		for(ComponentIf component : m_components)
		{
			component.update(Notification.GEO_OBJECT);
		}
		
		
		
	}

	@Override
	public void communicateContext(ContextElement[] _obj) 
	{
		
		System.out.println( _obj.getClass().getSimpleName());
		
		for(ComponentIf component : m_components)
		{
			component.update(Notification.CONTEXT_ELEMENT);
		}
		
	}
	
}
