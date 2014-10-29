package ContextModel;

/**
 * specifies where is the user
 * @author Vlad Herescu
 *
 */
public class LocationContext extends ContextElement{

	/**
	 * the latitude of the location
	 */
	public double m_latitude;
	
	/**
	 * the longitude of the location
	 */
	public double m_longitude;
	
	/**
	 * initializing the object
	 */
	public LocationContext() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * initializing the location data
	 * @param _longitude 
	 * @param _latitude
	 */
	LocationContext(double _longitude, double _latitude)
	{
		m_latitude = _latitude;
		m_longitude = _longitude;
	}
	
}
