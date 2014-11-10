package ContextModel;

/**
 * specifies the speed with which the user is moving
 * @author Vlad Herescu
 *
 */
public class TemperatureContext extends ContextElement{

	/**
	 * the value the user is moving
	 */
	String m_temperature;
	
	/**
	 * specifying the default constructor in case it is needed
	 */
	public TemperatureContext() {
		
	}
	/**
	 * @param _temperature : the speed received from xml
	 */
	public TemperatureContext(String _temperature)
	{
		m_temperature = _temperature;
	}
	
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return m_temperature;
	}
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "Temperature";
	}
	
}
