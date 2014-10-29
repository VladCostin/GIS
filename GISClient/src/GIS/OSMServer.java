package GIS;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import org.postgis.Geometry;
import org.postgis.LineString;
import org.postgis.MultiLineString;
import org.postgis.MultiPolygon;
import org.postgis.PGgeometry;
import org.postgresql.PGConnection;

/**
 * @author Vlad Herescu
 *
 */
public class OSMServer implements GeoServerInterface{
	
	/**
	 * the credentials of the user used for showing the map
	 */
	String m_User;
	
	/**
	 * the credentials of the user used for showing the map
	 */
	String m_Password;
	
	/**
	 * the list of tables used to get data from
	 */
	ArrayList<Tables_Austria_OSM> _tables;
	
	
	/**
	 * initializing the data
	 */
	public OSMServer()
	{

		initMetadata();
	}
	
	/**
	 * @param _User : the Username used for login
	 * @param _Password : the password used for login
	 */
	public OSMServer(String _User, String _Password) {
		
		m_User = _User;
		m_Password = _Password;
		
		
		initMetadata();

	}

	
	private void initMetadata() {
		
		_tables = new ArrayList<Tables_Austria_OSM>();
		
	
		_tables.add(Tables_Austria_OSM.water_austria_polygons);
		_tables.add(Tables_Austria_OSM.water_austria_polylines);
		_tables.add(Tables_Austria_OSM.roads_austria_polylines);
		_tables.add(Tables_Austria_OSM.railroads_austria_polylines);
		_tables.add(Tables_Austria_OSM.city_austria_polygons);
		_tables.add(Tables_Austria_OSM.amenity_austria_points);
		
		

	}

