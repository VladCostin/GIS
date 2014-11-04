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
	 * receives the data from the Mediator such as the context elements, geo objects, etc 
	 */
	public void update();

}
