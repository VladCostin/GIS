package ContextModel;

/**specifies the speed with which the user is moving
 * @author Vlad Herescu
 *
 */
public class VelocityContext extends ContextElement{

	/**
	 * the value the user is moving
	 */
	int m_speed;
	
	/**
	 * specifying the default constructor in case it is needed
	 */
	public VelocityContext() {
		
	}
	/**
	 * @param _speed : the speed received from xml
	 */
	public VelocityContext(int _speed)
	{
		m_speed = _speed;
	}
	
	@Override
	public void checkValue() {
		System.out.println("sunt velocity");
		
	}
	
}
