package ContextModel;

/**
 * interface developed by each context instance
 * @author Vlad Herescu
 *
 */
public interface InterfaceContext {

	/**
	 * @param _context : another context element of the same type 
	 * @return : checks if the current context element, such as the current time, has approximately the same value
	 * when the event should be triggered
	 */
	public boolean checkCompatibility(InterfaceContext _context);
}
