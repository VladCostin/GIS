package GIS;

import com.esri.sde.sdk.client.SeConnection;
import com.esri.sde.sdk.client.SeException;
import com.esri.sde.sdk.client.SeLayer;
import com.esri.sde.sdk.client.SeSqlConstruct;
import com.esri.sde.sdk.client.SeRow;
import com.esri.sde.sdk.client.SeColumnDefinition;
import com.esri.sde.sdk.client.SeQuery;
import com.esri.sde.sdk.client.SeColumnDefinition;
import com.esri.sde.sdk.client.SeTable;
import com.esri.sde.sdk.client.SeShape;
import com.esri.sde.sdk.client.SeShapeFilter;
import com.esri.sde.sdk.client.SeExtent;
import com.esri.sde.sdk.client.SeFilter;
import com.esri.sde.sdk.client.SeCoordinateReference;
import com.esri.sde.sdk.client.SeObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;


/**
 * class for making connection 
 * @author admin
 *
 */
public class SDEServer implements GeoServerInterface{

	protected SeConnection m_conn = null;
	protected String m_server = null;
	protected String m_user = null;
	protected String m_pass = null;

	public SDEServer(String _server, String _user, String _pass) {
		m_server = _server;
		m_user   = _user;
		m_pass   = _pass;
	}
	public SDEServer()
	{
		
	}
	
	
	public Vector<GeoObject> loadData() {
		Vector<GeoObject> objectContainer = new Vector<GeoObject>();
	    try {
	    	SeConnection m_conn = new SeConnection(m_server, 5151, "SDE", m_user, m_pass); 	
	    	System.out.println("connection established ...");    	
	    	Vector layerList = m_conn.getLayers();

		    String[] layerNames = new String[]{
		    	"SDE.GEO_ADMIN.ADMINAREA_1_BUNDESLAENDER_BMN31",
				"SDE.GEO_ADMIN.AREA_Wald_BMN31",
				"SDE.GEO_ADMIN.AREA_GEWAESSER_BMN31",
				"SDE.GEO_ADMIN.AREA_WOHNGEBIETE_BMN31"
		    	};    
		    
		    //
		    // Calculate the coordinate system of the layers (needed for sticky mode)
		    // 
		    SeLayer layer = new SeLayer(m_conn, new SeObjectId(60)); // 60 --> Bundeslaender layer
		    SeCoordinateReference coordSys = layer.getCoordRef(); 
		    
		    for (int l = 0 ; l < layerNames.length ; l++) {
		    	// each layer gets queried seperatly
                SeSqlConstruct sqlConstruct = new SeSqlConstruct(layerNames[l]);
          /*
            SeTable table1 = new SeTable(m_conn, layerName );
            // Get the table's column definition.
          
            SeColumnDefinition[] tableDef = table1.describe();
            String[] cols = new String[tableDef.length];
            //   Store the names of all the table's columns in the
            //    String array cols. This array specifies the columns
            //  to be retrieved from the database.
            //
            for( int i = 0 ; i < cols.length ; i++ ) {
                cols[i] = tableDef[i].getName();
            }
            */
          // define the columns of the result "table" 
	        String[] cols = new String[]{
	        	new String("OBJECTID"),
	        	new String("FEATTYP"),
	            new String("SHAPE")};
	
	        // build the query ... 
	        SeQuery query = new SeQuery(m_conn, cols, sqlConstruct);
	        
/*
	        if (m_stickyRectangle != null) {
		        //
		        // Raeumliche Beschränkung definieren
				    // double   minX   = 517334;
				    // double   minY   = 344883;
				    // double   maxX   = 541559;
				    // double   maxY   = 361906;
				    SeExtent extent = new SeExtent(m_stickyRectangle.x,
				                                   m_stickyRectangle.y,
				                                   m_stickyRectangle.x + m_stickyRectangle.width,
				                                   m_stickyRectangle.y + m_stickyRectangle.height);
				    
				    SeShape  bbox   = new SeShape(coordSys);    
				    bbox.generateRectangle(extent);
				    
				    SeShapeFilter filters[] = new SeShapeFilter[] {
				    	new SeShapeFilter( layerNames[l], "SHAPE", bbox, SeFilter.METHOD_ENVP)
				    };
				    // Raeumliche Beschraenkung der Anfrage festlegen ... evtl. zwischen prepare und execute ... 
	          query.setSpatialConstraints(SeQuery.SE_OPTIMIZE, false, filters);
				  } // if raeumliche Beschraenkung
*/			    
			    // prepare the query ... 
			    query.prepareQuery();
                // execute the query ... 
		        query.execute(); 
		      
		      String  id   = null;
		      int     type = -1;
		      Polygon poly = null;
		      
		      SeRow row = null;
		      while ((row = query.fetch()) != null) {      
			       // Get the definitions of all the columns retrieved
			      SeColumnDefinition[] colDefs = row.getColumns();
			
			      // Retrieve data from the first column
			      for (int j = 0 ; j < colDefs.length ; j++) {
				      int dataType = colDefs[j].getType();
				      // Assuming that the columns we retrieve are either string or shape data type
				      // Add more case statements to retrieve all possible SDE data types.
				      // ( See Working with Layers Example )
				      switch( dataType ) {
					      case SeColumnDefinition.TYPE_STRING:
			            // System.out.println("STRING > " + colDefs[j].getName()+ " --> " + row.getString(j));		            
			          break;
					      case SeColumnDefinition.TYPE_INT16:
					        type = row.getShort(j);
			            // System.out.println("INT16  > " + colDefs[j].getName()+ " --> " + type);
			          break;
					      case SeColumnDefinition.TYPE_INT32:
						      id = String.valueOf(row.getInteger(j).intValue());
		              // System.out.println("INT32  > " + colDefs[j].getName()+ " --> " + id);
			          break;
					      case SeColumnDefinition.TYPE_INT64:
			            // System.out.println("INT64  > " + colDefs[j].getName()+ " --> " + row.getLong(j));
			          break;
					      case SeColumnDefinition.TYPE_SHAPE:
			            // System.out.println("SHAPE  > " + colDefs[j].getName() );
			            SeShape s = row.getShape(j);
			            double[][][] pointArray = s.getAllCoords();
			            if (s.getType() == SeShape.TYPE_POLYGON) {
			            	poly = new Polygon();
			            	int count = pointArray[0][0].length;
			            	// System.out.println("count --> " + count);
			            	for (int i = 0 ; i < count ; i += 2) {
			            		poly.addPoint((int)(pointArray[0][0][i]), (int)(pointArray[0][0][i+1]));
			            	} // for i
			            } else if (s.getType() == SeShape.TYPE_MULTI_POLYGON) {
			              // System.out.println(s);
			            	poly = new Polygon();
			            	/*
			            	System.out.println("parts --> " + s.getNumParts());
			            	for (int k = 0 ; k < s.getNumParts() ; k++) {
			            	  System.out.println("supparts for part " + k + " --> " + s.getNumSubParts(k));
			            	} // for k
			            	*/
			            	int count = pointArray[0][0].length;
			            	// System.out.println("count --> " + count);
			            	for (int i = 0 ; i < count ; i += 2) {
			            		poly.addPoint((int)(pointArray[0][0][i]), (int)(pointArray[0][0][i+1]));
			            	} // for i		              
			            } // if TYPE_MULTI_POLYGON
			            // Call a function to retrieve the shape attributes
			          break;
				      } // switch
				      if (poly != null) {
				    	/*  
				    	 * 
				    	 * 
				      	objectContainer.addElement(new GeoObject(id,type,poly));
				      	poly = null;
				      	*/
				      }
				    } // for j
					} // while row   
				} // for i --> layers
		    System.out.println("... done");
		    m_conn.close();
	    } catch (Exception _e) { _e.printStackTrace(); }
		return objectContainer;
	}
	
	@Override
	public void setCredentials(String _username, String _password) {
		
		m_pass = _password;
		m_user = _username;
		
	}
	@Override
	public boolean checkCredentials(String _username, String _password) {
		try {
			
			 new SeConnection(m_server, 5151, "SDE", _username, _password);
		} catch (SeException e) {
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Vector<GeoObject> typeQuery(List<String> _types) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUsername() {
		return m_user;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return m_pass;
	}
	@Override
	public ArrayList<String> getTypes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String createWhereClause(List<String> _types, Rectangle _area,String _table) {
		// TODO Auto-generated method stub
		return null;
	}

}