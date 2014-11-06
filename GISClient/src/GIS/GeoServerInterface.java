package GIS;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import GeoObject.GeoObject;


/**
 * interface implemented by each class which loads data from a server
 * @author Vlad Herescu
 *
 */
public interface GeoServerInterface {
	
	/**used to check if the user has logged in before
	 * @return : the username
	 */
	public String getUsername();
	
	/**
	 * used to check if the user has logged in before
	 * @return : the password
	 */
	public String getPassword();

	
	/**
	 * @param _username : the username used for login
	 * @param _password : the password used for login
	 */
	public void setCredentials(String _username, String _password);
	
	/**
	 * loads the a map depending on the server chosen
	 * @param _types : the types used for filter
	 * @param _area : where the search will be done
	 * @return : a vector of objects (polygons, points, lines) used for drawing the map
	 */
	public Vector<GeoObject> typeAreaQuery(List<String> _types, Rectangle _area);
	
	/**
	 * @param _types : the types used to filter the data
	 * @return : the vector of GeoObject to be shown on the map
	 */
	public Vector<GeoObject> typeQuery(List<String> _types);
	
	/**
	 * tells whether the credentials used for login were correct
	 * @param _username : the _username used for login
	 * @param _password : the password used for login
	 * @return : true if the credentials offered were correct
	 */
	public boolean checkCredentials(String _username, String _password);
	
	
	/**
	 * @return : the types associated to a server
	 */
	public ArrayList<String> getTypes();
	
	/**
	 * @param _types : the types of the objected needed to be retrieved
	 * @param _area : the portion of the map where the data is retrieved
	 * @param _tableName : the name of the table
	 * @return : creates the where clause, based on the list of types received
	 */
	public String createWhereClause(List<String> _types, Rectangle _area, String _tableName);
	
	
}
