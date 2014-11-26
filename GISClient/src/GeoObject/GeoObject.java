package GeoObject;

import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.ArrayList;

import common.Notifications;


/**
 * The class is a first very "rudimentary" representation 
 * of a geo object
 */
public class GeoObject implements Notifications{

  /**
 * the id of the object
 */
	protected String  m_id   = null;
  /**
 * the type of the object
 */
  protected int     m_type = 0;
  
  /**
  * the main object containing other small objects
  * it can be a polygon containing other polygons
  */
  public ArrayList<InterfaceDraw> _components;
  
  /**
   * Constructor
   * 
   * @param _id The id of the object
   * @param _type the type of the object
   * @param _poly the geometry of the object
   */
  public GeoObject(String _id, int _type, Polygon _pol) 
  {
    m_id   = _id;
    m_type = _type;
   // this._pol = _pol;
  }
  
  public GeoObject(String _id, int _type)
  {
	  _components = new ArrayList<InterfaceDraw>();
	  m_id = _id;
	  m_type = _type;
  }
  
  public GeoObject()
  {
	  
  }
  
  /**
   * Returns the ID of the Object
   *
   * @return the ID of the Object
   *
   * @see java.lang.String
   */
  public String getId()   { return m_id; }
  
  /**
   * Returns the type (river, wood, ...) of the object
   *
   * @return the type of the object
   */
  public int    getType() { return m_type; }
  
  /**
   * Returns the minimum bounding box of the object
   *
   * @return the minimium bounding box of the object in form of a rectangle
   * @see java.awt.Rectangle
   */
  public Rectangle getBoundingBox() {
	  
	  
	 Rectangle r= _components.get(0).getBounds();
	 for(InterfaceDraw draw : _components)
	 	if(r.contains(draw.getBounds()))
	 		r= draw.getBounds();
	  
  	return r; 
  }
  

}