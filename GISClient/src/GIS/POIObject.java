package GIS;
import java.awt.Graphics;
import java.awt.MediaTracker;
import java.awt.Polygon;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.io.InputStream;

import GeoObject.GeoObject;

/**
 * a rudimentary class to represent point of interst objects
 */
public class POIObject extends GeoObject {


/// the bitmap representing the POI on a map
  private Image m_image = null;
  
  /// the position of the POI on a map
  private Point m_point = null;
  
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
  
  
  public POIObject(String _id, int _type, Point _pt)
  {
	  super(_id, _type, null);
	  m_point = _pt;
	  
  }
  
  
/*  public POIObject(String _id, int _type) {
		super(_id, _type);
		// TODO Auto-generated constructor stub
 }*/
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
  public Image getImage() { return m_image; }
    
  
  /**
   * the POI can use the grafic/drawing context and matrix to draw itself
   * 
   *
   * @see java.awt.Graphics
   * @see GeoTransformationsmatrix
   */
  public void paint(Graphics _g, GeoTransformationMatrix _matrix) {
	  
	  System.out.println("intra si in paintul din  POIObject2");
	  
	  Image        img     = null;
		// load the image representing the poi on the map
	  
	/*  InputStream  in      =
		       this.getClass().getResourceAsStream("location.gif");
		    if (in == null) {
		      System.err.println("Image (buddy.gif) not found");
		    } else {
		      byte[] buffer = null;
		      try {
						buffer = new byte[in.available()];
						in.read(buffer);
						System.out.println("IMAGinea a fost gasita");
						img = Toolkit.getDefaultToolkit().createImage(buffer);
					} catch (Throwable _e) {
			      _e.printStackTrace();
		      }
		    }
	  */
	  
    if (_g != null && _matrix != null) {
    	
    	System.out.println(m_point.x + " bbbbbbbb " + m_point.y);
    	
    	Point pt = _matrix.multiply(m_point);
    	
    	System.out.println(pt.x + " aaaaaaaaa " + pt.y);
    	
    		
    	
    	//_g.drawImage(img, pt.x, pt.y, new Panel());
    	_g.drawImage(m_image, pt.x, pt.y, new Panel());
    }
  }


public Image getM_image() {
	return m_image;
}


public void setM_image(Image m_image) {
	this.m_image = m_image;
}
}