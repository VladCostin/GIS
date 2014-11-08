package AALComponent;

import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.Notifications;
import ContextModel.InterfaceContext;
import ContextModel.MetadataContext;
import Mediator.ComponentIf;
import Mediator.TypesNotification;
import Mediator.Subject;

/**
 * the component which show the parsed data
 * @author Vlad Herescu
 *
 */
public class AALComponent implements ComponentIf, ItemListener, ActionListener{

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
	 * the context elements received from the parser
	 */
	ArrayList<Notifications> m_contextElements;
	
	
	/**
	 * a set of labels where the values obtained from XML are shown
	 */
	LinkedHashMap<String, JLabel> m_labels;
	
	/**
	 * the index of the current context element from the elements obtained from xml
	 */
	int m_indexContextsElement;
	
	
	
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
	    gui.add(initCOntextElementsPanel(), BorderLayout.CENTER);
		return gui;
	}
	
	/**
	 * @return : the panel where the data about the context element is shown
	 */
	public JPanel initCOntextElementsPanel() {
		
		JPanel panel = new JPanel(); 
		int i = 0;
		ArrayList<JLabel> label = new ArrayList<JLabel>();
		panel.setLayout(new GridLayout(6, 2));
		JButton buton = new JButton(AALConstants.buttonNextElement);
		m_labels = new LinkedHashMap<String, JLabel>();
		
		label.add(new JLabel(" ID : "));
		label.add(new JLabel(" VALUE : "));
		label.add(new JLabel(" DESCRIPTION : "));
		label.add( new JLabel(" TYPE : "));
		label.add(new JLabel(" UNIT : "));
		
		
		m_labels.put("ID", new JLabel());
		m_labels.put("Value", new JLabel());
		m_labels.put("Description", new JLabel());
		m_labels.put("Type", new JLabel());
		m_labels.put("Unit", new JLabel());
		
		
		for(String key : m_labels.keySet())
		{
			JLabel lb = m_labels.get(key);
			panel.add(label.get(i));
			panel.add(lb);
			i++;
		}
		buton.addActionListener(this);
		panel.add(buton);

		
		return panel;
		
		
		// TODO Auto-generated method stub
		//return null;
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
		m_contextElements = m_concrete_parsers.get(e.getItem().toString()).factoryMethod();
		m_indexContextsElement = 0;
		setValuesToLabels(m_indexContextsElement); 
		
		m_subject.communicateContext(m_contextElements);

		
	}
	
	/**
	 * @param i
	 */
	public void setValuesToLabels(int i)
	{
		
		InterfaceContext context = (InterfaceContext) m_contextElements.get(i);
		MetadataContext metadata = context.getM_data();
		
				
		m_labels.get("Type").setText(context.getType());
		m_labels.get("ID").setText(context.getId());
		m_labels.get("Value").setText(context.getValue());
		m_labels.get("Unit").setText(metadata.getM_unit());
		m_labels.get("Description").setText(metadata.getM_description());
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals(AALConstants.buttonNextElement))
		{
			m_indexContextsElement++;
			m_indexContextsElement = m_indexContextsElement == m_contextElements.size() ? 0 : m_indexContextsElement;
			System.out.println(m_indexContextsElement);
			setValuesToLabels(m_indexContextsElement);
		}
		
		
		
	}

}
