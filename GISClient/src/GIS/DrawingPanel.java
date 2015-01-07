package GIS;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Vector;

import common.Notifications;
import GISCalculation.GeoDoublePoint;
import GISCalculation.GeoTransformationMatrix;
import GeoObject.GeoObject;
import GeoObject.POIObject;
import Mediator.TypesNotification;


public class DrawingPanel extends Panel {
	
	/// the objects to paint
	private Vector                  m_objects   = null;
	/// the static POIs to paint
	private Vector                  m_pois      = null;	
	/// the transformation matrix
	private GeoTransformationMatrix m_matrix    = null;
	/// the invers transformation matrix
	private GeoTransformationMatrix m_matrixInv = null;
	/// the dpi amount of the screen
	private double m_dotPerInch = 72.0;
	
	
	/**
	 * specifies if the map should be redrawn
	 */
	public boolean m_reDrawMap = true;
	
	
	/**
	 * instance which will paint the map
	 */
	DrawingContext drawing;
	
	/**
	 * The default constructor
	 */
	public DrawingPanel() {
		super();
		setBackground(Color.lightGray);
		drawing = new DrawingContext(this);
		m_reDrawMap  = false;
		
		
	}
	
	/**
	 * The overwritten repaint method.
	 * Enables a more exact control over the repaint mechanism
	 */
	public void repaint() {
		paint(getGraphics());
	}
		
