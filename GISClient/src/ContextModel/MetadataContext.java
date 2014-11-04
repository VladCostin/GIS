package ContextModel;

/**
 * 
 * @author Vlad Herescu
 *
 */
public class MetadataContext {

	/**
	 * the version of the element
	 */
	String m_version;
	
	/**
	 * a description of the context element
	 */
	String m_description;
	
	/**
	 * specifies the unit of the context element's value
	 */
	String m_unit;
	
	
	/**
	 * time when the value has been registered
	 */
	String m_time;
	
	/**
	 * the location's latitude
	 */
	Double m_latitude;
	
	/**
	 * the location's longitude
	 */
	Double m_longitude;
	


	public MetadataContext(String _version, String _description, String _unit,
			String _time, Double _latitude, Double _longitude) {
		super();
		m_version = _version;
		m_description = _description;
		m_unit = _unit;
		m_time = _time;
		m_latitude = _latitude;
		m_longitude = _longitude;
	}
	
	public MetadataContext()
	{
		
	}
	
	public String getM_version() {
		return m_version;
	}

	public void setM_version(String m_version) {
		this.m_version = m_version;
	}

	public String getM_description() {
		return m_description;
	}

	public void setM_description(String m_description) {
		this.m_description = m_description;
	}

	public String getM_unit() {
		return m_unit;
	}

	public void setM_unit(String m_unit) {
		this.m_unit = m_unit;
	}

	public String getM_time() {
		return m_time;
	}

	public void setM_time(String m_time) {
		this.m_time = m_time;
	}


	public Double getM_latitude() {
		return m_latitude;
	}

	public void setM_latitude(Double m_latitude) {
		this.m_latitude = m_latitude;
	}

	public Double getM_longitude() {
		return m_longitude;
	}

	public void setM_longitude(Double m_longitude) {
		this.m_longitude = m_longitude;
	}
	
	
	
}
