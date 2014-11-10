package POIComponent;


import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import GeoObject.POIObject;
import common.Notifications;

/**
 * offers the GUI to add POI to the interface
 * @author Vlad Herescu
 *
 */
public class AddPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * the types of the POI
	 */
	HashMap<String, Integer> m_POINames_ID;
	
	/**
	 * the name of the text field
	 */
	JTextField m_name;
	
	
	JComboBox<String> m_types;
	
	
	JTextField m_latitude;
	
	
	JTextField m_longitude;
	
	
	JButton m_button;
	
	
	ArrayList<Notifications> m_newPOI;
	

	public AddPanel(HashMap<String, Integer> _POINames_ID) 
	{
		m_POINames_ID = _POINames_ID;
		m_newPOI = new ArrayList<Notifications>();
		setLayout(new GridLayout(5,2));
		initPanel();
	}

	public void initPanel() {	
		
		m_types = new JComboBox<String>();
		m_latitude = new JTextField();
		m_longitude = new JTextField();
		m_name = new JTextField();
		m_button = new JButton("Add the POI");
		
		for(String type : m_POINames_ID.keySet())
			m_types.addItem(type);
		
		
		add(new JLabel("	Name/Id"));
		add(m_name);
		
		
		add(new JLabel("	Latitude"));
		add(m_latitude);
		
		
		add(new JLabel("	Longitude"));
		add(m_longitude);
		
		
		add(new JLabel("	Type"));
		add(m_types);
		
		m_button.addActionListener(this);
		add(m_button);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		
		System.out.println(e.getActionCommand());
		System.out.println( m_types.getSelectedItem().toString());
		
		if( !m_longitude.getText().equals("") &&
			!m_latitude.getText().equals("") &&
			!m_name.getText().equals(""))
		{
			String id = m_name.getText();
			int type = m_POINames_ID.get(m_types.getSelectedItem().toString());
			int lat = (int)Double.parseDouble(m_latitude.getText());
			int lon = (int)Double.parseDouble(m_longitude.getText());
			
			m_newPOI.add( new POIObject(id ,type  , new Point( lat , lon ) ));
			
			
			m_name.setText("");
			m_latitude.setText("");
			m_longitude.setText("");
		}
		
		
	}

	public ArrayList<Notifications> getM_newPOI() {
		return m_newPOI;
	}

	public void setM_newPOI(ArrayList<Notifications> m_newPOI) {
		this.m_newPOI = m_newPOI;
	}
	

}
