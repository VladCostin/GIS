import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import common.Notifications;
import GIS.GeoServerInterface;
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
public class CASMediator implements MediatorIF, ActionListener, Subject, MenuListener{

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
	 * represents the current panel from a component of the application
	 */
	Panel m_currentPanel;
	
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
		Element root = (Element) _node;
		int indexElement;
		ArrayList<String> server = new ArrayList<String>();
		ArrayList<String> gui = new ArrayList<String>();
		ArrayList<String> parsers = new ArrayList<String>();
		
		String packageName =  getValueFromNode( root.getElementsByTagName("name"));
		String className = getValueFromNode( root.getElementsByTagName("class"));
		String aliasName = getValueFromNode( root.getElementsByTagName("alias"));
		
		NodeList nodeList = root.getElementsByTagName("elements");
		
		Node node = root.getElementsByTagName("elements").item(0);
		NodeList elements = ((Element) node).getElementsByTagName("element");
		

		for(indexElement=0; indexElement<elements.getLength(); indexElement++) {
        	
			
			Node element = elements.item(indexElement);

			String typeElement = elements.item(indexElement).getAttributes().getNamedItem("type").getNodeValue();
			
			if(typeElement.equals("server"))
				server.add(element.getChildNodes().item(0).getNodeValue());
			if(typeElement.equals("parser"))
				parsers.add(element.getChildNodes().item(0).getNodeValue());
			if(typeElement.equals("gui"))
				gui.add(element.getChildNodes().item(0).getNodeValue());
			

		}
		

       
		ComponentIf component = createComponentIf(packageName, className, server, parsers, gui);
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
	 * @param gui : the gui elements of the module
	 * @param parsers : the parser elements used to parse the xml file
	 * @param server : the server elements used by the GIS Component
	 * @return : the clas created
	 */
	public ComponentIf createComponentIf(String packageName, String className,
	ArrayList<String> server, ArrayList<String> parsers, ArrayList<String> gui)
	{
		HashMap<String,GeoServerInterface>  servers= getServers(packageName, server);
		
		@SuppressWarnings("rawtypes")
		Class cl;
		Constructor con;
		Object classCreated;
		try {
			cl = Class.forName(packageName+"."+ className);
			if(servers.size() != 0)
			{
				con = cl.getConstructor(Subject.class, HashMap.class);
				classCreated = con.newInstance(this, servers);
			}
			else
			{
				con = cl.getConstructor(Subject.class);
				classCreated = con.newInstance(this);
			}
			
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
	


	/**
	 * creates the server 
	 * @param packageName
	 * @param _server
	 * @return
	 */
	public HashMap<String,GeoServerInterface> getServers(String packageName, ArrayList<String> _server) {
		HashMap<String,GeoServerInterface> serverObjects = new HashMap<String,GeoServerInterface>();
		for(String server : _server)
		{
			try {
				Object cl = Class.forName(packageName+"."+ server).newInstance();
				serverObjects.put(server, (GeoServerInterface) cl  );
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return serverObjects;
	}

	private void initFrame() {
		m_frame = new JFrame();
		m_frame = new JFrame("a test frame");
		m_frame.setLayout( new BorderLayout());
		m_frame.setSize(840,720);
		
		
		setTheMenu();
		m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m_frame.setVisible(true);

		
	}

	/**
	 * setting the menu
	 */
	public void setTheMenu() {
		
		JToolBar toolBar = new JToolBar("Formatting");
		
		
		
	//	toolBar.add(button1);
		
		
	//	JMenuBar menuBar = new JMenuBar();
		
		for(String component : m_mapComponents.keySet())
		{
			JButton button1 = new JButton(component);
			button1.setMargin(new Insets(20, 50, 20, 50));
			button1.addActionListener(this); 
			toolBar.add(button1);
			
			/*JMenu menu = new JMenu(component);
			menu.addMenuListener(this);
			menuBar.add(menu);
			*/
		}
		
		
		/*
		
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
		
		*/
		//m_frame.setJMenuBar(menuBar);
		
		m_currentPanel = new Panel();	
		
		m_frame.getContentPane().add(toolBar, BorderLayout.NORTH);
		m_frame.getContentPane().add(m_currentPanel, BorderLayout.CENTER);
		
	}

	/**the main method of the project
	 * @param arg : no arguments are added
	 */
	public static void main(String arg[])
	{
		
		if(arg[0] == null)
			System.out.println("este null");

		CASMediator mediator = new CASMediator(arg[0]);	
		mediator.initComponents();
		mediator.initFrame(); 

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
	//	JRadioButtonMenuItem menuItem = (JRadioButtonMenuItem) e.getSource();
		JButton button = (JButton) e.getSource();	
		loadPanel(button.getText());
	}
	
	/**
	 * it loads a panel of a component chosen by the user
	 * @param _aliasComponent : the alias of the class that will be loaded
	 * @param _nrPanel : the id of the component whose panel will be loaded 
	 */
	public void loadPanel(String _aliasComponent)
	{
		
		m_frame.remove(m_currentPanel);
		m_currentPanel = m_mapComponents.get(_aliasComponent).getPanel();
		m_frame.getContentPane().add(m_currentPanel, BorderLayout.CENTER);
	//	m_panel.removeAll();
		
		//m_currentPanel = m_mapComponents.get(_aliasComponent).getPanel();
		//m_currentPanel.validate();
		//m_currentPanel.repaint();
		
		
	//	m_frame.setContentPane(m_mapComponents.get(_aliasComponent).getPanel(), BorderLayout.CENTER);
		
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
	public void communicateContextElement(ArrayList<Notifications> _contextElements) 
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
	
	/**
	 * METHOD CALLED BY THE MANAGER COMPONENT, WHEN A NEW  CONTEXT SITUATION IS READY
	 * @param _contextSituations
	 */
	public void communicateContextSituation(ArrayList<Notifications> _contextSituations)
	{

		
		m_notifications.put(TypesNotification.CONTEXT_SITUATION, _contextSituations);

		
		for(ComponentIf component : m_mapComponents.values())
		{
			component.update(TypesNotification.CONTEXT_SITUATION);
		}
	}
	

	@Override
	public ArrayList<Notifications> getNotifications(
			TypesNotification _notification) 
	{
		return m_notifications.get(_notification);
	}

	@Override
	public void menuSelected(MenuEvent e) {
		
		JMenu menu = (JMenu) e.getSource();	
		System.out.println("intra aici" + menu.getText());
		loadPanel(menu.getText());
		
	}

	@Override
	public void menuDeselected(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void menuCanceled(MenuEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Panel getPanel() {
		// TODO Auto-generated method stub
		return m_currentPanel;
	}
	
}
