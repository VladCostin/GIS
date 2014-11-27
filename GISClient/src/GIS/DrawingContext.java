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
	
	
	/**
	 * initiating the image to be drawn
	 */
	public DrawingContext(DrawingPanel _panel) {
		//bufferedImage = new  BufferedImage(500,500,BufferedImage.TYPE_INT_RGB);
		m_panel = _panel;
		m_imageCreated = false;
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
		m_imageCreated = true;
	}
	
	if(_obj instanceof POIObject)
	{
		System.out.println("obiect");
		Graphics g = bufferedImage.getGraphics();
		((POIObject) _obj).paint(g, _matrix);
		System.out.println("deseneaza un obiect" + ((POIObject) _obj).m_point.x + " " + ((POIObject) _obj).m_point.y);
		return;
	}
	

	
		
	if (_obj != null && _g != null && _matrix != null) 
		{
			type = _obj.getType();
  		
			Graphics g = bufferedImage.getGraphics();

			
  		
			for(InterfaceDraw draw : _obj._components)
			{	
				draw.multiply(_matrix);

				if(CoreData._hashMapFillColor.get(type) == null)
					draw.setColorObject(g, Color.black,null);
  	    	
				draw.setColorObject(g, CoreData._hashMapFillColor.get(type), CoreData._hashMapBorderColor.get(type));
			}
  		
			

		}
		
		System.out.println("mai intra in drawObject????");
		
	/*	_g.drawImage(bufferedImage, 0, 0, m_panel);
	}
	else
	{
		_g.drawImage(bufferedImage, 0, 0, m_panel);
		System.out.println("mai intra in drawObject????");
	}*/
	
	/*  	else
	    	  System.out.println("encountered null object");
	}
	_g.drawImage(bufferedImage, 0, 0, m_panel);*/
	/*else
	{
		_g.drawImage(bufferedImage, 0, 0, m_panel);
	}*/
								 
  }
  
  
  
}


