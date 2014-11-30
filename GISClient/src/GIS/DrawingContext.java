package GIS;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import GISCalculation.GeoTransformationMatrix;
import GeoObject.GeoObject;
import GeoObject.InterfaceDraw;
import GeoObject.POIObject;



/**
 * calls the methods that draw the objects : polygons, lines, points
 * @author Vlad Herescu
 *
 */
public class DrawingContext {
	
	/**
	 * instance which will save the picture
	 */
	static BufferedImage bufferedImage;
	
	static DrawingPanel m_panel;
	
	static boolean m_imageCreated;
	
	static Color color;
	
	
	/**
	 * initiating the image to be drawn
	 */
	public DrawingContext(DrawingPanel _panel) {
		//bufferedImage = new  BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
		m_panel = _panel;
		m_imageCreated = false;
		color = new Color(0,0,0);
	}

  /**
   * Set the color values for the geo objects based on their type
 * @param _obj : the object to be drawn
 * @param _g : used for drawing the object
 * @param _matrix : used to bring the coordinates of the objects to the ones of the frame
   */
  public static void drawObject(GeoObject _obj, 
  								Graphics  _g, 
  								GeoTransformationMatrix _matrix) {
	int type; 
	
	if(m_imageCreated == false)
	{
	  
		bufferedImage = new  BufferedImage(m_panel.getWidth(),m_panel.getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics graphics = bufferedImage.getGraphics();
		graphics.setColor( color );
		graphics.fillRect ( 0, 0, m_panel.getWidth(), m_panel.getHeight() );
		m_imageCreated = true;


	}
	else
	{
		Graphics graphics = bufferedImage.getGraphics();

	

	
		if(_obj instanceof POIObject)
		{
			System.out.println("obiect");
		
			((POIObject) _obj).paint(graphics, _matrix);
			System.out.println("deseneaza un obiect" + ((POIObject) _obj).m_point.x + " " + ((POIObject) _obj).m_point.y);
			return;
		}
	

		if (_obj != null && _g != null && _matrix != null) 
		{
			type = _obj.getType();


			for(InterfaceDraw draw : _obj._components)
			{	
				draw.multiply(_matrix);

				if(CoreData._hashMapFillColor.get(type) == null)
					draw.setColorObject(graphics, Color.black,null);
  	    	
				draw.setColorObject(graphics, CoreData._hashMapFillColor.get(type), CoreData._hashMapBorderColor.get(type));
			}
  		
			

		}
		
								 
	}
  
  }
  
}


