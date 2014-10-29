package at.fhooe.geo;

/*
 * $Log: GeographischWGS84.java,v $
 */

/**
 * <dl> 
 *   <dt><b>Copyright:</b></dt> 
 *     <dd> Copyright (c) 2006 </dd> 
 *   <dt><b>Company:</b></dt> 
 *     <dd> FH Oberoesterreich </dd>
 * </dl>
 *
 * @author Jens Kroesche
 * @version $Revision: 1.5 $
 * @created $Date: 2005/05/10 14:56:23 $
 */

public class GeographischWGS84 {
	
	/// Der Rechtswert in cm
	protected double m_longitude = 0;
	/// Der Hochwert   in cm
	protected double m_latitude  = 0;
	
	/**
	 * Konstruktor
	 * @param _longitude Die geographische Laenge in GRAD
	 * @param _latitude  Die geographische Breite in GRAD
	 */
	public GeographischWGS84(double _lon, double _lat) {
		m_longitude = _lon;
		m_latitude  = _lat;
	}
	
	
	public double getLongitude() { return m_longitude; }
	public double getLatitude()  { return m_latitude;  }

  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  //                                  PUBLIC
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
	
	/**
	 * Gibt die GaussKrueger-Koordinaten als String aus
	 * @return die Koordinate in String Form
	 * @see java.lang.String
	 */
	public String toString() {
		return "Geographische-Koordinate WGS84 in Grad (" +  
		       m_longitude + ";" + m_latitude + ")";		       
	}

  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  //                                 PROTECTED
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------


  // --------------------------------------------------------------------------
  //                             SUPPORT METHODS
  // --------------------------------------------------------------------------
	
	public static void main(String[] _argv) {
	}
} // class