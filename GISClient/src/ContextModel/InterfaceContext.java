package ContextModel;

import common.Notifications;

/**
 * interface developed by each context instance
 * @author Vlad Herescu
 *
 */
public interface InterfaceContext extends Notifications{

	/**
	 * @param _context : another context element of the same type 
	 * @return : checks if the current context element, such as the current time, has approximately the same value
	 * when the event should be triggered
	 */
	public boolean checkCompatibility(InterfaceContext _context);
	
	/**
	 * sets the MetaData as a member to the context element
	 * @param m_data : the metadata obtained from the xml file
	 */
	public void setM_data(MetadataContext m_data);
	
	public String getValue();
	
	public String getType();
	
	public String getId();
	
	public MetadataContext getM_data();
	
	public void setId(int id);
}
