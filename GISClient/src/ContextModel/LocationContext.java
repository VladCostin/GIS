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
	public LocationContext(double _longitude, double _latitude)
	{
		m_latitude = _latitude;
		m_longitude = _longitude;
	}

	@Override
	public String getValue() {
		return Double.toString(m_latitude) + " " + Double.toString(m_longitude);
	}

	@Override
	public String getType() {
		return ElementType.LOCATION.name();
	}



	
	
	
}
