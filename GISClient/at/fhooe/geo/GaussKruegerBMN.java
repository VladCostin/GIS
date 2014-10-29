package at.fhooe.geo;

/*
 * $Log: GaussKruegerBMN.java,v $
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

public class GaussKruegerBMN extends GaussKrueger {	
	
	public GaussKruegerBMN(int _rechts, int _hoch) {
		m_rechts = _rechts;
		m_hoch   = _hoch;
		// hmmm ... ob das wohl hinhaut ...
		if (m_rechts < 30000000) {
			m_meridian = 28;
		} else if (m_rechts < 70000000) {
			m_meridian = 31;
		} else {
			m_meridian = 34;
		}
	}
	
	public GaussKruegerBMN(int _meridian, int _rechts, int _hoch) {
		m_meridian = _meridian;
		m_rechts   = _rechts;
		m_hoch     = _hoch;
	}
	
	/**
	 * Konstruktor
	 * @param _geo Die geographische Koordinate der Position
	 */
	public GaussKruegerBMN(GeographischWGS84 _geo) {
		this(new GaussKruegerDatumAustria(_geo));
	}
	
	/**
	 * Konstruktor
	 * @param _geo      Die geographische Koordinate der Position
	 * @param _meridian Der Zielmeridian in den projeziert werden soll
	 */
	public GaussKruegerBMN(GeographischWGS84 _geo,
	                       int               _meridian) 
	{	
		this( new GaussKruegerDatumAustria(_geo, _meridian));
	}
	
	/**
	 * Konstruktor
	 * @param _longitude Die geographische Laenge in GRAD im WGS84 System
	 * @param _latitude  Die geographische Breite in GRAD im WGS84 System
	 * @param _meridian  Der Zielmeridianstreifen
   */
  public GaussKruegerBMN(double _longitude,
                         double _latitude,
                         int    _meridian) {
    this(new GeographischWGS84(_longitude,_latitude),_meridian);                       	
  }
	 
	
	
	/**
	 * Konstruktor
	 * @param _gkk Die GaussKrueger Koordinaten im geodaetischen Format
	 */
	public GaussKruegerBMN(GaussKruegerDatumAustria _gkk) {
		switch (_gkk.getMeridian()) {
			case 28 : m_rechts = 15000000 + _gkk.getRechts(); break;
			case 31 : m_rechts = 45000000 + _gkk.getRechts(); break;
			case 34 : m_rechts = 75000000 + _gkk.getRechts(); break;
		} // switch
		m_hoch     = _gkk.getHoch();
		m_meridian = _gkk.getMeridian();
	}

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
		return "GaussKrüger-Koordinate BMN in cm (" + m_meridian + 
		       ";" + m_rechts +
		       ";" + m_hoch + ")";		       
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
		if (_argv.length == 0) {
			System.out.println("Use:");
			System.out.println("GaussKruegerBMN TEST");
			System.out.println("GaussKruegerBMN TRANSFORM <longitude> <latitude> <meridian>");
		} else if (_argv[0].equals("TEST")) {
			GaussKruegerBMN temp = null;
			
			double lon = (11.0 + (50.0/60.0));
			double lat = (46.0 + (30.0/60.0));
			
			temp = new GaussKruegerBMN(new GeographischWGS84(lon, lat), 28);
			System.out.println(temp);
			temp = new GaussKruegerBMN(new GeographischWGS84(lon, lat), 31);
			System.out.println(temp);
			temp = new GaussKruegerBMN(new GeographischWGS84(lon, lat), 34);
			System.out.println(temp);
			System.out.println();
			
			
			temp = new GaussKruegerBMN(new GeographischWGS84(lon, lat));
			System.out.println(temp);
			lon = (11.0 + (55.0/60.0));
			temp = new GaussKruegerBMN(new GeographischWGS84(lon, lat));
			System.out.println(temp);
			lon = (11.0 + (45.0/60.0));
			temp = new GaussKruegerBMN(new GeographischWGS84(lon, lat));
			System.out.println(temp);
		} else if (_argv.length == 4 && _argv[0].equals("TRANSFORM")) {
			double lon = Double.parseDouble(_argv[1]);
			double lat = Double.parseDouble(_argv[2]);
			int    m   = Integer.parseInt(_argv[3]);
			System.out.println("--> " + new GaussKruegerBMN(lon,lat,m));
		} else {
			System.out.println("Use:");
			System.out.println("GaussKruegerBMN TEST");
			System.out.println("GaussKruegerBMN TRANSFORM <longitude> <latitude> <meridian>");
		}
	}
} // class