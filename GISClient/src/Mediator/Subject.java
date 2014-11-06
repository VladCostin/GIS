package Mediator;

import ContextModel.ContextElement;
import GeoObject.GeoObject;

/**
 * specifing the methods of the subject pattern. 
 * in this case, it contains the methods used by the Mediator to announce other modules regarding information from a certain module.
 * @author Vlad Herescu
 *
 */
public interface Subject {

	
	/**
	 * communicate to the components/modules what is shown on the map
	 * @param _obj : the geoObjects selected by the user to be shown on the map
	 */
	public void communicateGeoObject(GeoObject[] _obj);
	
	
	/**
	 * communicate to the components/modules what new context elements has the system detected from the environment
	 * @param _obj : the context elements received by the system from the environment
	 */
	public void communicateContext(ContextElement[] _obj);
	
}
