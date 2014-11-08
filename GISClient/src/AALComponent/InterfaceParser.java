package AALComponent;

import java.util.ArrayList;

import common.Notifications;

//import ContextModel.InterfaceContext;

/**
 * specifies the element needed to be implemented by the Parsers(Concrete Creators)
 * @author Vlad Herecu
 *
 */
public interface InterfaceParser {

	/**
	 * the method that creates a context element by parsing a xml file
	 * @return : the context element parsed from xml
	 */
	public ArrayList<Notifications> factoryMethod();
	
	
}
