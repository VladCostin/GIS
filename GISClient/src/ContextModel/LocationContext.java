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
	public int m_latitudeGrad;
	
	/**
	 * the latitude of the location
	 */
	public double m_latitudeMinuten;
	
	
	/**
	 * the latitude of the location
	 */
	public int m_longitudeGrad;
	
	/**
	 * the latitude of the location
	 */
	public double m_longitudeMinuten;
	
	
	/**
	 * the longitude of the location
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
		m_latitude  = _latitude;
		m_longitude = _longitude;
	}
	
	
	/**
	 * initializing the location data
	 * @param _longitude 
	 * @param _latitude
	 */
	public LocationContext(int _latitudeGrad, double _latitudeMinuten, int _longitudeGrad,double _longitudeMinuten)
	{
		m_latitudeGrad = _latitudeGrad;
		m_latitudeMinuten = _latitudeMinuten;
		m_longitudeGrad = _longitudeGrad;
		m_longitudeMinuten = _longitudeMinuten;
	}
	

	@Override
	public String getValue() {
		return Double.toString(m_latitudeGrad) + " " + Double.toString(m_longitude);
	}

	@Override
	public String getType() {
		return ElementType.LOCATION.name();
	}



	
	
	
}
