package GPSComponent;

import java.awt.Panel;
import java.util.Set;

import javax.swing.JPanel;

import Mediator.ComponentIf;
import Mediator.Subject;
import Mediator.TypesNotification;

/**
 * @author Vlad Herescu
 *
 */
public class GPS implements ComponentIf{
	
	/**
	 * the main panel, containing the others : the panel where he can select the action
	 * 
	 */
	public Panel m_panel;
	
	/**
	 * the instance of the mediator, that will share all the data from each observer 
	 */
	Subject m_subject;
	
	/**
	 *  initiating the members of GPS module
	 * @param _subject : the mediator to which the data will be transmitted
	 */
	public GPS(Subject _subject)
	{
		m_panel = initPanel();
		m_subject = _subject;

	}

	/**
	 * @return : the panel containing data about the gps 
	 */
	public Panel initPanel() {
		Panel panel = new Panel();
		return panel;
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
	public void update(TypesNotification _notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<TypesNotification> getNotificationsTypes() {
		// TODO Auto-generated method stub
		return null;
	}

}
