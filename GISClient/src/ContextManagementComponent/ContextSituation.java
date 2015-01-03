package ContextManagementComponent;

import ContextModel.ElementType;
import ContextModel.InterfaceContext;
import ContextModel.LocationContext;
import ContextModel.TemperatureContext;
import ContextModel.TemporalContext;
import ContextModel.VelocityContext;
import common.Notifications;

import java.util.HashMap;

/**
 * the output of ContextMangement, made from all the context types
 * @author Vlad Herescu
 *
 */
public class ContextSituation implements Notifications{

	/**
	 * contains the context elements and the keys associated
	 */
	HashMap<ElementType,InterfaceContext> m_contextData;
	
	/**
	 * initializing the data
	 */
	public ContextSituation() {
		m_contextData = new HashMap<ElementType,InterfaceContext>();
	}
	
	/**
	 * initializing the current context
	 * @param _time
	 * @param _velocity
	 * @param _location
	 * @param _temperatur
	 */
	public ContextSituation(TemporalContext _time, VelocityContext _velocity, LocationContext _location, TemperatureContext _temperatur)
	{
		m_contextData = new HashMap<ElementType,InterfaceContext>();
		m_contextData.put(ElementType.TIME_CTXT, _time);
		m_contextData.put(ElementType.VEL_CTXT, _velocity);
		m_contextData.put(ElementType.LOC_CTXT, _location);
		m_contextData.put(ElementType.TEMP_CTXT, _temperatur);
		
	}
	
	
}