	public Vector<GeoObject> typeAreaQuery(List<String> _types, Rectangle _area)
	{
		Vector<GeoObject> objectContainer = new Vector<GeoObject>(); // all objects to be shown on the map
		Connection conn;											 // connection with jdbc
		String queryForm;
		String whereClause;
		try 
		{
			// creating the connection
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/osm"; 
			conn = DriverManager.getConnection(url, m_User, m_Password);
		
			PGConnection c = (PGConnection) conn;
			c.addDataType("geometry", Class.forName("org.postgis.PGgeometry"));
			c.addDataType("box3d", Class.forName("org.postgis.PGbox3d"));
			Statement s = conn.createStatement();
			
			// searching for data for each table in the database
			for(Tables_Austria_OSM table : _tables)
			{
				
				
				s.execute(" SELECT UpdateGeometrySRID('"+table.toString() + "','geom',4326)");
				whereClause = createWhereClause(_types, _area, table.toString());
		//		if(whereClause.equals(""))
		//			continue;
				queryForm = createSelectStatement(table) + createFromStatement(table) +  whereClause;
				System.out.println(queryForm);

				ResultSet r = s.executeQuery(queryForm);
				while( r.next() ) { 
					PGgeometry geom = (PGgeometry)r.getObject(1);
					Geometry  g = geom.getGeometry();
					String id  = (String)  r.getObject(2);
					String _type = (String)r.getObject(4);
					Integer _typeInteger;
					//System.out.println(table.toString()+"."+_type);
					if(CoreData._hashMapTypeOSM.get(table.toString()+"."+_type) == null)
					{
						System.out.println(table.toString()+"."+_type);
						continue;
					}
					_typeInteger = CoreData._hashMapTypeOSM.get(table.toString()+"."+_type);
					
					GeoObject object = new GeoObject(id,_typeInteger );
					switch(g.type)
					{
						case Geometry.MULTIPOLYGON:
							
							object._components.addAll( createAreaObject((MultiPolygon) g));
						
							break;
							
						case Geometry.MULTILINESTRING:
							object._components.addAll(createLineObj((MultiLineString) g));
							break;
							
						case Geometry.POINT:
							
							org.postgis.Point point = (org.postgis.Point) g;
							object._components.add(new PointObj((int)  point.x,(int) point.y));
							break;
						
					}
				
				
					objectContainer.add(object);
				

				} 
				System.out.println("finish");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		return objectContainer;

	}


	@Override
	public Vector<GeoObject> typeQuery(List<String> _types) {
		
		return typeAreaQuery(_types, null);
	}
	
	/**
	 * @param _table : name on the table where data are retrieved
	 * @return : from clause
	 */
	public String createFromStatement(Tables_Austria_OSM _table) {
		
		return " from " + _table.toString();
	}

	/**
	 * @param _types : filters regarding the types to be shown
	 * @param _area : the area to be shown on the map
	 * @param _tableName : the name of the table where the data is queried
	 * @return : the where clause
	 */
	public String createWhereClause(List<String> _types, Rectangle _area, String _tableName) {
		
		
		if( _area == null && _types.size() == 0)
			return "";//" where geom && ST_MakeEnvelope(13, 48, 15, 49)";
		
		if(_area !=null && _types.size() == 0)
			return	""; //" where geom && ST_MakeEnvelope(" +   (_area.x - _area.width) + ","
					//										+ (_area.y - _area.height) + ","
					//										+ _area.x + ","
					//										+ _area.y + ")";
		boolean identify = false;
		//String whereClause = " where geom && ST_MakeEnvelope(13, 48, 15, 49) AND "+ 
		String whereClause = " where geom && ST_MakeEnvelope(14.3226, 48.3260, 14.2503, 48.2778) AND "+
		CoreData._hashMapTypeColumn.get(Tables_Austria_OSM.valueOf(_tableName)) + " in (";

		for(String _type :_types)
		{

			String words[]= _type.split("\\.");
			
			if(_tableName.equals(words[0]))
			{
				whereClause += "'"+words[1]+"',";
				identify = true;
			}

		}
		
		if(identify == false)
			return "";
			//return  " where geom && ST_MakeEnvelope(13, 48, 15, 49)";
		
		
		whereClause = whereClause.substring(0, whereClause.length() -1);
		whereClause += ")";

		
		return whereClause;

	}
	


	/**
	 * @param _table = instance of a Tables_Metadata, where information about a table is storeds
	 * @return : the select clause
	 */
	public String createSelectStatement(Tables_Austria_OSM _table) {
		
		String clause =  "Select ST_Transform(geom,31467) geom, id,name," +
		CoreData._hashMapTypeColumn.get(_table);
		
		
		return clause;
	}

	
	
	/**
	 * creates a list of AreaObj objects contained in a MultiPolygon, that will be displayed
	 * @param _multi : the MultiPolygon obtained from  the database
	 * @return : a list of ObjectTeil represented as AreaObh
	 */
	public ArrayList<AreaObj> createAreaObject(MultiPolygon _multi)
	{
		
		int j;
		int i;
		double x,y;
		ArrayList<AreaObj> areas = new ArrayList<AreaObj>(); // where the areas will be stored
		
		if(_multi.numPoints() == 1)
			System.out.println("Are dimensiunea : " + _multi.numPoints());
		
		for(j = 0; j < _multi.numPolygons(); j++)
		{
			
			
			AreaObj area = new AreaObj();
			Polygon _poligon = new Polygon();
			
			for(i = 0; i < _multi.getPolygon(j).numPoints(); i++)
			{
				 x = _multi.getPolygon(j).getPoint(i).x;
				 y = _multi.getPolygon(j).getPoint(i).y;
				_poligon.addPoint((int) x,(int) y);
			}
			area.set_polygon(_poligon);
			areas.add(area);
			
		}
		
		return areas;
	}
	
	/**
	 * creates a list of LineObj from a MultiLineString objects, that will be displayed
	 * @param _multi :  the MultiLineString obtained from  the database
	 * @return : a list of ObjectTeil represented as AreaObh
	 */
	public ArrayList<LineObj> createLineObj(MultiLineString _multi)
	{
		ArrayList<LineObj> lines = new ArrayList<LineObj>();
		
		for(LineString line : _multi.getLines())
		{
			LineObj lineObj = new LineObj();
			ArrayList<Point> _line = new ArrayList<Point>();
			int j;
			for(j = 0; j < line.numPoints(); j++)
				_line.add( new Point( (int) line.getPoint(j).x,(int) line.getPoint(j).y));
			lineObj.set_points(_line);
			lines.add(lineObj);
		}
		return lines;
		
	}
	

	@Override
	public void setCredentials(String _username, String _password) {
		
		m_Password = _password;
		m_User = _username;
		
	}

	@Override
	public boolean checkCredentials(String _username, String _password) {

		Connection conn;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/osm"; 
			conn = DriverManager.getConnection(url, _username, _password);
			System.out.println("executa si asta, nu se opreste aici");
			conn.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}

		m_Password = _password;
		m_User = _username;
		return true;
		
		
	}

	@Override
	public String getUsername() {
		return this.m_User;
	}

	@Override
	public String getPassword() {
		return this.m_Password;
	}

	@Override
	public ArrayList<String> getTypes() {
		// TODO Auto-generated method stub
		ArrayList<String> types = new ArrayList<String>();
		types.addAll(CoreData._hashMapTypeOSM.keySet());
		return types;
	}


}
