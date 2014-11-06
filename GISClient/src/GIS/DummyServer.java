package GIS;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import de.intergis.JavaClient.comm.CgError;
import de.intergis.JavaClient.comm.CgGeoConnection;
import de.intergis.JavaClient.comm.CgConnection;
import de.intergis.JavaClient.comm.CgGeoInterface;
import de.intergis.JavaClient.comm.CgResultSet;
import de.intergis.JavaClient.comm.CgStatement;
import de.intergis.JavaClient.comm.CgIGeoObject;
import de.intergis.JavaClient.comm.CgIGeoPart;










import java.awt.Polygon;
import java.awt.Rectangle;










import GeoObject.AreaObj;
import GeoObject.GeoObject;
import de.intergis.JavaClient.gui.IgcConnection;

public class DummyServer implements GeoServerInterface{

  // the connection to the geo server
  CgGeoConnection m_geoConnection = null;
  // the query interface of the server to extract data
  CgGeoInterface  m_geoInterface  = null;
  
  protected String m_server = null;
  private String m_user = null;
  private String m_pass = null;
	
  /**
  * the types that can be found in OSM server 
  */
  ArrayList<String> _types;

	public DummyServer()
	{
		m_server = "T:localhost:4949";
		initTypes();
	}


	public DummyServer(String _server, String _user, String _pass) {
		//m_server = _server;
		m_server = "T:localhost:4949";
		m_user   = _user;
		m_pass   = _pass;
		initTypes();
	}
	
	private void initTypes() {
		_types = new ArrayList<String>();
		_types.add("1112");
		_types.add("233");
		_types.add("931");
		_types.add("933");
		_types.add("1101");
		
	}
	

	@Override
	public void setCredentials(String _username, String _password) {
		
		m_pass = _password;
		m_user = _username;
		
	}

	@Override
	public boolean checkCredentials(String _username, String _password) {
		
		  // the geo server gets initialized
		  try {
			  m_geoConnection = 
			  new IgcConnection(new CgConnection (_username,
			                                            _password,
			                                            m_server,
			                                            null));
			
			  // the query interface of the server is retrieved
			  m_geoInterface = m_geoConnection.getInterface();
			  System.out.println("executa si asta, nu se opreste aici");
			  m_geoConnection.Quit();
		} catch (CgError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	    m_pass = _password;
		m_user = _username;
		return true;

		 
	}

	@Override
	public Vector<GeoObject> typeAreaQuery(List<String> types,
			Rectangle _area) {
		Vector<GeoObject> objectContainer = new Vector<GeoObject>();
	    try {
	      // the geo server gets initialized
	      m_geoConnection = 
	        new IgcConnection(new CgConnection (m_user,
	                                            m_pass,
	                                            m_server,
	                                            null));
	      // the query interface of the server is retrieved
	      m_geoInterface = m_geoConnection.getInterface();
	    } catch (Exception _e) {_e.printStackTrace();}
	    try {
	      String      cmd             = "select * from data " + createWhereClause(types, _area,null) ;
	      CgStatement stmt            = m_geoInterface.Execute(cmd);
	      CgResultSet cursor          = stmt.getCursor();
	      String      id              = null;
	      int         type            = 0;
	      Polygon     poly            = null;
		  // while there are still objects in the queue
	      while (cursor.next()) {
					CgIGeoObject obj = cursor.getObject();
					// extract object ID
					id   = obj.getName();
					// extract object type/category (for instance wood, river, land, ...)
					type = obj.getCategory();
					// extract object geometry, which could be build up from many parts
					CgIGeoPart[] parts = obj.getParts();
					for (int i = 0 ; i < parts.length ; i++){
					  int   pointCount = parts[i].getPointCount();
					  int[] xArray     = parts[i].getX();
					  
					  int[] yArray     = parts[i].getY();
				    poly = new Polygon(xArray, yArray, pointCount);
					} 
					GeoObject geo = new GeoObject(id, type);
					AreaObj area = new AreaObj();
					area.set_polygon(poly);
					geo._components.add(area);
					
					
					
					// System.out.println(geo);
				  objectContainer.addElement(geo);
				
	      } // while cursor
	    } catch (Exception _e) { _e.printStackTrace(); }
		return objectContainer;
		
	}
	


	@Override
	public String createWhereClause(List<String> _types, Rectangle _area, String _table) {
		
		if(_types.size() == 0)
			return  "where type in (233, 931, 932, 933, 934, 1101)";
		
		String clause = "where type in(";
		
		for(String _type : _types)
			clause += _type + ",";
		clause = clause.substring(0, clause.length() -1);
		clause += ")";
		System.out.println(clause);
		
		return clause;
		
		
	}

	@Override
	public Vector<GeoObject> typeQuery(List<String> _types) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getM_user() {
		return m_user;
	}

	public void setM_user(String m_user) {
		this.m_user = m_user;
	}

	public String getM_pass() {
		return m_pass;
	}

	public void setM_pass(String m_pass) {
		this.m_pass = m_pass;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return m_user;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return m_pass;
	}


	@Override
	public ArrayList<String> getTypes() {
		return _types;
	}

}