	/**
	 * Provides the objects to paint on the canvas of the component
	 * 
	 * @param _objects a container with the geo objects to paint
	 * @see java.util.Vector
	 */
	public void setGeoObjects(Vector _objects) {
		m_objects = _objects;
	}
	
		
	/**
	 * Initializes the POIs to be shown on the map
	 */
	public void initPOI() {
		if (m_pois == null) {
			m_pois = new Vector();
		  Image        img     = null;
		// load the image representing the poi on the map
	    MediaTracker tracker = new MediaTracker(this);
	    InputStream  in      =
	      //this.getClass().getResourceAsStream("poi_bank.png");
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
					tracker.addImage(img,1);
					tracker.waitForAll();
				} catch (Throwable _e) {
		      _e.printStackTrace();
	      }
	    }
		// this is only for Dummy Server, not for OSM
		// POI position is hardcoded and GaussKrueger coordinates are used (German ones --> DummyServer)
			m_pois.addElement(new POIObject("001", 666, img, new Point(54037055,580450444)));
			m_pois.addElement(new POIObject("002", 666, img, new Point(54038387,580448390)));
			m_pois.addElement(new POIObject("003", 666, img, new Point(54031280,580444115)));
		} else {
			m_pois = null;
		}
	}
	
	/**
	 * Calculate those objects that enclose a given point (in the window coordinate system)
	 * --> were selected by the user through a mouse click on the map
	 *
	 * @param _pt a selection point given in window coordinates
	 * @return an array of objects that contain the point
	 * 
	 * @see java.awt.Point
	 * @see GeoObject
	 */
	public GeoObject[] initSelection(Point _pt) {
		// convert window point to world point, because compared objects are in the world coordinate system
		Point  pt     = m_matrixInv.multiply(_pt);
		Vector result = new Vector();
		for (int i = 0 ; i < m_objects.size() ; i++) {
			GeoObject obj = (GeoObject)m_objects.elementAt(i);
			//if (obj.contains(pt))
			//  result.addElement(obj);
		}
		GeoObject[] r = new GeoObject[result.size()];
		for (int j = 0 ; j < r.length ; j++) {
			r[j] = (GeoObject)result.elementAt(j);
		}
		return r;
	}
	
	/**
	 * Calculates the internal transformation matrix, that transfers all the world coordinates into 
	 * window coordinates, so that ALL geo objects are visible on the canvas. 
	 * Uses scaling, translateion, and mirror aspects
	 */
	public void zoomToFit() {
		if(m_objects.size() == 0)
			return;
		
		Rectangle winBounds = getBounds();
		Rectangle mapBounds = getMapBounds(m_objects);
		if(mapBounds == null)
			System.out.println("este null");
		m_matrix = GeoTransformationMatrix.zoomToFit(mapBounds, winBounds);
		m_matrixInv = m_matrix.invers();
	}
	
	/**
	 * Calculates the internal transformation matrix, that transfers all the world coordinates into 
	 * window coordinates, so that the geo objects that reside in the given rectangle 
	 * are visible in the canvas. 
	 *
	 * @param _mapBounds the map area that should be visible in the canvas
	 */
	public void zoomToFit(Rectangle _mapBounds) {
		
		Rectangle winBounds = getBounds();
		m_matrix = GeoTransformationMatrix.zoomToFit(_mapBounds, winBounds);
		m_matrixInv = m_matrix.invers();
		DrawingContext.m_imageCreated = false;
		
	}
	
	/**
	 * Scales the presented map section to a provided map scale
	 * 
	 * @param _scale The new map scale
	 */
	public void zoomToScale(int _scale) {
		double scale  = calculateScale();
		double factor = scale/_scale;
		zoom(factor);
	}
	
	/**
	 * Scale the map based on a given factor, whereas the anchor point is the centre of the canvas
	 *
	 * @param _factor the factor to zoom in or out of the map
	 */
	public void zoom(double _factor) {
    zoom(new Point(getWidth() >> 1,
                   getHeight() >> 1),
         _factor);
	}
	
	/**
	 * Scale the map based on a given factor, whereas the anchor point is the provided point
	 *
	 * @param _pt the anchor point for the scaling procedure
	 * @param _factor the factor to zoom in or out of the map
	 */
	public void zoom(Point _pt, double _factor) {
		if (m_matrix == null) {
			zoomToFit();
		}
		// Scale at the given point and move the center of the resulting area
		// into the center of the screen/canvas
		m_matrix = GeoTransformationMatrix.zoomToPoint(m_matrix,
		                                               _pt,
		                                               new Point(getWidth()  >> 1,
                                                             getHeight() >> 1),
		                                               _factor);
		m_matrixInv = m_matrix.invers();
	}
		
		
		  
	/**
   * Calculates the scale of the map
   *
   * @param _matrix the currently used transformation matrix
   * @return the current map scale
   * @see GeoTransformationMatrix
   */
  protected int calculateScale() {
	  
	  if(m_objects.size() == 0)
		  return -1;
    // Calculate scale based on GaussKrueger coordinates (i.e. metrical coordinates) ...
    GeoDoublePoint vector             = new GeoDoublePoint(1.0,1.0);
    GeoDoublePoint vector_transformed = m_matrix.multiply(vector);
    // System.out.println("1. --> " + vector.length());
    // System.out.println("2. --> " + vector_transformed.length());
    // double length = vector_transformed.length();
    double length = vector.length() / vector_transformed.length();
    double cmPerInch	= 2.54;
    double dotPerCm 	= m_dotPerInch / cmPerInch; // pixel per cm ...
    double newCanvasScale = length * dotPerCm;
    // m_out.println("map scale	--> " + canvasScale);
    return (int)newCanvasScale;
  }

	/**
	 * Manipulates the interal transformation matrix in a way, that will result 
	 * in a horizontal scroll movment
	 *
	 * @param _delta the distance (in pixel) the map should be moved horizontal
	 */
	public void scrollHorizontal(int _delta) {
		if (m_matrix == null) {
			zoomToFit();
		}
		m_matrix = (GeoTransformationMatrix.translate(_delta,0)).multiply(m_matrix);
		m_matrixInv = m_matrix.invers();
	}
	
	/**
	 * Manipulates the interal transformation matrix in a way, that will result 
	 * in a vertical scroll movment
	 *
	 * @param _delta the distance (in pixel) the map should be moved vertical
	 */
	public void scrollVertical(int _delta) {
		if (m_matrix == null) {
			zoomToFit();
		}
		m_matrix = (GeoTransformationMatrix.translate(0,_delta)).multiply(m_matrix);
		m_matrixInv = m_matrix.invers();
	}
	
	/**
	 * Uses the given point to translate the map in such a way, that afterwards 
	 * the point represents the center of the canvas/screen
	 *
	 * @param _pt the point, that represents the new centre of the map
	 */
	public void translate(Point _pt) {
		int x  = getWidth()  >> 1;
		int y  = getHeight() >> 1;
		int dx = x - _pt.x;
		int dy = y - _pt.y;
		m_matrix = GeoTransformationMatrix.translate(dx,dy).multiply(m_matrix);
		m_matrixInv = m_matrix.invers();
	}
		
	/**
	 * Calculates a map rectangle (world coordinate system) for a given 
	 * window rectangle (window coordinate system)
	 * 
	 * @param _rect a given rectangle in the window coordinate system
	 * @return the same rectangle but in the world coordinate system
	 * @see java.awt.Rectangle
	 */		
	public Rectangle getMapRectangle(Rectangle _rect) { return m_matrixInv.multiply(_rect); }	
		
	/**
	 * Calculates a point (world coordinate system) for a given window point
	 * (window coordinate systems)
	 * 
	 * @param _pt a point in the window coordinate system 
	 * @return the same point converted to the world coordinate system
	 * @see java.awt.Point
	 */		
	public Point getMapPoint(Point _pt) { return m_matrixInv.multiply(_pt); }	
		
	/**
	 * Calculates the minimum bounding box (bbox) of all provided geo objects
	 *
	 * @param _objects the objects the bbox should be calculated for
	 * @return the corresponding bounding box
	 */
	public Rectangle getMapBounds(Vector _objects) {
		Rectangle bounds = null;
		if (_objects != null) {
			for (int i = 0 ; i < _objects.size() ; i++) {
				if (bounds == null) {
					bounds = ((GeoObject)_objects.elementAt(i)).getBoundingBox();
				} else {
					bounds = bounds.union(((GeoObject)_objects.elementAt(i)).getBoundingBox());
				} // if bounds
			} // for i
		}
		return bounds;
	}	
		
	/**
	 * overwrites the default paint mechanism of this Panel representing our canvas.
	 * Everytime paint is called, all the geo objects are drawn in the view based on 
	 * the predefined DrawingContext
	 *
	 * @param _g the current context of the Panel
	 *
	 * @see java.awt.Graphics
	 */
  public void paint(Graphics g) {
	    update(g);
  }
  public void update(Graphics _g) {
	  
	  if (m_objects != null && m_matrix != null) 
	  	{
	  		ArrayList<Notifications> objects = GISComponent.m_notifications.get(TypesNotification.POI_OBJECT);
	 		Image img;
	 		if(m_reDrawMap == true)
	 			_g.clearRect(0, 0, getWidth(), getHeight());
	  		

	  		if(DrawingContext.m_imageCreated == false)
	  		{
	  			for (int i = 0 ; i < m_objects.size() ; i++) 
	  			{
	  				GeoObject obj = (GeoObject)m_objects.elementAt(i);
	  				DrawingContext.drawObject(obj, _g, m_matrix);
	  			}

	  			for(Notifications object :  objects)
	  			{
	  				POIObject poiObject = (POIObject) object;
	  				img = getImage(poiObject);
	  	
	  				poiObject.setM_image(img); 
	  				DrawingContext.drawObject(poiObject,_g,m_matrix);
	  			}
	  			
	  			System.out.println("A desenat totul, ar trebui sa fie ok");
	  			

	  		}
	  	
	  	if(DrawingContext.bufferedImage == null)
	  			System.out.println("1.ESTE NULL");	
	  	if(_g == null)
  			System.out.println("2.ESTE NULL");	
	  	if(this == null)
  			System.out.println("3.ESTE NULL");	
	  
	  	
	  	_g.drawImage(DrawingContext.bufferedImage, 0, 0, this);
	  	
    	
	  	
	 	for(Notifications object :  objects)
		{
				POIObject poiObject = (POIObject) object;
				img = getImage(poiObject);
	
				System.out.println("INTRA IN FORUL ASTA" + " " + poiObject.m_point.x + " " + poiObject.m_point.y);
			//	poiObject.setM_image(img); 
			//	((POIObject) object).paint(_g, m_matrix);
			//	Point pt = m_matrix.multiply(new Point(14311129, 48300493));
				Point pt = m_matrix.multiply(poiObject.m_point);	
				_g.setColor(Color.red);
				_g.fillOval(pt.x, pt.y, 15, 15);
		}
	  	
	  	}
  }
  
  
  /**
   * @param poiObject : the POI to be drawn
   * @return : the image associated to the POI depending on the POI type
   */
  public Image getImage(POIObject poiObject)
  {
	  
	  MediaTracker tracker = new MediaTracker(this);
	  Image img  = null;
      InputStream  in;
	  
	  in = this.getClass().getResourceAsStream(CoreData.m_hashMapTypePOI_NameMarker.get(poiObject.getType()));
	  if (in == null)
		      System.err.println("Image (buddy.gif) not found");
	  else
	  {
		  byte[] buffer = null;
		   try
		   {
				buffer = new byte[in.available()];
				in.read(buffer);
				System.out.println("IMAGinea a fost gasita");
				img = Toolkit.getDefaultToolkit().createImage(buffer);
				tracker.addImage(img,1);
				tracker.waitForAll();
			} 
		   catch (Throwable _e)
		   {
			      _e.printStackTrace();
		   }
	  }
		 
	  return img;
  }
  
}