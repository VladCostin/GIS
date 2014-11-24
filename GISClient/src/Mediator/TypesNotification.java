package Mediator;

/**
 * used to identify the notifications, because all of them are casted to the interface Notification
 * @author Vlad Herescu
 *
 */
public enum TypesNotification {
	
	/**
	 * associated with the context_element notification from the subject
	 */
	CONTEXT_ELEMENT,
	
	/**
	 * associated with the geo object notification from the subject
	 */
	GEO_OBJECT,
	
	/**
	 * associated to the POI objects send to the subject
	 */
	POI_OBJECT,
	
	/**
	 * associated to the context created by the context management
	 */
	CONTEXT_SITUATION;
	
	
	
}
