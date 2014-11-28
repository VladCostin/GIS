import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.Notifications;
import Mediator.ComponentIf;
import Mediator.MediatorIF;
import Mediator.TypesNotification;
import Mediator.Subject;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Vlad Herescu
 *
 */
public class CASMediator implements MediatorIF, ActionListener, Subject{

	/**
	 * the list of components that collaborate using the MediatorIf
	 */
	HashMap<String, ComponentIf> m_mapComponents;
	
	
	
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
	 * it specifies the name of the modules and their structures
	 */
	String m_fileModuleNameStructure;
	
	/**
	 * initializing the window and the components of the mediator 
	 * @param fileName : the name of the xml file where the structure is specified
	 */
	public CASMediator(String fileName) 
	{
		m_fileModuleNameStructure = fileName;
		

		m_mapComponents = new HashMap<String, ComponentIf>();
		m_notifications = new HashMap<TypesNotification, ArrayList<Notifications>>();
	}
	
	/**
	 * specifying the components of the 
	 */
	public void initComponents() {
		
		DocumentBuilder builder;
		DocumentBuilderFactory factory =DocumentBuilderFactory.newInstance();
		Element root;
		NodeList modules;
		int indexNodes;
		
		try {
				builder =  factory.newDocumentBuilder();
				Document document =builder.parse(
				ClassLoader.getSystemResourceAsStream(m_fileModuleNameStructure));
				
				root = document.getDocumentElement();
				modules = root.getElementsByTagName("module");
				
				for(indexNodes = 0; indexNodes < modules.getLength(); indexNodes++)
					parseDataOneModule(modules.item(indexNodes));
				
				
		} 
		catch (ParserConfigurationException e) 
		{
				
				e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * parses the data from one module
	 * @param _node : the current node (Module) from the XML file to be parsed
	 */
	public void parseDataOneModule(Node _node)
	{
		Element element = (Element) _node;
		
		String packageName =  getValueFromNode( element.getElementsByTagName("name"));
		String className = getValueFromNode( element.getElementsByTagName("class"));
		String aliasName = getValueFromNode( element.getElementsByTagName("alias"));
		ComponentIf component = createComponentIf(packageName, className);
		m_mapComponents.put(aliasName, component);
		
		
	}
	/**
	 * @param _node : a node from the xml file
	 * @param _position : in case it is an array, extract element from this position
	 * @return : the value between tags : <>value</>
	 */
	public String getValueFromNode(NodeList _node)
	{
		return _node.item(0).getChildNodes().item(0).getNodeValue();
	}
	
	/**
	 * @param packageName : the name of the package the class belongs to
	 * @param className : the name of the class to be instantiated
	 * @return : the clas created
	 */
	public ComponentIf createComponentIf(String packageName, String className)
	{

		Class cl;
		try {
			cl = Class.forName(packageName+"."+ className);
			Constructor con = cl.getConstructor(Subject.class);
			Object classCreated = con.newInstance(this);
			return (ComponentIf) classCreated;
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
		JRadioButtonMenuItem menuItemGIS, menuItemAAL, menuItemPOI, menuItemManage,  menuItemGPS;
		ButtonGroup group;
		
		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		group = new ButtonGroup();
		menuItemGIS = new JRadioButtonMenuItem("GIS");
		menuItemAAL = new JRadioButtonMenuItem("AAL");
		menuItemPOI = new JRadioButtonMenuItem("POI");
		menuItemManage = new JRadioButtonMenuItem("Manager");
		menuItemGPS = new JRadioButtonMenuItem("GPS");
		
		
		menuItemGIS.addActionListener(this);
		menuItemAAL.addActionListener(this);
		menuItemPOI.addActionListener(this);
		menuItemManage.addActionListener(this); 
		menuItemGPS.addActionListener(this); 
		
		menu.add(menuItemGIS);
		menu.add(menuItemAAL);
		menu.add(menuItemPOI);
		menu.add(menuItemManage);
		menu.add(menuItemGPS);
		
		group.add(menuItemAAL);
		group.add(menuItemGIS);
		group.add(menuItemPOI);
		group.add(menuItemManage);
		group.add(menuItemGPS);
		
		menuBar.add(menu);
		
		
		m_frame.setJMenuBar(menuBar);
		
	}

	/**the main method of the project
	 * @param arg : no arguments are added
	 */
	public static void main(String arg[])
	{

		CASMediator mediator = new CASMediator(arg[0]);	
		mediator.initFrame(); 
		mediator.initComponents();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JRadioButtonMenuItem menuItem = (JRadioButtonMenuItem) e.getSource();
		loadPanel(menuItem.getText());
	}
	
	/**
	 * it loads a panel of a component chosen by the user
	 * @param _aliasComponent : the alias of the class that will be loaded
	 * @param _nrPanel : the id of the component whose panel will be loaded 
	 */
	public void loadPanel(String _aliasComponent)
	{
		m_frame.setContentPane(m_mapComponents.get(_aliasComponent).getPanel());
		m_frame.validate();
		m_frame.repaint();
	}

	@Override
	public void communicateGeoObject(ArrayList<Notifications> _geoObjects)
	{
		 m_notifications.put(TypesNotification.GEO_OBJECT, _geoObjects);
		
		for(ComponentIf component : m_mapComponents.values())
		{
			component.update(TypesNotification.GEO_OBJECT);
		}
		
	}

	@Override
	public void communicateContext(ArrayList<Notifications> _contextElements) 
	{
		System.out.println("intra aici si aici si aici");

		m_notifications.put(TypesNotification.CONTEXT_ELEMENT, _contextElements);
		for(ComponentIf component : m_mapComponents.values())
		{
			component.update(TypesNotification.CONTEXT_ELEMENT);
		}
		
	}

	@Override
	public void communicatePOI(ArrayList<Notifications> _POIObjects) {
		
		
		System.out.println("intra aici");
		
		m_notifications.put(TypesNotification.POI_OBJECT, _POIObjects);
		for(ComponentIf component : m_mapComponents.values())
		{
			component.update(TypesNotification.POI_OBJECT);
		}
		
	}
	
	public void communicateContextSituation(ArrayList<Notifications> _contextSituations)
	{
		m_notifications.put(TypesNotification.CONTEXT_SITUATION, _contextSituations);
		for(ComponentIf component : m_mapComponents.values())
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
