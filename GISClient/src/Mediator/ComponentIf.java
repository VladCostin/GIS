package Mediator;

import java.awt.Panel;


/**
 * @author Vlad Herescu
 *
 */
public interface ComponentIf {
	
	/**
	 * @return : the panel shown in the Mediator's frame
	 */
	public Panel getPanel();
	
	

	/**
	 * @param _notification : the notification sent by the subject
	 */
	public void update(Notification _notification);
	

}
