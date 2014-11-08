package Mediator;

import java.awt.Panel;
import java.util.Set;


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
	public void update();
	/**
	 * @param _notification : the notification send my the mediator,
	 * used by the component to check if the data would be useful for it
	 */
	public void update(TypesNotification _notification); 
	
	/**
	 * @return : the notifications that the component is interested in;
	 */
	public Set<TypesNotification> getNotificationsTypes();
	

}
