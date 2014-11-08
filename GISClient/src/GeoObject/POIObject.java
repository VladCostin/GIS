package GeoObject;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Image;
import java.awt.Panel;

import GISCalculation.GeoTransformationMatrix;

/**
 * a rudimentary class to represent point of interst objects
 */
public class POIObject extends GeoObject {


   /**
    * the image representative for the POIObject
 	*/
	Image m_image = null;
  

  /**
   * the location of the image on the map 
   */
	Point m_point = null;
  
  /**
   * Constructor
   * 
   * @param _id the id of the object
   * @param _type the type of the object
   * @param _image the bitmap representing the poi
   * @param _pt the origin/position of the poi
   */
  public POIObject(String _id, int _type, Image _image, Point _pt) {
   super(_id,_type,null);
    m_image = _image;
    m_point = _pt;
   
  }
  
  
  /**
   * doesn't specify the image because it is redundant to use image and type in the same time
  * @param _id : the name of the POI
  * @param _type : the type : school, bank, etc
  * @param _pt :
  */
  public POIObject(String _id, int _type, Point _pt)
  {
	  super(_id, _type, null);
	  m_point = _pt;
	  
  }
  
  
  /**
  * another constructor, don't know its purpose
  * @param _id
  * @param _type
  * @param _nr
  */
  public POIObject(String _id, int _type,int _nr)
  {
	  super(_id,_type, null);
  }
  
  /**
   * Returns the image of the POI
   *
   * @return the image
   *
   * @see java.awt.Image
   */
  public Image getImage() 
  {
	  return m_image; 
  }
    
  
  /**
   * the POI can use the grafic/drawing context and matrix to draw itself
   * @param _g : instance that draws the object
   * @param _matrix : used to fit the object into the screen
   */
  public void paint(Graphics _g, GeoTransformationMatrix _matrix) {

    if (_g != null && _matrix != null) 
    {
    	Point pt = _matrix.multiply(m_point);	
    	_g.drawImage(m_image, pt.x, pt.y, new Panel());
    }
  }


  /**
  * @return the image to be drawn
  */
  public Image getM_image() {
	  return m_image;
  }


  /**
 * @param m_image : the image associated to the POIObject
 */
  public void setM_image(Image m_image) {
	  this.m_image = m_image;
  }
}