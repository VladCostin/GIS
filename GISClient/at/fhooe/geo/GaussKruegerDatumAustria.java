package at.fhooe.geo;

/*
 * $Log: GaussKruegerDatumAustria.java,v $
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


public class GaussKruegerDatumAustria extends GaussKrueger {
	
	private static final double m_deltaGreenwich = 
		Math.toRadians(17.0 + (40.0/60.0));

	protected EllipsoidParameter m_ellipse = 
		EllipsoidParameter.getEllipsoidParameter(EllipsoidParameter.DA);
	
	public GaussKruegerDatumAustria(int _rechts,
	                                int _hoch,
	                                int _meridian) 
	{
	  m_rechts   = _rechts;
	  m_hoch     = _hoch;
	  m_meridian = _meridian;                       	
	}
	
	/**
	 * Konstruktor
	 * @param _geo Die Position in Form einer geographischen Koordinate (WGS84)
	 */
	public GaussKruegerDatumAustria(GeographischWGS84 _geo) 
	{
		this(_geo, 
				 GaussKruegerDatumAustria.calcMeridian(_geo.getLongitude()));
	}
	
	/**
	 * Konstruktor
	 * @param _geo      Die Position in Form einer geographischen Koordinate (WGS84)
	 * @param _meridian Der Zielmeridianstreifen
	 */
	public GaussKruegerDatumAustria(GeographischWGS84 _geo, 
	                                int               _meridian) 
	{	
		m_meridian = _meridian;
		
		double lon = Math.toRadians(_geo.getLongitude());
		double lat = Math.toRadians(_geo.getLatitude());
		
		// Unterschied Greenwich zu Hierro
		double lambdaMeridian = 0.0;
		switch (_meridian) {
			case 28 : lambdaMeridian = Math.toRadians(28.0) - m_deltaGreenwich; break;
			case 31 : lambdaMeridian = Math.toRadians(31.0) - m_deltaGreenwich; break;
			case 34 : lambdaMeridian = Math.toRadians(34.0) - m_deltaGreenwich; break;
		}//switch
		
		double lambdaMittelmeridian = lon - lambdaMeridian;
		// System.out.println("Lambda Mittelmeridian --> " + Math.toDegrees(lambdaMittelmeridian));
		//
		// tan(meridiankonvergenz) = tan(lambdaMittelmeridian) * sin(latitude)
		double meridiankonvergenz   = 
			Math.atan(Math.tan(lambdaMittelmeridian) * Math.sin(lat));		
		// System.out.println("Meridiankonvergenz    --> " + Math.toDegrees(meridiankonvergenz));
		//
		// N = a/(Sqrt(1-e^2*sin^2(latitude)))
		double normaleDurchPunkt    = 
			m_ellipse.get_a()/Math.sqrt(1 - (Math.pow(m_ellipse.get_e(),2) * (Math.sin(lat) * Math.sin(lat))));
		// System.out.println("N --> " + normaleDurchPunkt);
		//
		// sin(latitudeS) = sin(lambdaMittelmeridian) * cos(latitude)
		double latitudeS  = 
		  Math.asin(Math.sin(lambdaMittelmeridian)*Math.cos(lat));
		// System.out.println("Latitude Strich --> " + Math.toDegrees(latitudeS));
		//
		// B = c1 * latitude - c2 * sin(2*latitude) + c3 * sin(4*latitude)				
		double meridianbogen = m_ellipse.get_c1() * Math.toDegrees(lat) - 
		                       m_ellipse.get_c2() * Math.sin(2*lat) + 
		                       m_ellipse.get_c3() * Math.sin(4*lat);
		// System.out.println("B --> " + meridianbogen);
		//
		// x = 2 * N * tan(latitudeS / 2) * tan(meridankonvergenz / 2.0) + B
		double hochwert   = (2 * normaleDurchPunkt * Math.tan(latitudeS / 2.0) * 
		                     Math.tan(meridiankonvergenz / 2.0)) + meridianbogen;
		// System.out.println("hoch  --> " + hochwert);
		//
		// log(y) = log(N * sin(latitudeS)) + 1/3 * sin^2(latitudeS)
		double rechtswert = 0.0;
		if (latitudeS < 0) {
			latitudeS = -1 * latitudeS;
			rechtswert = Math.exp(Math.log(normaleDurchPunkt* Math.sin(latitudeS)) + 
		                    1.0/3.0 * (Math.sin(latitudeS) * Math.sin(latitudeS)));
		  rechtswert = -rechtswert;
		} else {
			rechtswert = Math.exp(Math.log(normaleDurchPunkt* Math.sin(latitudeS)) + 
		                    1.0/3.0 * (Math.sin(latitudeS) * Math.sin(latitudeS)));
		}
		// System.out.println("rechts --> " + rechtswert);
		                    
		m_rechts = (int)(rechtswert * 100); // Genauigkeit in cm, Nachkommastellen abschneiden
		hochwert = hochwert - 5000000;
		m_hoch   = (int)(hochwert   * 100); // Genauigkeit in cm, Nachkommastellen abschneiden
	}

  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
  //                                  PUBLIC
  // --------------------------------------------------------------------------
  // --------------------------------------------------------------------------
	
	public String toString() {
		return "GaussKrüger-Koordinate in cm (" + m_meridian + 
		       ";" + m_rechts +
		       ";" + m_hoch + ")";		       
	}
	
	
	public static int calcMeridian(double _longitude) {
		double lambdaMittelmeridian28 = 
			Math.toDegrees(Math.toRadians(_longitude) - (Math.toRadians(28.0) - m_deltaGreenwich));
		double lambdaMittelmeridian31 = 
			Math.toDegrees(Math.toRadians(_longitude) - (Math.toRadians(31.0) - m_deltaGreenwich));
		double lambdaMittelmeridian34 = 
			Math.toDegrees(Math.toRadians(_longitude) - (Math.toRadians(34.0) - m_deltaGreenwich));
			
		// System.out.println("28 --> " + lambdaMittelmeridian28);
		// System.out.println("31 --> " + lambdaMittelmeridian31);
		// System.out.println("34 --> " + lambdaMittelmeridian34);
			
		if ((-1.5 < lambdaMittelmeridian28) && (lambdaMittelmeridian28 <= 1.5)) {
			return 28;
		} else if ((-1.5 < lambdaMittelmeridian31) && (lambdaMittelmeridian31 <= 1.5)) {
			return 31;
		} else if ((-1.5 < lambdaMittelmeridian34) && (lambdaMittelmeridian34 <= 1.5)) {
			return 34;
	} else {
			System.out.println("Meridianstreifen konnte nicht ermittelt werden, verwende Meridianstreifen 31");
			return 31;
		}
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
		GaussKruegerDatumAustria temp = null;
		
		double lon = (11.0 + (50.0/60.0));
		double lat = (46.0 + (30.0/60.0));
		
		temp = new GaussKruegerDatumAustria(new GeographischWGS84(lon, lat), 28);
		System.out.println(temp);
		temp = new GaussKruegerDatumAustria(new GeographischWGS84(lon, lat), 31);
		System.out.println(temp);
		temp = new GaussKruegerDatumAustria(new GeographischWGS84(lon, lat), 34);
		System.out.println(temp);
		System.out.println();
		
		
		temp = new GaussKruegerDatumAustria(new GeographischWGS84(lon, lat));
		System.out.println(temp);
		lon = (11.0 + (55.0/60.0));
		temp = new GaussKruegerDatumAustria(new GeographischWGS84(lon, lat));
		System.out.println(temp);
		lon = (11.0 + (45.0/60.0));
		temp = new GaussKruegerDatumAustria(new GeographischWGS84(lon, lat));
		System.out.println(temp);
	}
} // class