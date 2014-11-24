package POIComponent;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import GeoObject.POIObject;
import common.Notifications;

public class DeletePanel extends JPanel implements ActionListener{

	ArrayList<Notifications> m_POIs;
	
	/**
	 * the list contained in the panel where the user can select his types of POI to be shown
	 */
	JList<String> m_list;
	
	
	DefaultListModel<String> model;
	
	JButton m_buttonDelete;
	
	
	ArrayList<String> m_POIsDeleted;
	

	public DeletePanel(ArrayList<Notifications> _pois) 
	{
		m_POIs = new ArrayList<Notifications>();
		m_POIsDeleted = new ArrayList<String>();
		m_POIs.addAll(_pois);
		setLayout(new FlowLayout());
		initPanel();
	}


	public void initPanel() 
	{
		model = new DefaultListModel<String>(); 
		m_list = new JList<String>(model);
		JScrollPane pane = new JScrollPane(m_list);
		m_buttonDelete = new JButton("Delete selected items");
		
		for (Notifications notification : m_POIs)
		{
			POIObject object = (POIObject) notification;
			model.addElement(object.getId());
		}

		m_buttonDelete.addActionListener(this);
		
		
		add(pane);
		add(m_buttonDelete);
		
	}


	public void addNewPois(ArrayList<Notifications> _notifications) {
		
		m_POIs.clear();
		model.clear();
		System.out.println("dfgfdgfdgfdgfdgfdgd" + _notifications.toString());
		for(Notifications notification : _notifications)
		{
			POIObject object = (POIObject) notification;
			model.addElement(object.getId());
		}
		m_POIs.addAll(_notifications);
		

		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		List<String> valuesSelected = m_list.getSelectedValuesList();
		for(String value : valuesSelected)
		{
			model.removeElement(value);
			m_POIsDeleted.add(value);
		}
		
		this.repaint();
		
	}

}
