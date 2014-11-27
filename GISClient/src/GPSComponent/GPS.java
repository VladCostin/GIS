package GPSComponent;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

















import org.postgresql.PGConnection;

import common.ConstantsId;
import common.Notifications;
import ContextModel.LocationContext;
import GIS.Tables_Austria_OSM;
import GeoObject.POIObject;
import Mediator.ComponentIf;
import Mediator.Subject;
import Mediator.TypesNotification;

/**
 * @author Vlad Herescu
 *
 */
public class GPS implements ComponentIf, Observer{
	
	
	private NMEAInfo m_info;
	
	/**
	 * I don't know what's this for
	 */
	boolean m_useErrorFile = false;
	
	/**
	 * object which will parse the data
	 */
	NMEAParser parser;
	
	
	/**
	 * the main panel, containing the others : the panel where he can select the action
	 * 
	 */
	Panel m_panel;
	
	/**
	 * panel where the location of the user is shown
	 */
	GPSInfoPanel m_gpsInfoPanel;
	
	/**
	 * the instance of the mediator, that will share all the data from each observer 
	 */
	Subject m_subject;
	
	/**
	 * a list containing the gps locations
	 */
	HashMap<TypesNotification, ArrayList<Notifications>> m_notifications;
	
	
	
	/**
	 *  initiating the members of GPS module
	 * @param _subject : the mediator to which the data will be transmitted
	 */
	public GPS(Subject _subject)
	{
		m_panel = initPanel();
		m_subject = _subject;
		m_notifications = new HashMap<TypesNotification, ArrayList<Notifications>>();
		m_notifications.put(TypesNotification.CONTEXT_ELEMENT, new ArrayList<Notifications>());
		m_notifications.put(TypesNotification.POI_OBJECT, new ArrayList<Notifications>());
		m_info = new NMEAInfo();

	}

	/**
	 * @return : the panel containing data about the gps 
	 */
	public Panel initPanel() {
		Panel panel = new Panel();
		panel.add(initInfoPanel());
		
		return panel;
	}

	private Component initInfoPanel() {
		m_gpsInfoPanel = new GPSInfoPanel(m_info);
		m_gpsInfoPanel.setSize(600, 100);
		m_gpsInfoPanel.setMinimumSize(new Dimension(600, 100));
		m_gpsInfoPanel.setPreferredSize(new Dimension(600, 100));
		
		return m_gpsInfoPanel;
	}

	@Override
	public Panel getPanel() {
		System.out.println("a intrat in startToDisplayData");
		// TODO Auto-generated method stub
		startToDisplayData();
		startToUpdatePanel();
		return  m_panel;
	}

	/**
	 * display the information parsed from the file
	 */
	public void startToUpdatePanel() 
	{
		new Thread() {
			public void run() {
				m_gpsInfoPanel.repaint();
				 
			}
		}.start();
		
	}

	/**
	 * starts the thread that parses the data from the file
	 */
	public void startToDisplayData() 
	{
		parser = null;
		try {
			if(m_useErrorFile)
				parser = new NMEAParser("GPS-Log-3.log");
			else
				parser = new NMEAParser("GPS-Log-3.log");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//parser.addObserver(m_gpsInfoPanel);
		parser.addObserver(this); 
		
		new Thread(parser).start();
		System.out.println("a intrat in startToDisplayData");
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TypesNotification _notification) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<TypesNotification> getNotificationsTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Observable o, Object _arg) {
		
		System.out.println("intra in observerul de aici");

		if (m_info != null)
			m_info = (NMEAInfo) _arg;
		
		communicateContextElement();
		communicateGPSData();
		
		
		m_gpsInfoPanel.m_info = m_info;
		m_gpsInfoPanel.repaint();
	}

	/**
	 * sends data to the GIS via Mediator
	 */
	public void communicateGPSData() {
		
		ArrayList<Notifications> contextElements = m_notifications.get(TypesNotification.POI_OBJECT);
		
		double latitude = m_info.getLatitudeGrad() * 1000000 + m_info.getLatitudeMinuten() * 10000;
		double longitude = m_info.getLongitudeGrad() * 1000000 + m_info.getLongitudeMinuten() * 10000;
		
		//System.out.println(m_info.getLatitudeGrad() + " " + m_info.getLatitudeMinuten());
		//System.out.println(m_info.getLongitudeGrad() + " " + m_info.getLongitudeMinuten());
		
		System.out.println("coordonate :" + latitude + " " + longitude);
		
		contextElements.clear();	
		
		contextElements.add(new  POIObject("999",ConstantsId.police, longitude,latitude ));
		
		System.out.println( ((POIObject) contextElements.get(0)).m_point.x + " " + ((POIObject) contextElements.get(0)).m_point.y);
		m_subject.communicatePOI(m_notifications.get(TypesNotification.POI_OBJECT));
	}

	/**
	 * sends data to the Context Manager via Mediator
	 */
	public void communicateContextElement() {
		
		ArrayList<Notifications> contextElements = m_notifications.get(TypesNotification.CONTEXT_ELEMENT);
		
	//	SELECT ST_AsText(ST_Transform(ST_GeomFromText('POLYGON((743238 2967416,743238 2967450,
	//			743265 2967450,743265.625 2967416,743238 2967416))',2249),4326)) As wgs_geom;
	//	generateValues();
		
		contextElements.clear();	
		contextElements.add(
		new LocationContext( m_info.getLatitudeGrad(),m_info.getLatitudeMinuten(), m_info.getLongitudeGrad(), m_info.getLongitudeMinuten() ));
		m_subject.communicateContext(m_notifications.get(TypesNotification.CONTEXT_ELEMENT));
		
	}
	
	public void generateValues()
	{
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/osm"; 
			Connection conn = DriverManager.getConnection(url, "gisuser", "gisuser");
		
			PGConnection c = (PGConnection) conn;
			c.addDataType("geometry", Class.forName("org.postgis.PGgeometry"));
			c.addDataType("box3d", Class.forName("org.postgis.PGbox3d"));
			Statement s = conn.createStatement();
			
			
			String queryForm = "Select ST_GeomFromText('POINT(-71.064544 42.28787)')";
			
			ResultSet r = s.executeQuery(queryForm);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// searching for data for each table in the database
		/*for(Tables_Austria_OSM table : _tables)
		{
			
			
			//s.execute(" SELECT UpdateGeometrySRID('"+table.toString() + "','geom',4326)");
			whereClause = createWhereClause(_types, _area, table.toString());

			
			
			queryForm = createSelectStatement(table) + createFromStatement(table) +  whereClause;
			System.out.println(queryForm);

			ResultSet r = s.executeQuery(queryForm);
		*/
	}


}
